package com.elasticsearch.data.utils;

/**
 * WordStroke class
 *
 * @author Lasse
 * @date 2018/9/5
 */
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sourceforge.pinyin4j.PinyinHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WordStroke class 对字符的操作
 *
 * @author lasse
 * @date 2016/10/31
 */

@SuppressWarnings("unchecked")
public class WordStroke {

    private static final Logger LOG = LoggerFactory.getLogger(WordStroke.class);

    public static final Map<Integer, String> getMap() {
        return Singleton.map;
    }

    private static String KEY = "FLNET_WORD";

    private static class Singleton {
        private static final Map<Integer, String> map = getMap();

        private static Map<Integer, String> getMap() {
            String filePath ="E:\\cihai.csv";

            if (filePath == null) {
                LOG.error("未找到对应的配置文件");
                return null;
            }
            InputStream inputStream = null;
            try {
                File file = new File(filePath);
                inputStream = new FileInputStream(file);
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                Map<Integer, String> readMap = (Map<Integer, String>) objectInputStream.readObject();
                Map<Integer, String> resultMap = new ConcurrentHashMap<>(32768);
                for(Map.Entry<Integer, String> map: readMap.entrySet()) {
                    resultMap.put(map.getKey(), map.getValue());
                }
                return resultMap;
            } catch (FileNotFoundException e) {
                LOG.error("未找到对应的配置文件", e);
            } catch (ClassNotFoundException e) {
                LOG.error("未找到对应的类文件", e);
            } catch (IOException e) {
                LOG.error("IO异常", e);
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        LOG.error("IO异常", e);
                    }
                }
            }
            return null;
        }
    }


    /**
     * @param code 字符的unicode值
     * @return 字符的笔画
     */
    public static String getStrokeByUnicode(Integer code) {
        return getMap() != null ? getMap().get(code) : null;
    }

    /**
     * @param code 字符的unicode值
     * @return 单个字符第一个笔画
     */
    public static String getFirstStrokeByUnicode(Integer code) {
        if (getStrokeByUnicode(code) == null) {
            return "";
        }
        return getStrokeByUnicode(code).substring(0, 1);
    }

    /**
     * @param word 单个字符
     * @return 返回首个字符的unicode的编码值
     */
    public static Integer getUnicodeByChar(String word) {
        return (int) word.charAt(0);
    }

    /**
     * @param str 一个字符串
     * @return 字符串里面中文字符的unicode编码的List
     */
    public static List<Integer> getUnicodeByString(String str) {
        List<Integer> unicodeList = new ArrayList<>();
        char[] cList = str.toCharArray();
        for (char c : cList) {
            if (isChinese(c)) {
                unicodeList.add((int) c);
            }
        }
        return unicodeList;
    }

    /**
     * @param word 单个字符
     * @return 单个字符笔画串
     */
    public static String getStrokeByChar(String word) {
        return getStrokeByUnicode(getUnicodeByChar(word));
    }

    /**
     * @param word 单个字符
     * @return 单个字符第一个笔画
     */
    public static String getFirstStrokeByChar(String word) {
        return getFirstStrokeByUnicode(getUnicodeByChar(word));
    }

    /**
     * @param c 单个字符
     * @return 是否为中文字符
     */
    public static boolean isChinese(char c) {
        return c >= 0x4E00 && c <= 0x9FA5;
    }

    /**
     * @param str 一个中文字符串
     * @return 里面的中文字符串的首笔画串
     */
    public static String getFirstStrokeByStr(String str) {
        StringBuilder builder = new StringBuilder();
        char[] cList = str.toCharArray();
        for (char c : cList) {
            if (isChinese(c)) {
                builder.append(getFirstStrokeByUnicode((int) c));
            }
        }
        return builder.toString();
    }

    /**
     * @param str 一个字符串
     * @return 过滤掉英文字母跟数字之外的字符
     */
    public static String filterLetterAndNum(String str) {
        StringBuffer buffer = new StringBuffer();
        char[] cList = str.toCharArray();
        for (char c : cList) {
            boolean isNumOrLetter = ('0' <= c && '9' >= c) || ('a' <= c && 'z' >= c);
            if (isNumOrLetter) {
                buffer.append(c);
            }
        }
        return buffer.toString();
    }

    /**
     * @param c 单个字符
     * @return 字符的拼音, 非中文字符返回空字符串
     */
    public static String getLetterOfPinyinOfChineseStr(char c) {
        if (!isChinese(c)) {
            return "";
        }
        String[] strs = PinyinHelper.toHanyuPinyinStringArray(c);
        StringBuffer buffer = new StringBuffer();
        for (String str : strs) {
            str = filterPinyin(str);
            buffer.append(str);
        }
        return buffer.toString();
    }

    /**
     * @param c 单个字符
     * @return 字符的首拼音, 非中文字符返回空字符串
     */
    public static String getFirstLetterOfPinyinOfChineseStr(char c) {
        if (!isChinese(c)) {
            return "";
        }
        String[] strs = PinyinHelper.toHanyuPinyinStringArray(c);
        return filterPinyin(String.valueOf(strs[0].toCharArray()[0]));
    }

    /**
     * @param c c 单个字符
     * @return 字符的拼音, 非中文字符返回原字符
     */
    public static String getLetterOfPinyinOfStr(char c) {
        StringBuffer buffer = new StringBuffer();
        if (!isChinese(c)) {
            buffer.append(c);
            return buffer.toString();
        }
        String[] strs = PinyinHelper.toHanyuPinyinStringArray(c);
        for (String str : strs) {
            str = filterPinyin(str);
            buffer.append(str);
        }
        return buffer.toString();
    }

    /**
     * @param c c 单个字符
     * @return 字符的首拼音, 非中文字符返回原字符
     */
    public static String getFirstLetterOfPinyinOfStr(char c) {
        if (!isChinese(c)) {
            return String.valueOf(c);
        }
        String[] strs = PinyinHelper.toHanyuPinyinStringArray(c);
        return filterPinyin(strs[0]);
    }

    /**
     * @param str 字符串
     * @return 返回字符串拼音的首字母构成的字符串, 非中文字符返回空字符串(首字母)
     */
    public static String getFirstLetterStrOfChineseStr(String str) {
        char[] cList = str.toCharArray();
        StringBuffer buffer = new StringBuffer();
        for (char c : cList) {
            buffer.append(getFirstLetterOfPinyinOfChineseStr(c));
        }
        return buffer.toString();
    }

    /**
     * @param str 字符串
     * @return 返回字符串拼音的首字母构成的字符串, 非中文字符返回原字符(首字母)
     */
    public static String getFirstLetterStrOfStr(String str) {
        char[] cList = str.toCharArray();
        StringBuffer buffer = new StringBuffer();
        for (char c : cList) {
            buffer.append(getFirstLetterOfPinyinOfStr(c));
        }
        return buffer.toString();
    }

    /**
     * @param str 字符串
     * @return 返回字符串拼音构成的字符串, 非中文字符返回空字符串(全拼)
     */
    public static String getLetterStrOfChineseStr(String str) {
        StringBuffer buffer = new StringBuffer();
        char[] cList = str.toCharArray();
        for (char c : cList) {
            buffer.append(getLetterOfPinyinOfChineseStr(c));
        }
        return buffer.toString();
    }

    /**
     * @param str 字符串
     * @return 返回字符串拼音构成的字符串, 非中文字符返回原字符(全拼)
     */
    public static String getLetterStrOfStr(String str) {
        StringBuffer buffer = new StringBuffer();
        char[] cList = str.toCharArray();
        for (char c : cList) {
            buffer.append(getLetterOfPinyinOfStr(c));
        }
        return buffer.toString();
    }

    /**
     * @param str 字符串
     * @return 返回字符串拼音首字母根据九宫格对应的字符串, 非中文字符返回空字符串(九宫格, 电视端)
     */
    public static String getSquareLetterStrOfChineseStrFirstPinyin(String str) {
        String strPinyin = getFirstLetterStrOfChineseStr(str);
        char[] cList = strPinyin.toCharArray();
        StringBuffer buffer = new StringBuffer();
        for (char c : cList) {
            buffer.append(squareNumOfLetter(c));
        }
        return buffer.toString();
    }

    /**
     * @param str 字符串
     * @return 返回字符串拼音首字母根据九宫格对应的字符串, 非中文字符返回原字符对应九宫格(九宫格, 电视端)
     */
    public static String getSquareLetterStrOfStrFirstPinyin(String str) {
        String strPinyin = getFirstLetterStrOfStr(str);
        char[] cList = strPinyin.toCharArray();
        StringBuffer buffer = new StringBuffer();
        for (char c : cList) {
            buffer.append(squareNumOfLetter(c));
        }
        return buffer.toString();
    }

    private static String squareNumOfLetter(char c) {
        char b = Character.toLowerCase(c);
        switch (b) {
            case 'a':
            case 'b':
            case 'c':
                return "2";
            case 'd':
            case 'e':
            case 'f':
                return "3";
            case 'g':
            case 'h':
            case 'i':
                return "4";
            case 'j':
            case 'k':
            case 'l':
                return "5";
            case 'm':
            case 'n':
            case 'o':
                return "6";
            case 'p':
            case 'q':
            case 'r':
            case 's':
                return "7";
            case 't':
            case 'u':
            case 'v':
                return "8";
            case 'w':
            case 'x':
            case 'y':
            case 'z':
                return "9";
            default:
                return "";
        }
    }

    private static String filterPinyin(String str) {
        char[] cList = str.toCharArray();
        StringBuffer buffer = new StringBuffer();
        for (char c : cList) {
            if ('a' <= c && c <= 'z') {
                buffer.append(c);
            }
        }
        return buffer.toString();
    }

}

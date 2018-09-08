package com.elasticsearch.data.service.impl;

import com.elasticsearch.data.dao.DataDao;
import com.elasticsearch.data.entity.Movie;
import com.elasticsearch.data.service.DataAccessService;
import com.elasticsearch.data.service.MovieElasticsearchRepository;
import com.elasticsearch.data.utils.WordStroke;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * DataAccessServiceImpl class
 *
 * @author Lasse
 * @date 2018/9/6
 */
@Service
public class DataAccessServiceImpl implements DataAccessService {

    @Autowired
    private DataDao dataDao;

    @Autowired
    private MovieElasticsearchRepository movieElasticsearchRepository;

    @Override
    public void insertData() {

        FileReader fileReader = null;
        List<Character> wordList = new ArrayList<>(21000);

        List<Movie> movieList = dataDao.queryMovieList();

        try {
            fileReader = new FileReader(new File("E:\\work2\\hanzi2.txt"));
            int fireChar = fileReader.read();
            while (fireChar != -1) {
                char c = (char)fireChar;
                wordList.add(c);
                fireChar = fileReader.read();
            }

            for(int i = 0; i < movieList.size(); i++) {
                Movie movie = null;
                try {
                    movie = movieList.get(i).clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                for(int j = 0; j < wordList.size(); j++) {
                    movie.setTitle(wordList.get(j) + movie.getTitle());

                    movie.setSearchName(WordStroke.getFirstLetterStrOfChineseStr(movie.getTitle()));

                    movie.setTitleStroke(WordStroke.getFirstStrokeByStr(movie.getTitle()));

//                    movieElasticsearchRepository.save(movie);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(movie.toString());
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

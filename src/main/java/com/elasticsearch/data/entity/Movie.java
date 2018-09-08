package com.elasticsearch.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Movie class
 *
 * @author Lasse
 * @date 2018/9/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Document(indexName="flnettvapidemo",type="drama",indexStoreType="fs",shards=5,replicas=1,refreshInterval="1s")
public class Movie {

    /**
     *剧集的唯一id
     */
    @Field(type = FieldType.Text, index = false)
    private String id;
    /**
     * 剧集名称
     */
    @Field(type = FieldType.Text, fielddata = true)
    private String title;

    @Field(type = FieldType.Text, index = false)
    private String titleLong;

    @Field(type = FieldType.Text, index = false)
    private String mYear;

    @Field(type = FieldType.Text, index = false)
    private String classify;

    @Field(type = FieldType.Text, index = false)
    private String mType;

    @Field(type = FieldType.Text, index = false)
    private String country;

    @Field(type = FieldType.Text, index = false)
    private String mLanguage;

    @Field(type = FieldType.Text, index = false)
    private String releaseDate;

    @Field(type = FieldType.Text, index = false)
    private String runtime;

    @Field(type = FieldType.Text, index = false)
    private String alias;

    @Field(type = FieldType.Text, index = false)
    private String tags;

    @Field(type = FieldType.Text, index = false)
    private String report;
    /**
     * 一级分类
     */
    @Field(type = FieldType.Integer, index = false)
    private Integer pcid;

    /**
     * 拼音首字母
     */
    @Field(type = FieldType.Text, fielddata = true)
    private String searchName;

    /**
     * 剧集名称的首笔画
     */
    @Field(type = FieldType.Text, fielddata = true)
    private String titleStroke;

    /**
     * 地区
     */
    @Field(type = FieldType.Text, index = false)
    private String area;

    /**
     * 上映日期
     */
    @Field(type = FieldType.Text, index = false)
    private String year;

    /**
     * 描述
     */
    @Field(type = FieldType.Text, index = false)
    private String desc;

    @Override
    public Movie clone() throws CloneNotSupportedException {
        return (Movie) super.clone();
    }
}

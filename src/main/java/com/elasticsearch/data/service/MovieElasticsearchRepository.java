package com.elasticsearch.data.service;

import com.elasticsearch.data.entity.Movie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * MovieElasticsearchRepository class
 *
 * @author Lasse
 * @date 2018/9/5
 */
public interface MovieElasticsearchRepository extends ElasticsearchRepository<Movie, String> {

    /**
     *  通过剧集的标题查询,已查询字符开头
     *
     * @param str
     * @param pageable
     * @return
     */
    List<Movie> findAllByTitleStartsWith(String str, Pageable pageable);

    /**
     *  通过剧集的标题拼音首字母查询
     *
     * @param str
     * @param pageable
     * @return
     */
    List<Movie> findAllBySearchNameStartsWith(String str, Pageable pageable);

    /**
     *  通过剧集的标题首笔笔画查询
     *
     * @param str
     * @param pageable
     * @return
     */
    List<Movie> findAllByTitleStrokeStartsWith(String str, Pageable pageable);

    /**
     * 通过剧集的标题查询,包含查询
     * @param str
     * @param pageable
     * @return
     */
    List<Movie> findAllByTitle(String str, Pageable pageable);
}

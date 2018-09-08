package com.elasticsearch.data.dao;

import com.elasticsearch.data.entity.Movie;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DataDao class
 *
 * @author Lasse
 * @date 2018/9/6
 */
@Repository
public interface DataDao {

    List<Movie> queryMovieList();
}

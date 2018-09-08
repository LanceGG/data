package com.elasticsearch.data.web;

import com.elasticsearch.data.service.DataAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * DataController class
 *
 * @author Lasse
 * @date 2018/9/6
 */
@Controller
@RequestMapping("/demo")
public class DataController {

    @Autowired
    private DataAccessService dataAccessService;

    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public void queryDramaDetail() {
        dataAccessService.insertData();
    }

}

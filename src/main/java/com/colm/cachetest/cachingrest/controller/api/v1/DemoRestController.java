package com.colm.cachetest.cachingrest.controller.api.v1;


import com.colm.cachetest.cachingrest.model.DemoObject;
import com.colm.cachetest.cachingrest.repository.DemoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/api/v1")
public class DemoRestController {

    @Autowired
    private DemoRepository demoRepository;

    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    public List<DemoObject> listAllDemos() {
        return demoRepository.findAll();
    }
}

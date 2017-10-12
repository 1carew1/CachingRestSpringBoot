package com.colm.cachetest.cachingrest.controller.api.v1;


import com.colm.cachetest.cachingrest.model.DemoObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api/v1")
public class DemoRestController {

    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    public DemoObject obtainRandomBeer() {
        DemoObject demoObject = new DemoObject("HelloRest");
        return demoObject;
    }
}

package com.colm.cachetest.cachingrest.model;

public class DemoObject {

    private String name;

    public DemoObject () {
    }

    public DemoObject (String name) {
        this.name = name;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }
}

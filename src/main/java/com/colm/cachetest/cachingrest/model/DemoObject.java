package com.colm.cachetest.cachingrest.model;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class DemoObject implements Serializable {

    private @Id
    @GeneratedValue
    Long id;

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

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }
}

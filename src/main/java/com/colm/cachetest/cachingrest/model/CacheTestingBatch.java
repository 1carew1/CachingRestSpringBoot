package com.colm.cachetest.cachingrest.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "batch")
public class CacheTestingBatch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "id", updatable = false, nullable = false)
    private Long id;
    private Date startDate = new Date();

    public Long getId() {
        return id;
    }

    public Date getStartDate() {
        return startDate;
    }
}

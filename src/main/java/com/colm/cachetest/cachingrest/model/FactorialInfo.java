package com.colm.cachetest.cachingrest.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table (name = "factorial_info")
public class FactorialInfo implements Serializable {

    @Id
    @Column (unique = true)
    private Long number;
    @Column(columnDefinition="BLOB NOT NULL")
    private String factorial;

    public FactorialInfo () {
    }

    public FactorialInfo (Long number, String factorial) {
        this.number = number;
        this.factorial = factorial;
    }

    public Long getNumber () {
        return number;
    }

    public void setNumber (Long number) {
        this.number = number;
    }

    public String getFactorial () {
        return factorial;
    }

    public void setFactorial (String factorial) {
        this.factorial = factorial;
    }
}

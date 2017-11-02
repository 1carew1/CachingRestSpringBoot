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
    private String factorialString;

    public FactorialInfo () {
    }

    public FactorialInfo (Long number, String factorial) {
        this.number = number;
        this.factorialString = factorial;
    }

    public Long getNumber () {
        return number;
    }

    public void setNumber (Long number) {
        this.number = number;
    }

    public String getFactorial () {
        return factorialString;
    }

    public void setFactorial (String factorial) {
        this.factorialString = factorial;
    }
}

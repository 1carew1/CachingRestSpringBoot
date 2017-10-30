package com.colm.cachetest.cachingrest.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class FactorialInfo {

    @Id
    @Column (unique = true)
    private Long number;
    private BigDecimal factorial;

    public FactorialInfo () {
    }

    public FactorialInfo (Long number, BigDecimal factorial) {
        this.number = number;
        this.factorial = factorial;
    }

    public Long getNumber () {
        return number;
    }

    public void setNumber (Long number) {
        this.number = number;
    }

    public BigDecimal getFactorial () {
        return factorial;
    }

    public void setFactorial (BigDecimal factorial) {
        this.factorial = factorial;
    }
}

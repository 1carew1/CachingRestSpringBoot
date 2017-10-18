package com.colm.cachetest.cachingrest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class CubedInfo implements Serializable {

    @Id
    @Column (unique=true)
    private Long number;
    private BigDecimal numberCubed;

    public CubedInfo () {
    }

    public CubedInfo (Long number, BigDecimal numberCubed) {
        this.number = number;
        this.numberCubed = numberCubed;
    }

    public Long getNumber () {
        return number;
    }

    public void setNumber (Long number) {
        this.number = number;
    }

    public BigDecimal getNumberCubed () {
        return numberCubed;
    }

    public void setNumberCubed (BigDecimal numberCubed) {
        this.numberCubed = numberCubed;
    }
}

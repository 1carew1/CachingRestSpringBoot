package com.colm.cachetest.cachingrest.model;

import java.io.Serializable;

public class LabelWithProbability implements Serializable {
    private String label;
    private float probability;

    public LabelWithProbability (String label, float probability) {
        this.label = label;
        this.probability = probability;
    }

    public String getLabel () {
        return label;
    }

    public void setLabel (String label) {
        this.label = label;
    }

    public float getProbability () {
        return probability;
    }

    public void setProbability (float probability) {
        this.probability = probability;
    }
}

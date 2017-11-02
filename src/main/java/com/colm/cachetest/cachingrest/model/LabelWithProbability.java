package com.colm.cachetest.cachingrest.model;

import lombok.Data;

public class LabelWithProbability {
    private String label;
    private float probability;
    private long elapsed;

    public LabelWithProbability (String s, float v, long l) {
        this.label = s;
        this.probability = v;
        this.elapsed = l;
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

    public long getElapsed () {
        return elapsed;
    }

    public void setElapsed (long elapsed) {
        this.elapsed = elapsed;
    }
}

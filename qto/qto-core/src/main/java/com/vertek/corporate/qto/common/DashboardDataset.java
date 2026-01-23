package com.vertek.corporate.qto.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author rcasey
 * @since 3/25/2024
 * @param <T> The type of data in the dataset. Usually a BigInteger of BigDecimal.
 */
public class DashboardDataset<T> implements Serializable {
    @JsonProperty("labels")
    private List<String> labels;
    @JsonProperty("data")
    private List<T> data;

    public DashboardDataset(final List<String> labels, final List<T> data) {
        this.labels = labels;
        this.data = data;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(final List<String> labels) {
        this.labels = labels;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(final List<T> data) {
        this.data = data;
    }
}

package com.vertek.corporate.qto.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class OrderCreateCompany implements Serializable {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;

    public OrderCreateCompany() {}

    public OrderCreateCompany(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}

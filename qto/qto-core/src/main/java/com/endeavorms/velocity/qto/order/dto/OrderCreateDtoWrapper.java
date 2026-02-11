package com.endeavorms.velocity.qto.order.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper class for passing a list of OrderCreateDto objects to the front end.
 * @author rcasey
 * @since 3/11/2023
 */
public class OrderCreateDtoWrapper implements Serializable {
    private List<OrderCreateDto> dtoList = new ArrayList<>();

    public List<OrderCreateDto> getDtoList() {
        return dtoList;
    }

    public void setDtoList(final List<OrderCreateDto> dtoList) {
        this.dtoList = dtoList;
    }
}

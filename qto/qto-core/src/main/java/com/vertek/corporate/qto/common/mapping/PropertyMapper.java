package com.vertek.corporate.qto.common.mapping;

public interface PropertyMapper {
    String getValue() throws Exception;

    void setPreMapper(PropertyMapper var1);
}
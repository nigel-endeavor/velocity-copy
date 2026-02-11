package com.endeavorms.velocity.qto.customfield.value;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class ServiceCustomFieldValueListDto implements Serializable {

    @JsonProperty("values")
    private List<ServiceCustomFieldValue> values;

    public List<ServiceCustomFieldValue> getValues() {
        return values;
    }

    public void setValues(final List<ServiceCustomFieldValue> values) {
        this.values = values;
    }

}

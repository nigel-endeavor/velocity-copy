package com.endeavorms.velocity.qto.customfield.value;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class LocationCustomFieldValueListDto implements Serializable {

    @JsonProperty("values")
    private List<LocationCustomFieldValue> values;

    public List<LocationCustomFieldValue> getValues() {
        return values;
    }

    public void setValues(final List<LocationCustomFieldValue> values) {
        this.values = values;
    }

}

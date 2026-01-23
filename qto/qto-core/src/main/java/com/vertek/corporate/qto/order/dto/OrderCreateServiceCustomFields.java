package com.vertek.corporate.qto.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class OrderCreateServiceCustomFields implements Serializable {

        @JsonProperty("customFieldId")
        private Long customFieldId;

        @JsonProperty("value")
        private String value;

        public Long getCustomFieldId() {
                return customFieldId;
        }

        public void setCustomFieldId(final Long customFieldId) {
                this.customFieldId = customFieldId;
        }

        public String getValue() {
                return value;
        }

        public void setValue(final String value) {
                this.value = value;
        }
}

package com.vertek.corporate.qto.note;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author llevit
 * @since 1/16/2023
 */
@Entity
@Table(name = "jeop_instance_note")
@JsonIgnoreProperties(ignoreUnknown = true)
public class JeopNote extends Note {

    @Column(name = "jeop_instance_id")
    private Long jeopInstanceId;

    public Long getJeopInstanceId() {
        return jeopInstanceId;
    }

    public void setJeopInstanceId(final Long jeopInstanceId) {
        this.jeopInstanceId = jeopInstanceId;
    }
}

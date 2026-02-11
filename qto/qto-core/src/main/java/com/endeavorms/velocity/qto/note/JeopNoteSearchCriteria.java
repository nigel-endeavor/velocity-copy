package com.endeavorms.velocity.qto.note;

import com.endeavorms.velocity.qto.common.BaseSearchCriteria;

import jakarta.ws.rs.QueryParam;

public class JeopNoteSearchCriteria extends BaseSearchCriteria<JeopNote> {

    @QueryParam("jeopInstanceId")
    private Long jeopInstanceId;

    public Long getJeopInstanceId() {
        return jeopInstanceId;
    }

    public void setJeopInstanceId(final Long jeopInstanceId) {
        this.jeopInstanceId = jeopInstanceId;
    }
}

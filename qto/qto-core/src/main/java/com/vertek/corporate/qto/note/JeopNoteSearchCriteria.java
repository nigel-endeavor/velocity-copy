package com.vertek.corporate.qto.note;

import com.vertek.corporate.qto.common.BaseSearchCriteria;

import javax.ws.rs.QueryParam;

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

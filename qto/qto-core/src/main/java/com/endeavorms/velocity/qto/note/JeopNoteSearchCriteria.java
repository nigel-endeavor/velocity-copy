package com.endeavorms.velocity.qto.note;

import com.endeavorms.velocity.qto.common.BaseSearchCriteria;


public class JeopNoteSearchCriteria extends BaseSearchCriteria<JeopNote> {

    private Long jeopInstanceId;

    public Long getJeopInstanceId() {
        return jeopInstanceId;
    }

    public void setJeopInstanceId(final Long jeopInstanceId) {
        this.jeopInstanceId = jeopInstanceId;
    }
}

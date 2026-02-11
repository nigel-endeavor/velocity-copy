package com.endeavorms.velocity.qto.message;

import com.endeavorms.velocity.qto.common.StandardVersionedBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @author rcasey
 * @since 4/27/2023
 */
@Entity
@Table(name = "message_thread_watcher")
public class MessageThreadWatcher extends StandardVersionedBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_thread_watcher_id")
    private Long id;

    @Column(name = "message_thread_id")
    private Long messageThreadId;

    @Column(name = "subject_id")
    private Long subjectId;

    @Override
    public Long getId() {
        return id;
    }

    public Long getMessageThreadId() {
            return messageThreadId;
        }

    public void setMessageThreadId(final Long messageThreadId) {
        this.messageThreadId = messageThreadId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(final Long subjectId) {
        this.subjectId = subjectId;
    }
}

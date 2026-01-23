package com.vertek.corporate.qto.attachment;

import com.vertek.corporate.qto.common.StandardVersionedBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * @author rcasey
 * @since 1/24/2023
 */
@Entity
@Table(name = "file_attachment_content")
public class FileAttachmentContent extends StandardVersionedBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_attachment_content_id")
    private Long id;

    @Lob
    @Column(name = "file_embed")
    private byte[] data;

    public FileAttachmentContent() {
    }

    public FileAttachmentContent(final byte[] data) {
        this.data = data;
    }

    @Override
    public Long getId() {
        return id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(final byte[] data) {
        this.data = data;
    }
}

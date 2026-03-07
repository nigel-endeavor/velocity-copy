package com.vertek.corporate.qto.attachment;

import com.vertek.corporate.qto.common.StandardVersionedBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

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

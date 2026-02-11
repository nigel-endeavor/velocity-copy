package com.endeavorms.velocity.qto.common;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Indicates what permissions the user has for handling file attachments.
 */
@JacksonXmlRootElement
public class FileAttachmentPermissions {

    /** User can attach files. */
    private boolean attach;

    /** User can view file attachments. */
    private boolean view;

    /** User can delete file attachments. */
    private boolean delete;

    public boolean getAttach() {
        return attach;
    }

    public void setAttach(final boolean attach) {
        this.attach = attach;
    }

    public boolean getView() {
        return view;
    }

    public void setView(final boolean view) {
        this.view = view;
    }

    public boolean getDelete() {
        return delete;
    }

    public void setDelete(final boolean delete) {
        this.delete = delete;
    }
}
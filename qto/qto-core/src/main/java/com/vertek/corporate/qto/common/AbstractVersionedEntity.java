package com.vertek.corporate.qto.common;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.io.Serializable;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * From vertek-commons.
 * @param <KeyType>
 */
@MappedSuperclass
public abstract class AbstractVersionedEntity<KeyType extends Serializable> extends AbstractBaseEntity<KeyType> implements VersionedBaseEntity<KeyType> {
    @Version
    @JacksonXmlProperty(
            isAttribute = true
    )
    protected Integer version;

    public AbstractVersionedEntity() {
    }

    public Integer getVersion() {
        return this.version;
    }

    private void setVersion(Integer var1) {
        this.version = var1;
    }
}

package com.vertek.corporate.qto.milestone;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.vertek.corporate.qto.common.StandardVersionedBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/** Models a Milestone.
 * @author rconnolly
 * @since 1.0.0 */
@Entity
@JacksonXmlRootElement
@Table(name = "milestone")
public class Milestone extends StandardVersionedBaseEntity {

    /** serial version. */
    private static final long serialVersionUID = -7772386247257960532L;

    /** System identifier. */
    @Id
    @JacksonXmlProperty(isAttribute = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "milestone_id")
    private Long id;

    @Column(name = "milestone_name")
    private String name;

    /** The code. */
    @Column(name = "milestone_code")
    private String code;

    @Override
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }
}
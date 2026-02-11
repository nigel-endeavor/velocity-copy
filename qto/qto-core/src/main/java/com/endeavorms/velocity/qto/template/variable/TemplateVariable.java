package com.endeavorms.velocity.qto.template.variable;

import com.endeavorms.velocity.qto.common.StandardVersionedBaseEntity;
import com.endeavorms.velocity.qto.template.TemplateType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Defines a variable that can be used in a template.
 */
@Entity
@Table(name = "template_variable")
public class TemplateVariable extends StandardVersionedBaseEntity {
    /** The ID. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "template_variable_id")
    private Long id;
    /** The human-readable label for the variable as it is presented in the UI normally. */
    @Column(name = "label")
    private String label;
    /** The path to the variable for use in the template. */
    @Column(name = "path")
    private String path;
    /** The type of the variable (string, date, etc.). */
    @Column(name = "type")
    private String type;
    /** The type of template. */
    @Enumerated(EnumType.STRING)
    @Column(name = "template_type")
    private TemplateType templateType;

    @Override
    public Long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public String getPath() {
        return path;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public TemplateType getTemplateType() {
        return templateType;
    }

    public void setTemplateType(final TemplateType templateType) {
        this.templateType = templateType;
    }
}

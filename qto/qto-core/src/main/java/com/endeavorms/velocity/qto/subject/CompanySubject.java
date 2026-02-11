package com.endeavorms.velocity.qto.subject;

import com.endeavorms.velocity.qto.common.StandardVersionedBaseEntity;
import com.endeavorms.velocity.qto.company.Company;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * @author rcasey
 * @since 10/12/2023
 */
@Entity
@Table(name = "company_subject")
public class CompanySubject extends StandardVersionedBaseEntity {

    /** Unique ID. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_subject_id")
    private Long id;

    /** Tenant. */
    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    protected Company company;

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "subject_id")
    private SubjectView subject;

    /** Flag to indicate whether this is the selected company for the user. */
    @Column(name = "company_subject_selected")
    private Boolean isSelected;

    @Override
    public Long getId() {
        return id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(final Company company) {
        this.company = company;
    }

    public SubjectView getSubject() {
        return subject;
    }

    public void setSubject(final SubjectView subject) {
        this.subject = subject;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(final Boolean selected) {
        isSelected = selected;
    }
}

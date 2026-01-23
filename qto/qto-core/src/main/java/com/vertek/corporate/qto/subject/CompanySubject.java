package com.vertek.corporate.qto.subject;

import com.vertek.corporate.qto.common.StandardVersionedBaseEntity;
import com.vertek.corporate.qto.company.Company;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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

package com.endeavorms.velocity.qto.contact;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractJpaDao;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.endeavorms.velocity.qto.contact.QContact.contact;
import static com.endeavorms.velocity.qto.contact.location.QLocationContact.locationContact;
import static com.endeavorms.velocity.qto.contact.order.QOrderContact.orderContact;

/**
 * @author rcasey
 * @since 1/20/2023
 */
@Component
public class ContactJpaDao extends AbstractJpaDao<Contact, Long> {
    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Contact findByCompanyIdAndType(final Long companyId, final ContactType type) {
        return new JPAQuery<Contact>(entityManager)
                .from(contact)
                .where(
                        contact.companyId.eq(companyId)
                        .and(contact.type.eq(type))
                        .and(contact.id.notIn(
                                JPAExpressions.select(contact.id)
                                        .from(contact)
                                        .join(locationContact).on(contact.id.eq(locationContact.id))
                                        .where(contact.companyId.eq(companyId))

                        ))
                        .and(contact.id.notIn(
                                JPAExpressions.select(contact.id)
                                        .from(contact)
                                        .join(orderContact).on(contact.id.eq(orderContact.id))
                                        .where(contact.companyId.eq(companyId))
                        ))
                )
                .fetchOne();
    }

    public List<Contact> findCompanyContacts(Long companyId) {
         return new JPAQuery<Contact>(entityManager)
                .from(contact)
                .where(
                        contact.companyId.eq(companyId)
                        .and(contact.type.in(ContactType.BILLING, ContactType.TECH, ContactType.SALES, ContactType.AUTHORIZATION))
                        .and(contact.id.notIn(
                                JPAExpressions.select(contact.id)
                                        .from(contact)
                                        .join(locationContact).on(contact.id.eq(locationContact.id))
                                        .where(contact.companyId.eq(companyId))

                        ))
                        .and(contact.id.notIn(
                                JPAExpressions.select(contact.id)
                                        .from(contact)
                                        .join(orderContact).on(contact.id.eq(orderContact.id))
                                        .where(contact.companyId.eq(companyId))
                        ))
                )
                .fetch();
    }
}

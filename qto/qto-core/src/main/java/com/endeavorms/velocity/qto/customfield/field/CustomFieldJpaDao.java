package com.endeavorms.velocity.qto.customfield.field;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractMultitenantJpaDao;
import com.endeavorms.velocity.qto.common.PlatformDatabase;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

import static com.endeavorms.velocity.qto.customfield.field.QCustomField.customField;
import static com.endeavorms.velocity.qto.customfield.field.QCustomFieldTab.customFieldTab;

@Component
public class CustomFieldJpaDao extends AbstractMultitenantJpaDao<CustomField, Long> {

    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Inject
    protected void setPlatformEntityManager(@PlatformDatabase final EntityManager platformEntityManager) {
        this.platformEntityManager = platformEntityManager;
    }

    public List<CustomField> findActiveByTab(final CustomFieldTabValue tab) {
        BooleanExpression expression = customField.active.isTrue()
                .and(customFieldTab.tab.eq(tab));
        expression = addTenantFilter(expression, customField.tenantId, false);
        return new JPAQuery<CustomField>(entityManager)
                .from(customField)
                .join(customField.tabs, customFieldTab)
                .where(expression)
                .fetch();
    }

    public CustomField findByNameAndTenant(String name, Long tenantId) {
        return new JPAQuery<CustomField>(entityManager)
                .from(customField)
                .where(customField.name.eq(name)
                        .and(customField.tenantId.eq(tenantId)))
                .fetchOne();
    }
}

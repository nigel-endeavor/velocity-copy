package com.vertek.corporate.qto.common;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.vertek.corporate.qto.common.QVisibleTenant is a Querydsl Projection type for VisibleTenant
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QVisibleTenant extends ConstructorExpression<VisibleTenant> {

    private static final long serialVersionUID = -410343474L;

    public QVisibleTenant(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<Boolean> active) {
        super(VisibleTenant.class, new Class<?>[]{long.class, String.class, boolean.class}, id, name, active);
    }

}


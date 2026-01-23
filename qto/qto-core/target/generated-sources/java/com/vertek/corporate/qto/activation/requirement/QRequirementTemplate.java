package com.vertek.corporate.qto.activation.requirement;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRequirementTemplate is a Querydsl query type for RequirementTemplate
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRequirementTemplate extends EntityPathBase<RequirementTemplate> {

    private static final long serialVersionUID = 798094319L;

    public static final QRequirementTemplate requirementTemplate = new QRequirementTemplate("requirementTemplate");

    public final com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity(this);

    public final BooleanPath active = createBoolean("active");

    public final NumberPath<Long> companyId = createNumber("companyId", Long.class);

    public final BooleanPath global = createBoolean("global");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDefault = createBoolean("isDefault");

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    public final StringPath name = createString("name");

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final ListPath<Requirement, QRequirement> requirements = this.<Requirement, QRequirement>createList("requirements", Requirement.class, QRequirement.class, PathInits.DIRECT2);

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    public final NumberPath<java.math.BigDecimal> ttuEquivalent = createNumber("ttuEquivalent", java.math.BigDecimal.class);

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QRequirementTemplate(String variable) {
        super(RequirementTemplate.class, forVariable(variable));
    }

    public QRequirementTemplate(Path<? extends RequirementTemplate> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRequirementTemplate(PathMetadata metadata) {
        super(RequirementTemplate.class, metadata);
    }

}


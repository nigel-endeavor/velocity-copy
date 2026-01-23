package com.vertek.corporate.qto.common;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.ws.rs.NotFoundException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public abstract class AbstractJpaDao<T extends BaseEntity<KeyType>, KeyType extends Serializable> implements GenericDao<T, KeyType> {
    public static final String INVALID_OFFSET = "Invalid query offset";
    protected EntityManager entityManager;
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractJpaDao.class);

    public AbstractJpaDao() {
    }

    protected abstract void setEntityManager(EntityManager var1);

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    public Class<T> getEntityClass() {
        return this.getEntityType(this.getClass());
    }

    private Class<T> getEntityType(Class var1) {
        Class var2 = null;
        Type var3 = var1.getGenericSuperclass();
        if (var3 instanceof ParameterizedType) {
            ParameterizedType var4 = (ParameterizedType)var1.getGenericSuperclass();
            var2 = (Class)var4.getActualTypeArguments()[0];
        } else {
            var2 = this.getEntityType(var1.getSuperclass());
        }

        return var2;
    }

    public T create(T var1) {
        Preconditions.checkArgument(var1 != null, "An entity is required");
        this.entityManager.persist(var1);
        this.entityManager.flush();
        this.entityManager.refresh(var1);
        return var1;
    }

    public T edit(T var1) {
        Preconditions.checkArgument(var1 != null, "An entity is required");
        T var2 = this.entityManager.merge(var1);
        this.entityManager.flush();
        return var2;
    }

    /** @deprecated */
    @Deprecated
    public T save(T var1) {
        Preconditions.checkArgument(var1 != null, "An entity is required");
        return var1.isNew() ? this.create(var1) : this.edit(var1);
    }

    public void remove(KeyType var1) {
        Preconditions.checkArgument(var1 != null, "An identifier is required");
        T var2 = this.retrieve(var1);
        if (var2 == null) {
            throw new NotFoundException((String) var1);
        } else {
            this.remove(var2);
        }
    }

    public void remove(T var1) {
        Preconditions.checkArgument(var1 != null, "An entity is required");
        this.entityManager.remove(var1);
    }

    public T retrieve(KeyType var1) {
        Preconditions.checkArgument(var1 != null, "An id is required");
        return this.entityManager.find(this.getEntityClass(), var1);
    }

    public PaginatedResult<T> list(int var1, int var2) {
        Preconditions.checkArgument(var1 >= 0, "Invalid query offset");
        Query var3 = this.entityManager.createQuery("select count(t.id) from " + this.getEntityClass().getSimpleName() + " as t");
        Long var4 = (Long)var3.getSingleResult();
        Query var5 = this.entityManager.createQuery("select t from " + this.getEntityClass().getSimpleName() + " as t");
        var5.setFirstResult(var1);
        var5.setMaxResults(var2);
        PaginatedResult var6 = new PaginatedResult();
        var6.setOffset(var1);
        var6.setLimit(var2);
        var6.setTotal(var4.intValue());
        var6.setCollection(var5.getResultList());
        return var6;
    }

    protected BooleanExpression createContainsExpression(StringPath var1, String var2) {
        String var3 = var2 != null ? var2.toLowerCase(Locale.getDefault()) : "";
        return var1.lower().like("%" + var3 + "%");
    }

    protected BooleanExpression getContainsExpression(BooleanExpression var1, StringPath var2, String var3) {
        BooleanExpression var4 = var1;
        if (!Strings.isNullOrEmpty(var3)) {
            if ("<NULL>".equals(var3)) {
                var4 = var1.and(var2.isNull());
            } else {
                var4 = var1.and(this.createContainsExpression(var2, var3));
            }
        }

        return var4;
    }

    protected BooleanExpression getContainsExpression(final BooleanExpression expression, final StringPath path, final List<String> values) {
        if (!values.isEmpty()) {
            BooleanBuilder containsExp = new BooleanBuilder();
            for (String value : values) {
                if ("ISEMPTY".equals(value)) {
                    containsExp = containsExp.or(path.isNull().or(path.eq("")));
                } else {
                    containsExp = containsExp.or(this.createContainsExpression(path, value));
                }
            }
            return expression.and(containsExp);
        }
        return expression;
    }

    protected BooleanExpression createStartsWithExpression(StringPath var1, String var2) {
        String var3 = var2 != null ? var2.toLowerCase(Locale.getDefault()) : "";
        return var1.lower().like(var3 + "%");
    }

    /**
     * Returns a BooleanExpression that generates a in expression for the given path and values and support ISEMPTY.
     * @param expression the current expression.
     * @param path the path to be used in the in expression.
     * @param values the values to be used in the in expression.
     * @return the updated expression.
     */
    protected BooleanExpression getInExpression(final BooleanExpression expression, final StringPath path, final List<String> values) {
        if (!values.isEmpty()) {
            LinkedList<String> valuesCopy = new LinkedList<>(values);
            BooleanBuilder inExp = new BooleanBuilder();
            boolean hasIsEmpty = valuesCopy.contains("ISEMPTY");
            if (hasIsEmpty) {
                valuesCopy.remove("ISEMPTY");
            }
            if (hasIsEmpty) {
                if (valuesCopy.isEmpty()) {
                    inExp = inExp.or(path.isNull().or(path.eq("")));
                } else {
                    inExp = inExp.or(path.in(valuesCopy).or(path.isNull().or(path.eq(""))));
                }
            } else {
                inExp = inExp.or(path.in(valuesCopy));
            }
            return expression.and(inExp);
        }
        return expression;
    }

    /**
     * Returns a BooleanExpression that generates a in expression for the given path and values and support ISEMPTY.
     * @param expression the current expression.
     * @param path the path to be used in the in expression.
     * @param values the values to be used in the in expression, expected to be a comma separated string.
     * @return the updated expression.
     */
    protected BooleanExpression getInExpression(BooleanExpression var1, StringPath var2, String var3) {
        BooleanExpression var4 = var1;
        if (!Strings.isNullOrEmpty(var3)) {
            String[] var5 = var3.split(",");
            var4 = this.getInExpression(var1, var2, Lists.newArrayList(var5));
        }
        return var4;
    }

    protected BooleanExpression getStartsWithExpression(BooleanExpression var1, StringPath var2, String var3) {
        BooleanExpression var4 = var1;
        if (!Strings.isNullOrEmpty(var3)) {
            var4 = var1.and(var2.startsWith(var3));
        }

        return var4;
    }

    protected BooleanExpression getDateComparisonExpression(BooleanExpression var1, DateTimeExpression<Date> var2,
                                                            List<Date> var3, List<DateRangeType> var4) {
        return getDateComparisonExpression(var1, var2, var3, var4, false);
    }

    protected BooleanExpression getDateComparisonExpression(BooleanExpression var1, DateTimeExpression<Date> var2,
                                                            List<Date> var3, List<DateRangeType> var4,
                                                            boolean stripTime) {
        BooleanExpression var5 = var1;
        List<Date> dateList = new ArrayList<>(var3);
        if (var4 != null && var4.contains(DateRangeType.ISEMPTY)) {
            int index = var4.indexOf(DateRangeType.ISEMPTY);
            if (dateList.size() < var4.size()) {
                //insert a value into dateList at same index as DateRangeType.ISEMPTY
                dateList.add(index, null);
            }
        }
        if (dateList != null && !dateList.isEmpty() && var4 != null && !var4.isEmpty()) {
            BooleanExpression var6 = null;
            BooleanExpression var7 = null;

            for(int var8 = 0; var8 < var4.size(); ++var8) {
                DateRangeType var9 = var4.get(var8);
                Calendar var10 = Calendar.getInstance();
                Date var11 = null;
                if (!DateRangeType.ISEMPTY.equals(var9)) {
                    var11 = dateList.get(var8);
                    // Strip time part from var11
                    if (stripTime) {
                        var11 = stripTime(var11);
                    }
                }

                switch (var9) {
                    case BEFORE:
                        BooleanExpression var12 = var2.lt(var11);
                        var6 = var6 == null ? var12 : var6.and(var12);
                        break;
                    case AFTER:
                        var10.setTime(var11);
                        BooleanExpression var13 = var2.goe(var10.getTime());
                        var6 = var6 == null ? var13 : var6.and(var13);
                        break;
                    case ON:
                        var10.setTime(var11);
                        var10.add(5, 1);
                        BooleanExpression var14 = var2.goe(var11);
                        BooleanExpression var15 = var2.lt(var10.getTime());
                        var6 = var6 == null ? var14 : var6.and(var14);
                        var6 = var6.and(var15);
                        break;
                    case ISEMPTY:
                        var7 = var2.isNull();
                }
            }

            if (var6 != null) {
                if (var7 != null) {
                    var6 = var6.or(var7);
                }

                var5 = var1.and(var6);
            } else if (var7 != null) {
                var5 = var1.and(var7);
            }
        }

        return var5;
    }

    private Date stripTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /** @deprecated */
    @Deprecated
    protected BooleanExpression getNumberComparisonExpression(BooleanExpression var1, NumberExpression<Long> var2, Integer var3, RangeType var4) {
        BooleanExpression var5 = var1;
        if (var3 != null && var4 != null) {
            BooleanExpression var6;
            switch (var4) {
                case LT:
                    var6 = var2.lt(var3);
                    break;
                case GT:
                    var6 = var2.gt(var3);
                    break;
                default:
                    var6 = var2.eq((long)var3);
            }

            var5 = var1.and(var6);
        }

        return var5;
    }

    protected BooleanExpression getStringComparisonExpression(BooleanExpression var1, StringExpression var2, String var3, StringRangeType var4) {
        BooleanExpression var5 = var1;
        if (var3 != null && var4 != null) {
            switch (var4) {
                case CONTAINS:
                    var5 = var1.and(var2.contains(var3));
                    break;
                case STARTS_WITH:
                    var5 = var1.and(var2.startsWith(var3));
                    break;
                case ENDS_WITH:
                    var5 = var1.and(var2.endsWith(var3));
                    break;
                case EQUALS:
                    var5 = var1.and(var2.eq(var3));
                    break;
                default:
                    var5 = var1.and(var2.eq(var3));
            }
        }

        return var5;
    }

    protected BooleanExpression getStringComparisonExpression(BooleanExpression var1, StringExpression var2, List<String> var3, List<StringRangeType> var4) {
        BooleanExpression var5 = var1;
        if (var3 != null && var4 != null && var3.size() > 0 && var4.size() > 0) {
            for(int var6 = 0; var6 < var3.size(); ++var6) {
                StringRangeType var7 = (StringRangeType)var4.get(var6);
                String var8 = (String)var3.get(var6);
                var5 = this.getStringComparisonExpression(var5, var2, var8, var7);
            }
        }

        return var5;
    }

    /** @deprecated */
    @Deprecated
    protected BooleanExpression getIntegerNumberComparisonExpression(BooleanExpression var1, NumberExpression var2, List<Integer> var3, List<RangeType> var4) {
        BooleanExpression var5 = var1;
        if (var3 != null && var4 != null && var3.size() > 0 && var4.size() > 0) {
            for(int var6 = 0; var6 < var3.size(); ++var6) {
                RangeType var7 = (RangeType)var4.get(var6);
                Integer var8 = (Integer)var3.get(var6);
                switch (var7) {
                    case LT:
                        var5 = var5.and(var2.lt(var8));
                        break;
                    case GT:
                        var5 = var5.and(var2.gt(var8));
                        break;
                    default:
                        var5 = var5.and(var2.eq(var8));
                }
            }
        }

        return var5;
    }

    /** @deprecated */
    @Deprecated
    protected BooleanExpression getNumberComparisonExpression(BooleanExpression var1, NumberExpression var2, List<Long> var3, List<RangeType> var4) {
        BooleanExpression var5 = var1;
        if (var3 != null && var4 != null && var3.size() > 0 && var4.size() > 0) {
            for(int var6 = 0; var6 < var3.size(); ++var6) {
                RangeType var7 = (RangeType)var4.get(var6);
                Long var8 = (Long)var3.get(var6);
                switch (var7) {
                    case LT:
                        var5 = var5.and(var2.lt(var8));
                        break;
                    case GT:
                        var5 = var5.and(var2.gt(var8));
                        break;
                    default:
                        var5 = var5.and(var2.eq(var8));
                }
            }
        }

        return var5;
    }

    protected <A extends Number & Comparable<?>> BooleanExpression getNumericComparisonExpression(BooleanExpression var1, NumberExpression var2, List<A> var3, List<RangeType> var4) {
        BooleanExpression var5 = var1;
        List<Number> numberList = new ArrayList<>(var3);
        List<RangeType> rangeTypeList = new ArrayList<>(var4);
        //if rangeTypeList contains RangeType.ISEMPTY, then numberList must contain a null value
        if (rangeTypeList.contains(RangeType.ISEMPTY) && (rangeTypeList.size() - numberList.size() == 1)) {
            //insert a value into numberList at same index as RangeType.ISEMPTY
            int index = rangeTypeList.indexOf(RangeType.ISEMPTY);
            numberList.add(index, null);
        }
        //assume single value without comparison is an equals
        if (numberList.size() == 1 && rangeTypeList.isEmpty()) {
            rangeTypeList.add(RangeType.EQ);
        }
        if (numberList.size() == rangeTypeList.size()) {
            for(int var6 = 0; var6 < numberList.size(); ++var6) {
                RangeType var7 = rangeTypeList.get(var6);
                Number var8 = numberList.get(var6);
                switch (var7) {
                    case LT:
                        var5 = var5.and(var2.lt(var8));
                        break;
                    case GT:
                        var5 = var5.and(var2.gt(var8));
                        break;
                    case ISEMPTY:
                        var5 = var5.and(var2.isNull().or(var2.eq(0)));
                        break;
                    default:
                        var5 = var5.and(var2.eq(var8));
                }
            }
        } else if (numberList.size() != rangeTypeList.size()) {
            throw new IllegalArgumentException("Size of numbers collection should match size of range types collection.");
        }

        return var5;
    }

    /** @deprecated */
    @Deprecated
    protected BooleanExpression getDecimalNumberComparisonExpression(BooleanExpression var1, NumberExpression<BigDecimal> var2, List<BigDecimal> var3, List<RangeType> var4) {
        BooleanExpression var5 = var1;
        if (var3 != null && var4 != null && var3.size() > 0 && var4.size() > 0) {
            for(int var6 = 0; var6 < var3.size(); ++var6) {
                RangeType var7 = (RangeType)var4.get(var6);
                BigDecimal var8 = (BigDecimal)var3.get(var6);
                switch (var7) {
                    case LT:
                        var5 = var5.and(var2.lt(var8));
                        break;
                    case GT:
                        var5 = var5.and(var2.gt(var8));
                        break;
                    default:
                        var5 = var5.and(var2.eq(var8));
                }
            }
        }

        return var5;
    }

    public BooleanExpression getNumberComparisonExpression(BooleanExpression var1, NumberPath<Long> var2, String var3, String var4) {
        return this.getNumericComparisonExpression(var1, var2, this.getLongList(var3), this.getRangeTypeList(var4));
    }

    public BooleanExpression getDateComparisonExpression(BooleanExpression var1, DateTimeExpression<Date> var2, String var3, String var4) {
        return this.getDateComparisonExpression(var1, var2, this.getDateList(var3), this.getDateRangeTypeList(var4));
    }

    public List<RangeType> getRangeTypeList(String var1) {
        ArrayList var2 = new ArrayList();
        if (var1 != null) {
            if (var1.contains(",")) {
                String[] var3 = var1.split(",");
                String[] var4 = var3;
                int var5 = var3.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    String var7 = var4[var6];
                    var2.add(RangeType.valueOf(var7.toUpperCase()));
                }
            } else {
                var2.add(RangeType.valueOf(var1.toUpperCase()));
            }
        }

        return var2;
    }

    public List<Long> getLongList(String var1) {
        ArrayList var2 = new ArrayList();
        if (var1 != null) {
            if (var1.contains(",")) {
                String[] var3 = var1.split(",");
                String[] var4 = var3;
                int var5 = var3.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    String var7 = var4[var6];
                    var2.add(Long.valueOf(var7));
                }
            } else {
                var2.add(Long.valueOf(var1));
            }
        }

        return var2;
    }

    public List<DateRangeType> getDateRangeTypeList(String var1) {
        ArrayList var2 = new ArrayList();
        if (var1 != null) {
            if (var1.contains(",")) {
                String[] var3 = var1.split(",");
                String[] var4 = var3;
                int var5 = var3.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    String var7 = var4[var6];
                    var2.add(DateRangeType.valueOf(var7.toUpperCase()));
                }
            } else {
                var2.add(DateRangeType.valueOf(var1.toUpperCase()));
            }
        }

        return var2;
    }

    public List<Date> getDateList(String var1) {
        ArrayList var2 = new ArrayList();
        SimpleDateFormat var3 = new SimpleDateFormat("EEE MMM dd yyyy");
        if (var1 != null) {
            String[] var4 = var1.split(",");
            String[] var5 = var4;
            int var6 = var4.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                String var8 = var5[var7];

                try {
                    var2.add(var3.parse(var8));
                } catch (ParseException var10) {
                    var2.add((Object)null);
                    LOGGER.error(var10.getMessage());
                }
            }
        }

        return var2;
    }

    protected List<Predicate> createNumberComparisonExpression(CriteriaBuilder var1, Path<Long> var2, List<Long> var3, List<RangeType> var4) {
        ArrayList var5 = Lists.newArrayList();
        if (var3 != null && var4 != null && var3.size() > 0 && var4.size() > 0) {
            for(int var6 = 0; var6 < var3.size(); ++var6) {
                RangeType var7 = (RangeType)var4.get(var6);
                Long var8 = (Long)var3.get(var6);
                switch (var7) {
                    case LT:
                        var5.add(var1.lt(var2, var8));
                        break;
                    case GT:
                        var5.add(var1.gt(var2, var8));
                        break;
                    default:
                        var5.add(var1.equal(var2, var8));
                }
            }
        }

        return var5;
    }

    protected List<Predicate> createDateComparisonExpression(CriteriaBuilder var1, Path<Date> var2, List<Date> var3, List<DateRangeType> var4) {
        ArrayList var5 = Lists.newArrayList();
        if (var3 != null && var3.size() > 0 && var4 != null && var4.size() > 0) {
            for(int var6 = 0; var6 < var4.size(); ++var6) {
                DateRangeType var7 = (DateRangeType)var4.get(var6);
                Calendar var8 = Calendar.getInstance();
                Date var9 = null;
                if (!DateRangeType.ISEMPTY.equals(var7)) {
                    var9 = (Date)var3.get(var6);
                }

                switch (var7) {
                    case BEFORE:
                        var5.add(var1.lessThan(var2, var9));
                        break;
                    case AFTER:
                        var8.setTime(var9);
                        var8.add(5, 1);
                        var5.add(var1.greaterThanOrEqualTo(var2, var9));
                        break;
                    case ON:
                        var8.setTime(var9);
                        var8.add(5, 1);
                        var5.add(var1.greaterThanOrEqualTo(var2, var9));
                        var5.add(var1.lessThan(var2, var8.getTime()));
                        break;
                    case ISEMPTY:
                        var5.add(var1.isNull(var2));
                }
            }
        }

        return var5;
    }

    /**
     * Detaches an entity from the entity manager.
     * @param entity the entity to detach.
     */
    public void detach(T entity) {
        entityManager.detach(entity);
    }
}

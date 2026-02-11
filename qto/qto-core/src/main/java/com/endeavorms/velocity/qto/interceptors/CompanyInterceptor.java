package com.endeavorms.velocity.qto.interceptors;

import com.google.common.collect.Lists;
import com.endeavorms.velocity.qto.common.BadRequestError;
import com.endeavorms.velocity.qto.common.ValidationError;
import com.endeavorms.velocity.qto.company.Company;
import com.endeavorms.velocity.qto.company.CompanyManager;
import com.endeavorms.velocity.qto.order.Order;
import com.endeavorms.velocity.qto.order.OrderManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;
import jakarta.ws.rs.core.Response;
import java.util.List;

public class CompanyInterceptor {
    /**
     * Logging Facade.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyInterceptor.class);

    /**
     * the error list.
     */
    private List<ValidationError> errors;

    @Inject
    private CompanyManager companyManager;

    @Inject
    private OrderManager orderManager;

    @AroundInvoke
    public Object validate(final InvocationContext context) throws Exception {
        LOGGER.info("CompanyInterceptor.validate() called");
        List<ValidationError> errors = Lists.newArrayList();
        for (Object param : context.getParameters()) {
            if (param instanceof Long) {
                Company company = companyManager.retrieve((Long) param);
                if ("End Customer".equalsIgnoreCase(company.getType())) {
                    List<Order> orders = orderManager.findByComapnyId(company.getId());
                    if (orders != null && !orders.isEmpty()) {
                        errors.add(new ValidationError("company", "Cannot delete an End Company with Orders"));
                    }
                } else {
                    List<Company> companies = companyManager.findByEndCustomerByParentId(company.getId());
                    if (companies != null && !companies.isEmpty()) {
                        errors.add(new ValidationError("company", "Cannot delete a Master Customer with End Customers"));
                    }
                }
            }
        }

        if (errors.size() > 0) {
            BadRequestError error = new BadRequestError(errors);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(error)
                    .build();
        }

        return context.proceed();
    }

}

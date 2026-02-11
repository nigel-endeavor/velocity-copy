package com.endeavorms.velocity.qto.service.macd;

import com.endeavorms.velocity.qto.common.BadRequestError;
import com.endeavorms.velocity.qto.common.ValidationError;
import com.endeavorms.velocity.qto.service.ServiceManager;
import com.endeavorms.velocity.qto.service.macd.request.MacdRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;
import jakarta.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

/**
 * Intercepts calls for MACD creation.
 * @since 1.3.0
 */
public class MacdInterceptor {
    /**
     * Logging Facade.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MacdInterceptor.class);

    /** Business logic for services. */
    @Inject
    private ServiceManager serviceManager;

    /**
     * Validates the incoming MACD request.
     * @param context the context of the invocation.
     * @return the result of the invocation.
     * @throws Exception if the invocation fails.
     */
    @AroundInvoke
    public Object validate(final InvocationContext context) throws Exception {
        LOGGER.debug("MacdInterceptor.validate() called");
        for (Object param : context.getParameters()) {
            if (param instanceof MacdRequestDto) {
                MacdRequestDto request = (MacdRequestDto) param;
                List<ValidationError> errorList = serviceManager.validateMacdRequest(request);

                if (!errorList.isEmpty()) {
                    BadRequestError errors = new BadRequestError(errorList);
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity(errors)
                            .build();
                }
            }
        }

        return context.proceed();
    }
}

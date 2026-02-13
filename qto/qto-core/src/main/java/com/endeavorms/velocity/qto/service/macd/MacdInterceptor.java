package com.endeavorms.velocity.qto.service.macd;

import com.endeavorms.velocity.qto.common.BadRequestError;
import com.endeavorms.velocity.qto.common.ValidationError;
import com.endeavorms.velocity.qto.service.ServiceManager;
import com.endeavorms.velocity.qto.service.macd.request.MacdRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Intercepts calls for MACD creation.
 */
@Component
public class MacdInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MacdInterceptor.class);

    @Autowired
    private ServiceManager serviceManager;

    public BadRequestError validate(MacdRequestDto request) {
        LOGGER.debug("MacdInterceptor.validate() called");
        List<ValidationError> errorList = serviceManager.validateMacdRequest(request);
        return errorList.isEmpty() ? null : new BadRequestError(errorList);
    }
}

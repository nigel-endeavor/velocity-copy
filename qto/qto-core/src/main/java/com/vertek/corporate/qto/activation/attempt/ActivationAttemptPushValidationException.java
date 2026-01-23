package com.vertek.corporate.qto.activation.attempt;

import java.util.List;

public class ActivationAttemptPushValidationException extends Exception {

    List<String> errors;

    public ActivationAttemptPushValidationException(final List<String> errors) {
        this.errors = errors;
    }

    public String getErrors() {
        return errors.toString().replace("[", "").replace("]", "").replace(", ", "\n");
    }
}

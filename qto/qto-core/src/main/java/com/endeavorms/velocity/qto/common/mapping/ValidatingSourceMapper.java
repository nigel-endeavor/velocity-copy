package com.endeavorms.velocity.qto.common.mapping;

import com.google.common.base.Strings;
import com.endeavorms.velocity.qto.common.adapter.ImportAdapter;

/**
 * @author mmeehan
 * @since 2.7.0
 */
public abstract class ValidatingSourceMapper extends SourceMapper {

    /** Validation message. */
    protected String validationMessage;

    /** Flag to indicate whether a value is required. */
    protected boolean isRequired = false;

    /**
     * Creates a ValidatingSourceMapper with a defined ImportAdapter and sourceIndex.
     * @param adapter the ImportAdapter being used.
     * @param sourceIndex the column index to be used when using the the data source.
     */
    public ValidatingSourceMapper(final ImportAdapter adapter, final String sourceIndex) {
        super(adapter, sourceIndex);
    }

    /**
     * Creates a ValidatingSourceMapper with a defined ImportAdapter and sourceIndex.
     * @param adapter the ImportAdapter being used.
     * @param sourceIndex the column index to be used when using the the data source.
     * @param isRequired indicates whether a value for this is required.
     */
    public ValidatingSourceMapper(final ImportAdapter adapter, final String sourceIndex, final boolean isRequired) {
        super(adapter, sourceIndex);
        this.isRequired = isRequired;
    }

    /**
     * Validate and set validation message.
     * @return true if valid, false if not.
     * @throws Exception if there is a problem reading from the source.
     */
    protected boolean validate() throws Exception {
        if (isRequired && Strings.isNullOrEmpty(super.getValue())) {
            StringBuilder sb = new StringBuilder();
            sb.append("A value for ").append(getSourceIndex()).append(" is required.");
            validationMessage = sb.toString();
            return false;
        } else {
            return true;
        }
    }

    /**
     * Get the nonvalidated value of the property.
     * @return the value of the property whether or not validated.
     * @throws Exception if it fails to return the value.
     */
    public String getNonValidatedValue() throws Exception {
        return super.getValue();
    }

    /**
     * Determine whether the value is valid.
     * @return true if valid, false if not.
     * @throws Exception if there is a problem reading from the source.
     */
    public boolean isValid() throws Exception {
        return validate();
    }

    public String getValidationMessage() {
        return validationMessage;
    }

    public void setValidationMessage(final String validationMessage) {
        this.validationMessage = validationMessage;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(final boolean required) {
        isRequired = required;
    }
}

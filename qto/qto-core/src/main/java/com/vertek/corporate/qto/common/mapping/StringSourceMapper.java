package com.vertek.corporate.qto.common.mapping;

import com.vertek.corporate.qto.common.adapter.ImportAdapter;
import org.codehaus.plexus.util.StringUtils;

public class StringSourceMapper extends ValidatingSourceMapper {

    /**
     * Max length of the value.
     */
    protected Integer maxLength = 50;

    /** Flag used to indicate that it is preferred to truncate the value as opposed to adding to the error list. */
    protected boolean truncate = false;

    /**
     * Creates a StringSourceMapper with a defined ImportAdapter and sourceIndex.
     * @param adapter the ImportAdapter being used.
     * @param sourceIndex the column index to be used when using the the data source.
     * @param maxLength the max length of the string.
     */
    public StringSourceMapper(final ImportAdapter adapter, final String sourceIndex, final int maxLength) {
        super(adapter, sourceIndex);
        this.maxLength = maxLength;
    }

    /**
     * Creates a StringSourceMapper with a defined ImportAdapter and sourceIndex.
     * @param adapter the ImportAdapter being used.
     * @param sourceIndex the column index to be used when using the the data source.
     * @param isRequired indicates whether a value for this is required.
     */
    public StringSourceMapper(final ImportAdapter adapter, final String sourceIndex, final boolean isRequired) {
        super(adapter, sourceIndex, isRequired);
    }


    /**
     * Creates a StringSourceMapper with a defined ImportAdapter and sourceIndex.
     * @param adapter the ImportAdapter being used.
     * @param sourceIndex the column index to be used when using the the data source.
     * @param maxLength the max length of the string.
     * @param isRequired indicates whether a value for this is required.
     */
    public StringSourceMapper(final ImportAdapter adapter,
                              final String sourceIndex,
                              final int maxLength,
                              final boolean isRequired) {
        super(adapter, sourceIndex, isRequired);
        this.maxLength = maxLength;
    }


    /**
     * Creates a StringSourceMapper with a defined ImportAdapter and sourceIndex.
     * @param adapter the ImportAdapter being used.
     * @param sourceIndex the column index to be used when using the the data source.
     * @param maxLength the max length of the string.
     * @param isRequired indicates whether a value for this is required.
     * @param truncate indicates whether the returned value should be truncated to fix the max size.
     */
    public StringSourceMapper(final ImportAdapter adapter,
                              final String sourceIndex,
                              final int maxLength,
                              final boolean isRequired,
                              final boolean truncate) {
        super(adapter, sourceIndex, isRequired);
        this.maxLength = maxLength;
        this.truncate = truncate;
    }

    @Override
    protected boolean validate() throws Exception {
        if (maxLength != null) {
            String s = super.getValue();
            if (s != null) {
                if (s.length() > maxLength && !truncate) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("The value for ")
                            .append(getSourceIndex())
                            .append(" is too long.  The maximum length is ")
                            .append(maxLength)
                            .append(" characters.");
                    validationMessage = sb.toString();
                    return false;
                }
            }
        }
        return super.validate();
    }

    @Override
    public String getValue() throws Exception {
        if (isValid()) {
            if (truncate) {
                return StringUtils.left(super.getValue(), maxLength);
            } else {
                return super.getValue();
            }
        } else {
            throw new Exception(getValidationMessage());
        }
    }

}


package com.vertek.corporate.qto.common.mapping;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.vertek.corporate.qto.common.adapter.ImportAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class BooleanSourceMapper extends ValidatingSourceMapper {

    /** Private logger for this class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(BooleanSourceMapper.class);

    /** Valid true values. */
    private static final ArrayList<String> VALID_TRUE;

    /** Valid false values. */
    private static final ArrayList<String> VALID_FALSE;

    static {
        VALID_TRUE = Lists.newArrayList();
        VALID_FALSE = Lists.newArrayList();

        VALID_TRUE.add("true");
        VALID_FALSE.add("false");

        VALID_TRUE.add("1");
        VALID_FALSE.add("0");

        VALID_TRUE.add("yes");
        VALID_FALSE.add("no");

        VALID_TRUE.add("y");
        VALID_FALSE.add("n");
    }

    /**
     * Constructor.
     * @param adapter the ImportAdapter being used.
     * @param sourceIndex the column index to be used when using the the data source.
     * @param isRequired indicates whether a value for this is required.
     */
    public BooleanSourceMapper(final ImportAdapter adapter, final String sourceIndex, final boolean isRequired) {
        super(adapter, sourceIndex, isRequired);
    }

    /**
     * Constructor.
     * @param adapter the ImportAdapter being used.
     * @param sourceIndex the column index to be used when using the the data source.
     */
    public BooleanSourceMapper(final ImportAdapter adapter, final String sourceIndex) {
        super(adapter, sourceIndex);
    }


    @Override
    protected boolean validate() throws Exception {
        boolean valid = super.validate();
        if (valid) {
            String val = getValue();
            try {
                LOGGER.trace("Validating value: {}", val);
                if (!Strings.isNullOrEmpty(val)) {
                    if (!VALID_TRUE.contains(val.toLowerCase()) && !VALID_FALSE.contains(val.toLowerCase())) {
                        throw new Exception("Invalid value: " + val);
                    }
                }
            } catch (Exception e) {
                valid = false;
                StringBuilder mesg = new StringBuilder();
                mesg.append("Invalid value for ").append(getSourceIndex()).append(": ").append(val);
                setValidationMessage(mesg.toString());
            }
        }
        return valid;
    }


    /**
     * Return a Boolean representing the source data.
     * @return a Boolean.
     * @throws Exception if it fails to return the value.
     */
    public Boolean getBooleanValue() throws Exception {
        if (isValid()) {
            return VALID_TRUE.contains(getValue().toLowerCase());
        } else {
            throw new Exception(getValidationMessage());
        }
    }
}

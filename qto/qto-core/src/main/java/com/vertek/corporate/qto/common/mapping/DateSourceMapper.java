package com.vertek.corporate.qto.common.mapping;

import com.google.common.base.Strings;
import com.vertek.corporate.qto.common.adapter.ImportAdapter;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Date;

public class DateSourceMapper extends ValidatingSourceMapper {

    /** Private logger for this class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(DateSourceMapper.class);

    /** Expected patterns. These are SimpleDateFormat patterns. */
    private static final String[] ACCEPTED_DATE_FORMATS = new String[]{
            "M/d/yy",
            "M-d-yy",
            "yy/M/d",
            "yy/M/d H:m:s",
            "yy/M/d H:m:s.S",
            "M/d/yy H:m:s",
            "M/d/yy H:m:s.S",
            "yy-M-d",
            "yy-M-d h:m a",
            "yy-M-d h:m:s a",
            "yy-M-d h:m:s.S a",
            "MMM, d, yy",
            "EEE, MMM, d, yy"
    };

    /**
     * Creates a DateSourceMapper with a defined ImportAdapter and sourceIndex.
     * @param adapter the ImportAdapter being used.
     * @param sourceIndex the column index to be used when using the the data source.
     */
    public DateSourceMapper(final ImportAdapter adapter, final String sourceIndex) {
        super(adapter, sourceIndex);
    }

    /**
     * Creates a DateSourceMapper with a defined ImportAdapter and sourceIndex.
     * @param adapter the ImportAdapter being used.
     * @param sourceIndex the column index to be used when using the the data source.
     * @param isRequired indicates whether a value for this is required.
     */
    public DateSourceMapper(final ImportAdapter adapter, final String sourceIndex, final boolean isRequired) {
        super(adapter, sourceIndex, isRequired);
    }


    @Override
    protected boolean validate() throws Exception {
        boolean valid = super.validate();
        if (valid) {
            String val = getValue();
            try {
                LOGGER.trace("Validating value: {}", val);
                if (!Strings.isNullOrEmpty(val)) {
                    parseDate(val);
                }
            } catch (ParseException e) {
                valid = false;
                StringBuilder mesg = new StringBuilder();
                mesg.append("Invalid value for ").append(getSourceIndex()).append(": ").append(val);
                setValidationMessage(mesg.toString());
            }
        }
        return valid;
    }

    /**
     * Return a Date representing the source data.
     * @return  a Date.
     * @throws Exception if it fails to return the value.
     */
    public Date getDateValue() throws Exception {
        if (isValid()) {
            if (!Strings.isNullOrEmpty(getValue())) {
                return parseDate(getValue());
            } else {
                return null;
            }
        } else {
            throw new Exception(getValidationMessage());
        }
    }

    /**
     * Parse val and return a Date.
     * @param val the String representation of the date.
     * @return  a Date.
     * @throws ParseException if val is not a recognized date format.
     */
    private Date parseDate(final String val) throws ParseException {
        LOGGER.trace("about to parseDate, val = {}", val);
        return DateUtils.parseDateStrictly(val, ACCEPTED_DATE_FORMATS);
    }
}


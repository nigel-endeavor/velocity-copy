package com.endeavorms.velocity.qto.common.adapter;

import com.endeavorms.velocity.qto.common.util.ListUtil;
import com.endeavorms.velocity.qto.common.util.SpreadsheetUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dkelly
 * @since Nov 11, 2009
 */
public abstract class AbstractAdapter {

    /** Boolean indicated whether the batch has a header. */
    protected boolean hasHeader = false;
    /** List of Strings representing the header. */
    protected List<String> header = new ArrayList<String>();
    /** Map representing the header. Key is the header name, value is the order. */
    protected Map<String, Integer> hashedHeader = null;
    /** Map with representing the currentRow. */
    protected Map<Integer, String> currentRowMap = new HashMap<Integer, String>();

    /** The connection String. */
    protected String connection = null;

    /** Map of configuration information. */
    protected Map<String, Object> configuration = new HashMap<String, Object>();
    /** The name of the current dataset, for example, a sheet in an Excel workbook. */
    protected String view;
    /** Flag indicating whether the view is open. */
    protected boolean isViewOpen = false;

    /** Constant used for verifying column state. */
    private static final int ASSERTION_NOT_SET = -1;
    /** Represent representing the minimum number of columns. */
    protected int columnMin = ASSERTION_NOT_SET;
    /** Represent the maximum number of columns. */
    protected int columnMax = ASSERTION_NOT_SET;

    /** Flag indicating whether the column range needs to be validated (columnMin and column have been set). */
    protected boolean validateColumnRange = false;

    /** Flag to indicate the trimming should be performed. */
    private boolean trimming = true;

    /**
     * Returns the number of empty columns to the left of the spreadsheet data.
     * @return the number of empty columns.
     */
    public abstract int getEmptyColCount();

    public void setConnection(final String connection) {
        this.connection = connection;
    }

    public String getConnection() {
        return this.connection;
    }

    public void setConfiguration(final Map<String, Object> configuration) {
        this.configuration = configuration;
    }

    /**
     * Add's a configuration item to the map of configurations.
     * @param key the name of the configuration variable.
     * @param value the value of the configuration variable.
     */
    public void addConfigurationItem(final String key, final Object value) {
        configuration.put(key, value);
    }

    public String getView() {
        return view;
    }

    public void setView(final String view) {
        this.view = view;
    }

    /**
     * Opens the view, calling the implementation of doOpenView() and setting the view as Open.
     * @throws NoSuchViewException if the view does not exist.
     */
    public void openView() throws NoSuchViewException {
        this.doOpenView();
        this.isViewOpen = true;
    }

    /**
     * Opens the view (for example: a sheet in a workbook).
     * @throws NoSuchViewException if the view does not exist/
     */
    protected abstract void doOpenView() throws NoSuchViewException;

    public void setHasHeader(final boolean hasHeader) {
        this.hasHeader = hasHeader;
    }

    /**
     * Returns the flag that indicates whethere there is a header or not.
     * @return whether there is currently a defined header.
     */
    public boolean hasHeader() {
        return hasHeader;
    }

    /**
     * Returns a List<String> containing the headers.
     * @return the header.
     */
    public List<String> getHeader() {
        this.assertHasHeader();
        return this.header;
    }

    /** Checks to see if there is a header specified and throws an exception if the header is not defined. */
    protected void assertHasHeader() {
        if (!this.hasHeader()) {
            final String msg = "hasHeader() is "
                    + this.hasHeader() + ", did you specify?";
            throw new NoHeaderException(msg);
        }
    }

    /**
     * Returns the column specified by name.
     * @param name the coloumn name to retrieve from the map.
     * @return the column.
     * @throws NoSuchColumnException thrown if the column doesn't exist.
     */
    public String getColumnByName(final String name) throws NoSuchColumnException {

        this.assertHasHeader();
        String columnName = name;

        if (name != null && trimmingEnabled()) {
            columnName = columnName.trim();
        }

        if (hashedHeader == null) {
            hashedHeader = ListUtil.toReverseMap(header);
        }

        Integer ref = hashedHeader.get(columnName) + getEmptyColCount();

        if (ref == null) {
            throw new NoSuchColumnException("no such column \"" + columnName + "\"");
        }

        String value;
        try {
            value = this.getColumn(ref + 1);

        } catch (NoSuchColumnException nsce) {
            throw new NoSuchColumnException("error find column \""
                    + columnName + "\", " + nsce.toString());
        }

        return value;
    }

    public Map<Integer, String> getCurrentRow() {

        return this.currentRowMap;
    }

    /** Currently, an unsupported operation. */
    public void remove() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a column based on the location of it in the currentRowMap.
     * @param ref the column to retrieve.
     * @return the column
     * @throws NoSuchColumnException thrown if the column doesn't exist.
     */
    public String getColumn(final int ref) throws NoSuchColumnException {

        String value = this.currentRowMap.get(ref - 1);

        if (value == null) {

            throw new NoSuchColumnException("invalid column \"" + ref + "\"");
        }

        return value;
    }

    /** Removes all entries from the currentRowMap. */
    protected void clearCurrentRowMap() {

        this.currentRowMap.clear();
    }

    /**
     * Returns a column based on the location of it in the currentRowMap.
     * @param ref the column to retrieve.
     * @return the column
     * @throws NoSuchColumnException thrown if the column doesn't exist.
     */
    public abstract String getColumn(String ref) throws NoSuchColumnException;

    /**
     * Set the current row.
     * @param rowMap the row to set as the current row.
     */
    public void setCurrentRow(final Map<Integer, String> rowMap) {

        this.currentRowMap = rowMap;
    }

    /**
     * Allows for the defining of the column range minimum and maximum.
     * @param columnMin the column minimum.
     * @param columnMax the column maximum.
     */
    public void assertColumnRange(final int columnMin, final int columnMax) {
        this.columnMin = columnMin;
        this.columnMax = columnMax;
    }

    /**
     * Allows for the defining of the column range minimum.
     * @param columnMin the column minimum.
     */
    public void assertColumnMinimum(final int columnMin) {
        this.columnMin = columnMin;
    }

    /**
     * Allows for the defining of the column range maximum.
     * @param columnMax the column maximum.
     */
    public void assertColumnMaximum(final int columnMax) {
        this.columnMax = columnMax;
    }

    /**
     * Whether the adapter has the column minimum defined.
     * @return true if the column minimum has been defined.
     */
    protected boolean hasColumnMinimumAssertion() {
        return columnMin != ASSERTION_NOT_SET;
    }

    /**
     * Whether the adapter has the column maximum defined.
     * @return true if the column maximum has been defined.
     */
    protected boolean hasColumnMaximumAssertion() {
        return columnMax != ASSERTION_NOT_SET;
    }

    protected int getColumnMinimum() {
        return columnMin;
    }

    protected int getColumnMaximum() {
        return columnMax;
    }

    public void setTrimming(final boolean useTrimming) {
        this.trimming = useTrimming;
    }

    /**
     * Returns whether trimming is enabled.
     * @return true if trimming is enabled, false if it is not.
     */
    public boolean trimmingEnabled() {
        return trimming;
    }

    /**
     * Implement this if you would like record checking for import adapters.
     * @return a record map
     */
    protected Map<Integer, String> doNext() {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the next record in the input.
     * @return a map of the next record indexed by column number
     */
    public Map<Integer, String> next() {

        Map<Integer, String> fieldMap = this.doNext();

        if (validateColumnRange) {
            validateColumnRange(fieldMap);
        }
        /* There's an efficiency vs. ease and consistency of implementation
         * call here. If speed proves a problem due to the extra loop
         * through each row then we can move this down into the individual
         * adapters or thing through another way to deal with it.
         */
        if (trimmingEnabled()) {

            for (Integer index : fieldMap.keySet()) {

                String field = fieldMap.get(index);

                if (field != null) {

                    fieldMap.put(index, field.trim());
                }
            }
        }

        return fieldMap;
    }

    /**
     * Validates that the fieldMap contains the correct amount of columns.
     * @param fieldMap used to validate against the minimum and maximum column counts.
     */
    public void validateColumnRange(final Map<Integer, String> fieldMap) {

        int cols = fieldMap.size();

        if (hasColumnMinimumAssertion() && cols < columnMin) {

            throw new ColumnCountException();
        }

        if (hasColumnMaximumAssertion() && cols > columnMax) {

            throw new ColumnCountException();
        }
    }

    /**
     * Returns whether the adapter supports views.
     * @return true if views are supported, false if they are not.
     */
    public abstract boolean supportsViews();

    /**
     * Override this for adapters that support views.
     * @return the number of views for the given input
     */
    public int getViewCount() {

        if (supportsViews()) {
            // must override this method, throw exception otherwise
            throw new UnsupportedOperationException();
        }

        return 1;
    }

    /**
     * Override this for adapters that support views.
     * @return a list of view names
     */
    public List<String> getViewNames() {

        throw new UnsupportedOperationException();
    }

    /**
     * Returns the column associated with an ordinal reference.
     * @param ref the ordinal reference of the column.
     * @return the column associated with the ordinal reference.
     * @throws NoSuchColumnException if the referenced column could not be found.
     */
    protected String getColumnByOrdinalReference(final String ref) throws NoSuchColumnException {

        String value = null;
        short ordinalRef;

        try {
            ordinalRef = Short.parseShort(ref);

            value = getColumn(ordinalRef);

        } catch (NumberFormatException nfe) {

            throw new RuntimeException("\"" + ref + "\" can't be converted to an column reference");
        }

        return value;
    }

    /**
     * Given a column letter provide the ordinal reference as a number, or provide the ordinal reference itself.  This
     * method will return the associated column value.
     * @param ref is an Excel column (e.g. "a", "b") or ordinal reference
     * @return the column value
     * @throws NoSuchColumnException if the referenced column could not be found.
     */
    protected String getColumnValue(final String ref) throws NoSuchColumnException {

        return getColumn(getColumnIndex(ref));
    }

    /**
     * Given a column letter provide the ordinal reference as a number, or provide the ordinal reference itself.  This
     * method will provide the column index.
     * <p/>
     * In the case of a letter, A = 1, AA = 26, BA = 52, etc.
     * @param ref is an Excel column (e.g. "a", "b") or ordinal reference
     * @return the number representing the ordinal reference
     * @throws NoSuchColumnException if the referenced column could not be found.
     */
    protected short getColumnIndex(final String ref) throws NoSuchColumnException {

        short tempIndex = -1;

        if (StringUtils.isNumeric(ref)) {

            try {
                tempIndex = Short.parseShort(ref);

            } catch (NumberFormatException nfe) {

                throw new RuntimeException("\"" + ref + "\" can't be converted to an column reference");
            }

        } else if (StringUtils.isAlpha(ref)) {

            tempIndex = (short) (SpreadsheetUtil.convertLetterToIndex(ref) + 1);
        } else {

            throw new NoSuchColumnException("invalid column index: \"" + ref + "\"");
        }

        return tempIndex;
    }

    /** Processes the current defined configuration for this adapter. */
    protected void processConfiguration() {

        if (configuration == null) {
            throw new IllegalArgumentException("null configuration");
        }

        try {
            if (configuration.containsKey(AdapterConstants.CONF_VALIDATE_COLUMN_RANGE)) {

                if ((Boolean) configuration.get(AdapterConstants.CONF_VALIDATE_COLUMN_RANGE)) {

                    validateColumnRange = true;
                }
            }
        } catch (Exception e) {

            throw new IllegalArgumentException("could't parse config: " + e);
        }
    }

    /**
     * Returns flag indicating whether the columns range has been validated already or not.
     * @return whether the column range has already been validated.
     */
    protected boolean validateColumnRange() {
        return validateColumnRange;
    }
}

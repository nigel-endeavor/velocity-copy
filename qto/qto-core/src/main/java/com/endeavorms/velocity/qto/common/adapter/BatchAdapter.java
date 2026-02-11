package com.endeavorms.velocity.qto.common.adapter;

import java.util.List;
import java.util.Map;

/**
 * The basic interface for processing records from a file, etc., in batch. That is, walking through record by record.
 * @author dkelly
 * @since Nov 11, 2009
 */
public interface BatchAdapter {

    /**
     * Set the connection to the file or resource.  This is flexible enough to accommodate various sources.
     * @param connection describes the connection information
     */
    void setConnection(String connection);

    /**
     * Gets the connection to the file or resource.
     * @return the connection to the file or resource.
     */
    String getConnection();

    /**
     * Some adapters require special configuration beyond standard methods like setConnection() or setView().  This is
     * where special configuration is passed.
     * @param configuration is a list of configuration options
     */
    void setConfiguration(Map<String, Object> configuration);

    /**
     * Adapters should have an internal configuration map to which you can add whatever configuration you'd like.  It's
     * a little easier than declaring a map and then filling it up and calling setConfiguration().
     * @param key is the key value (use the appropriate constant)
     * @param value is the configuration item value
     */
    void addConfigurationItem(String key, Object value);

    /**
     * Determines whether or not the adapter supports the concept of "views". Views are work sheets in excel, tables in
     * JDBC, etc.  Views do not exist for text, for example.  When they do exist the view that is read must be
     * specified.
     * @return true if views are supported
     */
    boolean supportsViews();

    /**
     * Returns the number of views.
     * @return the number of views in the input.
     */
    int getViewCount();

    /**
     * Gets a list of views.
     * @return a list of view names as strings
     */
    List<String> getViewNames();

    /**
     * Sets the view, which can be anything like a query, worksheet, table depending on the adapter.  Note that this
     * doesn't open the view.  You have to setView() and then openView().
     * @param view the view within the connect to open when processing
     */
    void setView(String view);

    /**
     * Getter for the view that is set for this adapter.
     * @return the adapter's view.
     */
    String getView();

    /**
     * Opens the view specified by setView().
     * @throws NoSuchViewException when the view doesn't exist
     */
    void openView() throws NoSuchViewException;

    /** Closes the adapter. */
    void close();

    /**
     * Sets the flag whether the adapter has a header or not.
     * @param hasHeader flag indicating whether the adapter has a header or not.
     */
    void setHasHeader(boolean hasHeader);

    /**
     * Getter for the hasHeader flag.
     * @return true if the adapter has a header, false if it does not.
     */
    boolean hasHeader();

    /**
     * If there is a header then get their names as a list of strings.
     * @return the header as a list of strings.
     */
    List<String> getHeader();

    /**
     * Gets a column from the row Map based on a reference ordinal.
     * @param ref the reference ordinal to search for the column with.
     * @return the column referenced by the ref ordinal.
     * @throws NoSuchColumnException if the column identified by the reference ordinal does not exist.
     */
    String getColumn(String ref) throws NoSuchColumnException;

    /**
     * Gets the column value as a string using the ordinal reference. The first column is one, and so on.
     * @param ref is the ordinal reference of the column, starts at one.
     * @return the column value.
     * @throws NoSuchColumnException if the column doesn't exist.
     */
    String getColumn(int ref) throws NoSuchColumnException;

    /**
     * Gets a column from the row Map based on the supplied name.
     * @param name the name of the column to return.
     * @return the column with the title equal to name.
     * @throws NoSuchColumnException if the specified column doesn't exist.
     */
    String getColumnByName(String name) throws NoSuchColumnException;

    /**
     * Gets the current row's index.  Please note that this includes the header row if one is defined.  The
     * Header behavior is independent of setHasHeader().
     *
     * @return the index of the current row.
     */
    long getRowIndex();

    /**
     * Gets the row Map of the current row.
     * @return the current row.
     */
    Map<Integer, String> getCurrentRow();
}

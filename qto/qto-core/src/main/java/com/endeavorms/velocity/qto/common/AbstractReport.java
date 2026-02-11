package com.endeavorms.velocity.qto.common;

import org.apache.poi.ss.SpreadsheetVersion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * from vertek-common.
 * Models a very basic report.
 *
 * @author rconnolly
 * @since 1.0
 */
public abstract class AbstractReport {

    /** The name of the Report. */
    private String name;

    /** The desired format of the Report. */
    private SpreadsheetVersion format;

    /** The List of Strings to be used as Column Headers.*/
    private List<String> columnHeaders = new ArrayList<String>();

    /** The List of Integer defining associated widths of columns.*/
    private List<Integer> columnWidths;

    /** A Map containing column header grouping information.*/
    private Map<String, List<String>> groupedColumnHeaders = new HashMap<String, List<String>>();



    /** The List of Object arrays to be used as data. */
    private List<Object[]> rowData = new ArrayList<Object[]>();


    /** Default Constructor.*/
    public AbstractReport() {
    }


    /**
     * Convenience Constructor.
     * @param name the name of the Report.
     * @param format the desired output format of the Report.
     * @param groupedColumnHeaders a Map of grouped Column Header information.
     * @param rowData the List of Objects to be used as cell data.
     */
    public AbstractReport(final String name, final SpreadsheetVersion format,
                          final Map<String, List<String>> groupedColumnHeaders,
                          final List<Object[]> rowData) {
        this.name = name;
        this.format = format;
        this.groupedColumnHeaders = groupedColumnHeaders;
        this.rowData = rowData;
    }

    /**
     * Convenience Constructor.
     * @param name the name of the Report.
     * @param format the desired output format of the Report.
     * @param columnHeaders a List of Column Headers.
     * @param rowData the List of Objects to be used as cell data.
     */
    public AbstractReport(final String name, final SpreadsheetVersion format,
                          final List<String> columnHeaders, final List<Object[]> rowData) {
        this.name = name;
        this.format = format;
        this.columnHeaders = columnHeaders;
        this.rowData = rowData;
    }

    /**
     * Constructor for creating a report with defined column widths.
     * @param name the name of the Report.
     * @param format the desired output format of the Report.
     * @param columnHeaders a List of Column Headers.
     * @param columnWidths a List of column widths to associate with the provided headers.
     * @param rowData the List of Objects to be used as cell data.
     */
    public AbstractReport(final String name, final SpreadsheetVersion format,
                          final List<String> columnHeaders, final List<Integer> columnWidths,
                          final List<Object[]> rowData) {
        this.name = name;
        this.format = format;
        this.columnHeaders = columnHeaders;
        this.columnWidths = columnWidths;
        this.rowData = rowData;
    }


    public String getName() {
        return name;
    }
    public void setName(final String name) {
        this.name = name;
    }

    public SpreadsheetVersion getFormat() {
        return format;
    }

    public void setFormat(final SpreadsheetVersion format) {
        this.format = format;
    }

    public Map<String, List<String>> getGroupedColumnHeaders() {
        return groupedColumnHeaders;
    }
    public void setGroupedColumnHeaders(final Map<String, List<String>> groupedColumnHeaders) {
        this.groupedColumnHeaders = groupedColumnHeaders;
    }

    public List<String> getColumnHeaders() {
        return columnHeaders;
    }
    public void setColumnHeaders(final List<String> columnHeaders) {
        this.columnHeaders = columnHeaders;
    }

    public List<Object[]> getRowData() {
        return rowData;
    }
    public void setRowData(final List<Object[]> rowData) {
        this.rowData = rowData;
    }

    public List<Integer> getColumnWidths() {
        return columnWidths;
    }

    public void setColumnWidths(final List<Integer> columnWidths) {
        this.columnWidths = columnWidths;
    }
}

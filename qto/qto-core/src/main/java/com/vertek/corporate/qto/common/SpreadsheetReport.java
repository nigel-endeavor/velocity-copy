package com.vertek.corporate.qto.common;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * From vertek-commons.
 * A Simple Spreadsheet Report.
 *
 * @author rconnolly
 * @since 1.0
 */
public class SpreadsheetReport extends AbstractReport {

    /** Logging. */
    private static final Logger LOGGER = LoggerFactory.getLogger(SpreadsheetReport.class);

    /** Cell Date format. */
    protected static final String DATE_FORMAT = "yyyy-mm-dd";

    /** ISO 8601 format regex. */
    protected static final String ISO8601 = "^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|"
            + "(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30))).*";

    /** Current row index. */
    protected int rowIndex = 0;

    /** Default Constructor. */
    public SpreadsheetReport() {
        super();
    }

    /**
     * Convenience Constructor.
     * @param name the name of the Report.
     * @param columnHeaders the List of Column Headers.
     * @param rowData the List of Objects to be used as cell data.
     */
    public SpreadsheetReport(final String name, final List<String> columnHeaders, final List<Object[]> rowData) {
        super(name, SpreadsheetVersion.EXCEL2007, columnHeaders, rowData);
    }

    /**
     * Convenience Constructor.
     * @param name the name of the Report.
     * @param format the desired output format of the spreadsheet.
     * @param columnHeaders the List of Column Headers.
     * @param rowData the List of Objects to be used as cell data.
     */
    public SpreadsheetReport(final String name, final SpreadsheetVersion format,
                             final List<String> columnHeaders, final List<Object[]> rowData) {
        super(name, format, columnHeaders, rowData);
    }

    /**
     * Constructor for creating a report with defined column widths.
     * todo: possibly create a ColumnInfo class containing information like headers, width/autowidth, grouping (?).
     * @param name the name of the Report.
     * @param format the desired output format of the spreadsheet.
     * @param columnHeaders the List of Column Headers.
     * @param columnWidths a List of column widths to associate with the provided headers.
     * @param rowData the List of Objects to be used as cell data.
     */
    public SpreadsheetReport(final String name, final SpreadsheetVersion format,
                             final List<String> columnHeaders, final List<Integer> columnWidths,
                             final List<Object[]> rowData) {
        super(name, format, columnHeaders, columnWidths, rowData);
    }

    /**
     * Convenience Constructor.
     * @param name the name of the Report.
     * @param groupedColumnHeaders a Map of grouped Column Header information.
     * @param rowData the List of Objects to be used as cell data.
     */
    public SpreadsheetReport(final String name, final Map<String, List<String>> groupedColumnHeaders,
                             final List<Object[]> rowData) {
        super(name, SpreadsheetVersion.EXCEL2007, groupedColumnHeaders, rowData);
    }


    /**
     * Convenience Constructor.
     * @param name the name of the Report.
     * @param resultSet the result set to create a report with.
     */
    public SpreadsheetReport(final String name, final ResultSet resultSet) {
        setName(name);
        setColumnHeaders(new ArrayList<String>());
        setRowData(new ArrayList<Object[]>());

        try {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
                getColumnHeaders().add(resultSetMetaData.getColumnName(i + 1));
            }

            while (resultSet.next()) {
                Object[] row = new Object[resultSetMetaData.getColumnCount()];
                for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
                    row[i] = resultSet.getObject(i + 1);
                }
                getRowData().add(row);
            }
        } catch (SQLException sqle) {
            throw new RuntimeException("Unable to read ResultSet", sqle);
        }
    }


    /**
     * Creates an {@link Workbook} using the SpreadsheetReport information.
     * @throws IOException when conversion of ISO8601 date fails.
     * @return an {@link Workbook} containing the data configured in this SpreadsheetReport instance.
     */
    protected Workbook createWorkbook() throws IOException {
        // create the main workbook
        Workbook workbook;

        LOGGER.trace("format = {}", getFormat().name());

        if (getFormat().equals(SpreadsheetVersion.EXCEL97)) {
            workbook = new HSSFWorkbook();
        } else {
            workbook = new SXSSFWorkbook();
        }
        createSheet(workbook, false);
        return workbook;
    }


    /**
     * Creates a {@link org.apache.poi.ss.usermodel.Sheet} using the SpreadsheetReport information
     * and adds it to workbook.
     * @param workbook the workbook to add the sheet to.
     * @param setName sets the sheet name to the report name when creating .
     * @throws java.io.IOException when conversion of ISO8601 date fails.
     */
    protected void createSheet(final Workbook workbook, final boolean setName) throws IOException {

        CreationHelper creationHelper = workbook.getCreationHelper();

        // create the main worksheet
        Sheet sheet = null;
        if (setName) {
            sheet = workbook.createSheet(getName());
        } else {
            sheet = workbook.createSheet();
        }
        sheet.setFitToPage(true);

        createHeaders(workbook, sheet);

        LOGGER.trace("format = {}", getFormat().name());

        // auto size columns after creating headers to ensure a minimum width
        // (SXSSFWorkbook only considers a sliding window of 100 rows by default).
        if (getFormat().equals(SpreadsheetVersion.EXCEL2007)) {
            if (getColumnWidths() != null) {
                int headerIndex = 0;
                for (Integer columnWidth : getColumnWidths()) {
                    if (columnWidth == null) {
                        sheet.autoSizeColumn(headerIndex);
                    } else {
                        sheet.setColumnWidth(headerIndex, columnWidth);
                    }
                    headerIndex++;
                }
            } else {
                autoSizeHeaders(sheet);
            }
        }

        // write our data
        for (final Object[] aRowData : getRowData()) {
            Row currentRow = sheet.createRow(rowIndex);
            // loop the cells
            for (int c = 0; c < aRowData.length; c++) {
                Cell dataCell = currentRow.createCell(c);

                String cellValue = aRowData[c] != null && !String.valueOf(aRowData[c]).equals("null")
                        ? String.valueOf(aRowData[c])
                        : "";
                Object o = aRowData[c];
                if (o instanceof Date || cellValue.matches(ISO8601)) {
                    if (!(o instanceof Date)) {
                        try {
                            if (cellValue.contains(".")) {
                                //SimpleDateFormat does not handle decimal seconds or time zone, so remove.
                                cellValue = cellValue.substring(0, cellValue.indexOf("."));
                            }
                            SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                            o = inputDateFormat.parse(cellValue);
                        } catch (ParseException e) {
                            try {
                                // we'll try another date format then...
                                SimpleDateFormat inputDateFormat = new SimpleDateFormat(DATE_FORMAT);
                                o = inputDateFormat.parse(cellValue);
                            } catch (ParseException pe) {
                                throw new IOException(pe);
                            }
                        }
                    }
                    CellUtil.setCellStyleProperty(dataCell, CellUtil.DATA_FORMAT,
                            creationHelper.createDataFormat().getFormat(DATE_FORMAT));
                    dataCell.setCellValue((Date) o);
                } else {
                    if (o != null && Number.class.isAssignableFrom(o.getClass())) {

                        if (Double.class.isAssignableFrom(o.getClass())
                                || Float.class.isAssignableFrom(o.getClass())
                                || BigInteger.class.isAssignableFrom(o.getClass())) {
                            LOGGER.trace("formatting {} as a decimal", o);
                            CellUtil.setCellStyleProperty(dataCell, CellUtil.DATA_FORMAT,
                                    creationHelper.createDataFormat().getFormat("###0.0##"));
                        }
                        dataCell.setCellValue(new Double(cellValue));

                    } else if ("".equals(cellValue)) {
                        String empty = null;
                        dataCell.setCellValue(empty);
                    } else {
                        dataCell.setCellValue(cellValue);
                    }
                }
            }
            rowIndex++;
        }

        // autosize columns will consider all rows unless using SXSSFWorkbook, which considers only the last 100 rows
        // to save memory, so size based on column headers.
        if (getFormat().equals(SpreadsheetVersion.EXCEL97)) {
            autoSizeHeaders(sheet);
        }
    }

    /**
     * Creates the column headers for the report.
     * @param workbook the current {@link Workbook}.
     * @param currentSheet the current {@link Sheet}.
     */
    protected void createHeaders(final Workbook workbook, final Sheet currentSheet) {
        final CellStyle headerStyle = getHeaderStyle(workbook);
        final CellStyle subHeaderStyle = getSubHeaderStyle(workbook);

        // create simple column headers
        if (getColumnHeaders().size() > 0) {
            final Row headerRow = currentSheet.createRow(rowIndex++);
            for (int i = 0; i < getColumnHeaders().size(); i++) {
                final Cell headerCell = headerRow.createCell(i);
                headerCell.setCellValue(getColumnHeaders().get(i));
                headerCell.setCellStyle(subHeaderStyle);
            }
        } else if (getGroupedColumnHeaders().keySet().size() > 0) {
            // create grouped column headers
            final Row headerRow = currentSheet.createRow(rowIndex);
            String previousKey = null;
            for (final Map.Entry<String, List<String>> headerEntry : getGroupedColumnHeaders().entrySet()) {
                final String header = headerEntry.getKey();
                final List<String> subHeaders = headerEntry.getValue();

                final int firstColIndex =
                        previousKey == null ? 0 : getGroupedColumnHeaders().get(previousKey).size();
                final int lastColIndex = firstColIndex + subHeaders.size() - 1;
                currentSheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, firstColIndex,
                        lastColIndex));

                final Cell headerCell = headerRow.createCell(firstColIndex);
                headerCell.setCellValue(header);
                headerCell.setCellStyle(headerStyle);

                previousKey = header;
            }

            rowIndex++;
            previousKey = null;
            final Row subHeaderRow = currentSheet.createRow(rowIndex++);
            for (final Map.Entry<String, List<String>> headerEntry : getGroupedColumnHeaders().entrySet()) {
                final List<String> subHeaders = headerEntry.getValue();

                final int firstColIndex =
                        previousKey == null ? 0 : getGroupedColumnHeaders().get(previousKey).size();

                for (int i = 0; i < subHeaders.size(); i++) {
                    final Cell headerCell = subHeaderRow.createCell(i + firstColIndex);
                    headerCell.setCellValue(subHeaders.get(i));
                    headerCell.setCellStyle(subHeaderStyle);
                }
                previousKey = headerEntry.getKey();
            }
        }
    }

    /**
     * Auto-resizes the spreadsheet columns.
     * @param currentSheet the current {@link Sheet} to auto-size columns for.
     */
    protected void autoSizeHeaders(final Sheet currentSheet) {
        if (getColumnHeaders().size() > 0) {
            if (currentSheet instanceof SXSSFSheet) {
                ((SXSSFSheet) currentSheet).trackAllColumnsForAutoSizing();
            }
            for (int i = 0; i < getColumnHeaders().size(); i++) {
                currentSheet.autoSizeColumn(i);
            }
        } else if (getGroupedColumnHeaders().keySet().size() > 0) {
            if (currentSheet instanceof SXSSFSheet) {
                ((SXSSFSheet) currentSheet).trackAllColumnsForAutoSizing();
            }
            final List<String> allHeaders = new ArrayList<String>();
            for (final Map.Entry<String, List<String>> headerEntry : getGroupedColumnHeaders().entrySet()) {
                allHeaders.addAll(headerEntry.getValue());
            }
            for (int i = 0; i < allHeaders.size(); i++) {
                currentSheet.autoSizeColumn(i);
            }
        }
    }

    /**
     * Creates a {@link CellStyle} for the worksheet's Header Cells.
     * @param workbook the current {@link Workbook}.
     * @return a {@link CellStyle} for the Header Cells.
     */
    protected CellStyle getHeaderStyle(final Workbook workbook) {
        final Font headerFont = workbook.createFont();
        headerFont.setBold(true);

        final CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderLeft(BorderStyle.THIN);
        style.setFont(headerFont);

        return style;
    }

    /**
     * Creates a {@link CellStyle} for the worksheet's Header Cells.
     * @param workbook the current {@link Workbook}.
     * @return a {@link CellStyle} for the Header Cells.
     */
    protected CellStyle getSubHeaderStyle(final Workbook workbook) {
        final Font headerFont = workbook.createFont();
        headerFont.setBold(true);

        final CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFont(headerFont);

        return style;
    }

    /**
     * Generates a spreadsheet and writes it to the given OutputStream.
     * @param outputStream the OutputStream to write the spreadsheet to.
     */
    public void writeTo(final OutputStream outputStream) {
        try {
            createWorkbook().write(outputStream);
        } catch (final IOException ioe) {
            ioe.printStackTrace();
            throw new RuntimeException("Unable to write workbook", ioe);
        }
    }
}

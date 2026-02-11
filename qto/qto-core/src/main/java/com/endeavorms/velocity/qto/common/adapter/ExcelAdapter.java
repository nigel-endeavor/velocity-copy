package com.endeavorms.velocity.qto.common.adapter;

import com.endeavorms.velocity.qto.common.util.FileUtils;
import org.apache.poi.ooxml.POIXMLException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Reads Excel documents.
 * <p/>
 * Note that for reads POI now supports an event API, which we aren't implementing here at the moment.  If performance
 * issues occur, this is one place to look for improving performance.
 * @author dkelly
 * @since Nov 11, 2009
 */
public class ExcelAdapter extends AbstractAdapter implements ImportAdapter {

    /** Logging. */
    private static final Logger OUT = LoggerFactory.getLogger(ExcelAdapter.class);

    /** Date formatter. */
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /** Date formatter. */
    private final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");

    /**
     *
     */
    private Workbook workbook;
    /**
     *
     */
    private Sheet currentSheet;
    /**
     *
     */
    private InputStream inputStream;
    /**
     *
     */
    private Iterator<Row> rowIterator;
    /**
     *
     */
    private int rowIndex = 0;

    //todo: make these configurable??
    /**
     * The row that contains the header.
     */
    private final int headerRowIndex = 4;

    /**
     * The first row that contains the data.
     */
    private final int dataRowIndex = 10;

    /**
     * The number of empty columns to the left of the spreadsheet data.
     */
    @Override
    public int getEmptyColCount() {
        return 1;
    }

    /**
     * Creates an InputStream with the current connection and opens it for reading.
     * @throws IOException if unable to open the InputStream.
     * @throws InvalidFileFormatException if the file is in the incorrect format.
     */
    public void openForRead() throws IOException, InvalidFileFormatException {
        inputStream = FileUtils.getInputStream(connection);
        openForRead(inputStream);
    }

    /**
     * Opens the provided InputStream for reading.
     * @param is the InputStream to be used for opening.
     * @throws IOException if unable to open the InputStream.
     * @throws InvalidFileFormatException if the file is in the incorrect format.
     */
    public void openForRead(final InputStream is) throws IOException, InvalidFileFormatException {
        this.processConfiguration();

        this.inputStream = is;

        try {
            workbook = WorkbookFactory.create(this.inputStream);

        } catch (IOException ioe) {
            OUT.warn("reading your file failed: " + ioe.toString());
            throw ioe;

        } catch (IllegalArgumentException iae) {

            OUT.warn("invalid file format: " + iae.toString());
            throw new InvalidFileFormatException(iae);

        } catch (POIXMLException pxe) {

            OUT.warn("invalid file format: " + pxe.toString());
            throw new InvalidFileFormatException(pxe);
        }
    }

    /**
     * Opens the sheet with the name of the current view.
     * @throws NoSuchViewException if the view is not defined.
     */
    protected void doOpenView() throws NoSuchViewException {
        if (this.workbook == null) {

            throw new RuntimeException("open the workbook before calling openView()");
        }

        if (this.view == null) {

            throw new RuntimeException("set the view before calling open()");
        }

        currentSheet = this.getSheet(this.view);

        if (this.hasHeader()) {
            this.readHeader();
        }
    }

    @Override
    public String getColumn(final String ref) throws NoSuchColumnException {
        return this.getColumnValue(ref);
    }

    @Override
    public String getColumn(final int ref) throws NoSuchColumnException {
        String value = currentRowMap.get(ref - 1);

        if (value == null) {

            OUT.trace("didn't find a value for column \"" + ref + "\"");
            value = "";
        }

        return value;
    }

    /** Reads the header from the row iterator. */
    private void readHeader() {
        if (this.hasNext()) {
            Row row = null;
            // skip to the header row
            while (rowIndex < headerRowIndex) {
                row = (Row) rowIterator.next();
                rowIndex++;
                while (currentSheet.getRow(rowIndex) == null) {
                    rowIndex++;
                }
            }

            this.header.clear();

            for (Iterator<Cell> cit = (Iterator<Cell>) row.cellIterator(); cit.hasNext();) {

                Cell cell = cit.next();

                String headerValue = cell.getStringCellValue();

                if (trimmingEnabled()) {
                    headerValue = cell.getStringCellValue().trim();
                }

                if (workbook.getSheetAt(0).getRow(rowIndex-2) != null) {
                    boolean aLocation = workbook.getSheetAt(0).getRow(rowIndex - 2).getCell(cell.getColumnIndex()) != null
                            && "A Location".equals(workbook.getSheetAt(0).getRow(rowIndex - 2).getCell(cell.getColumnIndex()).getStringCellValue());
                    boolean zLocation = workbook.getSheetAt(0).getRow(rowIndex - 2).getCell(cell.getColumnIndex()) != null
                            && "Z Location".equals(workbook.getSheetAt(0).getRow(rowIndex - 2).getCell(cell.getColumnIndex()).getStringCellValue());
                    if (aLocation && (!"Location Description".equalsIgnoreCase(headerValue))) {
                        headerValue += " (A)";
                    }
                    if (zLocation) {
                        headerValue += " (Z)";
                    }
                }

                this.header.add(headerValue);
            }

            // skip to the data row
            while (rowIndex < dataRowIndex - 1) {
                row = rowIterator.next();
                rowIndex++;
                while (currentSheet.getRow(rowIndex) == null) {
                    rowIndex++;
                }
            }

        }
    }

    public int getViewCount() {
        return workbook.getNumberOfSheets();
    }

    /**
     * Returns the list of all sheet names in the workbook.
     * @return the list of all sheets in the workbook.
     */
    public List<String> getViewNames() {
        List<String> sheetNames = new ArrayList<String>();

        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            String name = workbook.getSheetName(i);
            sheetNames.add(name);
        }

        return sheetNames;
    }

    /**
     * Retrieves a sheet, by name, from the current workbook if it exists.
     * @param sheetName the name of the sheet to return.
     * @return the sheet with the same name as sheetName.
     * @throws NoSuchViewException if the sheet does not exist.
     */
    private Sheet getSheet(final String sheetName) throws NoSuchViewException {
        OUT.debug("requested worksheet is " + sheetName);

        Sheet sheet = workbook.getSheet(sheetName);

        if (sheet == null) {

            throw new NoSuchViewException("can't find worksheet \"" + sheetName + "\"");
        }
        return sheet;
    }

    /** Closes the current InputStream. */
    public void close() {
        FileUtils.closeInputStream(inputStream);
    }

    /**
     * Indicates whether there is another row.
     * @return true or false, depending on whether there is another row in the sheet or not.
     */
    public boolean hasNext() {
        Boolean hasNext = false;

        if (currentSheet == null) {
            throw new RuntimeException("open the file and view first");
        }

        if (rowIterator == null) {
            rowIterator = currentSheet.rowIterator();
        }

        if (rowIterator.hasNext() && !isRowBlank()) {
            hasNext = true;
        }

        return hasNext;
    }


    /**
     * Determines if a row is blank.
     *
     * @return true if a row is blank, false if not
     */
    private boolean isRowBlank() {

        Row row = currentSheet.getRow(rowIndex);

        if (row == null) {
            return true;
        }

        boolean isRowBlank = false;
        String rowAsString = "";

        for (Iterator<Cell> cit = (Iterator<Cell>) row.cellIterator(); cit.hasNext();) {

            Cell cell = cit.next();

            String cellValue = this.getCellValue(cell);

            rowAsString += cellValue;
        }

        if (StringUtils.isEmpty(rowAsString)) {
            isRowBlank = true;
        }

        return isRowBlank;
    }

    /**
     * Gets the String value of a cell, no matter its type.
     *
     * @param cell the Cell to read.
     * @return the String value of the Cell.
     */
    private String getCellValue(final Cell cell) {

        String cellValue = null;

        try {
            CellType cellType;
            cellType = cell.getCellType();

            switch (cellType) {

                case BLANK:
                    cellValue = "";
                    break;
                case BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case ERROR:
                    logCellError(cell);
                    break;
                case FORMULA:
                    cellValue = getFormulaCellValue(cell);
                    break;
                case NUMERIC:
                    // This might be a date.
                    if (DateUtil.isCellDateFormatted(cell)) {
                        OUT.trace("isCellDateFormatted is true");
                        cellValue = getDateCellValue(cell);
                        OUT.trace("cellValue = " + cellValue);
                    } else {
                        cellValue = getNumericCellValue(cell);
                    }
                    break;
                case STRING:
                    cellValue = cell.getStringCellValue();
                    break;
                default:
                    throw new IllegalStateException();
            }
        } catch (IllegalStateException ise) {

            OUT.error("couldn't read column " + getCellInfo(cell));
            throw ise;
        }

        return cellValue;
    }

    /**
     * Get the cell value as a date.
     * @param cell the Cell.
     * @return a String containing a formatted date.
     */
    private String getDateCellValue(final Cell cell) {
        double d = cell.getNumericCellValue();
        Date date = DateUtil.getJavaDate(d);
        return dateFormat.format(date);
    }

    /**
     * This code addressed the issue of scientific notation being passed to recipient.
     *
     * @param cell is a Cell object
     * @return a string representing the value of the cell
     */
    private String getNumericCellValue(final Cell cell) {

        BigDecimal fullNumber;
        fullNumber = new BigDecimal(cell.getNumericCellValue());

        return fullNumber.toPlainString();
    }

    /**
     * Evaluates formula cells and returns the appropriate value.  Handles all of the cell types supported
     * for Formulas based on the most recent version of POI.
     *
     * @param cell is a Cell object
     * @return a string representing the value of the cell
     */
    private String getFormulaCellValue(final Cell cell) {

        String cellValue = null;

        switch (cell.getCachedFormulaResultType()) {
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case NUMERIC:
                cellValue = getNumericCellValue(cell);
                break;
            case BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case ERROR:
                logCellError(cell);
                break;
            default:
                throw new IllegalStateException();
        }

        return cellValue;
    }

    /**
     * Logs a warning on a cell.  Could be updated to switch on type of logging statement.
     *
     * @param cell is a Cell object
     */
    private void logCellError(final Cell cell) {

        OUT.warn("error in cell " + getCellInfo(cell));
    }

    /**
     * Return information about a cell for use is logging, etc.
     * @param cell is a Cell object
     * @return a String representing cell information
     */
    private String getCellInfo(final Cell cell) {

        return cell.getColumnIndex()
                + ", in row " + rowIndex
                + ", cell type " + cell.getCellType();
    }

    /**
     * Gets the next row.
     * @return the next row as a Map.
     */
    public Map<Integer, String> doNext() {
        Row row = rowIterator.next();

        this.clearCurrentRowMap();

        for (Iterator<Cell> cit = row.cellIterator(); cit.hasNext();) {

            Cell cell = cit.next();

            currentRowMap.put(cell.getColumnIndex(),
                    this.getCellValue(cell));
        }

        this.rowIndex++;
        return currentRowMap;
    }

    public long getRowIndex() {
        return this.rowIndex;
    }

    /**
     * Reads the top row in the current view.
     * @throws IOException if it's unable to read the top row.
     */
    public void top() throws IOException {
        this.rowIterator = null;
        this.rowIndex = 0;

        if (this.hasHeader()) {
            this.readHeader();
        }
    }

    /**
     * Returns whether or not views are supported.
     * @return true, since the ExcelAdapter supports views, since they are sheets.
     */
    public boolean supportsViews() {
        return true;
    }

    /**
     * Gets the next record in the input.
     * @param getTime determines if the time should be returned.
     * @return a map of the next record indexed by column number
     */
    public Map<Integer, String> next(final boolean getTime) {

        Map<Integer, String> fieldMap = this.doNext(getTime);

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
     * Gets the next row.
     * @param getTime determines if the time should be returned.
     * @return the next row as a Map.
     */
    public Map<Integer, String> doNext(final boolean getTime) {
        Row row = rowIterator.next();

        this.clearCurrentRowMap();

        for (Iterator<Cell> cit = row.cellIterator(); cit.hasNext();) {

            Cell cell = cit.next();

            currentRowMap.put(cell.getColumnIndex(),
                    this.getCellValue(cell, getTime));
        }

        this.rowIndex++;
        return currentRowMap;
    }

    /**
     * Gets the String value of a cell, no matter its type.
     *
     * @param cell the Cell to read.
     * @param getTime determines if the time should be returned.
     * @return the String value of the Cell.
     */
    private String getCellValue(final Cell cell, final boolean getTime) {

        String cellValue = null;

        try {
            CellType cellType;
            cellType = cell.getCellType();

            switch (cellType) {

                case BLANK:
                    cellValue = "";
                    break;
                case BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case ERROR:
                    logCellError(cell);
                    break;
                case FORMULA:
                    cellValue = getFormulaCellValue(cell);
                    break;
                case NUMERIC:
                    // This might be a date.
                    if (DateUtil.isCellDateFormatted(cell)) {
                        OUT.trace("isCellDateFormatted is true");
                        if (getTime) {
                            cellValue = getDateTimeCellValue(cell);
                        } else {
                            cellValue = getDateCellValue(cell);
                        }

                        OUT.trace("cellValue = " + cellValue);
                    } else {
                        cellValue = getNumericCellValue(cell);
                    }
                    break;
                case STRING:
                    cellValue = cell.getStringCellValue();
                    break;
                default:
                    throw new IllegalStateException();
            }
        } catch (IllegalStateException ise) {

            OUT.error("couldn't read column " + getCellInfo(cell));
            throw ise;
        }

        return cellValue;
    }

    /**
     * Get the cell value as a date/time.
     * @param cell the Cell.
     * @return a String containing a formatted date.
     */
    private String getDateTimeCellValue(final Cell cell) {
        double d = cell.getNumericCellValue();
        Date date = DateUtil.getJavaDate(d);
        return dateTimeFormat.format(date);
    }
}


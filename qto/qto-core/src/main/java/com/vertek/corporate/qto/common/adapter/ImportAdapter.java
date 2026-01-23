package com.vertek.corporate.qto.common.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

/** @author dkelly created Nov 10, 2009 */
public interface ImportAdapter extends BatchAdapter, Iterator<Map<Integer, String>> {

    /**
     * Creates a connection to the data.  For example, it could open a file or create a JDBC connection.
     * <p/>
     * For individual adapters the practice is to normalize various exceptions to an IO exception. Some adapters provide
     * richer feedback than others. It's possible that at some point we'll model something richer here but not at this
     * time. For example, if ExcelAdapter gets a text file versus Excel file we throw an IO exception even though
     * there's information that would distinguish the error from a standard IO exception.  It's all the same here.
     * @throws IOException when opening the connection fails.
     * @throws InvalidFileFormatException when the file format is incorrect.
     */
    void openForRead() throws IOException, InvalidFileFormatException;

    /**
     * Uses the provided InputStream to read data.  Please not that validation isn't as thorough (e.g. we can't inspect
     * file extensions, etc).
     * @param inputStream an initialized InputStream
     * @throws IOException when errors occur
     * @throws InvalidFileFormatException when the file format is incorrect
     */
    void openForRead(InputStream inputStream) throws IOException, InvalidFileFormatException;

    /**
     * Go to the top of the file.
     * @throws IOException in certain cases.
     */
    void top() throws IOException;

    /**
     * Asserts that the column count is within range for each record read from the input file.  If the column count
     * doesn't match then a ColumnCountException is thrown in next().
     * @param columnMin min column.
     * @param columnMax max column
     */
    void assertColumnRange(int columnMin, int columnMax);

    /**
     * Asserts that the column count is at or above the minimum for each record read from the input file.  If the column
     * count doesn't match then a ColumnCountException is thrown in next().
     * @param columnMin is the minimum required columns
     */
    void assertColumnMinimum(int columnMin);

    /**
     * Asserts that the column count is at or below the maximum for each record read from the input file.  If the column
     * count doesn't match then a ColumnCountException is thrown in next().
     * @param columnMax is the maximum required columns
     */
    void assertColumnMaximum(int columnMax);

    /**
     * Determines whether or not the input is trimmed automatically.  Also controls applies trimming for various API
     * calls, such as getColumnByName().  Adapters will default trimming to true.
     * @param isTrimming is true when trimming is desired, false otherwise.
     */
    void setTrimming(boolean isTrimming);
}


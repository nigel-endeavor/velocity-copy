package com.vertek.corporate.qto.report;

import com.vertek.corporate.qto.common.SecurityUtils;
import com.vertek.corporate.qto.notification.NotificationManager;
import com.vertek.corporate.qto.subject.Subject;
import com.vertek.corporate.qto.subject.SubjectManager;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Stateless
public class ReportBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportBuilder.class);

    @Inject
    private ReportMetaDataManager inventoryReportViewManager;

    @Inject
    private NotificationManager notificationManager;

    @Inject
    private SubjectManager subjectManager;

    private static CellStyle createCurrencyCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        cellStyle.setDataFormat(format.getFormat("$#,##0.00"));
        return cellStyle;
    }

    private static CellStyle createDateCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        cellStyle.setDataFormat(format.getFormat("mm/dd/yyyy"));
        return cellStyle;
    }

    /**
     * Creates a {@link CellStyle} for the worksheet's Header Cells.
     *
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
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setFont(headerFont);

        return style;
    }


    public Workbook createNativeReport(final String name, final String[] headers, final List<Object[]> data) throws IllegalAccessException {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(name);
        Row headerRow = sheet.createRow(0);
        CellStyle headerStyle = getHeaderStyle(workbook);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // Create data rows
        int rowNum = 1;
        for (Object[] rowData : data) {
            Row row = sheet.createRow(rowNum++);
            for (int i = 0; i < rowData.length; i++) {
                Cell cell = row.createCell(i);
                Object value = rowData[i];
                if (value != null) {
                    if (value instanceof BigDecimal) {
                        cell.setCellValue(((Number) value).doubleValue());
                        cell.setCellStyle(createCurrencyCellStyle(workbook));
                    } else if (value instanceof Date) {
                        cell.setCellValue((Date) value);
                        cell.setCellStyle(createDateCellStyle(workbook));
                    } else {
                        cell.setCellValue(value.toString());
                    }
                } else {
                    cell.setCellValue("");
                }
            }
        }
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
        sheet.setAutoFilter(new CellRangeAddress(0, 0, 0, headers.length - 1));
        sheet.createFreezePane(2, 1);
        Subject subject = subjectManager.findByEmailAddress(SecurityUtils.getLoggedInUser());
        notificationManager.create(subject.getId(), "Reports", "Your " + name + " is finished.", "priority_high", null);
        return workbook;
    }
}

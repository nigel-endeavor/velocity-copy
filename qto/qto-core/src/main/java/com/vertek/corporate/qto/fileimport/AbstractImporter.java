package com.vertek.corporate.qto.fileimport;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.vertek.corporate.qto.attachment.FileAttachment;
import com.vertek.corporate.qto.attachment.FileAttachmentContent;
import com.vertek.corporate.qto.attachment.FileAttachmentManager;
import com.vertek.corporate.qto.common.adapter.ExcelAdapter;
import com.vertek.corporate.qto.common.lookup.LookupValue;
import com.vertek.corporate.qto.common.lookup.LookupValueManager;
import com.vertek.corporate.qto.common.mapping.ValidatingSourceMapper;
import com.vertek.corporate.qto.fileimport.importactivity.ImportActivity;
import com.vertek.corporate.qto.fileimport.importactivity.ImportActivityManager;
import com.vertek.corporate.qto.fileimport.importactivity.ImportActivityWebsocket;
import com.vertek.corporate.qto.notification.NotificationManager;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.Resource;
import jakarta.ejb.EJBContext;
import jakarta.inject.Inject;
import jakarta.transaction.UserTransaction;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Contains methods and fields common to all importers.
 * @author rcasey
 * @since 9/1/2023
 */
public abstract class AbstractImporter {

    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractImporter.class);

    /** Context from which we can get a transaction. */
    @Resource
    protected EJBContext ctx;

    @Inject
    protected ImportActivityManager importActivityManager;

    @Inject
    private FileAttachmentManager fileAttachmentManager;

    @Inject
    protected LookupValueManager lookupValueManager;

    @Inject
    private ImportActivityWebsocket importActivityWebsocket;

    @Inject
    private NotificationManager notificationManager;

    @Inject
    private ImportTemplateBuilder importTemplateBuilder;

    protected Workbook errorWorkbook;

    protected Map<String, String> lookupValueMap;

    /**
     * Return true if the spreadsheet contains the correct headers for a row's module coverage.
     * @param adapter the adapter.
     * @return true if expected columns are present, false if not.
     */
    protected boolean hasExpectedColumns(final ExcelAdapter adapter) {
        List<String> expectedCols = getExpectedColumns();
        List<String> spreadsheetCols = adapter.getHeader();
        List<String> missingCols = Lists.newArrayList();

        boolean hasCols = true;

        for (String col : expectedCols) {
            LOGGER.trace("Checking for col {}", col);
            if (!spreadsheetCols.contains(col)) {
                hasCols = false;
                missingCols.add(col);
                LOGGER.error("Missing col {}", col);
            }
        }

        return hasCols;
    }

    /**
     * Create and return an Excel adapter with the content of a FileAttachment object.
     * @param fileAttachment a TenantOwnedFileAttachment.
     * @return an ExcelAdapter.
     * @throws Exception if an exception.
     */
    protected ExcelAdapter createExcelAdapter(final FileAttachment fileAttachment) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] data;

        IOUtils.copy(new ByteArrayInputStream(fileAttachment.getContent().getData()), baos);
        data = baos.toByteArray();

        InputStream inputStream = new ByteArrayInputStream(data);

        return getExcelAdapter(inputStream);
    }

    /**
     * Create and return an ExcelAdapter.
     * @param inputStream the stream.
     * @return an Adapter.
     * @throws Exception if something goes wrong.
     */
    private ExcelAdapter getExcelAdapter(final InputStream inputStream) throws Exception {
        try {
            ExcelAdapter adapter = new ExcelAdapter();
            adapter.setHasHeader(true);
            adapter.setTrimming(true);
            adapter.openForRead(inputStream);
            adapter.setView(adapter.getViewNames().get(0));
            adapter.openView();
            return adapter;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Gets the lowest level cause of an exception.
     * @param e the exception.
     * @return the lowest level cause message.
     */
    protected String getExceptionCauseMessage(Throwable e) {
        Throwable cause = e;
        while (cause.getCause() != null) {
            cause = cause.getCause();
        }
        return cause.getMessage();
    }

    /**
     * Return a list of columns.
     * @return List<String>
     */
    protected abstract List<String> getExpectedColumns();

    protected abstract String getTemplateName();

    protected abstract String getImportTypeName();

    protected abstract List<String> validateRow(Map<String, ValidatingSourceMapper> sourceMappers, Long tenantId);

    protected abstract void importRow(Map<String, ValidatingSourceMapper> sourceMappers, ImportActivity importActivity) throws Exception;

    /**
     * Import inventory information from spreadsheet.
     * @param id file attachment id
     * @return ImportActivity
     */
    public ImportActivity importFile(final Long id) {
        int numAdded;
        UserTransaction dbTrans = ctx.getUserTransaction();
        ImportActivity updatedImportActivity = null;
        ImportActivity importActivity = null;
        errorWorkbook = null;
        lookupValueMap = new HashMap<>();

        try {
            dbTrans.begin();

            importActivity = importActivityManager.retrieve(id);

            FileAttachment fileAttachment = importActivity.getFileAttachment();
            ExcelAdapter adapter = createExcelAdapter(fileAttachment);
            Map<String, ValidatingSourceMapper> sourceMappers = buildSourceMappers(adapter);

            importActivity.setStatus(ImportActivityStatus.PROCESSING);
            importActivity.setImportStartDate(new Date());
            updatedImportActivity = importActivityManager.edit(importActivity);
            dbTrans.commit();

            numAdded = 0;

            if (hasExpectedColumns(adapter)) {
                LOGGER.debug("Processing import spreadsheet: {}", fileAttachment.getName());
                importActivity.setNumProcessed(0L);

                while (adapter.hasNext()) {
                    adapter.next(true);
                    dbTrans.begin();
                    try {
                        long startTime = 0;
                        if (LOGGER.isDebugEnabled()) {
                            startTime = System.currentTimeMillis();
                        }
                        List<String> errors = checkRequired(sourceMappers);
                        List<String> validationErrors = validateRow(sourceMappers, importActivity.getTenantId());
                        errors.addAll(validationErrors);
                        if (!errors.isEmpty()) {
                            writeRowToErrorWorkbook(errors, sourceMappers, fileAttachment.getTenantId());
                            updatedImportActivity.setNumFailed(updatedImportActivity.getNumFailed() + 1);
                        } else {
                            writeRowToErrorWorkbook(null, sourceMappers, fileAttachment.getTenantId());
                            importRow(sourceMappers, importActivity);
                            numAdded++;
                            updatedImportActivity.setNumSuccessful(updatedImportActivity.getNumSuccessful() + 1);
                        }
                        importActivity.setNumProcessed(importActivity.getNumProcessed() + 1);
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("Row {} processed in {} ms", importActivity.getNumProcessed(), System.currentTimeMillis() - startTime);
                        }
                    } catch (Exception e) {
                        LOGGER.error("Unexpected error during import", e);
                        dbTrans.rollback();
                        dbTrans.begin();
                        writeRowToErrorWorkbook(Arrays.asList(getExceptionCauseMessage(e)), sourceMappers, fileAttachment.getTenantId());
                        updatedImportActivity.setNumFailed(updatedImportActivity.getNumFailed() + 1);
                    }
                    dbTrans.commit();
                }

                LOGGER.debug("Finished processing import spreadsheet: {}", fileAttachment.getName());

                dbTrans.begin();
                updatedImportActivity.setImportEndDate(new Date());
                String statusDetails = "Rows successfully processed: " + importActivity.getNumSuccessful()
                        + "\nRows with errors: " + importActivity.getNumFailed()
                        + "\n" + getImportTypeName() + " Added: " + numAdded;
                updatedImportActivity.setStatusDetails(statusDetails);
                if (importActivity.getNumFailed() > 0) {
                    updatedImportActivity.setStatus(ImportActivityStatus.PROCESSED_WITH_ERRORS);
                } else {
                    updatedImportActivity.setStatus(ImportActivityStatus.PROCESSED_SUCCESSFULLY);
                }

                if (updatedImportActivity.getNumFailed() > 0) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    errorWorkbook.write(baos);
                    byte[] data = baos.toByteArray();
                    FileAttachment errorFileAttachment = new FileAttachment();
                    errorFileAttachment.setName("i90-" + importActivity.getImportType().toLowerCase() + "-import-errors.xlsx");
                    errorFileAttachment.setDescription("Master Customer Import Errors");
                    errorFileAttachment.setMimeType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                    errorFileAttachment.setSize((long) data.length);
                    errorFileAttachment.setTenantId(importActivity.getTenantId());
                    errorFileAttachment.setContent(new FileAttachmentContent(data));
                    errorFileAttachment.setUploadedByUserName(importActivity.getFileAttachment().getUploadedByUserName());
                    errorFileAttachment.setUploadDate(new Date());
                    FileAttachment createdErrorFileAttachment = fileAttachmentManager.create(errorFileAttachment);
                    updatedImportActivity.setErrorFileAttachment(createdErrorFileAttachment);
                }

                updatedImportActivity = importActivityManager.edit(updatedImportActivity);
                dbTrans.commit();

            } else {
                LOGGER.error("File does not have expected columns");
                dbTrans.begin();
                importActivity.setImportEndDate(new Date());
                importActivity.setStatus(ImportActivityStatus.UPLOAD_ERROR);
                importActivity.setStatusDetails("File does not have expected columns");
                updatedImportActivity = importActivityManager.edit(importActivity);
                dbTrans.commit();
            }

        } catch (Exception e) {
            LOGGER.error("Error importing file: ", e);
            try {
                dbTrans.rollback();
                if (importActivity != null) {
                    dbTrans.begin();
                    importActivity.setStatus(ImportActivityStatus.SYSTEM_ERROR);
                    importActivity.setStatusDetails(e.getMessage());
                    importActivityManager.edit(importActivity);
                    dbTrans.commit();
                }
            } catch (Exception e1) {
                LOGGER.error("Error rolling back transaction {}", e.getMessage());
            }
        }

        if (updatedImportActivity != null && !updatedImportActivity.getImportType().equals("Order")) {
            importActivityWebsocket.sendRefreshMessage();
            notificationManager.create(
                    updatedImportActivity.getSubjectId(),
                    updatedImportActivity.getImportType() + " Import " + updatedImportActivity.getStatus(),
                    updatedImportActivity.getStatusDetails(),
                    "upload_file",
                    "import");
        }

        return updatedImportActivity;
    }

    /**
     * Check for required columns.
     * @param sourceMappers the source mappers.
     * @return a list of errors.
     */
    protected List<String> checkRequired(final Map<String, ValidatingSourceMapper> sourceMappers) throws Exception {
        List<String> errors = Lists.newArrayList();
        List<String> cols = getExpectedColumns();
        for (int i = 0; i < cols.size(); i++) {
            String col = cols.get(i);
            if (sourceMappers.get(col).isRequired() && Strings.isNullOrEmpty(sourceMappers.get(col).getNonValidatedValue())) {
                errors.add(sourceMappers.get(col).getSourceIndex() + " is required");
            }
        }
        return errors;
    }

    /**
     * Build and return the map of source mappers.
     * @param adapter the adapter.
     * @return a Map of ValidatingSourceMapper objects.
     */
    protected abstract Map<String, ValidatingSourceMapper> buildSourceMappers(ExcelAdapter adapter);

    protected void writeRowToErrorWorkbook(final List<String> errors, final Map<String, ValidatingSourceMapper> sourceMappers, final Long tenantId) throws Exception {
        Workbook workbook = getErrorWorkbook(tenantId);
        Sheet sheet = workbook.getSheetAt(0);
        Row newRow = sheet.createRow(sheet.getLastRowNum() + 1);
        if (errors != null && !errors.isEmpty()) {
            newRow.createCell(0).setCellValue(String.join(", \n", errors));
            sheet.autoSizeColumn(0);
        }
        List<String> cols = getExpectedColumns();

        DecimalFormat df = new DecimalFormat("#.##");

        for (int i = 0; i < cols.size(); i++) {
            Cell newCell = newRow.createCell(i + 1);
            if (Pattern.compile("^(\\d{1,2})/(\\d{1,2})/(\\d{4})$").matcher(sourceMappers.get(cols.get(i)).getNonValidatedValue()).matches()
                || Pattern.compile("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2} [APap][Mm]$").matcher(sourceMappers.get(cols.get(i)).getNonValidatedValue()).matches()) {
                try {
                    DateFormat inputDf = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
                    DateFormat outputDf = new SimpleDateFormat("MM/dd/yyyy");
                    Date date = inputDf.parse(sourceMappers.get(cols.get(i)).getNonValidatedValue());
                    String outputDateString = outputDf.format(date);
                    newCell.setCellValue(outputDateString);
                } catch (Exception e) {
                    newCell.setCellValue(sourceMappers.get(cols.get(i)).getNonValidatedValue());
                }
            } else if (Pattern.compile("^\\d+\\.\\d+$").matcher(sourceMappers.get(cols.get(i)).getNonValidatedValue()).matches()) {
                double value = Double.parseDouble(sourceMappers.get(cols.get(i)).getNonValidatedValue());
                newCell.setCellValue(df.format(value));
            }  else {
                newCell.setCellValue(sourceMappers.get(cols.get(i)).getNonValidatedValue());
            }
        }
    }

    private Workbook getErrorWorkbook(final Long tenantId) throws Exception {
        if (errorWorkbook != null) {
            return errorWorkbook;
        }
        errorWorkbook = importTemplateBuilder.getTemplate(getTemplateName(), tenantId);
        return errorWorkbook;
    }

    /**
     * Validate a lookup value column.
     * @param errorList running list of errors.
     * @param sourceMapper the source mapper for the given column.
     * @param typeCode the lookup type code.
     * @param tenantId the tenant id.
     * @return updated list of errors.
     */
    protected List<String> validateLookupValueCol(final List<String> errorList, final ValidatingSourceMapper sourceMapper, final String typeCode, final Long tenantId) {
        ArrayList<String> errors = new ArrayList<>(errorList);
        try {
            String value = sourceMapper.getNonValidatedValue();
            if (!Strings.isNullOrEmpty(value)) {
                LookupValue lookupValue = lookupValueManager.retrieveByTypeAndValue(typeCode, value);
                if (lookupValue == null) {
                    errors.add("Invalid value for " + sourceMapper.getSourceIndex());
                } else {
                    lookupValueMap.put(sourceMapper.getValue(), lookupValue.getValue());
                }
            }
        } catch (Exception e) {
            errors.add("Could not parse " + sourceMapper.getSourceIndex());
        }
        return errors;
    }

    /**
     * Validates a Yes/No columns.
     * @param errorList running list of errors.
     * @param sourceMapper the source mapper for the given column.
     * @return updated list of errors.
     */
    protected List<String> validateYesNoCol(final List<String> errorList, final ValidatingSourceMapper sourceMapper) {
        ArrayList<String> errors = new ArrayList<>(errorList);
        try {
            String value = sourceMapper.getNonValidatedValue();
            if (!Strings.isNullOrEmpty(value)
                    && (!value.equalsIgnoreCase("yes") && !value.equalsIgnoreCase("no"))) {
                errors.add("Invalid value for " + sourceMapper.getSourceIndex());
            }
        } catch (Exception e) {
            errors.add("Could not parse " + sourceMapper.getSourceIndex());
        }
        return errors;
    }

    /**
     * Validates an IP Format column.
     * @param errorList running list of errors.
     * @param sourceMapper the source mapper for the given column.
     * @return updated list of errors.
     */
    protected List<String> validateIpFormatCol(final List<String> errorList, final ValidatingSourceMapper sourceMapper) {
        ArrayList<String> errors = new ArrayList<>(errorList);
        try {
            String ipFormat = sourceMapper.getNonValidatedValue();
            if (!Strings.isNullOrEmpty(ipFormat)
                    && (!"IPv4".equalsIgnoreCase(ipFormat) && !"IPv6".equalsIgnoreCase(ipFormat))) {
                errors.add("Invalid value for IP Format");
            }
        } catch (Exception e) {
            errors.add("Could not parse IP Format");
        }
        return errors;
    }

    /**
     * Validate a date column.
     * @param errorList running list of errors.
     * @param sourceMapper the source mapper for the given column.
     * @return updated list of errors.
     */
    protected List<String> validateDateCol(final List<String> errorList, final ValidatingSourceMapper sourceMapper) {
        List<String> errors = new ArrayList<>(errorList);
        String[] dateFormats = {
                "yyyy-MM-dd",
                "MM/dd/yyyy"
        };
        Date parsed = null;
        for (String dateFormat : dateFormats) {
            SimpleDateFormat df = new SimpleDateFormat(dateFormat);
            try {
                parsed = df.parse(sourceMapper.getNonValidatedValue());
                break;
            } catch (Exception e) {
                // do nothing
            }
        }
        try {
            if (parsed == null && !Strings.isNullOrEmpty(sourceMapper.getNonValidatedValue())) {
                errors.add("Could not parse " + sourceMapper.getSourceIndex());
            }
        } catch (Exception e) {
            errors.add("Could not parse " + sourceMapper.getSourceIndex());
        }
        return errors;
    }

    /**
     * Validate a decimal column.
     * @param errorList running list of errors.
     * @param sourceMapper the source mapper for the given column.
     * @return updated list of errors.
     */
    protected List<String> validateBigDecimalCol(final List<String> errorList, final ValidatingSourceMapper sourceMapper) {
        List<String> errors = new ArrayList<>(errorList);
        try {
            if (!Strings.isNullOrEmpty(sourceMapper.getNonValidatedValue())) {
                new BigDecimal(sourceMapper.getNonValidatedValue());
            }
        } catch (Exception e) {
            errors.add("Could not parse " + sourceMapper.getSourceIndex());
        }
        return errors;
    }

    /**
     * Validate a long column.
     * @param errorList running list of errors.
     * @param sourceMapper the source mapper for the given column.
     * @return updated list of errors.
     */
    protected List<String> validateLongCol(final List<String> errorList, final ValidatingSourceMapper sourceMapper) {
        List<String> errors = new ArrayList<>(errorList);
        try {
            if (!Strings.isNullOrEmpty(sourceMapper.getNonValidatedValue())) {
                Long.parseLong(sourceMapper.getNonValidatedValue());
            }
        } catch (Exception e) {
            errors.add("Could not parse " + sourceMapper.getSourceIndex());
        }
        return errors;
    }

    protected List<String> validateZipcodeCol(final List<String> errorList, final ValidatingSourceMapper sourceMapper) {

        List<String> errors = new ArrayList<>(errorList);
        String usZipRegex = "\\d{5}(-\\d{4})?";
        String caPostalRegex = "[A-Za-z]\\d[A-Za-z] \\d[A-Za-z]\\d";
        try {
            if (!Pattern.matches(usZipRegex, sourceMapper.getNonValidatedValue())
                    && !Pattern.matches(caPostalRegex, sourceMapper.getNonValidatedValue())) {
                errors.add("Invalid value for " + sourceMapper.getSourceIndex());
            }
        } catch (Exception e) {
            errors.add("Could not parse " + sourceMapper.getSourceIndex());
        }
        return errors;
    }

    /**
     * Validates the state lookup value column.
     * @param errorList running list of errors.
     * @param sourceMapper the source mapper for the given column.
     * @param tenantId the tenant id.
     * @return updated list of errors.
     */
    protected List<String> validateStateLookupValueCol(final List<String> errorList, final ValidatingSourceMapper sourceMapper, final Long tenantId) {
        ArrayList<String> errors = new ArrayList<>(errorList);
        try {
            String value = sourceMapper.getNonValidatedValue();
            if (!Strings.isNullOrEmpty(value)) {
                List<LookupValue> lookupValueList = lookupValueManager.findByTypeCodeAndTenantId("STATE_PROVINCE", tenantId);
                LookupValue lookupValue = lookupValueList.stream().filter(lv -> lv.getValue().equals(value) || lv.getDisplay().equals(value)).findFirst().orElse(null);
                if (lookupValue == null) {
                    errors.add("Invalid value for " + sourceMapper.getSourceIndex());
                } else {
                    lookupValueMap.put(sourceMapper.getValue(), lookupValue.getValue());
                }
            }
        } catch (Exception e) {
            errors.add("Could not parse " + sourceMapper.getSourceIndex());
        }
        return errors;
    }

    protected Date parseDate(final String date) {
        String[] dateFormats = {
                "yyyy-MM-dd",
                "MM/dd/yyyy"
        };
        Date parsed = null;
        for (String dateFormat : dateFormats) {
            SimpleDateFormat df = new SimpleDateFormat(dateFormat);
            try {
                parsed = df.parse(date);
                break;
            } catch (Exception e) {
                // do nothing
            }
        }
        return parsed;
    }

    protected Long parseLong(final String value) {
        Long result = null;
        if (!Strings.isNullOrEmpty(value)) {
            result = Long.parseLong(value);
        }
        return result;
    }

    protected boolean parseBoolean(final String value) {
        return !Strings.isNullOrEmpty(value) && ("true".equalsIgnoreCase(value) || "yes".equalsIgnoreCase(value));
    }

    protected String parseIpFormat(final String ipFormat) {
        if ("IPv4".equalsIgnoreCase(ipFormat)) {
            return "IPv4";
        } else if ("IPv6".equalsIgnoreCase(ipFormat)) {
            return "IPv6";
        } else {
            return null;
        }
    }

    protected String parseYesNo(final String yesNo) {
        if ("Yes".equalsIgnoreCase(yesNo)) {
            return "Yes";
        } else if ("No".equalsIgnoreCase(yesNo)) {
            return "No";
        } else {
            return null;
        }
    }
}

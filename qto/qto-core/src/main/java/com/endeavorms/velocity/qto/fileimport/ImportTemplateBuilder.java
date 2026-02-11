package com.endeavorms.velocity.qto.fileimport;

import com.google.common.base.Strings;
import com.endeavorms.velocity.qto.company.Company;
import com.endeavorms.velocity.qto.company.CompanyManager;
import com.endeavorms.velocity.qto.service.ServiceType;
import com.endeavorms.velocity.qto.common.lookup.LookupValue;
import com.endeavorms.velocity.qto.common.lookup.LookupValueManager;
import com.endeavorms.velocity.qto.subject.Subject;
import com.endeavorms.velocity.qto.subject.SubjectManager;
import com.endeavorms.velocity.qto.subject.SubjectSearchCriteria;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ImportTemplateBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImportTemplateBuilder.class);

    @Inject
    private LookupValueManager lookupValueManager;

    @Inject
    private CompanyManager companyManager;

    @Inject
    private SubjectManager subjectManager;

    /** import template file names. */
    private static final String ORDER_IMPORT = "i90-order-import-template.xlsx";
    private static final String IMPORT_TEMPLATE_MASTER_CUSTOMER = "i90-master-customer-import-template.xlsx";
    private static final String IMPORT_TEMPLATE_END_CUSTOMER = "i90-end-customer-import-template.xlsx";
    private static final String IMPORT_TEMPLATE_BROADBAND = "i90-broadband-service-import-template.xlsx";
    private static final String IMPORT_TEMPLATE_DIA = "i90-dia-service-import-template.xlsx";
    private static final String IMPORT_TEMPLATE_UCAAS = "i90-ucaas-service-import-template.xlsx";
    private static final String IMPORT_TEMPLATE_4G5G = "i90-4g5g-service-import-template.xlsx";
    private static final String IMPORT_TEMPLATE_CROSSCONNECT = "i90-crossconnect-service-import-template.xlsx";
    private static final String IMPORT_TEMPLATE_ETHERNET = "i90-ethernet-service-import-template.xlsx";
    private static final String IMPORT_TEMPLATE_TELEVISION = "i90-television-service-import-template.xlsx";
    private static final String IMPORT_TEMPLATE_MPLS = "i90-mpls-service-import-template.xlsx";

    public Workbook getTemplate(final String type, final Long tenantId) {
        String templateName = getTemplateName(type);
        Workbook workbook;
        try {
            // Load the workbook from the template
            InputStream inputStream = getClass().getResourceAsStream(templateName);
            workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0); // Get the first sheet
            Row headerRow = sheet.getRow(3); // Get the header row (row index 3, zero-based)

            // Create sheets for storing list items and end customers
            Sheet listSheet = workbook.createSheet("ListSheet");

            int i = 1; // Column index for headers
            Cell currentCell = headerRow.getCell(i); // Get the first header cell
            while (currentCell != null) {
                String columnHeader = currentCell.getStringCellValue(); // Get the header text

                List<String> dropDownValues = getDropDownValues(columnHeader, tenantId); // Get the dropdown values for the column

                if (!dropDownValues.isEmpty()) {
                    // Populate the list sheet with the dropdown values
                    for (int r = 0; r < dropDownValues.size(); r++) {
                        String value = dropDownValues.get(r);
                        if (listSheet.getRow(r) == null) {
                            listSheet.createRow(r);
                        }
                        Row row = listSheet.getRow(r);
                        row.createCell(i).setCellValue(value);
                    }

                    // Create a named range for the dropdown values
                    String colRef = CellReference.convertNumToColString(i);
                    String reference = "ListSheet!$" + colRef + "$1:$" + colRef + "$" + dropDownValues.size();
                    Name namedRange = workbook.createName();
                    namedRange.setNameName("ListItems_" + i); // Unique named range for each column
                    namedRange.setRefersToFormula(reference);

                    // Create the data validation for the current column using the named range
                    DataValidationHelper validationHelper = sheet.getDataValidationHelper();
                    DataValidationConstraint constraint = validationHelper.createFormulaListConstraint("ListItems_" + i);
                    CellRangeAddressList addressList = new CellRangeAddressList(9, 1000, i, i); // Apply to the column
                    DataValidation dataValidation = validationHelper.createValidation(constraint, addressList);

                    dataValidation.setSuppressDropDownArrow(true); // Suppress dropdown arrow
                    sheet.addValidationData(dataValidation); // Add validation to the sheet
                }

                i++; // Move to the next column
                currentCell = headerRow.getCell(i); // Get the next header cell
            }

            // Hide the ListSheet
            workbook.setSheetHidden(workbook.getSheetIndex("ListSheet"), true);

            // Set the first sheet as active
            workbook.setActiveSheet(0);

        } catch (Exception e) {
            throw new RuntimeException("Error building template: " + templateName, e);
        }

        return workbook;
    }

    public String getTemplateName(final String type) {
        String templateName;
        switch (type) {
            case "Order":
                templateName = ORDER_IMPORT;
                break;
            case "Master Customer":
                templateName = IMPORT_TEMPLATE_MASTER_CUSTOMER;
                break;
            case "End Customer":
                templateName = IMPORT_TEMPLATE_END_CUSTOMER;
                break;
            case "Broadband":
                templateName = IMPORT_TEMPLATE_BROADBAND;
                break;
            case "Television":
                templateName = IMPORT_TEMPLATE_TELEVISION;
                break;
            case "DIA":
                templateName = IMPORT_TEMPLATE_DIA;
                break;
            case "UCaaS":
                templateName = IMPORT_TEMPLATE_UCAAS;
                break;
            case "4G/5G":
                templateName = IMPORT_TEMPLATE_4G5G;
                break;
            case "Cross Connect":
                templateName = IMPORT_TEMPLATE_CROSSCONNECT;
                break;
            case "Ethernet":
                templateName = IMPORT_TEMPLATE_ETHERNET;
                break;
            case "MPLS":
                templateName = IMPORT_TEMPLATE_MPLS;
                break;
            default:
                throw new RuntimeException("Unknown import type: " + type);
        }
        return templateName;
    }

    private List<String> getDropDownValues(final String column, final Long tenantId) {
        LOGGER.debug("Getting dropdown values for column: {}", column);

        if (column.equalsIgnoreCase("Master Customer")) {
            return companyManager.findByTypeAndTenantId("Master Customer", tenantId).stream().map(Company::getName).collect(Collectors.toList());
        }

        if (column.equalsIgnoreCase("End Customer")) {
            return companyManager.findByTypeAndTenantId("End Customer", tenantId).stream().map(Company::getName).collect(Collectors.toList());
        }

        if (column.equalsIgnoreCase("Service Type") || column.equalsIgnoreCase("Service")) {
            return Stream.of(ServiceType.values()).map(ServiceType::getServiceName).collect(Collectors.toList());
        }

        if (column.equalsIgnoreCase("i90 Project Manager")) {
            return subjectManager.findAll(new SubjectSearchCriteria()).stream().map(Subject::getDisplayName).collect(Collectors.toList());
        }

        if (column.equalsIgnoreCase("IP Format")) {
            return List.of("IPv4", "IPv6");
        }

        //yes/no
        if (column.equalsIgnoreCase("Managed Service")
                || column.equalsIgnoreCase("Auto Renewal")
                || column.equalsIgnoreCase("Co-Terminus")
                || column.equalsIgnoreCase("Linked/Bundled")
                || column.equalsIgnoreCase("Commissions ICB")
                || column.equalsIgnoreCase("Engineer Resource")
                || column.equalsIgnoreCase("LOA Required")
                || column.equalsIgnoreCase("Manned")
                || column.equalsIgnoreCase("DVR Included")
                || column.equalsIgnoreCase("Production Impacting")
                || column.equalsIgnoreCase("Submitted in Advance to Provider")
                || column.equalsIgnoreCase("Submitted in Advance to TSD")) {
            return List.of("Yes", "No");
        }

        //lookups
        String typeCode;
        switch (column.toLowerCase()) {
            case "state/province/region":
            case "billing state":
            case "billing state/province/region":
                typeCode = "STATE_PROVINCE";
                break;
            case "country":
            case "billing country":
                typeCode = "COUNTRY";
                break;
            case "provider":
            case "last mile provider":
                typeCode = "PROVIDER";
                break;
            case "service billed to":
                typeCode = "SERVICE_BILLED_TO";
                break;
            case "project name":
                typeCode = "PROJECT_NAME";
                break;
            case "contract term":
                typeCode = "CONTRACT_TERM";
                break;
            case "download speed":
            case "upload speed":
            case "port speed":
            case "mpls access speed":
            case "access speed":
            case "mpls port speed":
            case "circuit upload speed":
            case "circuit download speed":
                typeCode = "SPEED";
                break;
            case "media type":
            case "handoff media type":
                typeCode = "MEDIA_TYPE";
                break;
            case "additional ip block":
            case "wan block":
            case "lan block":
                typeCode = "ADDITIONAL_IP_BLOCK";
                break;
            case "underlying provider":
                typeCode = "UNDERLYING_PROVIDER";
                break;
            case "field services provider":
                typeCode = "FIELD_SERVICES_PROVIDER";
                break;
            case "commission payment type":
                typeCode = "COMMISSIONS_PAYMENT_TYPE";
                break;
            case "notice period for renewal/cancel":
                typeCode = "NOTICE_PERIOD";
                break;
            case "sub agent":
                typeCode = "AGENCY_NAME";
                break;
            case "parent tsd":
                typeCode = "PARENT_TSD";
                break;
            case "handoff fiber mode":
                typeCode = "HANDOFF_FIBER_MODE";
                break;
            case "interface connector":
                typeCode = "INTERFACE_CONNECTOR";
                break;
            case "cable category":
                typeCode = "CABLE_CATEGORY";
                break;
            case "mux":
                typeCode = "MUX";
                break;
            case "mtu":
                typeCode = "MTU";
                break;
            case "product type":
                typeCode = "ETHERNET_PRODUCT_TYPE";
                break;
            case "provider order activation method":
                typeCode = "PROVIDER_ACTIVATION_METHOD";
                break;
            case "mpls type":
                typeCode = "MPLS_TYPE";
                break;
            case "access type":
                typeCode = "ACCESS_TYPE";
                break;
            case "network protocol":
                typeCode = "NETWORK_PROTOCOL";
                break;
            case "xc type":
                typeCode = "CROSS_CONNECT_TYPE";
                break;
            default:
                typeCode = null;
        }

        if (!Strings.isNullOrEmpty(typeCode)) {
            List<String> lookupValues = lookupValueManager.findByTypeCodeAndTenantId(typeCode, tenantId).stream().map(LookupValue::getValue).collect(Collectors.toList());
            if (lookupValues.isEmpty()) {
                LOGGER.debug("No lookup values found for type code: {}", typeCode);
            }
            return lookupValues;
        } else {
            return List.of();
        }
    }

}

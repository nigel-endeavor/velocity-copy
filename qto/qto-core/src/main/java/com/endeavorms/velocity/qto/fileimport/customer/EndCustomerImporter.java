package com.endeavorms.velocity.qto.fileimport.customer;

import com.endeavorms.velocity.qto.common.adapter.ExcelAdapter;
import com.endeavorms.velocity.qto.common.mapping.StringSourceMapper;
import com.endeavorms.velocity.qto.common.mapping.ValidatingSourceMapper;
import com.endeavorms.velocity.qto.company.Company;
import com.endeavorms.velocity.qto.fileimport.importactivity.ImportActivity;

import org.springframework.stereotype.Component;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@TransactionManagement(TransactionManagementType.BEAN)
public class EndCustomerImporter extends AbstractCompanyImporter {
    @Override
    protected void importRow(final Map<String, ValidatingSourceMapper> sourceMappers, final ImportActivity importActivity) throws Exception {
        Long tenantId = importActivity.getTenantId();
        Company masterCompany
                = getCompanyManager().findMasterCustomerByNameAndTenantId(
                        sourceMappers.get(CustomerImportColumns.MASTER_CUSTOMER_NAME).getValue(), tenantId);
        createCompany(sourceMappers, EndCustomerImportColumns.END_CUSTOMER_NAME, tenantId, "End Customer", masterCompany);
    }

    @Override
    protected List<String> validateRow(Map<String, ValidatingSourceMapper> sourceMappers, Long tenantId) {
        List<String> errors = new ArrayList<>();
        //verify master company name exists
        String masterCompanyName = null;
        try {
            masterCompanyName = sourceMappers.get(CustomerImportColumns.MASTER_CUSTOMER_NAME).getValue();
            Company masterCompany = getCompanyManager().findMasterCustomerByNameAndTenantId(masterCompanyName, tenantId);
            if (masterCompany == null) {
                errors.add("Master Customer with name " + masterCompanyName + " doesn't exist");
            } else {
                //verify the end company name is unique for the master company
                try {
                    String companyName = sourceMappers.get(EndCustomerImportColumns.END_CUSTOMER_NAME).getValue();
                    Company company = getCompanyManager()
                            .findEndCustByName(
                                    companyName, tenantId);
                    if (company != null) {
                        errors.add("End Customer with name " + companyName + " already exists");
                    }
                } catch (Exception e) {
                    errors.add("Error validating End Customer name");
                }
            }
        } catch (Exception e) {
            errors.add("Error validating Master Customer name");
        }

        //zip code
        errors = validateZipcodeCol(errors, sourceMappers.get(CustomerImportColumns.BILLING_POSTAL_CODE));

        return errors;
    }

    @Override
    protected List<String> getExpectedColumns() {
        return EndCustomerImportColumns.getExpectedColumns();
    }

    @Override
    protected String getTemplateName() {
        return "End Customer";
    }
    @Override
    protected String getImportTypeName() {
        return "End Customers";
    }

    @Override
    protected Map<String, ValidatingSourceMapper> buildSourceMappers(final ExcelAdapter adapter) {
        Map<String, ValidatingSourceMapper> sourceMappers = super.buildSourceMappers(adapter);
        sourceMappers.put(EndCustomerImportColumns.END_CUSTOMER_NAME, new StringSourceMapper(adapter, EndCustomerImportColumns.END_CUSTOMER_NAME, 100, true));
        return sourceMappers;
    }
}

package com.endeavorms.velocity.qto.fileimport.customer;

import com.endeavorms.velocity.qto.common.mapping.ValidatingSourceMapper;
import com.endeavorms.velocity.qto.company.Company;
import com.endeavorms.velocity.qto.fileimport.importactivity.ImportActivity;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author rcasey
 * @since 9/1/2023
 */
@Component
public class CustomerImporter extends AbstractCompanyImporter {

    @Override
    protected void importRow(final Map<String, ValidatingSourceMapper> sourceMappers, final ImportActivity importActivity) throws Exception {
        Long tenantId = importActivity.getTenantId();
        createCompany(sourceMappers, CustomerImportColumns.MASTER_CUSTOMER_NAME,
                tenantId, "Master Customer", null);
    }


    @Override
    protected List<String> validateRow(Map<String, ValidatingSourceMapper> sourceMappers, Long tenantId) {
        List<String> errors = new ArrayList<>();
        //verify company name is unique
        String companyName;
        try {
            companyName = sourceMappers.get(CustomerImportColumns.MASTER_CUSTOMER_NAME).getValue();
            Company company = getCompanyManager().findMasterCustomerByNameAndTenantId(companyName, tenantId);
            if (company != null) {
                errors.add("Company with name " + companyName + " already exists");
            }

            //zip code
            String zip = sourceMappers.get(CustomerImportColumns.BILLING_POSTAL_CODE).getValue();
            if (zip != null && zip.length() > 0) {
                errors = validateZipcodeCol(errors, sourceMappers.get(CustomerImportColumns.BILLING_POSTAL_CODE));
            }
        } catch (Exception e) {
            errors.add("Error validating Master Customer name");
        }

        return errors;
    }

    @Override
    protected List<String> getExpectedColumns() {
        return CustomerImportColumns.getExpectedColumns();
    }

    @Override
    protected String getTemplateName() {
        return "Master Customer";
    }

    @Override
    protected String getImportTypeName() {
        return "Master Customers";
    }
}

package com.endeavorms.velocity.qto.fileimport;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.fileimport.importactivity.ImportActivity;
import com.endeavorms.velocity.qto.fileimport.importactivity.ImportActivityManager;
import com.endeavorms.velocity.qto.fileimport.importactivity.ImportActivitySearchCriteria;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author rcasey
 * @since 9/1/2023
 */
@Component
@RestController
@RequestMapping("/api/importActivities")
public class ImportActivityResource extends AbstractResource<ImportActivity> {

    @Override
    protected String getResourcePath() {
        return "/importActivities";
    }

    @Autowired
    private ImportActivityManager importActivityManager;

    @GetMapping
    public ResponseEntity<?> getImportActivities(@ModelAttribute final ImportActivitySearchCriteria criteria) {
        ImportActivitySearchCriteria crit = getExportCriteria(criteria);
        PaginatedResult<ImportActivity> result = importActivityManager.findBySearchCriteria(crit);
        return getCollectionResource(result, crit, getLocation(ImportActivityResource.class));
    }
}

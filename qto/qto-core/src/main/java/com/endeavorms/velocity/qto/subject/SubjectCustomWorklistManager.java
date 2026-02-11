package com.endeavorms.velocity.qto.subject;

import com.endeavorms.velocity.qto.common.SecurityUtils;
import com.endeavorms.velocity.qto.common.StandardManager;
import com.endeavorms.velocity.qto.common.Tenant;
import com.endeavorms.velocity.qto.common.TenantSubjectManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class SubjectCustomWorklistManager extends StandardManager<SubjectCustomWorklist> {

    @Inject
    private SubjectCustomWorklistJpaDao dao;

    @Inject CustomWorklistManager customWorklistManager;

    @Inject
    private TenantSubjectManager tenantSubjectManager;

    @Inject
    private SubjectManager subjectManager;

    @Override
    protected SubjectCustomWorklistJpaDao getDao() {
        return dao;
    }

    public List<SubjectCustomWorklist> findByLoggedInUser(final String worklistName) {
        return dao.findByLoggedInUser(worklistName);
    }

    public List<SubjectCustomWorklist> findByNameAndTenant(final String name, final Long tenantId) {
        return dao.findByNameAndTenant(name, tenantId);
    }

    public List<CustomWorklistSubjectDto> findFullCustomWorklist(final String worklistName) {

        //get the full list of shared or owned custom worklists, then we will update that list with the subject data
        //for the records that have entries in the subject custom worklist table for the logged in user
        List<CustomWorklist> customWorklists = customWorklistManager.findSharedOrOwned(worklistName);
        List<CustomWorklistSubjectDto> dtos = customWorklists.stream().map(cw -> {
            CustomWorklistSubjectDto dto = new CustomWorklistSubjectDto();
            dto.setId(cw.getId());
            dto.setAuthorId(cw.getAuthorId());
            dto.setAuthorName(cw.getAuthorName());
            dto.setContent(cw.getContent());
            dto.setShared(cw.isShared());
            dto.setWorklistName(cw.getWorklistName());
            dto.setLastModifiedDate(cw.getLastModifiedDate());
            dto.setName(cw.getName());
            return dto;
        }).collect(Collectors.toList());

        List<SubjectCustomWorklist> subjectCustomWorklists = dao.findByLoggedInUser(worklistName);
        Map<Long, SubjectCustomWorklist> subjectMap = subjectCustomWorklists.stream()
                .collect(Collectors.toMap(SubjectCustomWorklist::getCustomWorklistId, Function.identity()));

        // Step 3: Update DTOs with matching SubjectCustomWorklist data
        for (CustomWorklistSubjectDto dto : dtos) {
            SubjectCustomWorklist subjectCustomWorklist = subjectMap.get(dto.getId());
            if (subjectCustomWorklist != null) {
                dto.setSubjectId(subjectCustomWorklist.getSubjectId());
                dto.setFavorite(subjectCustomWorklist.isFavorite());
                dto.setLastViewedDate(subjectCustomWorklist.getLastViewedDate());
                dto.setSubjectCustomWorklistId(subjectCustomWorklist.getId());
            }
        }
        dtos.sort(Comparator.comparing(CustomWorklistSubjectDto::getLastViewedDate, Comparator.nullsLast(Comparator.reverseOrder())));
        return dtos;
    }

    public void createFromDto(CustomWorklistSubjectDto dto) {
        String username = SecurityUtils.getLoggedInUser();
        Subject subject = subjectManager.findByUsername(username);
        Tenant tenant = tenantSubjectManager.getCurrentTenant();
        dto.setTenantId(tenant.getId());
        dto.setAuthorId(subject.getId());
        dto.setSubjectId(subject.getId());

        CustomWorklist cw = new CustomWorklist();
        cw.setAuthorId(dto.getAuthorId());
        cw.setContent(dto.getContent());
        cw.setShared(dto.isShared());
        cw.setWorklistName(dto.getWorklistName());
        cw.setName(dto.getName());
        cw.setLastModifiedDate(new Date());
        cw.setTenantId(dto.getTenantId());
        cw = customWorklistManager.create(cw);

        SubjectCustomWorklist scw = new SubjectCustomWorklist();
        scw.setCustomWorklistId(cw.getId());
        scw.setSubjectId(dto.getSubjectId());
        scw.setFavorite(dto.isFavorite());
        scw.setLastViewedDate(new Date());
        super.create(scw);
    }

    public void editFromDto(CustomWorklistSubjectDto customWorklist) {
        SubjectCustomWorklist scw = dao.retrieve(customWorklist.getSubjectCustomWorklistId());
        CustomWorklist cw = customWorklistManager.retrieve(customWorklist.getId());
        scw.setFavorite(customWorklist.isFavorite());
        scw.setLastViewedDate(customWorklist.getLastViewedDate());
        super.edit(scw);
        cw.setContent(customWorklist.getContent());
        cw.setLastModifiedDate(new Date());
        cw.setName(customWorklist.getName());
        cw.setShared(customWorklist.isShared());
        customWorklistManager.edit(cw);
    }

    public void editFavorite(CustomWorklistSubjectDto customWorklist) {
        SubjectCustomWorklist scw = dao.retrieve(customWorklist.getSubjectCustomWorklistId());
        scw.setFavorite(customWorklist.isFavorite());
        super.edit(scw);
    }

    public void editLastViewedDate(CustomWorklistSubjectDto customWorklist) {
        SubjectCustomWorklist scw = dao.retrieve(customWorklist.getSubjectCustomWorklistId());
        scw.setLastViewedDate(new Date());
        super.edit(scw);
    }

    public List<SubjectCustomWorklist> findByCustomWorklistId(Long id) {
        return dao.findByCustomWorklistId(id);
    }
}

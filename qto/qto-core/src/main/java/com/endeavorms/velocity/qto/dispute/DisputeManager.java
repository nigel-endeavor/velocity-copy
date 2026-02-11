package com.endeavorms.velocity.qto.dispute;

import com.google.common.base.Strings;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.SecurityUtils;
import com.endeavorms.velocity.qto.common.StandardManager;
import com.endeavorms.velocity.qto.dispute.multidispute.MultiDisputeRequestDto;
import com.endeavorms.velocity.qto.multiedit.request.MultiEditRequestDto;
import com.endeavorms.velocity.qto.multiedit.response.EntityDescription;
import com.endeavorms.velocity.qto.multiedit.response.MultiEditError;
import com.endeavorms.velocity.qto.multiedit.response.MultiEditResponseDto;
import com.endeavorms.velocity.qto.note.DisputeNote;
import com.endeavorms.velocity.qto.note.DisputeNoteManager;
import com.endeavorms.velocity.qto.service.Service;
import com.endeavorms.velocity.qto.service.ServiceManager;
import com.endeavorms.velocity.qto.subject.Subject;
import com.endeavorms.velocity.qto.subject.SubjectManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.endeavorms.velocity.qto.multiedit.MultiEditUtility.containsKey;
import static com.endeavorms.velocity.qto.multiedit.MultiEditUtility.getKeyBigDecimal;
import static com.endeavorms.velocity.qto.multiedit.MultiEditUtility.getKeyDate;
import static com.endeavorms.velocity.qto.multiedit.MultiEditUtility.getKeyLong;
import static com.endeavorms.velocity.qto.multiedit.MultiEditUtility.getKeyString;

/**
 * @author llevit
 * @since 9/7/2023
 */
@Component
public class DisputeManager extends StandardManager<Dispute> {

    /**
     * Persistence tier for Disputes.
     */
    @Inject
    private DisputeJpaDao dao;

    /**
     * Business logic tier for Services.
     */
    @Inject
    private ServiceManager serviceManager;

    /**
     * Business logic tier for Dispute Notes.
     */
    @Inject
    private DisputeNoteManager disputeNoteManager;

    @Inject
    private SubjectManager subjectManager;

    @Override
    protected DisputeJpaDao getDao() {
        return dao;
    }

    public PaginatedResult<Dispute> findBySearchCriteria(final DisputeSearchCriteria criteria) {
        return getDao().findBySearchCriteria(criteria);
    }

    @Override
    public Dispute create(final Dispute entity) {
        Service service = serviceManager.retrieve(entity.getServiceId());
        entity.setTenantId(service.getTenantId());
        entity.setMasterCustomerId(service.getMasterCustomerId());
        entity.setDisputeStatus(getStatus(entity));
        Dispute created = super.create(entity);
        if (!Strings.isNullOrEmpty(entity.getInitialNote())) {
            DisputeNote note = new DisputeNote();
            note.setDisputeId(created.getId());
            note.setNote(entity.getInitialNote());
            note.setCreatedDate(new Date());
            note.setCategory("Dispute");
            if (entity.getCreatedBySubjectId() == null) {
                String username = SecurityUtils.getLoggedInUser();
                Subject subject = subjectManager.findByUsername(username);
                note.setCreatedBy(subject == null ? username : subject.getDisplayName());
                note.setCreatedById(subject.getId());
            } else {
                Subject subject = subjectManager.retrieve(entity.getCreatedBySubjectId());
                note.setCreatedBy(subject.getDisplayName());
                note.setCreatedById(subject.getId());
            }
            note.setTenantId(created.getTenantId());
            note.setInternalOnly(entity.isInitialNoteInternalOnly());
            disputeNoteManager.create(note);
        }
        return created;
    }

    @Override
    public Dispute edit(final Dispute entity) {
        Service service = serviceManager.retrieve(entity.getServiceId());
        entity.setTenantId(service.getTenantId());
        entity.setMasterCustomerId(service.getMasterCustomerId());
        entity.setDisputeStatus(getStatus(entity));
        if (Arrays.asList("Pending Bill Review", "Billing Review Complete", "Dispute Closed").contains(entity.getDisputeStatus())
                && entity.getRealizedMrcAdjustment() != null) {
            entity.setAnnualizedMrcSave(entity.getRealizedMrcAdjustment().multiply(new BigDecimal(12)));
        }
        return super.edit(entity);
    }

    private String getStatus(final Dispute dispute) {
        if (dispute.getDisputeClosedDate() != null) {
            return "Dispute Closed";
        }
        if (dispute.getBillingReviewComplete() != null) {
            return "Billing Review Complete";
        }
        if (dispute.getCreditRecognized() != null) {
           }
        if (dispute.getDisputeFollowUpDate() != null) {
            return "Dispute Pending Follow Up";
        }
        return "Dispute Open";
    }

    /**
     * Given a collection of dispute IDs and fields to process, loops through the disputes and makes the appropriate
     * changes/additions.
     * @param multiEditRequest the request.
     * @return a response indicating any failures.
     */
    public MultiEditResponseDto multiEdit(final MultiEditRequestDto multiEditRequest) {
        try {
            MultiEditResponseDto response = new MultiEditResponseDto();
            for (Long disputeId : multiEditRequest.getIds()) {
                Dispute dispute = retrieve(disputeId);
                //dispute level fields
                if (dispute != null) {
                    if (containsKey(multiEditRequest, "disputeType")) {
                        dispute.setDisputeType(getKeyString(multiEditRequest, "disputeType"));
                    }
                    if (containsKey(multiEditRequest, "invoiceNum")) {
                        dispute.setInvoiceNum(getKeyString(multiEditRequest, "invoiceNum"));
                    }
                    if (containsKey(multiEditRequest, "vendorTrackingNum")) {
                        dispute.setVendorTrackingNum(getKeyString(multiEditRequest, "vendorTrackingNum"));
                    }
                    if (containsKey(multiEditRequest, "amountDisputedMrc")) {
                        dispute.setAmountDisputedMrc(getKeyBigDecimal(multiEditRequest, "amountDisputedMrc"));
                    }
                    if (containsKey(multiEditRequest, "amountDisputedNrc")) {
                        dispute.setAmountDisputedNrc(getKeyBigDecimal(multiEditRequest, "amountDisputedNrc"));
                    }
                    if (containsKey(multiEditRequest, "openDate")) {
                        dispute.setOpenDate(getKeyDate(multiEditRequest, "openDate"));
                    }
                    if (containsKey(multiEditRequest, "disputeFollowUpDate")) {
                        dispute.setDisputeFollowUpDate(getKeyDate(multiEditRequest, "disputeFollowUpDate"));
                    }
                    if (containsKey(multiEditRequest, "creditRecognizedDate")) {
                        dispute.setCreditRecognized(getKeyDate(multiEditRequest, "creditRecognizedDate"));
                    }
                    if (containsKey(multiEditRequest, "billingReviewCompleteDate")) {
                        dispute.setBillingReviewComplete(getKeyDate(multiEditRequest, "billingReviewCompleteDate"));
                    }
                    if (containsKey(multiEditRequest, "disputeClosedDate")) {
                        dispute.setDisputeClosedDate(getKeyDate(multiEditRequest, "disputeClosedDate"));
                    }
                    if(containsKey(multiEditRequest, "disputeAssignment")){
                        if (getKeyLong(multiEditRequest,"disputeAssignment") != null) {
                            Subject subject = subjectManager.retrieve(getKeyLong(multiEditRequest, "disputeAssignment"));
                            dispute.setDisputeAssignment(subject.getDisplayName());
                        } else {
                            dispute.setDisputeAssignment(null);
                        }
                    }
                    //dispute note
                    if (containsKey(multiEditRequest, "disputeNote")) {
                        DisputeNote note = new DisputeNote();
                        note.setDisputeId(disputeId);
                        note.setNote(getKeyString(multiEditRequest, "disputeNote"));
                        note.setCreatedDate(new Date());
                        note.setCategory("Dispute");
                        //get current user
                        String username = SecurityUtils.getLoggedInUser();
                        Subject subject = subjectManager.retrieve(multiEditRequest.getSubjectId());
                        note.setCreatedBy(subject == null ? username : subject.getDisplayName());
                        note.setCreatedById(subject.getId());
                        note.setTenantId(dispute.getTenantId());
                        disputeNoteManager.create(note);
                    }
                }
                edit(dispute);
            }
            return response;
        } catch (RuntimeException e) {
            throw new RuntimeException("Unable to perform multi edit operation. " + e.getMessage());
        }
    }

    /**
     * Creates disputes for the given service ids.
     * @param dto the request dto.
     * @return the number of disputes created.
     */
    public Long multiCreate(final MultiDisputeRequestDto dto) {
        Long numDisputesCreated = 0L;
        for (Long serviceId : dto.getServiceIds()) {
            Dispute dispute = new Dispute();
            dispute.setServiceId(serviceId);
            dispute.setDisputeType(dto.getDispute().getDisputeType());
            dispute.setDisputeAssignment(dto.getDispute().getDisputeAssignment());
            dispute.setInvoiceNum(dto.getDispute().getInvoiceNum());
            dispute.setVendorTrackingNum(dto.getDispute().getVendorTrackingNum());
            dispute.setAmountDisputedMrc(dto.getDispute().getAmountDisputedMrc());
            dispute.setAmountDisputedNrc(dto.getDispute().getAmountDisputedNrc());
            dispute.setOpenDate(dto.getDispute().getOpenDate());
            dispute.setDisputeFollowUpDate(dto.getDispute().getDisputeFollowUpDate());
            dispute.setInitialNote(dto.getDispute().getInitialNote());
            dispute.setInitialNoteInternalOnly(dto.getDispute().isInitialNoteInternalOnly());
            dispute.setCreatedBySubjectId(dto.getSubjectId());
            create(dispute);
            numDisputesCreated++;
        }
        return numDisputesCreated;
    }

    /**
     * Utility for adding a field error to the multiEditError.
     *
     * @param multiEditError the current multiEditError instance. If it's null, a new one will be created.
     * @param dispute        the dispute used to populate the description for the error.
     * @param fieldName      the field name to add to the errors for the service.
     * @return the updated/created multiEditError instance.
     */
    private MultiEditError addFieldError(MultiEditError multiEditError, final Dispute dispute, final String fieldName) {
        if (multiEditError == null) {
            multiEditError = new MultiEditError();
            EntityDescription description = new EntityDescription();
            description.setId(dispute.getId());
            //TODO: implement
            description.setDisplayName("TODO");
            multiEditError.setDescription(description);
        }
        multiEditError.getFields().add(fieldName);
        return multiEditError;
    }

    /**
     * Retrieves all disputes for a given service.
     * @param serviceId the service id to filter by.
     * @return matching disputes.
     */
    public List<Dispute> findByServiceId(final Long serviceId) {
        return getDao().findByServiceId(serviceId);
    }

    /**
     * Retrieves all open disputes for the given service IDs.
     * @param serviceIds list of services IDs.
     * @return matching disputes.
     */
    public List<Dispute> findOpenDisputesByServiceId(final Long serviceIds) {
        return getDao().findOpenDisputesByServiceId(serviceIds);
    }

    /**
     * Gets the meta data for the grid built from the provided search criteria.
     * @param criteria The search criteria.
     * @return The meta data for the grid.
     */
    public DisputeGridMeta getDisputesGridMeta(final DisputeSearchCriteria criteria) {
        return getDao().getDisputesGridMeta(criteria);
    }
}

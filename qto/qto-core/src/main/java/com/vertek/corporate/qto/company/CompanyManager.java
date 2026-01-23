package com.vertek.corporate.qto.company;

import com.vertek.corporate.qto.activation.requirement.RequirementTemplate;
import com.vertek.corporate.qto.activation.requirement.RequirementTemplateManager;
import com.vertek.corporate.qto.attachment.CompanyFileAttachment;
import com.vertek.corporate.qto.attachment.CompanyFileAttachmentManager;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.StandardManager;
import com.vertek.corporate.qto.common.TenantSubjectManager;
import com.vertek.corporate.qto.company.jms.CompanyMessage;
import com.vertek.corporate.qto.company.jms.CompanyMessageHandler;
import com.vertek.corporate.qto.company.task.CompanyTask;
import com.vertek.corporate.qto.company.task.CompanyTaskManager;
import com.vertek.corporate.qto.company.task.TaskGroup;
import com.vertek.corporate.qto.company.task.TaskGroupManager;
import com.vertek.corporate.qto.config.CompanyConfigKey;
import com.vertek.corporate.qto.config.CompanyConfigPropertiesDto;
import com.vertek.corporate.qto.config.CompanyConfigPropertyManager;
import com.vertek.corporate.qto.contact.Contact;
import com.vertek.corporate.qto.contact.ContactManager;
import com.vertek.corporate.qto.contact.ContactType;
import com.vertek.corporate.qto.contact.order.OrderContact;
import com.vertek.corporate.qto.contact.order.OrderContactManager;
import com.vertek.corporate.qto.order.Order;
import com.vertek.corporate.qto.order.OrderManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Stateless
public class CompanyManager extends StandardManager<Company> {

    /**
     * Persistence tier for Company.
     */
    @Inject
    private CompanyJpaDao dao;

    @Override
    protected CompanyJpaDao getDao() {
        return dao;
    }

    @Inject
    private TenantSubjectManager tenantSubjectManager;

    @Inject
    private CompanyConfigPropertyManager<CompanyConfigKey> configPropertyManager;

    @Inject
    private CompanyMessageHandler companyMessageHandler;

    @Inject
    private ContactManager contactManager;

    @Inject
    private OrderManager orderManager;

    @Inject
    private TaskGroupManager taskGroupManager;

    @Inject
    private CompanyTaskManager companyTaskManager;

    @Inject
    private RequirementTemplateManager requirementTemplateManager;

    @Inject
    private OrderContactManager orderContactManager;

    @Inject
    private CompanyFileAttachmentManager fileAttachmentManager;

    public PaginatedResult<Company> findBySearchCriteria(final CompanySearchCriteria criteria) {
        return getDao().findBySearchCriteria(criteria);
    }

    /**
     * Finds a companies by a given name.
     * @param name the name to search by.
     * @return matching companies.
     */
    public Company findTenantByName(final String name) {
        return dao.findTenantByName(name);
    }

    /**
     * Finds a companies by a given tenantId.
     * @param tenantId the tenantId to search by.
     * @return matching companies.
     */
    public Company findTenantByTenantId(final Long tenantId) {
        return dao.findTenantByTenantId(tenantId);
    }

    /**
     * Finds all companies by a given name.
     *
     * @param name the name to search by.
     * @return all matching companies.
     */
    public Company findParentByName(final String name, final Long tenantId) {
        return dao.findParentByName(name, tenantId);
    }

    /**
     * Finds all companies by a given name.
     *
     * @param name the name to search by.
     * @return all matching companies.
     */
    public Company findEndCustByName(final String name, final Long tenantId) {
        return dao.findEndCustByName(name, tenantId);
    }

    /**
     * Finds a tenant record.
     *
     * @param tenantId the related tenant.
     * @return all matching companies.
     */
    public Company getCompanyIdForTenant(final Long tenantId) {
        return dao.getCompanyIdForTenant(tenantId);
    }

    @Override
    public Company create(final Company company) {
        company.setTenantId(company.getTenantId() == null ? tenantSubjectManager.getCurrentTenant().getId() : company.getTenantId());
        company.setUuid(UUID.randomUUID().toString());
        company.setName(company.getName().trim());
        if (company.getParentCompany() != null) {
            company.setMasterCustomerId(company.getParentCompany().getId());
        }

        Company existingCompany = null;
        if ("Master Customer".equals(company.getType())) {
            existingCompany = dao.findMasterCustomerByNameAndTenantId(company.getName(), company.getTenantId());
        } else if ("End Customer".equals(company.getType())) {
            existingCompany = dao.findEndCustomerByNameAndTenantId(company.getName(), company.getTenantId());
        }
        if (existingCompany != null) {
            throw new IllegalArgumentException(company.getType() + " name already exists.  Please verify your existing customer list and enter a new value");
        }

        //apply default task group to master customers
        if ("Master Customer".equals(company.getType()) && company.getTaskGroupId() == null) {
            TaskGroup taskGroup = taskGroupManager.getDefaultTaskGroup(company.getTenantId());
            if (taskGroup != null) {
                company.setTaskGroupId(taskGroup.getId());
            }
        }
        Company created = super.create(company);
        //create company tasks
        if (created.getTaskGroupId() != null) {
            companyTaskManager.updateCompanyTasks(created, created.getTaskGroupId());
        }

        if (created.getType().equals("End Customer") && created.isDuplicatedMasterCustomerDetails()) {
            Company parentCompany = retrieve(created.getMasterCustomerId());
            created.setAddress1(parentCompany.getAddress1());
            created.setAddress2(parentCompany.getAddress2());
            created.setCity(parentCompany.getCity());
            created.setState(parentCompany.getState());
            created.setPostalCode(parentCompany.getPostalCode());
            created.setCountry(parentCompany.getCountry());
            //duplicate the billing, technical, and sales contacts and assign them to the new company
            if (parentCompany.getBillingContact() != null) {
                Contact duplicateBillingContact = contactManager.duplicateContact(parentCompany.getBillingContact());
                duplicateBillingContact.setCompanyId(created.getId());
                duplicateBillingContact.setMasterCustomerId(parentCompany.getId());
                contactManager.create(duplicateBillingContact);
            }
            Contact techContact = contactManager.findByCompanyIdAndType(parentCompany.getId(), ContactType.TECH);
            if (techContact != null) {
                Contact duplicateTechnicalContact = contactManager.duplicateContact(techContact);
                duplicateTechnicalContact.setCompanyId(created.getId());
                duplicateTechnicalContact.setMasterCustomerId(parentCompany.getId());
                contactManager.create(duplicateTechnicalContact);
            }
            Contact salesContact = contactManager.findByCompanyIdAndType(parentCompany.getId(), ContactType.SALES);
            if (salesContact != null) {
                Contact duplicateSalesContact = contactManager.duplicateContact(salesContact);
                duplicateSalesContact.setCompanyId(created.getId());
                duplicateSalesContact.setMasterCustomerId(parentCompany.getId());
                contactManager.create(duplicateSalesContact);
            }
            Contact authorizationContact
                    = contactManager.findByCompanyIdAndType(parentCompany.getId(), ContactType.AUTHORIZATION);
            if (authorizationContact != null) {
                Contact duplicateAuthorizationContact = contactManager.duplicateContact(authorizationContact);
                duplicateAuthorizationContact.setCompanyId(created.getId());
                duplicateAuthorizationContact.setMasterCustomerId(parentCompany.getId());
                contactManager.create(duplicateAuthorizationContact);
            }
        }

        return retrieve(created.getId());
    }

    @Override
    public Company edit(final Company entity) {
        Company existing = retrieve(entity.getId());
        Long tenantId = existing.getTenantId();
        Long taskGroupId = existing.getTaskGroupId();
        Long existingMcId = existing.getMasterCustomerId();
        entity.setTenantId(tenantId);
        entity.setName(entity.getName().trim());
        entity.setMasterCustomerId(entity.getParentCompany() == null ? null : entity.getParentCompany().getId());
        if (existing.getMasterCustomerId() != null) {
            Company parentCompany = retrieve(entity.getMasterCustomerId());
            if (existing.getTenantId() != parentCompany.getTenantId()) {
                throw new IllegalArgumentException("Company and Parent Company must belong to the same tenant");
            }
        }

        //update all orders with the new default provisioner unless it's null
        if ("Master Customer".equals(entity.getType()) && entity.getProvisioner() != null) {
            orderManager.updateProvisionerForMasterCompany(entity.getId(), entity.getProvisioner());
        }

        //duplicate master customer details to end customer
        if (entity.getType().equals("End Customer") && entity.isDuplicatedMasterCustomerDetails()) {
            entity.setBillingContacts(null);
            Company parentCompany = retrieve(entity.getMasterCustomerId());
            entity.setAddress1(parentCompany.getAddress1());
            entity.setAddress2(parentCompany.getAddress2());
            entity.setCity(parentCompany.getCity());
            entity.setState(parentCompany.getState());
            entity.setPostalCode(parentCompany.getPostalCode());
            entity.setCountry(parentCompany.getCountry());
            Contact billingContact = contactManager.duplicateContactOnEdit(ContactType.BILLING, parentCompany.getId(), entity.getId());
            contactManager.duplicateContactOnEdit(ContactType.TECH, parentCompany.getId(), entity.getId());
            contactManager.duplicateContactOnEdit(ContactType.SALES, parentCompany.getId(), entity.getId());
            contactManager.duplicateContactOnEdit(ContactType.AUTHORIZATION, parentCompany.getId(), entity.getId());
            entity.setBillingContacts(List.of(billingContact));
        } else {
            //filter out null contacts
            entity.setBillingContacts(entity.getBillingContacts().stream().filter(Objects::nonNull).collect(Collectors.toList()));

        }
        Company edited = super.edit(entity);

        //if master customer, check to see if any end customers have the duplicate details checked
        if ("Master Customer".equals(entity.getType())) {
            List<Company> endCustomers = dao.findByEndCustomerByParentId(entity.getId());
            endCustomers.forEach(endCustomer -> {
                if (endCustomer.isDuplicatedMasterCustomerDetails()) {
                    endCustomer.setAddress1(entity.getAddress1());
                    endCustomer.setAddress2(entity.getAddress2());
                    endCustomer.setCity(entity.getCity());
                    endCustomer.setState(entity.getState());
                    endCustomer.setPostalCode(entity.getPostalCode());
                    endCustomer.setCountry(entity.getCountry());
                    super.edit(endCustomer);
                    contactManager.duplicateContactOnEdit(ContactType.BILLING, entity.getId(), endCustomer.getId());
                    contactManager.duplicateContactOnEdit(ContactType.TECH, entity.getId(), endCustomer.getId());
                    contactManager.duplicateContactOnEdit(ContactType.SALES, entity.getId(), endCustomer.getId());
                    contactManager.duplicateContactOnEdit(ContactType.AUTHORIZATION, entity.getId(), endCustomer.getId());
                }
            });
        }

//        if ("Master Customer".equals(entity.getType())) {
            //if task group has changed (either it's not the same, or one is null and the other is not, update company tasks
            if (!Objects.equals(edited.getTaskGroupId(), taskGroupId)) {
                companyTaskManager.updateCompanyTasks(edited, edited.getTaskGroupId());
            }
//        }
        //check to see if the master_customer_id needs to be updated in all the tables
        if ("End Customer".equalsIgnoreCase(entity.getType()) && !Objects.equals(existingMcId, entity.getMasterCustomerId())) {
            List<CompanyTask> tasks = companyTaskManager.findByCompanyId(entity.getId());
            tasks.forEach(task -> {
                task.setMasterCustomerId(entity.getMasterCustomerId());
                companyTaskManager.edit(task);
            });

            Contact contact = contactManager.findByCompanyIdAndType(entity.getId(), ContactType.BILLING);
            if (contact != null) {
                contact.setMasterCustomerId(entity.getMasterCustomerId());
                contactManager.edit(contact);
            }
            contact = contactManager.findByCompanyIdAndType(entity.getId(), ContactType.TECH);
            if (contact != null) {
                contact.setMasterCustomerId(entity.getMasterCustomerId());
                contactManager.edit(contact);
            }
            contact = contactManager.findByCompanyIdAndType(entity.getId(), ContactType.SALES);
            if (contact != null) {
                contact.setMasterCustomerId(entity.getMasterCustomerId());
                contactManager.edit(contact);
            }
            contact = contactManager.findByCompanyIdAndType(entity.getId(), ContactType.AUTHORIZATION);
            if (contact != null) {
                contact.setMasterCustomerId(entity.getMasterCustomerId());
                contactManager.edit(contact);
            }
            List<RequirementTemplate> templates = requirementTemplateManager.findByCompanyId(entity.getId(), true);
            templates.forEach(template -> {
                template.setMasterCustomerId(entity.getMasterCustomerId());
                requirementTemplateManager.edit(template);
            });

            List<Order> orders = orderManager.findByComapnyId(entity.getId());
            orders.forEach(order -> {
                order.setMasterCustomerId(entity.getMasterCustomerId());
                orderManager.edit(order);
                orderManager.updateMcId(order.getId(), entity.getId(), entity.getMasterCustomerId());

                List<OrderContact> orderContacts = orderContactManager.findByOrderId(order.getId());
                orderContacts.forEach(oc -> {
                    oc.setMasterCustomerId(entity.getMasterCustomerId());
                    orderContactManager.edit(oc);
                });
            });

        }
        return retrieve(edited.getId());
    }

    public List<Company> findByEndCustomerByParentId(Long id) {
        return dao.findByEndCustomerByParentId(id);
    }

    /**
     * Finds all companies by a given name.
     * @param name the name to search by.
     * @param tenantId the related tenant.
     * @return all matching companies.
     */
    public Company findMasterCustomerByNameAndTenantId(final String name, final Long tenantId) {
        return dao.findMasterCustomerByNameAndTenantId(name, tenantId);
    }

    /**
     * Finds all companies by a given name.
     * @param name the name to search by.
     * @param tenantId the related tenant.
     * @return all matching companies.
     */
    public Company findEndCustomerByNameAndTenantId(final String name, final Long tenantId) {
        return dao.findEndCustomerByNameAndTenantId(name, tenantId);
    }

    /**
     * Finds end customer by name, masterCustomerId, and tenantId.
     * @param name the name to search by.
     * @param masterCustomerId the related master customer ID.
     * @param tenantId the related tenant.
     * @return all matching companies.
     */
    public Company findEndCustomerByNameMasterCustomerIdAndTenantId(final String name, final Long masterCustomerId,
                                                                  final Long tenantId) {
        return dao.findEndCustomerByNameMasterCustomerIdAndTenantId(name, masterCustomerId, tenantId);
    }

    /**
     * Finds tenant ids by names.
     * @param tenantNames the names to search by.
     * @return matching tenant ids.
     */
    public List<Long> findTenantIdsByNames(final List<String> tenantNames) {
        return dao.findTenantIdsByNames(tenantNames);
    }


    public CompanyConfigPropertiesDto getConfigDto(Long tenantId) {
        Company tCompany = getCompanyIdForTenant(tenantId);
        return configPropertyManager.findByCompanyId(tCompany.getId());
    }

    public void updateInventoryCounts(Long companyId) {
        Company company = retrieve(companyId);
        if ("End Customer".equals(company.getType())) {
            //if parent company exists, update counts
            if (company.getParentCompany() != null) {
                companyMessageHandler.sendMessageToQueue(new CompanyMessage(company.getParentCompany().getId(), "updateCounts"));
            }
            //update tenant company counts
            Company tenantCompany = getCompanyIdForTenant(company.getTenantId());
            companyMessageHandler.sendMessageToQueue(new CompanyMessage(tenantCompany.getId(), "updateCounts"));
        }
        Long inventoryLocationCount = dao.getInventoryLocationCount(companyId, company.getType());
        company.setInventoryLocationCount(inventoryLocationCount == null ? 0L : inventoryLocationCount);
        BigDecimal inventoryMrc = dao.getInventoryMrc(companyId, company.getType());
        BigDecimal inventoryMrr = dao.getInventoryMrr(companyId, company.getType());
        BigDecimal inventoryNrr = dao.getInventoryNrr(companyId, company.getType());
        company.setInventoryMrc(inventoryMrc == null ? BigDecimal.ZERO : inventoryMrc);
        company.setInventoryMrr(inventoryMrr == null ? BigDecimal.ZERO : inventoryMrr);
        company.setInventoryNrr(inventoryNrr == null ? BigDecimal.ZERO : inventoryNrr);
        super.edit(company);
    }

    public void updateAllInventoryCounts() {
        CompanySearchCriteria criteria = new CompanySearchCriteria();
        criteria.setLimit(100000);
        PaginatedResult<Company> result = findBySearchCriteria(criteria);
        List<Company> companies = result.getCollection();
        for (Company company : companies) {
            companyMessageHandler.sendMessageToQueue(new CompanyMessage(company.getId(), "updateCounts"));
        }
    }

    public List<Long> getCompanyIdsFromNames(final List<String> companyNames, final String type, final Long tenantId) {
        return dao.getCompanyIdsFromNames(companyNames, type, tenantId);
    }

    public List<Company> findByTypeAndTenantId(final String type, final Long tenantId) {
        return dao.findByTypeAndTenantId(type, tenantId);
    }

        @Override
    public void remove(final Long id) {
        List<CompanyTask> companyTasks = companyTaskManager.findByCompanyId(id);
        for (CompanyTask task : companyTasks) {
            companyTaskManager.remove(task.getId());
        }
        List<CompanyFileAttachment> attachments = fileAttachmentManager.findByCompanyId(id).getCollection();
        for (CompanyFileAttachment attachment : attachments) {
            fileAttachmentManager.remove(attachment.getId());
        }

        List<Contact> contacts = contactManager.findCompanyContacts(id);
        for (Contact contact : contacts) {
            contactManager.remove(contact.getId());
        }
        super.remove(id);
    }

    /**
     * Finds all companies with a specific task group assigned.
     * @param id the id to search by.
     * @param tenantId the related tenant.
     * @return all matching companies.
     */
    public List<Company> findUsedTaskGroups(final Long id, final Long tenantId) {
        return dao.findUsedTaskGroups(id, tenantId);
    }
}

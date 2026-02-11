package com.endeavorms.velocity.qto.contact;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.company.Company;
import com.endeavorms.velocity.qto.company.CompanyManager;
import com.endeavorms.velocity.qto.contact.order.OrderContact;
import com.endeavorms.velocity.qto.contact.order.OrderContactManager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.inject.Inject;
import java.util.List;

/**
 * @author rcasey
 * @since 1/4/2024
 */
@RestController
@RequestMapping("/api/contacts")
@PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
public class ContactResource extends AbstractResource<Contact> {

    @Override
    protected String getResourcePath() {
        return "/contacts";
    }

    @Inject
    private ContactManager manager;

    @Inject
    private OrderContactManager orderContactManager;

    @Inject
    private CompanyManager companyManager;

    @GetMapping
    public ResponseEntity<Contact> findByCompanyIdAndType(
            @RequestParam(required = false) final Long companyId,
            @RequestParam(required = false) final ContactType type) {
        Contact contact = manager.findByCompanyIdAndType(companyId, type);
        return ResponseEntity.ok(contact);
    }

    @GetMapping("/order")
    public ResponseEntity<List<OrderContact>> findByOrderId(@RequestParam(required = false) final Long orderId) {
        List<OrderContact> contacts = orderContactManager.findByOrderId(orderId);
        return ResponseEntity.ok(contacts);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<?> create(@RequestBody final Contact contact) {
        try {
            Contact created = manager.create(contact);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public ResponseEntity<?> edit(@PathVariable("id") final Long id, @RequestBody final Contact contact) {
        try {
            Contact edited = manager.edit(contact);

            if (contact.getType().equals(ContactType.BILLING)
                    || contact.getType().equals(ContactType.TECH)
                    || contact.getType().equals(ContactType.SALES)
                    || contact.getType().equals(ContactType.AUTHORIZATION)) {
                Company company = companyManager.retrieve(contact.getCompanyId());
                if (company != null && "Master Customer".equalsIgnoreCase(company.getType())) {
                    List<Company> endCustomers = companyManager.findByEndCustomerByParentId(company.getId());
                    endCustomers.forEach(endCustomer -> {
                        if (endCustomer.isDuplicatedMasterCustomerDetails()) {
                            manager.duplicateContactOnEdit(contact.getType(), company.getId(), endCustomer.getId());
                        }
                    });
                }
            }
            return ResponseEntity.ok(edited);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}

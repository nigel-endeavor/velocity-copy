package com.vertek.corporate.qto.contact;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.company.Company;
import com.vertek.corporate.qto.company.CompanyManager;
import com.vertek.corporate.qto.contact.order.OrderContact;
import com.vertek.corporate.qto.contact.order.OrderContactManager;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * @author rcasey
 * @since 1/4/2024
 */
@Path("/contacts")
@Consumes("application/json")
@Produces("application/json")
@RequiresPermissions(value = {Permissions.INVENTORY_READ, Permissions.ORDER_READ},
        logical = Logical.OR)
public class ContactResource extends AbstractResource<Contact> {

    @Inject
    private ContactManager manager;

    @Inject
    private OrderContactManager orderContactManager;

    @Inject
    private CompanyManager companyManager;

    @GET
    public Response findByCompanyIdAndType(@QueryParam("companyId") final Long companyId, @QueryParam("type") final ContactType type) {
        Contact contact = manager.findByCompanyIdAndType(companyId, type);
        return Response.ok(contact).build();
    }

    @GET
    @Path("/order")
    public Response findByOrderId(@QueryParam("orderId") final Long orderId) {
        List<OrderContact> contacts = orderContactManager.findByOrderId(orderId);
        return Response.ok(contacts).build();
    }

    @POST
    @RequiresPermissions(value = {Permissions.INVENTORY_READ, Permissions.ORDER_READ},
            logical = Logical.OR)
    public Response create(final Contact contact) {
        try {
            Contact created = manager.create(contact);
            return Response.ok(created).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    //edit
    @PUT
    @Path("/{id: \\d+}")
    @RequiresPermissions(value = {Permissions.INVENTORY_READ, Permissions.ORDER_READ},
            logical = Logical.OR)
    public Response edit(@PathParam("id") final Long id, final Contact contact) {
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
            return Response.ok(edited).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

}

package com.vertek.corporate.qto;

import com.google.common.collect.Maps;
import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.SecurityUtils;
import com.vertek.corporate.qto.common.TenantViewManager;
import com.vertek.corporate.qto.company.CompanyManager;
import com.vertek.corporate.qto.graph.MSGraph;
import com.vertek.corporate.qto.inventory.PendingDisconnectManager;
import com.vertek.corporate.qto.location.LocationManager;
import com.vertek.corporate.qto.service.ServiceManager;
import com.vertek.corporate.qto.service.snapshot.ServiceSnapshotManager;
import com.vertek.corporate.qto.subject.SubjectManager;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.env.BasicIniEnvironment;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.SubjectThreadState;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.ThreadState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

/**
 * Resource class for admin operations.
 *
 * @author rcasey
 * @since 7/31/2023
 */
@Path("/admin")
@Consumes("application/json")
@Produces("application/json")
@RequiresPermissions(Permissions.ADMIN)
public class AdminResource {

    /** Private Logger for this class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminResource.class);

    /** The MSGraph service. */
    @Inject
    private MSGraph msGraph;

    @Inject
    private ServiceSnapshotManager serviceSnapshotManager;

    @Inject
    private CompanyManager companyManager;

    @Inject
    private SubjectManager subjectManager;

    @Inject
    private TenantViewManager tenantViewManager;

    @Inject
    private PendingDisconnectManager pendingDisconnectManager;

    @Inject
    private ServiceManager serviceManager;

    @Inject
    private LocationManager locationManager;

    @GET
    @Path("/azureAdGroups")
    public Response getAzureAdGroups() {
        try {
            return Response.ok(msGraph.getGroups()).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/refreshAzureAdGroups")
    public Response refreshAzureAdGroups() {
        try {
            LOGGER.debug("Azure AD group refresh requested by {}", SecurityUtils.getLoggedInUser());
            Map<String, List<String>> groups = msGraph.loadGroups();
            subjectManager.loadGroups();
            return Response.ok(groups).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    /**
     * Test endpoint for ServiceSnapshotJob.
     * @return a Response.
     */
    @POST
    @Path("/runServiceSnapshotJob")
    public Response runInventoryHistoryJob() {
        try {
            LOGGER.debug("Service snapshot job requested by {}", SecurityUtils.getLoggedInUser());
            createSchedulerSubjectThreadState("scheduler@vertek.com", "aad");
            serviceSnapshotManager.createServiceSnapshots();
            LOGGER.debug("Service snapshot job manual run finished");
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/updateCompanyInventoryCounts")
    public Response updateCompanyInventoryCounts() {
        try {
            LOGGER.debug("Company inventory counts update requested by {}", SecurityUtils.getLoggedInUser());
            createSchedulerSubjectThreadState("scheduler@vertek.com", "aad");
            companyManager.updateAllInventoryCounts();
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/runPendingDisconnects")
    public Response runPendingDisconnects() {
        try {
            LOGGER.debug("Pending Disconnects requested by {}", SecurityUtils.getLoggedInUser());
            createSchedulerSubjectThreadState("scheduler@vertek.com", "aad");
            pendingDisconnectManager.processPendingDisconnects();
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }



    @POST
    @Path("/runDeleteJob")
    public Response runDeleteJob() {
        try {
            LOGGER.debug("Delete Job requested by {}", SecurityUtils.getLoggedInUser());
            createSchedulerSubjectThreadState("scheduler@vertek.com", "aad");
            serviceManager.deleteServiceJob();
            locationManager.deleteLocationJob();
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }


    private ThreadState createSchedulerSubjectThreadState(final String subjectName, final String realmName) {
        Subject subject;
        try {
            org.apache.shiro.SecurityUtils.getSecurityManager();
            Map<String, Object> attributes = Maps.newHashMap();
            // create simple authentication info
            List<Object> principals = CollectionUtils.asList(subjectName, attributes);
            PrincipalCollection principalCollection = new SimplePrincipalCollection(principals, realmName);
            subject = new Subject.Builder()
                    .principals(principalCollection)
                    .buildSubject();
        } catch (UnavailableSecurityManagerException ex) {
            LOGGER.trace("Creating IniSecurityManagerFactory...");
            BasicIniEnvironment factory = new BasicIniEnvironment("classpath:shiro-ejbstartup.ini");
            LOGGER.trace("Getting SecurityManager instance...");
            org.apache.shiro.mgt.SecurityManager securityManager = factory.getSecurityManager();
            LOGGER.trace("Setting SecurityManager...");
            org.apache.shiro.SecurityUtils.setSecurityManager(securityManager);
            subject = org.apache.shiro.SecurityUtils.getSubject();
            subject.login(new UsernamePasswordToken(subjectName, "schedpassword"));
        }
        ThreadState state = new SubjectThreadState(subject);
        state.bind();
        return state;
    }
}

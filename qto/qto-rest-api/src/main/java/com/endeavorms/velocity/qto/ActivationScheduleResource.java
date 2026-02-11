package com.endeavorms.velocity.qto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.endeavorms.velocity.qto.activation.schedule.ActivationSchedule;
import com.endeavorms.velocity.qto.activation.schedule.ActivationScheduleManager;
import com.endeavorms.velocity.qto.authentication.Permissions;
import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.company.CompanyManager;
import com.endeavorms.velocity.qto.config.CompanyConfigKey;
import com.endeavorms.velocity.qto.config.CompanyConfigPropertyManager;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.springframework.security.access.prepost.PreAuthorize;

import jakarta.inject.Inject;
import javax.net.ssl.SSLContext;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author rcasey
 * @since 3/22/2023
 */
@Path("/activationSchedules")
@Consumes("application/json")
@Produces("application/json")
public class ActivationScheduleResource extends AbstractResource {

    @Override
    protected String getResourcePath() {
        return "/activationSchedules";
    }

    /**
     * Business methods for ActivationSchedules.
     */
    @Inject
    private ActivationScheduleManager manager;

    @Inject
    private CompanyConfigPropertyManager<CompanyConfigKey> companyConfigPropertyManager;

    @Inject
    private CompanyManager companyManager;


    /**
     * ObjectMapper.
     */
    private static ObjectMapper mapper;

    @GET
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public Response getSchedules(@QueryParam("serviceId") final Long serviceId) {
        List<ActivationSchedule> schedules = manager.findByServiceId(serviceId);
        return Response.ok(schedules).build();
    }

    @GET
    @Path("/{id: \\d+}")
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public Response getSchedule(@PathParam("id") final Long id) {
        ActivationSchedule schedule = manager.retrieve(id);
        return Response.ok(schedule).build();
    }

    @POST
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public Response create(final ActivationSchedule schedule) {
        try {
            ActivationSchedule created = manager.create(schedule);

            return Response.ok(created).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id: \\d+}")
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public Response edit(@PathParam("id") final Long id, final ActivationSchedule schedule) {
        try {
            if (!id.equals(schedule.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            ActivationSchedule updated = manager.edit(schedule);
            return Response.ok(updated).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

//    @Interceptors({ActivationScheduleInterceptor.class})
//    @PUT
//    @Path("/pushToFtdi")
//    @RequiresPermissions(value = {
//            Permissions.ORDER_WRITE,
//            Permissions.INVENTORY_WRITE}, logical = Logical.OR)
//    public Response submitToFtdi(final ActivationSchedule schedule) {
//        try {
//
//            ActivationSchedule updated = manager.edit(schedule);
//            for (FtdiDispatch ftdiDispatch : schedule.getDispatches()) {
//                if (ftdiDispatch != null && ftdiDispatch.getVendorDispatchId() == null) {
//
//                    Company company = companyManager.getCompanyIdForTenant(updated.getTenantId());
//                    String url = companyConfigPropertyManager.getString(company.getId(), CompanyConfigKey.ENDEAVOR_PROXY_URL);
//
////                    manager.sendDispatchToQueue(schedule, ftdiDispatch);
//                    callFieldTechDispatch(url + "?dispatchId=" + ftdiDispatch.getId() + "&tenantId=" + updated.getTenantId());
//
//
//                }
//
//            }
//            return Response.ok(updated).build();
//        } catch (Exception e) {
//            LOGGER.debug(e.getMessage());
//            return Response.serverError().entity(e.getMessage()).build();
//        }
//    }

    private String callFieldTechDispatch(String url) {

        SSLContext sslContext = null;
        try {
            sslContext = SSLContexts.custom().loadTrustMaterial(null,
                            (TrustStrategy) (chain, authType) -> true)
                    .build();
            HttpPost request = new HttpPost(url);
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);

            CloseableHttpClient httpClient = HttpClientBuilder.create().setSSLSocketFactory(sslsf).build();
            CloseableHttpResponse response = httpClient.execute(request);
            System.out.println(response.getStatusLine().getStatusCode());

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // return it as a String
                String result = EntityUtils.toString(entity);
                System.out.println(result);
            }

        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        }

        return "Done";
    }


    /**
     * Get configured Object Mapper.
     *
     * @return ObjectMapper
     */
    public static synchronized ObjectMapper getMapper() {
        if (mapper == null) {
            mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
            mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        }
        return mapper;
    }
}

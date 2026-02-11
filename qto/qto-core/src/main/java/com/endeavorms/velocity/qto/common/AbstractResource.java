package com.endeavorms.velocity.qto.common;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Base class for REST API controllers. Spring MVC compatible.
 * @param <T> the Resource type.
 */
public abstract class AbstractResource<T extends BaseEntity<? extends Serializable>> {

    /** Logging Facade.*/
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractResource.class);

    /** Name of the standard Link Header (http://tools.ietf.org/html/rfc5988#page-6).*/
    protected static final String LINK = "Link";

    /** Excel 97 file extension. */
    private static final String XLS = "xls";

    /** Excel 2007+ file extension. */
    private static final String XLSX = "xlsx";

    /** The Request.*/
    @Autowired
    protected HttpServletRequest request;

    /** A Map for storing LinkRel info.*/
    protected Map<String, String> relMap = Maps.newHashMap();

    /**
     * Returns the base path for this resource (e.g. "/api/services").
     * Subclasses must override.
     */
    protected abstract String getResourcePath();

    /**
     * Gets absolute URI from relative path.
     */
    protected String getUri(final String path) {
        String baseUri = getBaseUri();
        if (baseUri.endsWith("/")) {
            baseUri = baseUri.substring(0, baseUri.length() - 1);
        }
        return baseUri + path;
    }

    /** Build base URI from request. */
    protected String getBaseUri() {
        if (request == null) return "/api";
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int port = request.getServerPort();
        String contextPath = request.getContextPath() != null ? request.getContextPath() : "";
        StringBuilder sb = new StringBuilder();
        sb.append(scheme).append("://").append(serverName);
        if ((scheme.equals("http") && port != 80) || (scheme.equals("https") && port != 443)) {
            sb.append(":").append(port);
        }
        sb.append(contextPath).append("/api");
        return sb.toString();
    }

    /**
     * Gets an absolute URI for this resource's collection.
     */
    protected String getLocation(final Class<?> serviceClass) {
        return getBaseUri() + getResourcePath();
    }

    /**
     * Gets an absolute URI for the given resource and identifier.
     */
    protected String getLocation(final Class<?> serviceClass, final Serializable identifier) {
        return getUri(getResourcePath() + "/" + identifier);
    }

    /** Build request URI with optional query param replacement for pagination. */
    protected String getPaginatedUri(final int newOffset, final int newLimit) {
        if (request == null) return getResourcePath() + "?offset=" + newOffset + "&limit=" + newLimit;
        String query = request.getQueryString();
        Map<String, String> params = new java.util.HashMap<>();
        if (query != null) {
            for (String pair : query.split("&")) {
                String[] kv = pair.split("=", 2);
                if (kv.length == 2) params.put(kv[0], kv[1]);
            }
        }
        params.put("offset", String.valueOf(newOffset));
        params.put("limit", String.valueOf(newLimit));
        StringBuilder qs = new StringBuilder();
        for (Entry<String, String> e : params.entrySet()) {
            if (qs.length() > 0) qs.append("&");
            qs.append(e.getKey()).append("=").append(e.getValue());
        }
        return request.getRequestURL().toString() + "?" + qs;
    }

    protected URI toUri(final String aUri) {
        try {
            return new URI(aUri);
        } catch (URISyntaxException e) {
            LOGGER.error("Unable to create URI", e);
        }
        return null;
    }


    /**
     * Executes the given criteria instance and produces an appropriate response based on the passed format parameter.
     * @param <C> search criteria type.
     * @param criteria the BaseSearchCriteria instance.
     * @return an appropriate Response.
     */
    public <C extends BaseSearchCriteria<T>> C getExportCriteria(final C criteria) {
        boolean isExport = isExport(criteria);
        int excelMaxRows = 65536;

        criteria.setOffset(isExport ? 0 : criteria.getOffset());
        criteria.setLimit(isExport ? excelMaxRows : criteria.getLimit());

        return criteria;
    }


    /**
     * Determines whether the given search criteria is requesting an Excel export or not.
     * @param criteria the search criteria.
     * @return true if this is an export, false otherwise.
     */
    private boolean isExport(final BaseSearchCriteria<T> criteria) {
        return isExport(criteria.getFormat());
    }


    /**
     * Determines whether the given search criteria is requesting an Excel export or not.
     * @param format the format specified with the request.
     * @return true if this is an export, false otherwise.
     */
    protected boolean isExport(final String format) {
        String accept = request != null ? request.getHeader("Accept") : "";
        if (accept == null) accept = "";
        return (XLSX.equalsIgnoreCase(format) || accept.contains(MediaTypes.MS_EXCEL_2007)
                || XLS.equalsIgnoreCase(format) || accept.contains(MediaTypes.MS_EXCEL));
    }


    /**
     * Determines the export media type to be used.
     * @param format the format specified with the request.
     * @return a String specifying the media type.
     */
    protected String determineExportMediaType(final String format) {

        PreconditionsUtil.checkArgument(isExport(format), "Format must be an export type.");

        // Default to Excel 2007.
        String mediaType = MediaTypes.MS_EXCEL_2007;

        String accept = request != null ? request.getHeader("Accept") : "";
        if (accept != null && (XLS.equalsIgnoreCase(format) || accept.contains(MediaTypes.MS_EXCEL))) {
            mediaType = MediaTypes.MS_EXCEL;
        }
        return mediaType;
    }


    /**
     * Wraps the given PaginatedResult in a ResourceWrapper for the purpose of decorating it with the appropriate
     * Link Relations while also allowing for excel exports.
     * @return ResponseEntity containing link relations appropriate for a paginated response.
     */
    protected ResponseEntity<?> getCollectionResource(final PaginatedResult<T> result,
                                             final BaseSearchCriteria<T> criteria,
                                             final String location) {

        // allow exports in a single call to getCollectionResource
        if (isExport(criteria)) {
            ResourceWrapper wrapper = new ResourceWrapper(result);
            if (!Strings.isNullOrEmpty(criteria.getFields())) {
                wrapper.withProperty("fields", criteria.getFields());
            }
            if (!Strings.isNullOrEmpty(criteria.getHeaders())) {
                wrapper.withProperty("headers", criteria.getHeaders());
            }
            String mediaType = determineExportMediaType(criteria.getFormat());
            return ResponseEntity.ok()
                    .header("Cache-Control", "no-cache")
                    .contentType(MediaType.parseMediaType(mediaType))
                    .body(wrapper);
        }

        // wrap each item and tack on their linkRels.
        List<ResourceWrapper> wrappedCollection = Lists.newArrayList();
        for (T resource : result.getCollection()) {
            ResourceWrapper wrappedResource = wrapResource(resource, location);
            Map<String, String> linkRelMap = getLinkRelMap(resource);
            if (linkRelMap != null) {
                for (Entry<String, String> linkEntry : linkRelMap.entrySet()) {
                    wrappedResource.addLink(linkEntry.getKey(), linkEntry.getValue());
                }
            }
            wrappedCollection.add(wrappedResource);
        }

        int offset = result.getOffset();
        int limit = result.getLimit();

        ResourceWrapper wrappedResource = new ResourceWrapper(location);
        wrappedResource.withProperty("offset", offset);
        wrappedResource.withProperty("limit", limit);
        wrappedResource.withProperty("total", result.getTotal());
        wrappedResource.withProperty("collection", wrappedCollection);

        int previousOffset = (offset - limit) <= 0 ? 0 : (offset - limit);
        int nextOffset = (offset + limit);

        if (previousOffset != offset) {
            wrappedResource.addLink("previous", getPaginatedUri(previousOffset, limit));
        }
        if (nextOffset < result.getTotal()) {
            wrappedResource.addLink("next", getPaginatedUri(nextOffset, limit));
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-cache");
        headers.set(LINK, getLinkHeader(wrappedResource));
        return ResponseEntity.ok().headers(headers).body(wrappedResource);
    }


    @Deprecated
    protected ResponseEntity<?> getCollectionResource(final PaginatedResult<T> result, final String location) {
        List<ResourceWrapper> wrappedCollection = Lists.newArrayList();
        for (T resource : result.getCollection()) {
            ResourceWrapper wrappedResource = wrapResource(resource, location);
            Map<String, String> linkRelMap = getLinkRelMap(resource);
            if (linkRelMap != null) {
                for (Entry<String, String> linkEntry : linkRelMap.entrySet()) {
                    wrappedResource.addLink(linkEntry.getKey(), linkEntry.getValue());
                }
            }
            wrappedCollection.add(wrappedResource);
        }

        int offset = result.getOffset();
        int limit = result.getLimit();

        ResourceWrapper wrappedResource = new ResourceWrapper(location);
        wrappedResource.withProperty("offset", offset);
        wrappedResource.withProperty("limit", limit);
        wrappedResource.withProperty("total", result.getTotal());
        wrappedResource.withProperty("collection", wrappedCollection);

        int previousOffset = (offset - limit) <= 0 ? 0 : (offset - limit);
        int nextOffset = (offset + limit);

        if (previousOffset != offset) {
            wrappedResource.addLink("previous", getPaginatedUri(previousOffset, limit));
        }
        if (nextOffset < result.getTotal()) {
            wrappedResource.addLink("next", getPaginatedUri(nextOffset, limit));
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-cache");
        headers.set(LINK, getLinkHeader(wrappedResource));
        return ResponseEntity.ok().headers(headers).body(wrappedResource);
    }



    /**
     * Generates a Link header from the given ResourceWrapper's link rels.
     * @param wrapper the ResourceWrapper for which to get Link Header formatted String.
     * @return a Link Header value from the given ResourceWrapper.
     */
    protected String getLinkHeader(final ResourceWrapper wrapper) {
        StringBuilder sb = new StringBuilder();
        int indx = 0;
        Set<Map.Entry<String, LinkRel>> entrySet = wrapper.getLinkRels().entrySet();
        for (Map.Entry<String, LinkRel> rel : entrySet) {
            sb.append("<").append(rel.getValue().getHref()).append(">; rel=\"").append(rel.getKey()).append("\"");
            if (indx++ < (entrySet.size() - 1)) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }


    /**
     * Wraps the given BaseEntity in a ResourceWrapper instance (this is our default media-type).
     */
    protected ResourceWrapper wrapResource(final T resource) {
        String location = request != null ? request.getRequestURL().append(request.getQueryString() != null ? "?" + request.getQueryString() : "").toString() : "";
        ResourceWrapper wrapper = new ResourceWrapper(location, resource);
        Map<String, String> linkRelMap = getLinkRelMap(resource);
        if (linkRelMap != null) {
            for (Entry<String, String> linkEntry : linkRelMap.entrySet()) {
                wrapper.addLink(linkEntry.getKey(), linkEntry.getValue());
            }
        }
        return wrapper;
    }

    /**
     * Wraps the given BaseEntity in a ResourceWrapper instance (this is our default media-type).
     * @param resource the BaseEntity to wrap.
     * @param collectionLocation the location of the collection resource.
     * @return the wrapped resource as a ResourceWrapper instance.
     */
    private ResourceWrapper wrapResource(final T resource, final String collectionLocation) {
        String location = String.format(collectionLocation + "/%s", resource.getId());
        ResourceWrapper wrapper = new ResourceWrapper(location, resource);
        Map<String, String> linkRelMap = getLinkRelMap(resource);
        if (linkRelMap != null) {
            for (Entry<String, String> linkEntry : linkRelMap.entrySet()) {
                wrapper.addLink(linkEntry.getKey(), linkEntry.getValue());
            }
        }
        return wrapper;
    }


    /**
     * Implementations should supply a Map of link rel values to add to the wrapped resource.
     * @param resource the BaseEntity for which to return a Map of valid link rels.
     * @return a Map of link rels (name is key, href is value) to add to the wrapped resource.
     */
    protected Map<String, String> getLinkRelMap(final T resource) {
        return relMap;
    }

    /**
     * Converts ResponseEntity to JAX-RS Response for backward compatibility during migration.
     * @param entity the Spring ResponseEntity.
     * @return equivalent JAX-RS Response.
     */
    protected jakarta.ws.rs.core.Response toResponse(final ResponseEntity<?> entity) {
        jakarta.ws.rs.core.Response.ResponseBuilder builder =
                jakarta.ws.rs.core.Response.status(entity.getStatusCode().value());
        entity.getHeaders().forEach((name, values) -> {
            for (String v : values) {
                builder.header(name, v);
            }
        });
        return builder.entity(entity.getBody()).build();
    }

}

package com.vertek.corporate.qto.common;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * From vertek-commons.
 * Base class for security-related REST API classes.
 * @author <a href="mailto:rconnolly@vertek.com">rconnolly</a>
 * @since 1.5.0 - 2/5/13 11:15 AM
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


    /** URI Info.*/
    @Context
    protected UriInfo uriInfo;

    /** The Request.*/
    @Context
    protected HttpServletRequest request;


    /** A Map for storing LinkRel info.*/
    protected Map<String, String> relMap = Maps.newHashMap();


    /**
     * Gets absolute URI from relative path.
     * @param path the relative path.
     * @return absolute URI from relative path.
     */
    protected String getUri(final String path) {
        String baseUri = uriInfo.getBaseUri().toString();
        if (baseUri.endsWith("/")) {
            baseUri = baseUri.substring(0, (baseUri.length() - 1));
        }
        return baseUri + path;
    }


    /**
     * Gets an absolute URI for the given service class.
     * @param serviceClass the path annotated service class.
     * @return an absolute URI for the given service class.
     */
    protected String getLocation(final Class<?> serviceClass) {
        return uriInfo.getBaseUriBuilder().path(serviceClass).build().toString();
    }

    /**
     * Gets an absolute URI for the given service class and identifier.
     * @param serviceClass the path annotated service class.
     * @param identifier the resource identifier.
     * @return an absolute URI for the given service class and identifier.
     */
    protected String getLocation(final Class<?> serviceClass, final Serializable identifier) {
        return getUri(UriBuilder.fromResource(serviceClass).path("{arg0}").build(identifier).toString());
    }

    /**
     * Convenience for getting a URI from a String.
     * @param aUri a String representation of a URI.
     * @return a URI from the given String representation.
     */
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
        String accept = request.getHeader("Accept");
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

        String accept = request.getHeader("Accept");
        if (XLS.equalsIgnoreCase(format) || accept.contains(MediaTypes.MS_EXCEL)) {
            mediaType = MediaTypes.MS_EXCEL;
        }
        return mediaType;
    }


    /**
     * Wraps the given PaginatedResult in a ResourceWrapper for the purpose of decorating it with the appropriate
     * Link Relations while also allowing for excel exports.
     * @param result the PaginatedResult to decorate with hypermedia.
     * @param criteria the search criteria.
     * @param location the URI of the collection resource responsible for the PaginatedResult.
     * @return a ResourceWrapper containing link relations appropriate for a paginated response.
     */
    protected Response getCollectionResource(final PaginatedResult<T> result,
                                             final BaseSearchCriteria<T> criteria,
                                             final String location) {

        // allow exports in a single call to getCollectionResource
        if (isExport(criteria)) {
            // our wrapped result to send in the response
            ResourceWrapper wrapper = new ResourceWrapper(result);
            if (!Strings.isNullOrEmpty(criteria.getFields())) {
                wrapper.withProperty("fields", criteria.getFields());
            }
            if (!Strings.isNullOrEmpty(criteria.getHeaders())) {
                wrapper.withProperty("headers", criteria.getHeaders());
            }
            return NoCacheResponse.ok(wrapper).type(determineExportMediaType(criteria.getFormat())).build();
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


        // adjust pagination links
        int offset = result.getOffset();
        int limit = result.getLimit();

        ResourceWrapper wrappedResource = new ResourceWrapper(location);
        wrappedResource.withProperty("offset", offset);
        wrappedResource.withProperty("limit", limit);
        wrappedResource.withProperty("total", result.getTotal());
        wrappedResource.withProperty("collection", wrappedCollection);

        int previousOffset = (offset - limit) <= 0
                ? 0
                : (offset - limit);

        int nextOffset = (offset + limit);

        if (previousOffset != offset) {
            wrappedResource.addLink("previous", getPaginatedUri(previousOffset, limit));
        }
        if (nextOffset < result.getTotal()) {
            wrappedResource.addLink("next", getPaginatedUri(nextOffset, limit));
        }

        return NoCacheResponse.ok(wrappedResource)
                .header(LINK, getLinkHeader(wrappedResource))
                .build();
    }


    /**
     * Wraps the given PaginatedResult in a ResourceWrapper for the purpose of decorating it with the appropriate
     * Link Relations.
     * @param result the PaginatedResult to decorate with hypermedia.
     * @param location the URI of the collection resource responsible for the PaginatedResult.
     * @return a ResourceWrapper containing link relations appropriate for a paginated response.
     * @deprecated use #getCollectionResource(PaginatedResult<T>,BaseSearchCriteria<T>,String) instead as it supports
     * Excel exports as well.
     */
    @Deprecated
    protected Response getCollectionResource(final PaginatedResult<T> result, final String location) {

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


        // adjust pagination links
        int offset = result.getOffset();
        int limit = result.getLimit();

        ResourceWrapper wrappedResource = new ResourceWrapper(location);
        wrappedResource.withProperty("offset", offset);
        wrappedResource.withProperty("limit", limit);
        wrappedResource.withProperty("total", result.getTotal());
        wrappedResource.withProperty("collection", wrappedCollection);

        int previousOffset = (offset - limit) <= 0
                ? 0
                : (offset - limit);

        int nextOffset = (offset + limit);

        if (previousOffset != offset) {
            wrappedResource.addLink("previous", getPaginatedUri(previousOffset, limit));
        }
        if (nextOffset < result.getTotal()) {
            wrappedResource.addLink("next", getPaginatedUri(nextOffset, limit));
        }

        return NoCacheResponse.ok(wrappedResource)
                .header(LINK, getLinkHeader(wrappedResource))
                .build();
    }



    /**
     * Wraps the given PaginatedResult in a ResourceWrapper for the purpose of decorating it with the appropriate
     * Link Relations.
     * @param paginatedResult the PaginatedResult to decorate with hypermedia.
     * @param collectionUri the URI of the collection resource responsible for the PaginatedResult.
     * @param baseItemUri the base URI of the members of the collection.
     * @param <T> the BaseEntity type of the collection.
     * @return a ResourceWrapper containing link relations appropriate for a paginated response.
     * @deprecated it is typically better for Collections resources to deal with single resource types rather than
     *             provide collections of varying types. Relations can be links to another collection that
     *             is pre-filtered (api/someCollection/:someId/ ->links {"related": {href: relatedUri}}.
     *             Use AbstractResource#getCollectionResource(PaginatedResult<T>, String) instead.
     */
    @Deprecated
    protected <T extends BaseEntity> Response getCollectionResource(final PaginatedResult<T> paginatedResult,
                                                                    final String collectionUri,
                                                                    final String baseItemUri) {
        int offset = paginatedResult.getOffset();
        int limit = paginatedResult.getLimit();
        int previousOffset = (offset - limit) <= 0
                ? 0
                : (offset - limit);
        int nextOffset = (offset + limit);

        ResourceWrapper wrappedResource = new ResourceWrapper(collectionUri);
        wrappedResource.withProperty("offset", offset);
        wrappedResource.withProperty("limit", limit);
        wrappedResource.withProperty("total", paginatedResult.getTotal());
        wrappedResource.withProperty("page", paginatedResult.getPage());
        List<ResourceWrapper> wrappedCollection = Lists.newArrayList();
        for (BaseEntity baseEntity : paginatedResult.getCollection()) {
            wrappedCollection.add(new ResourceWrapper(baseItemUri + "/" + baseEntity.getId(), baseEntity));
        }
        wrappedResource.withProperty("collection", wrappedCollection);

        if (previousOffset != offset) {
            wrappedResource.addLink("previous", getPaginatedUri(previousOffset, limit));
        }
        if (nextOffset < paginatedResult.getTotal()) {
            wrappedResource.addLink("next", getPaginatedUri(nextOffset, limit));
        }

        return NoCacheResponse.ok(wrappedResource)
                .header(LINK, getLinkHeader(wrappedResource))
                .build();
    }




    /**
     * Replaces the offset and limit query params in order to create a new URI for use in LinkRels.
     * @param newOffset the new pagination offset.
     * @param newLimit the new page size.
     * @return a new URI containing the passed offset and limit values in the query parameters.
     */
    private String getPaginatedUri(final int newOffset, final int newLimit) {
        return uriInfo.getRequestUriBuilder()
                .replaceQueryParam("offset", newOffset)
                .replaceQueryParam("limit", newLimit)
                .build()
                .toString();
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
     * @param resource the the BaseEntity to wrap.
     * @return the wrapped resource as a ResourceWrapper instance.
     */
    protected ResourceWrapper wrapResource(final T resource) {
        String location = uriInfo.getRequestUri().toString();
        ResourceWrapper wrapper = new ResourceWrapper(location, resource);
        for (Entry<String, String> linkEntry : getLinkRelMap(resource).entrySet()) {
            wrapper.addLink(linkEntry.getKey(), linkEntry.getValue());
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
        for (Entry<String, String> linkEntry : getLinkRelMap(resource).entrySet()) {
            wrapper.addLink(linkEntry.getKey(), linkEntry.getValue());
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

}

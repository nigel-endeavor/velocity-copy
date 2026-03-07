package com.vertek.corporate.qto.common;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.google.common.collect.Maps;

import jakarta.ws.rs.HttpMethod;
import java.util.Map;

/**
 * Decorates an Object with {@link LinkRel} objects in order to address HATEOAS concerns.
 * @author <a href="mailto:rconnolly@vertek.com">rconnolly</a>
 * @since 1.2.0
 */
public class ResourceWrapper {

    /** Constant for 'self' link relation type.*/
    private static final String SELF = "self";


    /** The Object to wrap.*/
    @JsonUnwrapped
    private Object target;

    /** Map of properties.*/
    private Map<String, Object> properties = Maps.newLinkedHashMap();


    /** Map of link relations.*/
    private Map<String, LinkRel> linkRels = Maps.newLinkedHashMap();




    /** Default Constructor.*/
    public ResourceWrapper() {
    }

    /**
     * Constructor.
     * @param href the URI of the target resource.
     */
    public ResourceWrapper(final String href) {
        addSelfLink(href);
    }

    /**
     * Constructor.
     * @param target the target resource to wrap.
     */
    public ResourceWrapper(final Object target) {
        this.target = target;
    }

    /**
     * Constructor.
     * @param href the URI of the target resource.
     * @param target the target resource to wrap.
     */
    public ResourceWrapper(final String href, final Object target) {
        addSelfLink(href);
        this.target = target;
    }




    public Object getTarget() {
        return target;
    }
    public void setTarget(final Object target) {
        this.target = target;
    }


    @JsonAnyGetter
    public Map<String, Object> getProperties() {
        return properties;
    }
    @JsonAnySetter
    public void setProperties(final Map<String, Object> properties) {
        this.properties = properties;
    }


    public Map<String, LinkRel> getLinkRels() {
        return linkRels;
    }
    public void setLinkRels(final Map<String, LinkRel> linkRels) {
        this.linkRels = linkRels;
    }

    /**
     * Adds a Link with the 'self' link relation.
     * @param href the URI of the 'self' link.
     */
    private void addSelfLink(final String href) {
        addLink(SELF, href);
    }


    /**
     * Adds a new link.
     * @param rel the rel name.
     * @param method the HTTP method name.
     * @param href the URI.
     */
    public void addLink(final String rel, final String method, final String href) {
        linkRels.put(rel, new LinkRel(method, href));
    }

    /**
     * Adds a new link with a default method of 'get'.
     * @param rel the rel name.
     * @param href the URI.
     */
    public void addLink(final String rel, final String href) {
        linkRels.put(rel, new LinkRel(HttpMethod.GET, href));
    }


    /**
     * test.
     * @param propertyName test.
     * @param value test.
     */
    public void withProperty(final String propertyName, final Object value) {
        properties.put(propertyName, value);
    }

}

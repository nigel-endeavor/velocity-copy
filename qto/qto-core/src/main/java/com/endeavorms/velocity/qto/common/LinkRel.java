package com.endeavorms.velocity.qto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * @author <a href="mailto:rconnolly@vertek.com">rconnolly</a>
 * @since 2/5/13 11:00 AM
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LinkRel {

    /**
     * The Link's 'href' attribute.
     * @deprecated an OPTIONS request should be used instead if there is any question what methods are supported.
     */
    @Deprecated
    private String method;

    /** The Link's 'href' attribute.*/
    private String href;


    /** Default no-arg Constructor.*/
    public LinkRel() {
    }


    /**
     * Constructor.
     * @param method the Link's HTTP method.
     * @param href the Link's 'href'.
     */
    public LinkRel(@JsonProperty(value = "method") final String method,
                   @JsonProperty(value = "href") final String href) {
        this.method = method;
        this.href = href;
    }



    @Deprecated
    public String getMethod() {
        return method;
    }
    @Deprecated
    public void setMethod(final String method) {
        this.method = method;
    }


    public String getHref() {
        return href;
    }
    public void setHref(final String href) {
        this.href = href;
    }

}

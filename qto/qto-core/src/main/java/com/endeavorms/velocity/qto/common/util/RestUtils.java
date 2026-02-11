package com.endeavorms.velocity.qto.common.util;

import com.google.common.base.Strings;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.TrustSelfSignedStrategy;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author llevit
 * @since 2.0.0
 */
public final class RestUtils {

    /** Private logger for this class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(RestUtils.class);

    /**
     * Request config default value. The time waiting for data – after establishing the connection
     * maximum time of inactivity between two data packets.
     */
    private static int socketTimeout = 30000;

    /** Request config default value. The time to establish the connection with the remote host. */
    private static int connectTimeout = 15000;

    /** Request config default value. The time to wait for a connection from the connection manager/pool. */
    private static int connectionRequestTimeout = 5000;

    /** Request config default value. Maximum total connections. */
    private static int maxConnectionsTotal = 100;

    /** Request config default value. Maximum connections per route. */
    private static int maxConnectionsPerRoute = 10;

    /** HTTP Client. */
    private static CloseableHttpClient closeableHttpClient;


    /* Set HTTP Client config from System properties. */
    static {
        String connectTimeoutStr = System.getProperty("org.apache.http.connectTimeout");
        if (!Strings.isNullOrEmpty(connectTimeoutStr)) {
            try {
                connectTimeout = Integer.parseInt(connectTimeoutStr);
            } catch (NumberFormatException nfe) {
                LOGGER.warn("Could not parse as Integer, connectTimeoutStr = {}", connectTimeoutStr);
            }
        }

        String connectionRequestTimeoutStr = System.getProperty("org.apache.http.connectionRequestTimeout");
        if (!Strings.isNullOrEmpty(connectionRequestTimeoutStr)) {
            try {
                connectionRequestTimeout = Integer.parseInt(connectionRequestTimeoutStr);
            } catch (NumberFormatException nfe) {
                LOGGER.warn("Could not parse as Integer, connectionRequestTimeoutStr = {}",
                        connectionRequestTimeoutStr);
            }
        }

        String socketTimeoutStr = System.getProperty("org.apache.http.socketTimeout");
        if (!Strings.isNullOrEmpty(socketTimeoutStr)) {
            try {
                socketTimeout = Integer.parseInt(socketTimeoutStr);
            } catch (NumberFormatException nfe) {
                LOGGER.warn("Could not parse as Integer, socketTimeoutStr = {}", socketTimeoutStr);
            }
        }

        String maxConnectionsTotalStr = System.getProperty("org.apache.http.maxConnectionsTotal");
        if (!Strings.isNullOrEmpty(maxConnectionsTotalStr)) {
            try {
                maxConnectionsTotal = Integer.parseInt(maxConnectionsTotalStr);
            } catch (NumberFormatException nfe) {
                LOGGER.warn("Could not parse as Integer, maxConnectionsTotal = {}", maxConnectionsTotalStr);
            }
        }

        String maxConnectionsPerRouteStr = System.getProperty("org.apache.http.maxConnectionsPerRoute");
        if (!Strings.isNullOrEmpty(maxConnectionsPerRouteStr)) {
            try {
                maxConnectionsPerRoute = Integer.parseInt(maxConnectionsPerRouteStr);
            } catch (NumberFormatException nfe) {
                LOGGER.warn("Could not parse as Integer, maxConnectionsPerRoute = {}", maxConnectionsPerRouteStr);
            }
        }
    }

    /** Private constructor instead of public default constructor for this utility class. */
    private RestUtils() {
    }

    /**
     * Set socket timeout variable.
     * @param socketTimeout socket timeout.
     */
    public static void setSocketTimeout(final int socketTimeout) {
        RestUtils.socketTimeout = socketTimeout;
    }

    /**
     * Executes a POST against the given service with the given request body.
     *
     * @param service       the target URL.
     * @param requestString the request string, in JSON format.
     * @param headers       the headers to use for the request.
     * @return the response string, in JSON format.
     * @throws Exception if there is an issue with the POST.
     */
    public static String executePost(final String service,
                              final String requestString,
                              final Header[] headers) throws Exception {

        LOGGER.trace("About to POST to {} - {}", service, requestString);

        HttpEntity body = new StringEntity(requestString);

        return executePost(service, body, headers);
    }


    /**
     * Executes a POST against the given service with the given request body.
     *
     * @param service the target URL.
     * @param body  the request body.
     * @param headers the headers to use for the request.
     * @return the response string, in JSON format.
     * @throws Exception if there is an issue with the POST.
     */
    public static String executePost(final String service,
                                     final HttpEntity body,
                                     final Header[] headers) throws Exception {

        LOGGER.debug("About to POST to {}", service);

        HttpPost post = new HttpPost(service);
        post.setHeaders(headers);
        post.setEntity(body);

        try (CloseableHttpResponse response = getCloseableHttpClient().execute(post)) {
            String responseString = "";
            if (response.getEntity() != null) {
                responseString = EntityUtils.toString(response.getEntity());
                LOGGER.trace("responseString: {}", responseString);
            }

            if (response.getCode() != 200
                    && response.getCode() != 201
                    && response.getCode() != 204) {
                throw new RestUtilException(responseString, response.getCode());
            }
            return responseString;
        } catch (RestUtilException e) {
            throw e;
        } catch (Exception e) {
            throw new RestUtilException(e);
        }
    }

    /**
     * Executes an http PUT.
     *
     * @param service       URL for the request.
     * @param requestString Request body.
     * @param headers       Request header array.
     * @return PUT return.
     * @throws Exception When error encountered.
     */
    public static String executePut(final String service,
                             final String requestString,
                             final Header[] headers) throws Exception {
        LOGGER.trace("About to PUT to {} - {}", service, requestString);

        HttpPut put = new HttpPut(service);
        put.setHeaders(headers);
        HttpEntity body = new StringEntity(requestString);
        put.setEntity(body);

        // Execute HTTP PUT Request
        try (CloseableHttpResponse response = getCloseableHttpClient().execute(put)) {
            String responseString = "";
            if (response.getEntity() != null) {
                responseString = EntityUtils.toString(response.getEntity());
                LOGGER.trace("responseString: {}", responseString);
            }

            if (response.getCode() != 200
                    && response.getCode() != 201
                    && response.getCode() != 204) {
                throw new RestUtilException(responseString, response.getCode());
            }
            return responseString;
        } catch (RestUtilException e) {
            throw e;
        } catch (Exception e) {
            throw new RestUtilException(e);
        }
    }

    /**
     * Executes an http get.
     *
     * @param service       URL for the request.
     * @param headers       Request header array.
     * @return GET return.
     * @throws Exception When general error encountered.
     */
    public static String executeGet(final String service, final Header[] headers) throws Exception {

        LOGGER.trace("About to GET {}", service);

        HttpGet get = new HttpGet(service);
        get.setHeaders(headers);

        // Execute HTTP GET Request
        try (CloseableHttpResponse response = getCloseableHttpClient().execute(get)) {
            String responseString = "";
            if (response.getEntity() != null) {
                responseString = EntityUtils.toString(response.getEntity());
                LOGGER.trace("responseString: {}", responseString);
            }

            if (response.getCode() != 200) {
                throw new RestUtilException(responseString, response.getCode());
            }
            return responseString;
        } catch (RestUtilException e) {
            throw e;
        } catch (Exception e) {
            throw new RestUtilException(e);
        }
    }

    /**
     * Executes an http delete.
     *
     * @param service       URL for the request.
     * @param requestString Request body.
     * @param headers       Request header array.
     * @return GET return.
     * @throws Exception When general error encountered.
     */
    public static String executeDelete(final String service,
                                final String requestString,
                                final Header[] headers) throws Exception {

        LOGGER.trace("About to DELETE {} - {}", service, requestString);

        HttpDelete delete = new HttpDelete(service);
        delete.setHeaders(headers);

        try (CloseableHttpResponse response = getCloseableHttpClient().execute(delete)) {
            // Execute HTTP DELETE Request
            String responseString = "";
            if (response.getEntity() != null) {
                responseString = EntityUtils.toString(response.getEntity());
                LOGGER.trace("responseString: {}", responseString);
            }

            if (response.getCode() != 204) {
                throw new RestUtilException(responseString, response.getCode());
            }
            return responseString;
        } catch (RestUtilException e) {
            throw e;
        } catch (Exception e) {
            throw new RestUtilException(e);
        }
    }

    /**
     * Gets Http Client.
     * @return CloseableHttpClient
     * @throws Exception if can't retrieve client
     */
    public static synchronized CloseableHttpClient getCloseableHttpClient() throws Exception {
        if (closeableHttpClient == null) {
            LOGGER.trace("Creating closeableHttpClient...");
            closeableHttpClient = createCloseableHttpClient();
        }
        return closeableHttpClient;
    }

    /**
     * Create and configure a CloseableHttpClient.
     * @return a CloseableHttpClient.
     * @throws Exception if an error occurs.
     */
    public static CloseableHttpClient createCloseableHttpClient() throws Exception {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(Timeout.ofMilliseconds(connectionRequestTimeout))
                .setConnectTimeout(Timeout.ofMilliseconds(connectTimeout))
                .build();

        PoolingHttpClientConnectionManager httpClientConnectionManager = PoolingHttpClientConnectionManagerBuilder
                .create()
                .setSSLSocketFactory(new SSLConnectionSocketFactory(SSLContexts.custom()
                        .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                        .build()))
                .setDefaultSocketConfig(SocketConfig.custom()
                        .setSoTimeout(Timeout.ofMilliseconds(socketTimeout))
                        .build())
                .setMaxConnTotal(maxConnectionsTotal)
                .setMaxConnPerRoute(maxConnectionsPerRoute)
                .build();

        return HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(httpClientConnectionManager)
                .build();
    }


}

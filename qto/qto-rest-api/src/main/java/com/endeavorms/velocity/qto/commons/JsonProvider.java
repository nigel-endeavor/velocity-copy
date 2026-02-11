package com.endeavorms.velocity.qto.commons;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.endeavorms.velocity.qto.common.MediaTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyReader;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Standard JSON Provider for JAX-RS Services.
 * @author rconnolly
 * @since 1.0
 */
@Singleton
@Provider
@Consumes(MediaTypes.APPLICATION_JSON)
@Produces(MediaTypes.APPLICATION_JSON)
public class JsonProvider implements MessageBodyReader<Object>, MessageBodyWriter<Object> {

    /** Logging Facade.*/
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonProvider.class);

    /** The Jackson ObjectMapper used for JSON serialization. */
    private static final ObjectMapper MAPPER = new ObjectMapper();
    static {
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        MAPPER.registerModule(new GuavaModule());
    }

    /** Request Headers.*/
    @Context
    private HttpHeaders requestHeaders;



    @Override
    public boolean isWriteable(final Class<?> aClass, final Type type, final Annotation[] annotations,
                               final MediaType mediaType) {
        return true;
    }


    @Override
    public long getSize(final Object o, final Class<?> aClass, final Type type, final Annotation[] annotations,
                        final MediaType mediaType) {
        // not necessary to figure out content length by writing the object twice.
        return -1;
    }


    @Override
    public void writeTo(final Object o, final Class<?> aClass, final Type type, final Annotation[] annotations,
                        final MediaType mediaType, final MultivaluedMap<String, Object> stringObjectMultivaluedMap,
                        final OutputStream outputStream)
            throws IOException {

        ObjectWriter writer = MAPPER.writerWithView(JsonViews.Public.class);

        // -DjsonPrettyPrint=true will trigger pretty printing
        boolean prettyPrint = Boolean.parseBoolean(System.getProperty("jsonPrettyPrint", "false"));
        if (prettyPrint) {
            writer = writer.with(new DefaultPrettyPrinter());
        }

        writer.writeValue(outputStream, o);
    }


    @Override
    public boolean isReadable(final Class<?> type, final Type genericType, final Annotation[] annotations,
                              final MediaType mediaType) {
        return true;
    }

    @Override
    public Object readFrom(final Class<Object> type, final Type genericType, final Annotation[] annotations,
                           final MediaType mediaType, final MultivaluedMap<String, String> httpHeaders,
                           final InputStream entityStream) throws IOException {

        JsonParser jp = MAPPER.getFactory().createParser(entityStream);
        // Important: we are NOT to close the underlying stream after mapping, so we need to instruct parser.
        jp.disable(JsonParser.Feature.AUTO_CLOSE_SOURCE);

        return MAPPER.readValue(jp, type);
    }
}

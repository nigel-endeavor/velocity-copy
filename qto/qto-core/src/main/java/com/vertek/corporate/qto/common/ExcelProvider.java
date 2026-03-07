package com.vertek.corporate.qto.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

/**
 * from vertek-commons.
 * MessageBodyWriter implementation for writing Excel Spreadsheet responses from an instance of PaginatedResult.
 * @author <a href="mailto:rconnolly@vertek.com">rconnolly</a>
 * @since 1.2.0 - 6/10/13 4:20 PM
 */
@Provider
@Produces({ MediaTypes.MS_EXCEL_2007, MediaTypes.MS_EXCEL })
public class ExcelProvider implements MessageBodyWriter<ResourceWrapper> {

    /** Logging. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelProvider.class);

    /** Max rows for Excel.*/
    public static final int LIMIT = 1048576;

    @Override
    public boolean isWriteable(final Class<?> aClass, final Type type, final Annotation[] annotations,
                               final MediaType mediaType) {
        return aClass.equals(ResourceWrapper.class);
    }

    @Override
    public long getSize(final ResourceWrapper resourceWrapper, final Class<?> aClass, final Type type,
                        final Annotation[] annotations, final MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(final ResourceWrapper resourceWrapper, final Class<?> aClass, final Type type,
                        final Annotation[] annotations, final MediaType mediaType,
                        final MultivaluedMap<String, Object> httpHeaders, final OutputStream outputStream)
            throws IOException {


        LOGGER.trace("about to write... mediaType.toString = {}", mediaType.toString());

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        String fields = (String) resourceWrapper.getProperties().get("fields");
        String headers = (String) resourceWrapper.getProperties().get("headers");

        List<Object[]> rows = Lists.newArrayList();
        // this is the default case where we have a wrapped paginated result.
        if (resourceWrapper.getTarget() instanceof PaginatedResult) {

            PaginatedResult<? extends BaseEntity> paginatedResult =
                    (PaginatedResult<? extends BaseEntity>) resourceWrapper.getTarget();
            for (Object entity : paginatedResult.getCollection()) {
//                String json = mapper.writerWithView(JsonViews.Public.class).writeValueAsString(entity);
                String json = mapper.writeValueAsString(entity);
                List<Object> columnValues = Lists.newArrayList();
                List<String> fieldList = Lists.newArrayList(Splitter.on(',').split(fields));

                for (String field : fieldList) {
                    columnValues.add(getJsonValue(json, field));
                }

                rows.add(columnValues.toArray());
            }
        } else {
            // probably encountered a situation where a "collection" property was added instead.
            Object collectionObject = resourceWrapper.getProperties().get("collection");
            if (collectionObject != null) {
                Collection<ResourceWrapper> collection = (Collection<ResourceWrapper>) collectionObject;

                // this should be our collection.
                for (ResourceWrapper entity : collection) {
//                    String json = mapper.writerWithView(JsonViews.Public.class).writeValueAsString(entity.getTarget());
                    String json = mapper.writeValueAsString(entity.getTarget());
                    List<Object> columnValues = Lists.newArrayList();
                    List<String> fieldList = Lists.newArrayList(Splitter.on(',').split(fields));
                    for (String field : fieldList) {
                        columnValues.add(getJsonValue(json, field));
                    }
                    rows.add(columnValues.toArray());
                }
            }
        }

        String fileName;

        if (mediaType.toString().equals(MediaTypes.MS_EXCEL_2007)) {
            fileName = "workbook.xlsx";
        } else {
            fileName = "workbook.xls";
        }
        // deliver the spreadsheet as a file download.
        httpHeaders.putSingle("Content-Disposition", "attachment; filename=" + fileName);

        List<String> columnHeaders = Lists.newArrayList(Splitter.on(',').split(headers));
        SpreadsheetReport report = new SpreadsheetReport(fileName, columnHeaders, rows);
        report.writeTo(outputStream);
    }


    /**
     * Gets nested property values from a json string.
     * @param json the JSON string.
     * @param propertyPath the dot notation property path.
     * @return property value.
     */
    private Object getJsonValue(final String json, final String propertyPath) {
        try {
            JsonNode rootNode = new ObjectMapper().readTree(json);
            JsonNode currentNode = null;
            List<String> propertyPaths = Lists.newArrayList(Splitter.on('.').split(propertyPath));
            for (String property : propertyPaths) {
                currentNode = (currentNode == null)
                        ? rootNode.get(property)
                        : currentNode.get(property);

                // try to handle some nested array paths.
                if (currentNode != null && currentNode.isArray()) {
                    return getArrayValue(currentNode, propertyPaths, property);
                }
            }

            // get the value
            Object value = null;
            if (currentNode != null) {
                switch (currentNode.asToken()) {
                    case VALUE_TRUE:
                    case VALUE_FALSE:
                        value = currentNode.asBoolean();
                        break;
                    case VALUE_NUMBER_FLOAT:
                        value = currentNode.asDouble();
                        break;
                    case VALUE_NUMBER_INT:
                        value = currentNode.asInt();
                        break;
                    default:
                        value = currentNode.asText();
                        break;
                }
            }

            return value;

        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to parse json");
        }
    }


    /**
     * Try to get array values as a csv.
     * @param jsonArray the array JsonNode.
     * @param propertyPaths the passed property paths.
     * @param arrayPath the path of the array.
     * @return the array property path as csv.
     */
    private Object getArrayValue(final JsonNode jsonArray, final List<String> propertyPaths, final String arrayPath) {
        String value = "";
        for (String path : propertyPaths) {
            if (!path.equals(arrayPath)) {
                for (JsonNode objNode : jsonArray) {
                    value += objNode.get(path).asText() + ",";
                }
            }
        }
        if (!Strings.isNullOrEmpty(value) && value.endsWith(",")) {
            value = value.substring(0, value.length() - 1);
        }

        return value;
    }


}

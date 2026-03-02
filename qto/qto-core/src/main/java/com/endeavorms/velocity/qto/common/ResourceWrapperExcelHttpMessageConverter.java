package com.endeavorms.velocity.qto.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Spring HttpMessageConverter for writing ResourceWrapper as Excel spreadsheet.
 * Excel HTTP message converter for Spring MVC.
 */
public class ResourceWrapperExcelHttpMessageConverter implements HttpMessageConverter<ResourceWrapper> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceWrapperExcelHttpMessageConverter.class);

    private static final List<MediaType> SUPPORTED_MEDIA_TYPES = List.of(
            MediaType.parseMediaType(MediaTypes.MS_EXCEL_2007),
            MediaType.parseMediaType(MediaTypes.MS_EXCEL)
    );

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return ResourceWrapper.class.isAssignableFrom(clazz) && mediaType != null
                && (MediaTypes.MS_EXCEL_2007.equals(mediaType.toString())
                || MediaTypes.MS_EXCEL.equals(mediaType.toString()));
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return SUPPORTED_MEDIA_TYPES;
    }

    @Override
    public ResourceWrapper read(Class<? extends ResourceWrapper> clazz, HttpInputMessage inputMessage) {
        throw new UnsupportedOperationException("Reading Excel to ResourceWrapper is not supported");
    }

    @Override
    public void write(ResourceWrapper resourceWrapper, MediaType contentType,
                     HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        LOGGER.trace("about to write... mediaType = {}", contentType);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        String fields = (String) resourceWrapper.getProperties().get("fields");
        String headers = (String) resourceWrapper.getProperties().get("headers");

        List<Object[]> rows = Lists.newArrayList();
        if (resourceWrapper.getTarget() instanceof PaginatedResult) {
            PaginatedResult<? extends BaseEntity> paginatedResult =
                    (PaginatedResult<? extends BaseEntity>) resourceWrapper.getTarget();
            for (Object entity : paginatedResult.getCollection()) {
                String json = mapper.writeValueAsString(entity);
                List<Object> columnValues = Lists.newArrayList();
                List<String> fieldList = Lists.newArrayList(Splitter.on(',').split(fields));
                for (String field : fieldList) {
                    columnValues.add(getJsonValue(json, field));
                }
                rows.add(columnValues.toArray());
            }
        } else {
            Object collectionObject = resourceWrapper.getProperties().get("collection");
            if (collectionObject != null) {
                @SuppressWarnings("unchecked")
                Collection<ResourceWrapper> collection = (Collection<ResourceWrapper>) collectionObject;
                for (ResourceWrapper entity : collection) {
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

        String fileName = MediaTypes.MS_EXCEL_2007.equals(contentType.toString()) ? "workbook.xlsx" : "workbook.xls";
        outputMessage.getHeaders().set("Content-Disposition", "attachment; filename=" + fileName);

        List<String> columnHeaders = Lists.newArrayList(Splitter.on(',').split(headers));
        SpreadsheetReport report = new SpreadsheetReport(fileName, columnHeaders, rows);
        report.writeTo(outputMessage.getBody());
    }

    private Object getJsonValue(String json, String propertyPath) {
        try {
            JsonNode rootNode = new ObjectMapper().readTree(json);
            JsonNode currentNode = null;
            List<String> propertyPaths = Lists.newArrayList(Splitter.on('.').split(propertyPath));
            for (String property : propertyPaths) {
                currentNode = (currentNode == null) ? rootNode.get(property) : currentNode.get(property);
                if (currentNode != null && currentNode.isArray()) {
                    return getArrayValue(currentNode, propertyPaths, property);
                }
            }
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

    private Object getArrayValue(JsonNode jsonArray, List<String> propertyPaths, String arrayPath) {
        StringBuilder value = new StringBuilder();
        for (String path : propertyPaths) {
            if (!path.equals(arrayPath)) {
                for (JsonNode objNode : jsonArray) {
                    value.append(objNode.get(path).asText()).append(",");
                }
            }
        }
        String result = value.toString();
        if (!Strings.isNullOrEmpty(result) && result.endsWith(",")) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }
}

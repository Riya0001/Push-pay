package com.tiptappay.store.app.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
public class DataUtils {
    public static <T> String convertToJsonString(T object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (Exception exception) {
            log.error("DataUtils.convertToJsonString > {}", exception.getMessage());
            return null;
        }
    }

    public static <T> T convertToObject(String responseBody, Class<T> responseType) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(responseBody, responseType);
        } catch (Exception exception) {
            log.error("DataUtils.convertToObject > {}", exception.getMessage());
            return null;
        }
    }

    public static <T> List<T> convertToObjectList(String responseBody, Class<T> responseType) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            TypeFactory typeFactory = objectMapper.getTypeFactory();
            return objectMapper.readValue(responseBody, typeFactory.constructCollectionType(List.class, responseType));
        } catch (Exception exception) {
            log.error("DataUtils.convertToObjectList > {}", exception.getMessage());
            return null;
        }
    }

    public static List<String> getFileNamesInPath(String directoryPath) {
        try {
            List<String> fileList = new ArrayList<>();

            // Use ResourcePatternResolver to resolve classpath resources
            ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(null);

            // Resolve the resources in the specified directory on the classpath
            Resource[] resources = resolver.getResources("classpath:" + directoryPath + "/*");

            for (Resource resource : resources) {
                // Extract the filename without extension from the resource
                String fileName = getFileNameWithoutExtension(Objects.requireNonNull(resource.getFilename()));
                fileList.add(fileName);
            }

            return fileList;
        } catch (Exception exception) {
            log.error("DataUtils.getFileNamesInPath > Exception occurred : {}", exception.getMessage());
            return Collections.emptyList();
        }
    }

    public static String getFileNameWithoutExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex != -1) {
            return fileName.substring(0, lastDotIndex);
        } else {
            return fileName;
        }
    }

    public static String escapeJsonString(String html) {
        return "\"" + html.replace("\"", "\\\"").replace("\n", "") + "\"";
    }
}

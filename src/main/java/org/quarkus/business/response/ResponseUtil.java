package org.quarkus.business.response;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.quarkus.business.utils.User;

import java.util.*;

public class ResponseUtil {

    public static <T> Response buildResponseList(List<T> data) {
        if (data.isEmpty()) {
            ApiResponse<List<T>> apiResponse = new ApiResponse<>(404, new HashMap<>(), data);
            return Response.status(404).entity(apiResponse).type(MediaType.APPLICATION_JSON).build();
        } else {
            ApiResponse<List<T>> apiResponse = new ApiResponse<>(200, new HashMap<>(), data);
            return Response.ok(apiResponse, MediaType.APPLICATION_JSON_TYPE).build();
        }
    }

    public static <T> Response buildResponseObject(T data) {
        if (data == null) {
            HashMap<String, List<String>> errorHeaders = new HashMap<>();
            errorHeaders.put("errors", Arrays.asList("Name is required", "Description is required", "Size is required"));
            return buildResponseHeaders(errorHeaders);
        } else {
            ApiResponse<T> apiResponse = new ApiResponse<>(200, new HashMap<>(), data);
            return Response.ok(apiResponse, MediaType.APPLICATION_JSON).build();
        }
    }

    public static <T> Response handleError(Throwable throwable) {
        Map<String, List<String>> errorHeaders = new HashMap<>();
        errorHeaders.put("Error-Message", Collections.singletonList(throwable.getMessage()));
        ApiResponse<List<User>> errorResponse = new ApiResponse<>(500, errorHeaders, null);
        return Response.status(500).entity(errorResponse).type(MediaType.APPLICATION_JSON).build();
    }

    public static <T> Response buildResponseHeaders(HashMap<String, List<String>> data) {
        ApiResponse<T> apiResponse = new ApiResponse<>(400, data, null);
        return Response.status(400).entity(apiResponse).type(MediaType.APPLICATION_JSON).build();
    }
}

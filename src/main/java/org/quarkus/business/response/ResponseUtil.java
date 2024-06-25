package org.quarkus.business.response;

import jakarta.ws.rs.core.Response;
import org.openapitools.client.ApiResponse;
import org.quarkus.business.utils.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseUtil {

    public static <T> Response buildResponseList(List<T> data) {
        if (data.isEmpty()) {
            ApiResponse<List<T>> apiResponse = new ApiResponse<>(404, new HashMap<>(), data);
            return Response.status(404).entity(apiResponse).build();
        } else {
            ApiResponse<List<T>> apiResponse = new ApiResponse<>(200, new HashMap<>(), data);
            return Response.ok(apiResponse).build();
        }
    }

    public static <T> Response buildResponseObject(T data) {
        if (data == null) {
            ApiResponse<T> apiResponse = new ApiResponse<>(404, new HashMap<>(), data);
            return Response.status(404).entity(apiResponse).build();
        } else {
            ApiResponse<T> apiResponse = new ApiResponse<>(200, new HashMap<>(), data);
            return Response.ok(apiResponse).build();
        }
    }

    public static <T> Response handleError(Throwable throwable) {
        Map<String, List<String>> errorHeaders = new HashMap<>();
        errorHeaders.put("Error-Message", Collections.singletonList(throwable.getMessage()));
        ApiResponse<List<User>> errorResponse = new ApiResponse<>(500, errorHeaders, null);
        return Response.status(500).entity(errorResponse).build();
    }

    public static <T> Response buildResponseHeaders(HashMap<String, List<String>> data) {
        ApiResponse<T> apiResponse = new ApiResponse<>(400, data, null);
        return Response.status(400).entity(apiResponse).build();
    }
}

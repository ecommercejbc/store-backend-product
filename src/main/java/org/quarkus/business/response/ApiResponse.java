package org.quarkus.business.response;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@RegisterForReflection
public class ApiResponse<T> {
    final private int statusCode;
    final private Map<String, List<String>> headers;
    final private T data;
}

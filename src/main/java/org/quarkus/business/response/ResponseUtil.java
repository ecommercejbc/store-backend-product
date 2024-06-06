package org.quarkus.business.response;


import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.Response;

public class ResponseUtil {

    public static Response buildResponse(Response.Status status, String message) {
        RespBase response = new RespBase.Builder()
                .status(status.getStatusCode())
                .message(message)
                .build();
        return Response.status(status).entity(response).build();
    }

    public static Uni<Response> handleError(Throwable throwable, String message) {
        RespBase response = new RespBase.Builder()
                .status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                .message(message)
                .build();
        return Uni.createFrom().item(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build());
    }

}

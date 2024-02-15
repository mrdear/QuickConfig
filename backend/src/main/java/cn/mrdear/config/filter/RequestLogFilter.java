package cn.mrdear.config.filter;

import io.quarkus.logging.Log;
import io.vertx.core.http.HttpServerRequest;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

/**
 * 请求日志拦截器
 *
 * @author quding
 * @since 2024/2/15
 */
@Provider
public class RequestLogFilter implements ContainerRequestFilter {

    @Context
    UriInfo uriInfo;

    @Context
    HttpServerRequest request;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        String method = containerRequestContext.getMethod();
        String path = uriInfo.getPath();
        String remote = request.remoteAddress().toString();

        Log.infof("Request %s %s from %s", method, path, remote);
    }

}

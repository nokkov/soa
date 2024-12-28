package ru.itmo.config;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CorsFilter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext req, ContainerResponseContext resp) {
        resp.getHeaders().add("Access-Control-Allow-Origin", "*");
        resp.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        resp.getHeaders().add("Access-Control-Allow-Credentials", "true");
        resp.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PATCH, DELETE, OPTIONS, HEAD");
        resp.getHeaders().add("Access-Control-Max-Age", "1209600");
    }
}

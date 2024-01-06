package com.example.laborator7;
import com.example.laborator7.model.Timetable;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
@ApplicationScoped
public class CacheSubmissionsFilter implements ContainerResponseFilter {
    private static final Logger LOGGER = Logger.getLogger(CacheSubmissionsFilter.class.getName());
    private List<Timetable> timetableCache = new ArrayList<>();
    private boolean isCachePopulated = false;

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        String method = requestContext.getMethod();
        URI requestUri = requestContext.getUriInfo().getRequestUri();
        LOGGER.log(Level.INFO, "LOGGER: Intercepted {0} request for URI: {1}", new Object[]{method, requestUri});

        // Check if the request is for the 'get all timetables' endpoint and not 'get by user id'
        String getAllEndpoint = "/timetables";
        if (requestUri.getPath().endsWith(getAllEndpoint)) {
            if ("GET".equalsIgnoreCase(method) && responseContext.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
                if (!isCachePopulated) {
                    LOGGER.log(Level.INFO, "LOGGER: Cache is empty. Attempting to populate cache.");
                    Object entity = responseContext.getEntity();
                    if (entity instanceof List<?>) {
                        timetableCache = new ArrayList<>((List<Timetable>) entity);
                        isCachePopulated = true;
                        LOGGER.log(Level.INFO, "LOGGER: Cache populated with {0} timetable entries.", timetableCache.size());
                    } else {
                        LOGGER.log(Level.WARNING, "LOGGER: Expected entity is not of type List<Timetable>.");
                    }
                } else {
                    LOGGER.log(Level.INFO, "LOGGER: Serving response from cache.");
                    responseContext.setEntity(timetableCache, null, MediaType.APPLICATION_JSON_TYPE);
                }
            }
        }

        // Invalidate cache for modification requests
        if ("POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method) || "DELETE".equalsIgnoreCase(method)) {
            LOGGER.log(Level.INFO, "LOGGER: Modification request detected. Invalidating cache.");
            timetableCache.clear();
            isCachePopulated = false;
        }
    }

}

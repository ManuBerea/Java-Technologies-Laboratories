package com.example.laborator7.service;

import com.example.laborator7.model.Timetable;
import com.example.laborator7.repository.TimetableRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/timetables")
@Api(value = "/timetables")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TimetableSubmissionService {

    @Inject
    private TimetableRepository timetableRepository;

    @POST
    @ApiOperation(value = "Create a new timetable entry", notes = "Submits a new timetable entry and stores it in the database")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created the timetable entry"),
            @ApiResponse(code = 400, message = "Invalid input provided")
    })
    public Response createTimetable(@ApiParam(value = "Timetable object to be created", required = true) Timetable timetable) {
        try {
            timetableRepository.createTimetable(timetable);
            return Response.status(Response.Status.CREATED).entity(timetable).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    @ApiOperation(value = "Update an existing timetable entry",
            notes = "Updates an existing timetable entry specified by its ID with new data")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully updated the timetable entry"),
            @ApiResponse(code = 404, message = "Timetable entry not found"),
            @ApiResponse(code = 400, message = "Invalid input provided")
    })
    public Response updateTimetable(@ApiParam(value = "ID of the timetable entry to be updated", required = true) @PathParam("id") Long id,
                                    @ApiParam(value = "Updated timetable object", required = true) Timetable timetable) {
        try {
            timetableRepository.updateTimetable(id, timetable);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @ApiOperation(value = "Delete a timetable entry",
            notes = "Deletes the timetable entry specified by its ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted the timetable entry"),
            @ApiResponse(code = 404, message = "Timetable entry not found")
    })
    public Response deleteTimetable(@ApiParam(value = "ID of the timetable entry to be deleted", required = true) @PathParam("id") Long id) {
        try {
            timetableRepository.deleteTimetable(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @ApiOperation(value = "List all timetable entries",
            notes = "Retrieves a list of all timetable entries")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the list of timetable entries")
    })
    public Response getAllTimetables() {
        List<Timetable> timetables = timetableRepository.getAllTimetables();
        return Response.ok(timetables).build();
    }

    @GET
    @Path("/user/{userId}")
    @ApiOperation(value = "List all timetable entries for a specific user",
            notes = "Retrieves a list of all timetable entries for the given user ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the list of timetable entries"),
            @ApiResponse(code = 404, message = "User not found or no timetables for the user")
    })
    public Response getTimetablesByUserId(@ApiParam(value = "User ID to retrieve timetables for", required = true) @PathParam("userId") Long userId) {
        List<Timetable> timetables = timetableRepository.findTimetablesByUserId(userId);
        if (timetables == null || timetables.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No timetables found for user with ID: " + userId).build();
        }
        return Response.ok(timetables).build();
    }

}
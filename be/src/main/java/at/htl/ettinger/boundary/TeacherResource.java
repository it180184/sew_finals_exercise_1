package at.htl.ettinger.boundary;

import at.htl.ettinger.teacher.TeacherService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/teacher")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TeacherResource {

    @Inject
    TeacherService teacherService;

    @GET
    public Response getTeachers() {
        return Response.ok(teacherService.getAll()).build();
    }
}

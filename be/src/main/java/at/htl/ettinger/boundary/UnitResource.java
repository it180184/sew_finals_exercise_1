package at.htl.ettinger.boundary;

import at.htl.ettinger.schoolclass.SchoolClass;
import at.htl.ettinger.schoolclass.SchoolClassService;
import at.htl.ettinger.teacher.Teacher;
import at.htl.ettinger.teacher.TeacherService;
import at.htl.ettinger.unit.Unit;
import at.htl.ettinger.unit.UnitDTO;
import at.htl.ettinger.unit.UnitService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;

@Path("/unit")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UnitResource {

    @Inject
    UnitService unitService;
    @Inject
    TeacherService teacherService;
    @Inject
    SchoolClassService schoolClassService;
    @Inject
    UpdateSocket updateSocket;

    @GET
    @Path("/class/{id}")
    public Response getUnitsForClass(@PathParam("id") String classId){
        return Response.ok(unitService.getAllForClass(classId)).build();
    }

    @POST
    @Transactional
    public Response postUnit(UnitDTO unitDTO, @Context UriInfo uriInfo) {
        Teacher t = teacherService.getById(unitDTO.teacherId());
        SchoolClass sc = schoolClassService.getById(unitDTO.classId());
        if (t == null || sc == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Unit u = unitService.postUnit(Unit.fromDTO(unitDTO), t, sc);
        updateSocket.sendUpdate(u.toDTO());
        URI uri = uriInfo.getAbsolutePathBuilder().path(Long.toString(u.id)).build();
        return Response.created(uri).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteUnit(@PathParam("id") long id) {
        unitService.delete(id);
        return Response.ok().build();
    }
}

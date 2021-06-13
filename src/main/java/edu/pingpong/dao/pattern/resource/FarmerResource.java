package edu.pingpong.dao.pattern.resource;

import edu.pingpong.dao.pattern.entity.Farmer;
import edu.pingpong.dao.pattern.entity.Fruit;
import edu.pingpong.dao.pattern.service.FarmerService;
import edu.pingpong.dao.pattern.util.FarmersResponse;
import edu.pingpong.dao.pattern.util.FruitsResponse;
import edu.pingpong.dao.pattern.util.MessagedResponse;
import edu.pingpong.dao.pattern.util.PageRequest;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/farmers")
public class FarmerResource {

    @Inject
    FarmerService farmerService;

    public FarmerResource() {}

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response farmersData(@BeanParam PageRequest pageRequest) {
        List<Farmer> data = farmerService.getData(pageRequest);
        if (data.isEmpty()) return Response.status(Response.Status.NOT_FOUND)
                .entity(new MessagedResponse("The farmer storage is without farmers!"))
                .build();
        return Response.status(Response.Status.OK).entity(
                new FarmersResponse(data)).build();
    }

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addData(@Valid Farmer farmer) {
        farmerService.addFarmer(farmer);
        return Response.status(Response.Status.OK)
                .entity(new MessagedResponse("Added " + farmer.name + " farmer succesfully."))
                .build();
    }
}

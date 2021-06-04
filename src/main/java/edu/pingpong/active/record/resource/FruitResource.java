package edu.pingpong.active.record.resource;


import edu.pingpong.active.record.service.FruitService;
import edu.pingpong.active.record.entity.Fruit;
import edu.pingpong.active.record.util.FruitsResponse;
import edu.pingpong.active.record.util.MessagedResponse;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/fruits")
public class FruitResource {

    @Inject
    FruitService service;

    public FruitResource() {
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response fruitsData() {
        return Response.status(Response.Status.OK).entity(
                new FruitsResponse(service.getData())).build();

        //return Response.ok(service.getData(), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addData(@Valid Fruit fruit) {
        service.addFruit(fruit);
        return Response.status(Response.Status.OK)
                .entity(new MessagedResponse("Added " + fruit.name + " fruit."))
                .build();
    }

    @DELETE
    @Transactional
    @Path("/{fruitname}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteData(@PathParam("fruitname") String fruitname) {
        Optional<Fruit> fruit = service.getFruit(fruitname);
        if (fruit.isEmpty()) return Response.status(Response.Status.NOT_FOUND)
                .entity(new MessagedResponse("The fruit with name " + fruitname + " doesn't exist."))
                .build();
        service.removeFruit(fruitname);
        return Response.accepted(fruitname).build();
    }

    @GET
    @Path("/{fruitname}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getData(@PathParam("fruitname") String fruitname) {
        Optional<Fruit> fruit = service.getFruit(fruitname);
        return fruit.isPresent() ? Response.ok(fruit).build() :
                Response.status(Response.Status.NOT_FOUND)
                        .entity(new MessagedResponse("The fruit with name " + fruitname + " doesn't exist."))
                        .build();
    }
}
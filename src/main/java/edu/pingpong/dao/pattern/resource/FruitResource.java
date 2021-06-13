package edu.pingpong.dao.pattern.resource;


import edu.pingpong.dao.pattern.entity.Farmer;
import edu.pingpong.dao.pattern.service.FarmerService;
import edu.pingpong.dao.pattern.service.FruitService;
import edu.pingpong.dao.pattern.entity.Fruit;
import edu.pingpong.dao.pattern.util.FruitsResponse;
import edu.pingpong.dao.pattern.util.MessagedResponse;
import edu.pingpong.dao.pattern.util.PageRequest;
import io.quarkus.panache.common.Page;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/fruits")
public class FruitResource {

    @Inject
    FruitService fruitService;

    @Inject
    FarmerService farmerService;

    public FruitResource() {
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response fruitsData(@BeanParam PageRequest pageRequest) {
        List<Fruit> data = fruitService.getData(pageRequest);
        if (data.isEmpty()) return Response.status(Response.Status.NOT_FOUND)
                .entity(new MessagedResponse("The fruit storage is without fruits!"))
                .build();
        return Response.status(Response.Status.OK).entity(
                new FruitsResponse(data)).build();
    }

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addData(@Valid Fruit fruit) {
        Optional<Farmer> optionalFarmer = farmerService.getFarmer(fruit.farmer.name);
        if (optionalFarmer.isEmpty()) farmerService.addFarmerWithoutLocation(fruit.farmer.name);
        fruitService.addFruit(fruit);
        return Response.status(Response.Status.OK)
                .entity(new MessagedResponse("Added " + fruit.name + " fruit succesfully."))
                .build();
    }

    @PUT
    @Path("/{fruitname}")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateData(@PathParam("fruitname") String fruitname, Fruit newFruit) {
        Optional<Fruit> fruitToUpdate = fruitService.getFruit(fruitname);
        if (fruitToUpdate.isEmpty()) return Response.status(Response.Status.NOT_FOUND)
                .entity(new MessagedResponse("The fruit with name " + fruitname + " doesn't exist."))
                .build();
        fruitService.updateFruit(fruitToUpdate, newFruit);
        return Response.status(Response.Status.OK)
                .entity(new MessagedResponse("Updated " + fruitname + " fruit succesfully."))
                .build();
    }

    @DELETE
    @Transactional
    @Path("/{fruitname}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteData(@PathParam("fruitname") String fruitname) {
        Optional<Fruit> fruit = fruitService.getFruit(fruitname);
        if (fruit.isEmpty()) return Response.status(Response.Status.NOT_FOUND)
                .entity(new MessagedResponse("The fruit with name " + fruitname + " doesn't exist."))
                .build();
        fruitService.removeFruit(fruitname);
        return Response.status(Response.Status.OK)
                .entity(new MessagedResponse("Deleted " + fruitname + " fruit succesfully."))
                .build();
    }

    @GET
    @Path("/{fruitname}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getData(@PathParam("fruitname") String fruitname) {
        Optional<Fruit> fruit = fruitService.getFruit(fruitname);
        return fruit.isPresent() ? Response.ok(fruit).build() :
                Response.status(Response.Status.NOT_FOUND)
                        .entity(new MessagedResponse("The fruit with name " + fruitname + " doesn't exist."))
                        .build();
    }
}
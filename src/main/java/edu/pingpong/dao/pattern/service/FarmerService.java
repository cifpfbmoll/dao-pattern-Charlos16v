package edu.pingpong.dao.pattern.service;

import edu.pingpong.dao.pattern.entity.Farmer;
import edu.pingpong.dao.pattern.repository.FarmerRepository;
import edu.pingpong.dao.pattern.util.PageRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;


@ApplicationScoped
public class FarmerService {

    @Inject
    FarmerRepository repository;

    public FarmerService() {}

    public List<Farmer> getData(PageRequest pageRequest) {
        return repository.getAllDataSortedByName(pageRequest);
    }

    public Optional<Farmer> getFarmer(String name) {
        return name.isBlank() ?
                Optional.ofNullable(null) :
                repository.getFarmerByName(name);
    }

    public void addFarmerWithoutLocation(String farmerName) {
        Farmer newFarmer = new Farmer(farmerName, "");
        repository.addFarmer(newFarmer);
    }

    public void addFarmer(Farmer farmer) {
        repository.addFarmer(farmer);
    }
}

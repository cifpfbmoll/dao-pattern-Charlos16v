package edu.pingpong.dao.pattern.repository;

import edu.pingpong.dao.pattern.entity.Farmer;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class FarmerRepository implements PanacheRepositoryBase<Farmer, String> {

    public List<Farmer> getAllDataSortedByName() {
        return this.listAll(Sort.by("name").ascending());
    }

    public Optional<Farmer> getFarmerByName(String name) {
        return this.find("name", name).firstResultOptional();
    }

    public void addFarmer(Farmer farmer) {
        this.persistAndFlush(farmer);
    }
}
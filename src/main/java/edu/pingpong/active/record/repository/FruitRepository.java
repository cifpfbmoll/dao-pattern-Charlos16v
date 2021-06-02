package edu.pingpong.active.record.repository;

import edu.pingpong.active.record.entity.Fruit;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class FruitRepository implements PanacheRepositoryBase<Fruit, Integer> {

    public List<Fruit> getAllDataSortedById() {
        return this.listAll(Sort.by("id").ascending());
    }

    public Optional<Fruit> getFruitByName(String name) {
        return this.find("name", name).firstResultOptional();
    }

    public void addFruit(Fruit fruit){
        this.persist(fruit);
    }

    public void removeFruitByName(String name) {
        Optional<Fruit> fruit = this.getFruitByName(name);
        fruit.ifPresent(this::delete);
    }
}

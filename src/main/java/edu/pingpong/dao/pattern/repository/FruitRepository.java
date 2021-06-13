package edu.pingpong.dao.pattern.repository;

import edu.pingpong.dao.pattern.entity.Fruit;
import edu.pingpong.dao.pattern.util.PageRequest;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.BeanParam;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class FruitRepository implements PanacheRepositoryBase<Fruit, Integer> {

    public List<Fruit> getAllDataSortedById(PageRequest pageRequest) {
        //return this.listAll(Sort.by("id").ascending());
        return this.findAll(Sort.ascending("id")).page(Page.of(pageRequest.getPageNum(), pageRequest.getPageSize())).list();
    }

    public Optional<Fruit> getFruitByName(String name) {
        return this.find("name", name).firstResultOptional();
    }

    public void addFruit(Fruit fruit){
        this.persist(fruit);
    }

    public void updateFruit(Optional<Fruit> fruitToUpdate, Fruit newFruit){
        fruitToUpdate.ifPresent(f -> f.setNameAndDescription(newFruit.name, newFruit.description));
    }

    public void removeFruitByName(String name) {
        Optional<Fruit> fruit = this.getFruitByName(name);
        fruit.ifPresent(this::delete);
    }
}

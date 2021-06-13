package edu.pingpong.dao.pattern.service;

import edu.pingpong.dao.pattern.entity.Fruit;
import edu.pingpong.dao.pattern.repository.FruitRepository;
import edu.pingpong.dao.pattern.util.PageRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import java.util.List;
import java.util.Optional;

// Single bean instance
@ApplicationScoped
public class FruitService {

    @Inject
    FruitRepository repository;

    public FruitService() {
    }

    public List<Fruit> getData(PageRequest pageRequest) {
        return repository.getAllDataSortedById(pageRequest);
    }

    public Optional<Fruit> getFruit(String name) {
        return name.isBlank() ?
                Optional.ofNullable(null) :
                repository.getFruitByName(name);
    }

    public void addFruit(Fruit fruit) {
        repository.addFruit(fruit);
    }

    public void updateFruit(Optional<Fruit> fruitToUpdate, Fruit newFruit) {
        repository.updateFruit(fruitToUpdate, newFruit);
    }

    public void removeFruit(String name) {
        repository.removeFruitByName(name);
    }
}

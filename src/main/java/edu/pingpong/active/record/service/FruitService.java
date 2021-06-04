package edu.pingpong.active.record.service;

import edu.pingpong.active.record.entity.Fruit;
import edu.pingpong.active.record.repository.FruitRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

// Single bean instance
@ApplicationScoped
public class FruitService {

    @Inject
    FruitRepository repository;

    public FruitService() {
    }

    public List<Fruit> getData() {
        return repository.getAllDataSortedById();
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

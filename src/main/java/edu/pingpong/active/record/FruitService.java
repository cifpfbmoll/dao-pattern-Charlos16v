package edu.pingpong.active.record;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public void removeFruit(String name) {
        repository.removeFruitByName(name);
    }
}

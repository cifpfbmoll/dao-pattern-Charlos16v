package edu.pingpong.dao.pattern;

import edu.pingpong.dao.pattern.entity.Farmer;
import edu.pingpong.dao.pattern.entity.Fruit;
import edu.pingpong.dao.pattern.service.FarmerService;
import edu.pingpong.dao.pattern.service.FruitService;
import io.quarkus.test.junit.QuarkusTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Optional;

@QuarkusTest
@Transactional
public class FruitServiceTest {

    // ./mvnw -Dtest=FruitServiceTest test
    @Inject
    FruitService fruitService;

    @Inject
    FarmerService farmerService;

    @Test
    public void checkSetupTest() {
        Assertions.assertThat(fruitService.getData()).hasSize(2);
    }

    @Test
    public void containsFruitTest() {
        Assertions.assertThat(fruitService.getData().stream().anyMatch(fruit -> fruit.getName().equalsIgnoreCase("Orange"))).isTrue();
    }

    @Test
    public void removeFruitTest() {
        fruitService.removeFruit("Orange");
        Assertions.assertThat(fruitService.getData()).hasSize(1);
        Assertions.assertThat(fruitService.getData().stream().anyMatch(fruit -> fruit.getName().equalsIgnoreCase("Orange"))).isFalse();

        Optional<Farmer> farmerMaster = farmerService.getFarmer("Farmer Master");
        Fruit.persist(new Fruit("Orange", "Summer fruit", farmerMaster.get()));
    }

    @Test
    public void addFruitTest() {
        Optional<Farmer> farmerAguila = farmerService.getFarmer("Aguila");

        fruitService.addFruit(new Fruit("Kiwi", "I am a f*****g kiwi",farmerAguila.get()));
        Assertions.assertThat(fruitService.getData()).hasSize(3);
        Assertions.assertThat(fruitService.getData().stream().anyMatch(fruit -> fruit.getName().equalsIgnoreCase("kiwi"))).isTrue();

        Fruit fruit = Fruit.find("name", "Kiwi").firstResult();
        fruit.delete();
    }

    @Test
    public void updateFruitTest() {
        Optional<Fruit> fruitToUpdate = fruitService.getFruit("Orange");
        Optional<Farmer> farmerMaster = farmerService.getFarmer("Farmer Master");
        Fruit newFruit = new Fruit("Orange", "niceBaby", farmerMaster.get());

        fruitService.updateFruit(fruitToUpdate, newFruit);

        Assertions.assertThat(fruitService.getData()).hasSize(2);
        Assertions.assertThat(fruitService.getData().stream().anyMatch(fruit -> fruit.getName().equalsIgnoreCase("Orange"))).isTrue();
        Assertions.assertThat(fruitService.getData().stream().anyMatch(fruit -> fruit.description.equalsIgnoreCase("niceBaby"))).isTrue();

        // Manual Rollback
        fruitService.removeFruit("Orange");
        Fruit.persist(new Fruit("Orange", "Summer fruit", farmerMaster.get()));
    }

    @Test
    public void getFruitTest() {
        Assertions.assertThat(fruitService.getFruit("Orange").get()).hasFieldOrPropertyWithValue("name", "Orange");
        Assertions.assertThat(fruitService.getFruit("IDontExist")).isEmpty();
    }


}

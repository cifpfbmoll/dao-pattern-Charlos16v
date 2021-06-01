package edu.pingpong.active.record;

import io.quarkus.test.junit.QuarkusTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;

@QuarkusTest
@Transactional
public class FruitServiceTest {

    // ./mvnw -Dtest=FruitRepository test
    @Inject
    FruitService activeRecord;

    @Test
    public void checkSetupTest() {
        Assertions.assertThat(activeRecord.getData()).hasSize(2);
    }

    @Test
    public void containsFruitTest() {
        Assertions.assertThat(activeRecord.getData().stream().anyMatch(fruit -> fruit.getName().equalsIgnoreCase("Orange"))).isTrue();
    }

    @Test
    public void removeFruitTest() {
        activeRecord.removeFruit("Orange");
        Assertions.assertThat(activeRecord.getData()).hasSize(1);
        Assertions.assertThat(activeRecord.getData().stream().anyMatch(fruit -> fruit.getName().equalsIgnoreCase("Orange"))).isFalse();

        Fruit.persist(new Fruit("Orange", "Summer fruit"));
    }

    @Test
    public void addFruitTest() {
        activeRecord.addFruit(new Fruit("Kiwi", "I am a f*****g kiwi"));
        Assertions.assertThat(activeRecord.getData()).hasSize(3);
        Assertions.assertThat(activeRecord.getData().stream().anyMatch(fruit -> fruit.getName().equalsIgnoreCase("kiwi"))).isTrue();

        Fruit fruit = Fruit.find("name", "Kiwi").firstResult();
        fruit.delete();
    }

    @Test
    public void getFruitTest() {
        Assertions.assertThat(activeRecord.getFruit("Orange").get()).hasFieldOrPropertyWithValue("name", "Orange");
        Assertions.assertThat(activeRecord.getFruit("IDontExist")).isEmpty();
    }


}

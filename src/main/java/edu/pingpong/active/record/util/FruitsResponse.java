package edu.pingpong.active.record.util;

import edu.pingpong.active.record.entity.Fruit;

import java.util.List;

public class FruitsResponse {

    public List<Fruit> fruits;

    public FruitsResponse(List<Fruit> fruits) {
        this.fruits = fruits;
    }
}

package edu.pingpong.dao.pattern.util;

import edu.pingpong.dao.pattern.entity.Fruit;

import java.util.List;

public class FruitsResponse {

    public List<Fruit> fruits;

    public FruitsResponse(List<Fruit> fruits) {
        this.fruits = fruits;
    }
}

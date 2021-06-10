package edu.pingpong.dao.pattern.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "farmer")
public class Farmer extends PanacheEntityBase {
    /*
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    public Integer id;*/

    @Id
    @Column(name = "name", unique = true)
    public String name;

    @Column(name = "location")
    public String location;

    @JsonIgnore
    @OneToMany(mappedBy = "farmer")
    public List<Fruit> fruits = new ArrayList<>();

    public Farmer() {};

    public Farmer(String name, String location) {
        this.name = name;
        this.location = location;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\n" +
                "Location: " + location + "\n";
    }

}

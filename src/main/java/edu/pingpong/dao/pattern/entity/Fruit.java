package edu.pingpong.dao.pattern.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "fruit")
@JsonPropertyOrder({"id", "name", "description"})
public class Fruit extends PanacheEntityBase {
    /*
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Integer id;*/

    @Id
    @Column(name = "name", unique = true)
    @NotBlank
    public String name;

    @Column(name = "description")
    @NotEmpty
    public String description;

    @ManyToOne
    @JoinColumn(name = "farmer_name")
    public Farmer farmer;

    // Required constructor by the JSON serialization layer
    public Fruit() {
    }

    public Fruit(String name, String description, Farmer farmer) {
        this.name = name;
        this.description = description;
        this.farmer = farmer;
    }

    public void setNameAndDescription(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "Fruit{" +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

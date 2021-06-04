package edu.pingpong.active.record;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.transaction.Transactional;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;

@QuarkusTest
@Transactional
public class FruitResourceTest {

    @Test
    public void fruitsDataEndpoint() {
        Map<String,List<Map<String, Object>>> fruits =
                given()
                        .contentType(ContentType.JSON)
                        .when().get("/fruits")
                        .as(new TypeRef<Map<String, List<Map<String, Object>>>>() {
                        });

        List<Map<String, Object>> fruitsList = fruits.get("fruits");

        Assertions.assertThat(fruitsList).hasSize(2);

        fruits.get("fruits").sort(Comparator.comparing(map -> (String) map.get("name")));

        Assertions.assertThat(fruitsList.get(0)).containsValue("Orange");
        Assertions.assertThat(fruitsList.get(0)).containsEntry("description", "Summer fruit");

        Assertions.assertThat(fruitsList.get(1)).containsValue("Strawberry");
        Assertions.assertThat(fruitsList.get(1)).containsEntry("description", "Winter fruit");
    }

    @Test
    public void fruitsDataTransactionTest() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/fruits")
                .then()
                .statusCode(200)
                .body("fruits", hasSize(2),
                        "fruits.name", containsInAnyOrder("Strawberry", "Orange"),
                        "fruits.description", containsInAnyOrder("Winter fruit", "Summer fruit"));
    }

    @Test
    public void addDeleteFruitTest() {
        given()
                .body("{\"name\": \"Kiwi\", \"description\": \"nice\"}")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .when()
                .post("/fruits")
                .then()
                .statusCode(200)
                .body("message", equalTo("Added Kiwi fruit succesfully."));

        given()
                .pathParam("fruitname", "Kiwi")
                .when()
                .delete("/fruits/{fruitname}")
                .then()
                .statusCode(200)
                .body("message", equalTo("Deleted Kiwi fruit succesfully."));
    }

    @Test
    public void udpateFruitTest() {

    }


    @Test
    public void getFruitTest() {
        given()
                .pathParam("name", "Orange")
                .when()
                .get("/fruits/{name}")
                .then()
                .contentType(ContentType.JSON)
                .body("name", equalTo("Orange"));

        given()
                .pathParam("name", "Papaya")
                .when()
                .get("/fruits/{name}")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(404)
                .body("message", equalTo("The fruit with name Papaya doesn't exist."));
    }
}
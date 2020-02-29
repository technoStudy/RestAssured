package review;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ReviewTasksSolution {
    @BeforeClass
    public void init(){
        RestAssured.baseURI = "http://api.zippopotam.us";
    }

    @Test
    public  void task1() {
        given()
                .when()
                .get("us/90210")
                .then()
        .body( "places[0].'place name'", equalTo( "Beverly Hills" ) )
                ;
    }
}

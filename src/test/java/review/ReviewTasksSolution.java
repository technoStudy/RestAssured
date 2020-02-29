package review;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
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

    @Test
    public void task2(){
        given()
                .pathParam( "country", "us" )
                .pathParam( "zipcode", "90210" )
                .when()
                .get("/{country}/{zipcode}")
                .then()
                .body( "places", not( empty()) )
                ;
    }

    @Test(dataProvider = "task3data")
    public void task3(String placeName, String country, String zipCode){
        given()
                .pathParam( "country", country )
                .pathParam( "zipcode", zipCode )
                .when()
                .get("/{country}/{zipcode}")
                .then()
                .body( "places.'place name'", hasItem( placeName ) )
        ;
    }

    @DataProvider
    public Object[][] task3data() {
        return new Object[][] {
                {"Beverly Hills", "us", "90210"},
                {"Fort-de-France", "MQ", "97200"},
                {"Çiçekli Köyü", "TR", "01000"},
        };
    }
}

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class IntroductionTestsSolution {
    @BeforeClass
    public void init(){
        RestAssured.baseURI = "http://api.zippopotam.us";
    }
    
    @Test
    public void simpleGetTest() {
        given().
                when().
                get( "/us/90210" ).
                then().
                statusCode( 200 );
    }

    @Test
    public void simpleResponseTypeTest() {
        given().
                when().
                get( "/us/90210" ).
                then().
                contentType( ContentType.JSON );
    }

    @Test
    public void logRequestAndResponseDetails() {
        given().
                log().all(). // add this
                when().
                get("http://zippopotam.us/us/90210").
                then().
                log().body();  // and this
    }

    @Test
    public void checkReponseBody() {
        given().
                when().
                get("http://zippopotam.us/us/90210").
                then().
                body( "places[0].state", equalTo( "California" ) );
    }

    @Test
    public void checkReponseBodyPostCode() {
        given().
                when().
                get("http://zippopotam.us/us/90210").
                then().
                body( "'post code'", equalTo( "90210" ) );
    }

    @Test
    public void checkListHasItem(){
        given().
                when().
                get("/tr/34295").
                then().
                body( "places.'place name'",  hasItem( "Kartaltepe Mah." ));
    }

    @Test
    public void  checkListSize(){
        given().
                when().
                get("/tr/34840").
                then().
                body( "places", hasSize( 2 ) );
    }

    @Test
    public void combinedTest(){
        given().
                when().
                get( "/us/90210" ).
                then().
                statusCode( 200 ).
                contentType( ContentType.JSON ).
                body( "places[0].state", equalTo( "California" ) );
    }

    @Test
    public void pathParameterTest(){
        given().
                log().uri().
                pathParam( "country", "us" ).
                pathParam( "zipcode", "90210" ).
                when().
                get("/{country}/{zipcode}").
                then().
                log().status().
                statusCode( 200 );
    }

    //https://gorest.co.in/public-api/users?_format=json&access-token=j6XoJSutZrv-ikB-4X4_Zndi54_iqSZES-Ap
    @Test
    public void queryParamsTest(){
        given().
                log().uri().
                param( "access-token", "j6XoJSutZrv-ikB-4X4_Zndi54_iqSZES-Ap" ).
                param( "_format", "json" ).
                when().
                get("https://gorest.co.in/public-api/users").
                then().
                log().status().
                log().body().
                statusCode( 200 );
    }

}

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class IntroductionTestsSolution {
    @Test
    public void simpleGetTest() {
        given().
                when().
                get( "http://api.zippopotam.us/us/90210" ).
                then().
                statusCode( 200 );
    }

    @Test
    public void simpleResponseTypeTest() {
        given().
                when().
                get( "http://api.zippopotam.us/us/90210" ).
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

}

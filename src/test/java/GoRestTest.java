import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pojo.GoRestUser;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class GoRestTest {

    private RequestSpecification requestSpec;

    //https://gorest.co.in/public-api/users?_format=json&access-token=j6XoJSutZrv-ikB-4X4_Zndi54_iqSZES-Ap
    @Test
    public void queryParamsTest(){
        given().
                log().uri()
                .param( "access-token", "j6XoJSutZrv-ikB-4X4_Zndi54_iqSZES-Ap" ).
                param( "_format", "json" ).
                when().
                get("https://gorest.co.in/public-api/users").
                then().
                log().status().
                log().body().
                body( "_meta.code", equalTo( 200 ) );
    }

    @Test
    public void basicAuthTest(){
        given()
                .auth()         // basic auth
                .preemptive()// basic auth
                .basic("j6XoJSutZrv-ikB-4X4_Zndi54_iqSZES-Ap", "") // basic auth
                 .log().headers()
                .when()
                .get("https://gorest.co.in/public-api/users").
                then().
                log().status().
                log().body().
                body( "_meta.code", equalTo( 200 ) );
    }


    @Test
    public void oAuth2Test(){
        given()
                .auth()
                .oauth2("j6XoJSutZrv-ikB-4X4_Zndi54_iqSZES-Ap") // basic OAuth 2
                .log().headers()
                .when()
                .get("https://gorest.co.in/public-api/users").
                then().
                log().status().
                log().body().
                body( "_meta.code", equalTo( 200 ) );
    }

    @Test
    public void noAuthTest(){
        given()
                .when()
                .get("https://gorest.co.in/public-api/users").
                then().
                log().status().
                log().body().
                body( "_meta.code", equalTo( 401 ) );
    }

    @BeforeClass
    private void createRequestSpec() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://gorest.co.in/public-api/users?_format=json&access-token=j6XoJSutZrv-ikB-4X4_Zndi54_iqSZES-Ap")
                .setContentType( ContentType.JSON )
//                .setAuth(oauth2( "j6XoJSutZrv-ikB-4X4_Zndi54_iqSZES-Ap" )) //TODO: fix this
                .log( LogDetail.ALL )
                .build();
    }

    @Test
    public void createUserTest() {
        GoRestUser user = new GoRestUser();
        user.setEmail( "asdfvx2@asd.as" );
        user.setFirstName( "My First Name" );
        user.setLastName( "My Last Name" );
        user.setGender( "male" );

        // Create user part
        String userId = given()
                .spec( requestSpec )
                .body( user )
                .when()
                .post()
                .then()
                .body( "_meta.code", equalTo( 201 ) )
                .log().everything()
                .extract().jsonPath().getString( "result.id" );

        // Create user negative case part
        given()
                .spec( requestSpec )
                .body( user )
                .when()
                .post(  )
                .then()
                .body( "_meta.code", equalTo( 422 ) );

        // Get user part
        given()
                .spec( requestSpec )
                .when()
                .get(userId)
                .then()
                .body( "_meta.code", equalTo( 200 ) )
                .body( "result.email", equalTo( user.getEmail() ) )
        ;

        // Update user part
        Map<String, String> updateUser = new HashMap<>(  );
        updateUser.put( "first_name", "Updated first name" );
        updateUser.put( "last_name", "Updated last name" );

        given()
                .spec( requestSpec )
                .body( updateUser )
                .when()
                .patch(  userId )
                .then()
                .body( "_meta.code", equalTo( 200 ) );

        // Get user update check part
        given()
                .spec( requestSpec )
                .when()
                .get(userId)
                .then()
                .body( "_meta.code", equalTo( 200 ) )
                .body( "result.first_name", equalTo( updateUser.get( "first_name" ) ) )
                .body( "result.last_name", equalTo( updateUser.get( "last_name" ) ) )
        ;

        // Delete user part
        given()
                .spec( requestSpec )
                .when()
                .delete(userId)
                .then()
                .body( "_meta.code", equalTo( 204 ) )
        ;

        // Get user negative part
        given()
                .spec( requestSpec )
                .when()
                .get(userId)
                .then()
                .body( "_meta.code", equalTo( 404 ) )
        ;

        // Update user negative part
        Map<String, String> updateUserNegative = new HashMap<>(  );
        updateUserNegative.put( "first_name", "Negative first name" );
        updateUserNegative.put( "last_name", "Negative last name" );

        given()
                .spec( requestSpec )
                .body( updateUserNegative )
                .when()
                .patch(  userId )
                .then()
                .body( "_meta.code", equalTo( 404 ) );
    }
}

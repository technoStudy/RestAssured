import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class TasksSolution {
    @Test
    public void task1() {
        given().
                when().
                get( "https://httpstat.us/203" ).
                then().
                statusCode( 203 ).
                contentType( ContentType.TEXT ).
                body( equalTo( "203 Non-Authoritative Information" ) );
    }

    @Test
    public void task2() {
        given().
                when().
                get( "https://httpstat.us/418" ).
                then().
                statusCode( 418 ).
                contentType( ContentType.TEXT ).
                body( equalTo( "418 I'm a teapot" ) );
    }

    @Test
    public void task3() {
        given().
                when().
                get( "https://jsonplaceholder.typicode.com/todos/2" ).
                then().
                statusCode( 200 ).
                contentType( ContentType.JSON ).
                body( "title", equalTo( "quis ut nam facilis et officia qui" ) );
    }

    @Test
    public void task3alternative() {
        String title = given().
                when().
                get( "https://jsonplaceholder.typicode.com/todos/2" ).
                then().
                statusCode( 200 ).
                contentType( ContentType.JSON ).
                extract().path( "title" );
        Assert.assertEquals( title, "quis ut nam facilis et officia qui" );
    }

    @Test
    public void task4() {
        given().
                when().
                get( "https://jsonplaceholder.typicode.com/todos/2" ).
                then().
                statusCode( 200 ).
                contentType( ContentType.JSON ).
                body( "completed", equalTo( false ) );
    }

    @Test
    public void task4alternative() {
        Boolean status = given().
                when().
                get( "https://jsonplaceholder.typicode.com/todos/2" ).
                then().
                statusCode( 200 ).
                contentType( ContentType.JSON ).
                extract().path( "completed" );
        Assert.assertFalse( status );
    }

    @Test
    public void task5() {
        given().
                when().
                get( "https://reqres.in/api/users?page=2" ).
                then().
                statusCode( 200 ).
                contentType( ContentType.JSON ).
                body( "data.first_name", hasItem( "George" ) );
    }
}

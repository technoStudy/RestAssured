import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class TasksSolution {
    @Test
    public void task1() {
        given().
                when().
                get("https://httpstat.us/203").
                then().
                statusCode( 203 ).
                contentType( ContentType.TEXT ).
                body( equalTo( "203 Non-Authoritative Information" ) );
    }
}

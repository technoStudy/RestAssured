package review;

public class ReviewTasks {
    /** Task 1
     * create a get request to zippo api us/90210
     * validate that first place's place name is Beverly Hills
     **/

    /** Task 2
     * create a get request to zippo api {country}/{zipcode}
     * provide any country and zipcode
     * validate that places array is not empty
     **/

    /** Task 3
     * create data provide with 3 fields, place_name, country, zipcode
     * add at least 3 rows of data in your data provider
     * create a get request to zippo api {country}/{zipcode} that uses your data provider
     * validate that places array contains your place_name
     **/

    /** Task 4
     * create data provide with 3 fields, number of places, country, zipcode
     * add at least 3 rows of data in your data provider
     * create a get request to zippo api {country}/{zipcode} that uses your data provider
     * validate that places array has exactly the number of places
     **/

    /** Task 5
     * use a pojo for users
     * create a user request to https://gorest.co.in/public-api/users
     * send your pojo inside body of your post request
     * expect status 201
     * expect content type json
     * check that use is created by checking that results of
     * https://gorest.co.in/public-api/users?name={your_user_name}
     * contain your user id
     ***/
}

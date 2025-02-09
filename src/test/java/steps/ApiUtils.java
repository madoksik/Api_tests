package steps;

import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class ApiUtils {

    //возвращает response Get
    public static Response Get(String baseUrl, String params) {
        var response = given().log().all()
                .when()
                .contentType(JSON)
                .get(baseUrl + params);
        return response;
    }

    //возвращает тело ответа как лист
    public static <T> List<T> parseResponse(Response resp, Class<T> clazz) {
        return resp.body().jsonPath().getList(".", clazz);
    }

    //возвращает тело ответа PostsData
    public static PostsData postDataResponse(Response resp) {
        return resp.body().as(PostsData.class);
    }

    //возвращает тело ответа UsersData
    public static UsersData usersDataResponse(Response resp) {
        return resp.body().as(UsersData.class);
    }
    //возвращает response Post
    public static Response Post(String baseUrl, String data) {
        var response2 = given().log().all()
                .body(data)
                .contentType(JSON)
                .when()
                .post(baseUrl + "/posts");
        return response2;
    }
    //возвращает тело ответа SuccessPosts
    public static SuccessPosts successPostsResponse(Response resp) {
        return resp.body().as(SuccessPosts.class);
    }
}


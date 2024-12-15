package tests;

import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;
import steps.PostsData;
import steps.Specifications;
import steps.SuccessPosts;
import steps.UsersData;


import java.util.List;

import static io.restassured.RestAssured.given;


public class test {
    private final static String URL = "https://jsonplaceholder.typicode.com";

    @Test
    public void step1() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        List<PostsData> posts = given()
                .when()
                .get(URL+"/posts")
                .then().log().all()
                .extract().body().jsonPath().getList("data", PostsData.class);
    }
    @Test
    public void step2() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        List<PostsData> posts = given()
                .when()
                .get(URL+"/posts/99")
                .then().log().all()
                .extract().body().jsonPath().getList("data", PostsData.class);
    }
    @Test
    public void step3() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecError404());
        List<PostsData> posts = given()
                .when()
                .get(URL+"/posts/150")
                .then().log().all()
                .extract().body().jsonPath().getList("data", PostsData.class);
    }
    @Test
    public void step4() {
            Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK201());
            Integer userId = 1;
            Integer id = 101;
            String title = "Очень важная";
            String body = "Бугагашенька";
            PostsData post = new PostsData(1, 101, "Очень важная", "Бугагашенька");
            SuccessPosts successPosts = given()
                    .body(post)
                    .when()
                    .post("/posts")
                    .then().log().all()
                    .extract().as(SuccessPosts.class);
        Assert.assertEquals(userId, successPosts.getUserId());
        Assert.assertEquals(id, successPosts.getId());
        Assert.assertEquals(title, successPosts.getTitle());
        Assert.assertEquals(body, successPosts.getBody());


    }
    @Test
    public void step5() {
          Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        List<UsersData> users = given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL+"/users")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UsersData.class);
    }
    @Test
    public void step6() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        List<UsersData> users = given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL+"/users/5")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UsersData.class);
    }
}

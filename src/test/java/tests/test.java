package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import steps.*;


import java.util.Random;

import static io.restassured.RestAssured.given;

public class test {
    private final static String URL = "https://jsonplaceholder.typicode.com";

    String randomTitle = "randomTitle " + new Random().nextInt(1000);
    String randomBody = "randomBody " + new Random().nextInt(1000);
    Integer userId_1 =1;

    // Создание JSON-объекта для тела запроса
    String requestBody = String.format("{\"title\":\"%s\", \"body\":\"%s\", \"userId\":\"%d\"}", randomTitle, randomBody, userId_1);

    @Test
    public void tests() {

        //1. Отправьте запрос GET, чтобы получить все сообщения (/posts)
        var postsResponse = ApiUtils.Get(URL, "/posts");
        assert postsResponse.getStatusCode() == 200;
        var posts = ApiUtils.parseResponse(postsResponse, PostsData.class);

        //2. Отправьте запрос GET, чтобы получить пост с id=99 (/posts/99)
        var post99Response = ApiUtils.Get(URL, "/posts/99");
        assert post99Response.getStatusCode() == 200;
        var post99 = ApiUtils.postDataResponse(post99Response);

        //3. Отправьте запрос GET, чтобы получить пост с id=150 (/posts/150).
        var post150Response = ApiUtils.Get(URL, "/posts/150");
        assert post150Response.getStatusCode() == 404;

        //4. Отправьте POST-запрос, чтобы создать сообщение с userId=1 и случайным телом и случайным заголовком (/posts).
        var postResponse = ApiUtils.Post(URL, requestBody);
        assert postResponse.getStatusCode() == 201;
        var post = ((ApiUtils.successPostsResponse(postResponse)));
        Assert.assertEquals(userId_1, SuccessPosts.getuserId());
        Assert.assertEquals(randomTitle, SuccessPosts.getTitle());
        Assert.assertEquals(randomBody, SuccessPosts.getBody());
        Assert.assertEquals(101, SuccessPosts.getId());

        //5. Отправьте запрос GET, чтобы получить пользователей (/users).
        var usersResponse = ApiUtils.Get(URL, "/users");
        assert usersResponse.getStatusCode() == 200;
        var users = ApiUtils.parseResponse(usersResponse, UsersData.class);
        Assert.assertEquals(users.get(4).getName(), "Chelsey Dietrich");
        Assert.assertEquals(users.get(4).getUsername(), "Kamren");
        Assert.assertEquals(users.get(4).getAddress().getStreet(), "Skiles Walks");
        Assert.assertEquals(users.get(4).getAddress().getSuite(), "Suite 351");
        Assert.assertEquals(users.get(4).getAddress().getCity(), "Roscoeview");
        Assert.assertEquals(users.get(4).getAddress().getZipcode(), "33263");
        Assert.assertEquals(users.get(4).getAddress().getGeo().getLat(), "-31.8129");
        Assert.assertEquals(users.get(4).getAddress().getGeo().getLng(), "62.5342");
        Assert.assertEquals(users.get(4).getPhone(), "(254)954-1289");
        Assert.assertEquals(users.get(4).getWebsite(), "demarco.info");
        Assert.assertEquals(users.get(4).getCompany().getName(), "Keebler LLC");
        Assert.assertEquals(users.get(4).getCompany().getBs(), "revolutionize end-to-end systems");
        Assert.assertEquals(users.get(4).getCompany().getCatchPhrase(), "User-centric fault-tolerant solution");


        //6. Отправьте запрос GET, чтобы получить пользователя с id=5 (/users/5).
        var user5Response = ApiUtils.Get(URL, "/users/5");
        assert user5Response.getStatusCode() == 200;
        var user5 = ApiUtils.usersDataResponse(user5Response);
        Assert.assertEquals(users.get(4).getName(), user5.getName());
        Assert.assertEquals(users.get(4).getUsername(), user5.getUsername());
        Assert.assertEquals(users.get(4).getAddress().getStreet(), user5.getAddress().getStreet());
        Assert.assertEquals(users.get(4).getAddress().getSuite(),user5.getAddress().getSuite());
        Assert.assertEquals(users.get(4).getAddress().getCity(), user5.getAddress().getCity());
        Assert.assertEquals(users.get(4).getAddress().getZipcode(), user5.getAddress().getZipcode());
        Assert.assertEquals(users.get(4).getAddress().getGeo().getLat(), user5.getAddress().getGeo().getLat());
        Assert.assertEquals(users.get(4).getAddress().getGeo().getLng(), user5.getAddress().getGeo().getLng());
        Assert.assertEquals(users.get(4).getPhone(), user5.getPhone());
        Assert.assertEquals(users.get(4).getWebsite(), user5.getWebsite());
        Assert.assertEquals(users.get(4).getCompany().getName(), user5.getCompany().getName());
        Assert.assertEquals(users.get(4).getCompany().getBs(), user5.getCompany().getBs());
        Assert.assertEquals(users.get(4).getCompany().getCatchPhrase(), user5.getCompany().getCatchPhrase());
    }
}

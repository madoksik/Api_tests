package tests;


import org.testng.Assert;
import org.testng.annotations.Test;
import steps.*;
import java.util.List;
import java.util.Random;



public class test {
    private final static String URL = "https://jsonplaceholder.typicode.com";
    private final static String emptyBody = "{}";
    private final static String posts = "/posts";
    private final static String posts150 = "/posts/150";
    private final static String posts99 = "/posts/99";
    private final static String users = "/users";
    private final static String users5 = "/users/5";


    String randomTitle = "randomTitle " + new Random().nextInt(1000);
    String randomBody = "randomBody " + new Random().nextInt(1000);
    Integer userId_1 =1;

    // Создание JSON-объекта для тела запроса
    String requestBody = String.format("{\"title\":\"%s\", \"body\":\"%s\", \"userId\":\"%d\"}", randomTitle, randomBody, userId_1);

    @Test
    public void tests() {

        //1. Отправьте запрос GET, чтобы получить все сообщения (/posts)
        var postsResponse = ApiUtils.Get(URL, posts);
        assert postsResponse.getStatusCode()==HttpStatusCode.OK.getCode();
        //проверка что формат JSON
        String contentType = postsResponse.getContentType();
        Assert.assertTrue(contentType.contains("application/json"), "Формат НЕ JSON");
        //проверка что сообщения отсортированы по возрастанию
        List<Integer> postId = postsResponse.jsonPath().getList("id");
        for (int i = 0; i < postId.size() - 1; i++) {
            assert postId.get(i) < postId.get(i + 1) : "Сообщения не отсортированы по возрастанию (по id)";
        }

        //2. Отправьте запрос GET, чтобы получить пост с id=99 (/posts/99)
        var post99Response = ApiUtils.Get(URL, posts99);
        assert post99Response.getStatusCode()==HttpStatusCode.OK.getCode();
        var post99 = ApiUtils.postDataResponse(post99Response);
        Assert.assertEquals(PostsData.getUserId(), 10 );
        Assert.assertEquals(PostsData.getId(), 99 );
        Assert.assertNotNull(PostsData.getTitle(), "Поле Title - пустое");
        Assert.assertNotNull(PostsData.getBody(), "Поле Body - пустое");

        //3. Отправьте запрос GET, чтобы получить пост с id=150 (/posts/150).
        var post150Response = ApiUtils.Get(URL, posts150);
        assert post150Response.getStatusCode()==HttpStatusCode.Not_found.getCode();
        String responseBody = post150Response.asString();
        Assert.assertEquals(responseBody, emptyBody);

        //4. Отправьте POST-запрос, чтобы создать сообщение с userId=1 и случайным телом и случайным заголовком (/posts).
        var postResponse = ApiUtils.Post(URL, requestBody);
        assert postResponse.getStatusCode()==HttpStatusCode.OK_post.getCode();
        var post = ((ApiUtils.successPostsResponse(postResponse)));
        Assert.assertEquals(userId_1, SuccessPosts.getuserId());
        Assert.assertEquals(randomTitle, SuccessPosts.getTitle());
        Assert.assertEquals(randomBody, SuccessPosts.getBody());
        Assert.assertNotNull(SuccessPosts.getId(), "Поле ID - пустое");

        //5. Отправьте запрос GET, чтобы получить пользователей (/users).
        var usersResponse = ApiUtils.Get(URL, users);
        assert usersResponse.getStatusCode()==HttpStatusCode.OK.getCode();
        var users = ApiUtils.parseResponse(usersResponse, UsersData.class);
        //находим пользователя с id = 5
        int targetId = 5;
        UsersData usersId5 = null;
        for (UsersData obj : users) {
            if (obj.getId() == targetId) {
                usersId5 = obj;
                break;
            }
        }
        Assert.assertEquals(usersId5.getName(), "Chelsey Dietrich");
        Assert.assertEquals(usersId5.getUsername(), "Kamren");
        Assert.assertEquals(usersId5.getAddress().getStreet(), "Skiles Walks");
        Assert.assertEquals(usersId5.getAddress().getSuite(), "Suite 351");
        Assert.assertEquals(usersId5.getAddress().getCity(), "Roscoeview");
        Assert.assertEquals(usersId5.getAddress().getZipcode(), "33263");
        Assert.assertEquals(usersId5.getAddress().getGeo().getLat(), "-31.8129");
        Assert.assertEquals(usersId5.getAddress().getGeo().getLng(), "62.5342");
        Assert.assertEquals(usersId5.getPhone(), "(254)954-1289");
        Assert.assertEquals(usersId5.getWebsite(), "demarco.info");
        Assert.assertEquals(usersId5.getCompany().getName(), "Keebler LLC");
        Assert.assertEquals(usersId5.getCompany().getBs(), "revolutionize end-to-end systems");
        Assert.assertEquals(usersId5.getCompany().getCatchPhrase(), "User-centric fault-tolerant solution");


        //6. Отправьте запрос GET, чтобы получить пользователя с id=5 (/users/5).
        var user5Response = ApiUtils.Get(URL, users5);
        assert user5Response.getStatusCode()==HttpStatusCode.OK.getCode();
        var user5 = ApiUtils.usersDataResponse(user5Response);
        //сравниваем данные с данными полученными на предыдущем шаге
        Assert.assertEquals(usersId5.getName(), user5.getName());
        Assert.assertEquals(usersId5.getUsername(), user5.getUsername());
        Assert.assertEquals(usersId5.getAddress().getStreet(), user5.getAddress().getStreet());
        Assert.assertEquals(usersId5.getAddress().getSuite(),user5.getAddress().getSuite());
        Assert.assertEquals(usersId5.getAddress().getCity(), user5.getAddress().getCity());
        Assert.assertEquals(usersId5.getAddress().getZipcode(), user5.getAddress().getZipcode());
        Assert.assertEquals(usersId5.getAddress().getGeo().getLat(), user5.getAddress().getGeo().getLat());
        Assert.assertEquals(usersId5.getAddress().getGeo().getLng(), user5.getAddress().getGeo().getLng());
        Assert.assertEquals(usersId5.getPhone(), user5.getPhone());
        Assert.assertEquals(usersId5.getWebsite(), user5.getWebsite());
        Assert.assertEquals(usersId5.getCompany().getName(), user5.getCompany().getName());
        Assert.assertEquals(usersId5.getCompany().getBs(), user5.getCompany().getBs());
        Assert.assertEquals(usersId5.getCompany().getCatchPhrase(), user5.getCompany().getCatchPhrase());
    }
}

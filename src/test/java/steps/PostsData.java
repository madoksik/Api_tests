package steps;

public class PostsData {
    private static Integer userId;
    private static String title;
    private static String body;
    private static Integer id;

    public static Integer getUserId() {
        return userId;
    }

    public static String getTitle() {
        return title;
    }

    public static String getBody() {
        return body;
    }

    public static Integer getId() {
        return id;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

package bo.ara.com.demoapp.model;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

/**
 * Created by LENOVO on 13/06/2016.
 */
public class Post extends SugarRecord{
    private String title;
    private String content;
    private User user;
    @Ignore
    private long user_id;
    private String posted_date;

    public Post() {
        title = "";
        content = "";
        user = new User();
        user_id = 0;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getPosted_date() {
        return posted_date;
    }

    public void setPosted_date(String posted_date) {
        this.posted_date = posted_date;
    }
}

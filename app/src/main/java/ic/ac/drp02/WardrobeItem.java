package ic.ac.drp02;


import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.riversun.okhttp3.OkHttp3CookieHelper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WardrobeItem {

    private Integer item_id;
    private Integer uid;
    private String imageUrl;
    private List<String> pics;
    private List<String> tags;
    private String description;

    private String name;
    private String type_;
    private Integer likes;




    public WardrobeItem(Integer item_id, Integer uid, List<String> pics, String description, List<String> tags, String name, String type_, Integer likes) {
        this.item_id = item_id;
        this.uid = uid;
        this.pics = pics;
        this.imageUrl = pics.get(0);
        this.description = description;
        this.tags = tags;
        this.name = name;
        this.type_ = type_;
        this.likes = likes;
    }
    public Integer getUid() {
        return uid;
    }
    public Integer getId() {
        return item_id;
    }

    public String getDescription() {
        return description;
    }

    public String getTagString() {
        return tags.toString();
    }

    public String getImageUrl() {
        return pics.get(0);
    }

    public String getItemName() {
        return name;
    }

    public String getItemType() {
        return type_;
    }

    public Integer getLikes() {
        return likes;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getUsername() {
        try {
            return retrieveUser().get().getName();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private CompletableFuture<User> retrieveUser() {
        OkHttp3CookieHelper cookieHelper = new OkHttp3CookieHelper();
        OkHttpClient client = new OkHttpClient().newBuilder().cookieJar(cookieHelper.cookieJar()).build();
        String url = "https://drp02-backend.herokuapp.com/user/get_user_by_id/" + uid;
        Request request = new Request.Builder()
                .url(url)
                .build();
        cookieHelper.setCookie(url,"uid",StaticUser.getUidStr());
        CompletableFuture<User> result = new CompletableFuture<>();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.completeExceptionally(e);
                e.printStackTrace();
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {


                Type listType = new TypeToken<User>() {
                }.getType();
                User users = new Gson().fromJson(response.body().string(), listType);

                result.complete(users);


            }});
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Integer getNumFriends() {
        try {
            return retrieveUser().get().getUsers_following().size();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WardrobeItem that = (WardrobeItem) o;
        return Objects.equals(item_id, that.item_id) && Objects.equals(uid, that.uid) && Objects.equals(imageUrl, that.imageUrl) && Objects.equals(pics, that.pics) && Objects.equals(tags, that.tags) && Objects.equals(description, that.description) && Objects.equals(name, that.name) && Objects.equals(type_, that.type_) && Objects.equals(likes, that.likes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item_id, uid, imageUrl, pics, tags, description, name, type_, likes);
    }

}

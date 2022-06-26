package ic.ac.drp02;


import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.riversun.okhttp3.OkHttp3CookieHelper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WardrobeItem {

    private Integer id;
    private Integer uid;
    private String imageUrl;
    private List<String> pics;
    private List<String> tags;
    private String description;

    private String name;
    private String type_;
    private Integer likes;




    public WardrobeItem(Integer uid, List<String> pics, String description, List<String> tags, String name, String type_, Integer likes) {
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
        return id;
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
            return retrieveUsername().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private CompletableFuture<String> retrieveUsername() {
        OkHttp3CookieHelper cookieHelper = new OkHttp3CookieHelper();
        OkHttpClient client = new OkHttpClient().newBuilder().cookieJar(cookieHelper.cookieJar()).build();
        String url = "https://drp02-backend.herokuapp.com/user/get_user_by_id/" + uid;
        Request request = new Request.Builder()
                .url(url)
                .build();
        cookieHelper.setCookie(url,"uid",StaticUser.getUid());
        CompletableFuture<String> result = new CompletableFuture<>();

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

                result.complete(users.getName());


            }});
        return result;
    }
}

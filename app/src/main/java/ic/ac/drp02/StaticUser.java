package ic.ac.drp02;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.riversun.okhttp3.OkHttp3CookieHelper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StaticUser {

    private static StaticUser staticUser;
    private static String uidStr;
    private static User user;


    private StaticUser(){
    }

    public static StaticUser getInstance(){
        if(staticUser == null) {
            staticUser = new StaticUser();
        }
        return staticUser;
    }

    public static String getUidStr() {
        return uidStr;
    }

    public static void setUidStr(String uidStr) {
        StaticUser.uidStr = uidStr;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static CompletableFuture<User> retrieveUser() {
        OkHttp3CookieHelper cookieHelper = new OkHttp3CookieHelper();
        OkHttpClient client = new OkHttpClient().newBuilder().cookieJar(cookieHelper.cookieJar()).build();
        String url = "https://drp02-backend.herokuapp.com/user/get_user";
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
                User user = new Gson().fromJson(response.body().string(), listType);

                result.complete(user);


            }});
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String getUsername() {
        if (user != null) {
            return user.getName();
        }
        try {
            return retrieveUser().get().getName();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Integer getUserid() {
        if (user != null) {
            return user.getUid();
        }
        try {
            return retrieveUser().get().getUid();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<User> getFriends() {
        try {
            return retrieveFriends().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static CompletableFuture<List<User>> retrieveFriends() {
        OkHttp3CookieHelper cookieHelper = new OkHttp3CookieHelper();
        OkHttpClient client = new OkHttpClient().newBuilder().cookieJar(cookieHelper.cookieJar()).build();
        String url = "https://drp02-backend.herokuapp.com/user/following";
        Request request = new Request.Builder()
                .url(url)
                .build();
        cookieHelper.setCookie(url,"uid",StaticUser.getUidStr());
        CompletableFuture<List<User>> result = new CompletableFuture<>();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.completeExceptionally(e);
                e.printStackTrace();
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Type listType = new TypeToken<ArrayList<User>>() {
                }.getType();
                List<User> users = new Gson().fromJson(response.body().string(), listType);
                result.complete(users);
            }});
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<User> getFriendsToAdd() {
        try {
            return retrieveFriendsToAdd().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static CompletableFuture<List<User>> retrieveFriendsToAdd() {
        OkHttp3CookieHelper cookieHelper = new OkHttp3CookieHelper();
        OkHttpClient client = new OkHttpClient().newBuilder().cookieJar(cookieHelper.cookieJar()).build();
        String url = "https://drp02-backend.herokuapp.com/user/findfriends";
        Request request = new Request.Builder()
                .url(url)
                .build();
        cookieHelper.setCookie(url,"uid",StaticUser.getUidStr());
        CompletableFuture<List<User>> result = new CompletableFuture<>();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.completeExceptionally(e);
                e.printStackTrace();
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Type listType = new TypeToken<ArrayList<User>>() {
                }.getType();
                List<User> users = new Gson().fromJson(response.body().string(), listType);
                result.complete(users);
            }});
        return result;
    }
}

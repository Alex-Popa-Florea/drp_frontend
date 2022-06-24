package ic.ac.drp02;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.riversun.okhttp3.OkHttp3CookieHelper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import ic.ac.drp02.databinding.MyFriendsBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyFriends extends Fragment {

    private MyFriendsBinding binding;
    private RecyclerView recyclerView;
    private Handler mHandler;
    private OkHttp3CookieHelper cookieHelper = new OkHttp3CookieHelper();
    private final OkHttpClient client = new OkHttpClient().newBuilder().cookieJar(cookieHelper.cookieJar()).build();
    private Fragment fragment = this;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        //getActivity().setTitle("Hi");
        binding = MyFriendsBinding.inflate(inflater, container, false);

        recyclerView = (RecyclerView) binding.getRoot().findViewById(R.id.my_friend_recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        Friend friend1 = new Friend("Kleenex", "1");
        Friend friend2 = new Friend("Grandpaarukan", "1");
        Friend friend3 = new Friend("Lemonade", "5");
        List<User> friends = null;
        try {
            friends = getFriends().get();
            MyFriendsAdapter adapter = new MyFriendsAdapter(getActivity().getApplicationContext(), new ArrayList<>(friends), fragment);
            recyclerView.setAdapter(adapter);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }



        //getWardrobe(); get friends to add


        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        Log.e("adhithi", "lkfjklfj");
        super.onViewCreated(view, savedInstanceState);




    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private CompletableFuture<List<User>> getFriends() {
        String url = "https://drp02-backend.herokuapp.com/user/following";
        Request request = new Request.Builder()
                .url(url)
                .build();
        cookieHelper.setCookie(url,"uid",StaticUser.getUid());
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

package ic.ac.drp02;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.riversun.okhttp3.OkHttp3CookieHelper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ic.ac.drp02.databinding.FeedBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class FeedFragment extends Fragment {
    private FeedBinding binding;
    private RecyclerView recyclerView;
    private Handler mHandler;
    private OkHttp3CookieHelper cookieHelper = new OkHttp3CookieHelper();
    private final OkHttpClient client = new OkHttpClient().newBuilder().cookieJar(cookieHelper.cookieJar()).build();
    private Fragment fragment = this;

    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        //getActivity().setTitle("Hi");
        binding = FeedBinding.inflate(inflater, container, false);

        recyclerView = (RecyclerView) binding.getRoot().findViewById(R.id.feed_recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        getWardrobe();


        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        Log.e("adhithi", "lkfjklfj");
        super.onViewCreated(view, savedInstanceState);
        
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void getWardrobe() {
        mHandler = new Handler(Looper.getMainLooper());
        String url = "https://drp02-backend.herokuapp.com/items/feed";
        Request request = new Request.Builder()
                .url(url)
                .build();
        Log.e("Thaarukan", StaticUser.getUid());
        cookieHelper.setCookie(url,"uid", StaticUser.getUid());
        //List<String> results = Collections.emptyList();.get()
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                List<String> results = Collections.emptyList();
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//
                //PUT JSON INTO CLASSES LIST HERE
//
                Gson gson = new Gson();
//                for (String item : response.body().string().split("},")) {
//                    Log.e("adhithi",item);
//                }

                Type listType = new TypeToken<ArrayList<WardrobeItem>>(){}.getType();
                List<WardrobeItem> wardrobeItems = new Gson().fromJson(response.body().string(), listType);
                Collections.reverse(wardrobeItems);


//                Type listType = new TypeToken<ArrayList<WardrobeItem>>(){}.getType();
//                List<WardrobeItem> yourClassList = new Gson().fromJson(response.body().string(), listType);
                //User user = gson.fromJson(,User.class);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        FeedRecyclerAdapter adapter = new FeedRecyclerAdapter(getActivity().getApplicationContext(), new ArrayList<>(wardrobeItems), fragment);

                        recyclerView.setAdapter(adapter);
                        Log.e("adhithi",wardrobeItems.toString());


                    }
                });
            }
        });

    }

    }

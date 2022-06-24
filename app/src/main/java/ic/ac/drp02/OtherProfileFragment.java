package ic.ac.drp02;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.riversun.okhttp3.OkHttp3CookieHelper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


import ic.ac.drp02.databinding.OtherProfileBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OtherProfileFragment extends Fragment {
    private OkHttp3CookieHelper cookieHelper = new OkHttp3CookieHelper();
    private final OkHttpClient client = new OkHttpClient().newBuilder().cookieJar(cookieHelper.cookieJar()).build();
    private OtherProfileBinding binding;
    private Handler mHandler;
    private RecyclerView recyclerView;
    private Fragment fragment = this;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = OtherProfileBinding.inflate(inflater, container, false);

        recyclerView = (RecyclerView) binding.getRoot().findViewById(R.id.recyclerView);
        // set a GridLayoutManager with default vertical orientation and 2 number of columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        //WardrobeItem item1 = new WardrobeItem("https://lp.stories.com/app005prod?set=source[/39/bf/39bf9cb4d4f277a63570377db257604d8ecb0950.jpg],origin[dam],type[DESCRIPTIVESTILLLIFE],device[hdpi],quality[80],ImageVersion[1]&call=url[file:/product/main]", "Blue jumper", Arrays.asList("woolen", "blue"), "itemName", "itemType");
        //WardrobeItem item2 = new WardrobeItem("https://lp2.hm.com/hmgoepprod?set=quality%5B79%5D%2Csource%5B%2Fba%2F9d%2Fba9d9c48e4847ae9e18b3ed23d10878f6c758e38.jpg%5D%2Corigin%5Bdam%5D%2Ccategory%5B%5D%2Ctype%5BDESCRIPTIVESTILLLIFE%5D%2Cres%5Bm%5D%2Chmver%5B2%5D&call=url[file:/product/main]", "Blue jeans", Arrays.asList("jeans", "lightwash"), "itemName", "itemType");
        //WardrobeItem item3 = new WardrobeItem("https://lp2.hm.com/hmgoepprod?set=quality%5B79%5D%2Csource%5B%2Fba%2F9d%2Fba9d9c48e4847ae9e18b3ed23d10878f6c758e38.jpg%5D%2Corigin%5Bdam%5D%2Ccategory%5B%5D%2Ctype%5BDESCRIPTIVESTILLLIFE%5D%2Cres%5Bm%5D%2Chmver%5B2%5D&call=url[file:/product/main]", "Blue jeans", Arrays.asList("jeans", "lightwash"), "itemName", "itemType");
        //ArrayList<WardrobeItem> wardrobeItems = new ArrayList<>(Arrays.asList(item1, item2, item3));
//        GridRecyclerAdapter customAdapter = new GridRecyclerAdapter(getActivity().getApplicationContext(), wardrobeItems, this);
//        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
        getWardrobe();
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        binding.theirLikesProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(OtherProfileFragment.this)
                        .navigate(R.id.action_otherProfileFragment_to_otherUserLikesFragment);
            }
        });

        binding.addFriendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(OtherProfileFragment.this)
                        .navigate(R.id.action_otherProfileFragment_to_otherUserLikesFragment);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



    private void getWardrobe() {
        //replace this to get the other user's wardrobe
        mHandler = new Handler(Looper.getMainLooper());
        Bundle bundle = this.getArguments();
        String url = "https://drp02-backend.herokuapp.com/items/see_wardrobe/"+ bundle.getInt("uid");
        Request request = new Request.Builder()
                .url(url)

                .build();
        cookieHelper.setCookie(url,"uid",StaticUser.getUid());
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

                Type listType = new TypeToken<ArrayList<WardrobeItem>>() {
                }.getType();
                List<WardrobeItem> wardrobeItems = new Gson().fromJson(response.body().string(), listType);

                //Log.e("adhithi", wardrobeItems.get(0).getItemName());

//                Type listType = new TypeToken<ArrayList<WardrobeItem>>(){}.getType();
//                List<WardrobeItem> yourClassList = new Gson().fromJson(response.body().string(), listType);
                //User user = gson.fromJson(,User.class);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        GridRecyclerAdapter customAdapter = new GridRecyclerAdapter(getActivity().getApplicationContext(), new ArrayList<>(wardrobeItems), fragment);
                        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
                        Log.e("adhithi", wardrobeItems.toString());


                    }
                });
            }
        });

    }

    private void getWardrobe2(ListView listView) {
        mHandler = new Handler(Looper.getMainLooper());
        Request request = new Request.Builder()
                .url("https://drp02-backend.herokuapp.com/wardrobe")

                .build();
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
                List<String> results = Arrays.asList(response.body().string().split(","));
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //        GridRecyclerAdapter customAdapter = new GridRecyclerAdapter(getActivity().getApplicationContext(), wardrobeItems, this);
                        //        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
                        //CustomAdapter listAdapter = new CustomAdapter(results);
                        //listView.setAdapter(listAdapter);
                    }
                });
            }
        });

    }


}


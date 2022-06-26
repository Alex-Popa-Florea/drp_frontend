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

import ic.ac.drp02.databinding.OtherUserLikesBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OtherUserLikesFragment extends Fragment {

    private OkHttp3CookieHelper cookieHelper = new OkHttp3CookieHelper();
    private final OkHttpClient client = new OkHttpClient().newBuilder().cookieJar(cookieHelper.cookieJar()).build();
    private OtherUserLikesBinding binding;
    private Handler mHandler;
    private RecyclerView recyclerView;
    private Fragment fragment = this;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = OtherUserLikesBinding.inflate(inflater, container, false);

        recyclerView = (RecyclerView) binding.getRoot().findViewById(R.id.recyclerView);
        // set a GridLayoutManager with default vertical orientation and 2 number of columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
//        WardrobeItem item1 = new WardrobeItem("https://lp.stories.com/app005prod?set=source[/39/bf/39bf9cb4d4f277a63570377db257604d8ecb0950.jpg],origin[dam],type[DESCRIPTIVESTILLLIFE],device[hdpi],quality[80],ImageVersion[1]&call=url[file:/product/main]", "Blue jumper", Arrays.asList("woolen", "blue"), "itemName", "itemType");
//        WardrobeItem item2 = new WardrobeItem("https://lp2.hm.com/hmgoepprod?set=quality%5B79%5D%2Csource%5B%2Fba%2F9d%2Fba9d9c48e4847ae9e18b3ed23d10878f6c758e38.jpg%5D%2Corigin%5Bdam%5D%2Ccategory%5B%5D%2Ctype%5BDESCRIPTIVESTILLLIFE%5D%2Cres%5Bm%5D%2Chmver%5B2%5D&call=url[file:/product/main]", "Blue jeans", Arrays.asList("jeans", "lightwash"), "itemName", "itemType");
//        WardrobeItem item3 = new WardrobeItem("https://lp2.hm.com/hmgoepprod?set=quality%5B79%5D%2Csource%5B%2Fba%2F9d%2Fba9d9c48e4847ae9e18b3ed23d10878f6c758e38.jpg%5D%2Corigin%5Bdam%5D%2Ccategory%5B%5D%2Ctype%5BDESCRIPTIVESTILLLIFE%5D%2Cres%5Bm%5D%2Chmver%5B2%5D&call=url[file:/product/main]", "Blue jeans", Arrays.asList("jeans", "lightwash"), "itemName", "itemType");
        //ArrayList<WardrobeItem> wardrobeItems = new ArrayList<>(Arrays.asList(item1, item2, item3));
//        GridRecyclerAdapter customAdapter = new GridRecyclerAdapter(getActivity().getApplicationContext(), wardrobeItems, this);
//        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
        getWardrobe();
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = fragment.getArguments();
        String name = bundle.getString("username");
        int uid = bundle.getInt("uid");

        TextView username = binding.getRoot().findViewById(R.id.likes_profile_user_name);
        username.setText(name);

        binding.theirSharedWaredrobeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = fragment.getArguments();
                int uid = bundle.getInt("uid");
                Bundle newBundle = new Bundle();
                newBundle.putInt("uid", uid);
                newBundle.putString("username", bundle.getString("username"));
                NavHostFragment.findNavController(OtherUserLikesFragment.this)
                        .navigate(R.id.action_otherUserLikesFragment_to_otherProfileFragment, newBundle);
            }
        });

//        BottomNavigationView bottomNavigationView = binding.getRoot().findViewById(R.id.bottomNavigationView);
//        bottomNavigationView.setOnItemSelectedListener(item -> {
//            if (item.getItemId() == R.id.home) {
//                NavHostFragment.findNavController(ProfileFragment.this)
//                        .navigate(R.id.action_profileFragment_to_feedFragment);
//                bottomNavigationView.setSelectedItemId(R.id.home);
//                return true;
//            }
//            return false;
//        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    class CustomAdapter extends BaseAdapter {
        List<String> items;

        public CustomAdapter(List<String> items) {
            super();
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return items.get(i).hashCode();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView textView = new TextView(getContext());
            textView.setText(items.get(i));
            return textView;
        }
    }

    private void getWardrobe() {
        mHandler = new Handler(Looper.getMainLooper());
        String url = "https://drp02-backend.herokuapp.com/likes/liked_items/" + this.getArguments().getInt("uid");
        Request request = new Request.Builder()
                .url(url)
                .build();
        cookieHelper.setCookie(url,"uid", StaticUser.getUidStr());
        //List<String> results = Collections.emptyList();.get()
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                List<String> results = Collections.emptyList();
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Gson gson = new Gson();


                Type listType = new TypeToken<ArrayList<WardrobeItem>>(){}.getType();
                List<WardrobeItem> wardrobeItems = new Gson().fromJson(response.body().string(), listType);

                List<WardrobeItem> likedItems = new ArrayList<>();

                for (WardrobeItem item : wardrobeItems) {
                    if (item.getLikes() > 0) {
                        likedItems.add(item);
                    }
                }

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        OthersGridRecyclerAdapter customAdapter = new OthersGridRecyclerAdapter(getActivity().getApplicationContext(), new ArrayList<>(likedItems), fragment);
                        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
                        Log.e("adhithi",wardrobeItems.toString());


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


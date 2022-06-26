package ic.ac.drp02;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import java.util.concurrent.ExecutionException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import ic.ac.drp02.databinding.ProfileBinding;

public class ProfileFragment extends Fragment {
    private OkHttp3CookieHelper cookieHelper = new OkHttp3CookieHelper();
    private final OkHttpClient client = new OkHttpClient().newBuilder().cookieJar(cookieHelper.cookieJar()).build();
    private ProfileBinding binding;
    private Handler mHandler;
    private RecyclerView recyclerView;
    private Fragment fragment = this;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = ProfileBinding.inflate(inflater, container, false);

        recyclerView = (RecyclerView) binding.getRoot().findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);

        getWardrobe();
        return binding.getRoot();

    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView username = binding.getRoot().findViewById(R.id.profile_user_name);
        username.setText(StaticUser.getUsername());

        Button friends = binding.getRoot().findViewById(R.id.my_friends_button);
        friends.setText(Integer.toString(StaticUser.getFriends().size()));


        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ProfileFragment.this)
                        .navigate(R.id.action_profileFragment_to_addItem);
            }
        });

        binding.myLikesProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ProfileFragment.this)
                        .navigate(R.id.action_profileFragment_to_likesFragment);
            }
        });

        binding.myFriendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ProfileFragment.this)
                        .navigate(R.id.action_profileFragment_to_myFriends);
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



    private void getWardrobe() {
        mHandler = new Handler(Looper.getMainLooper());
        String url = "https://drp02-backend.herokuapp.com/items/wardrobe";
        cookieHelper.setCookie(url,"uid", StaticUser.getUidStr());
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                List<String> results = Collections.emptyList();
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Type listType = new TypeToken<ArrayList<WardrobeItem>>(){}.getType();
                List<WardrobeItem> wardrobeItems = new Gson().fromJson(response.body().string(), listType);

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                                GridRecyclerAdapter customAdapter = new GridRecyclerAdapter(getActivity().getApplicationContext(), new ArrayList<>(wardrobeItems), fragment);
                                recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
                                Log.e("adhithi",wardrobeItems.toString());


                    }
                });
            }
        });

    }
}
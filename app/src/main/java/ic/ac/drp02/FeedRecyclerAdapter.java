package ic.ac.drp02;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import ic.ac.drp02.analytics.TimeToLike;
import ic.ac.drp02.databinding.PostLayoutBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.riversun.okhttp3.OkHttp3CookieHelper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class FeedRecyclerAdapter  extends RecyclerView.Adapter<FeedRecyclerAdapter.Viewholder>{
    private Context context;
    private ArrayList<WardrobeItem> wardrobeItems;
    Fragment fragment;
    PostLayoutBinding binding;
    View view;
    private Handler mHandler;
    private OkHttp3CookieHelper cookieHelper = new OkHttp3CookieHelper();
    private final OkHttpClient client = new OkHttpClient().newBuilder().cookieJar(cookieHelper.cookieJar()).build();

    // Constructor
    public FeedRecyclerAdapter(Context context, ArrayList<WardrobeItem> wardrobeItems, Fragment fragment, Handler mHandler) {
        this.context = context;
        this.wardrobeItems = wardrobeItems;
        this.fragment = fragment;
        this.mHandler = mHandler;
    }

    @NonNull
    @Override
    public FeedRecyclerAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        //binding = FeedBinding.inflate(inflater, container, false);
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_layout, parent, false);



        return new Viewholder(view);
    }

    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull FeedRecyclerAdapter.Viewholder holder, @SuppressLint("RecyclerView") int position) {
        // to set data to textview and imageview of each card layout SORT THIS SHIT OUT
        WardrobeItem model = wardrobeItems.get(position);
        holder.description.setText(model.getDescription());
        //holder.itemName.setText(model.getItemName());
        holder.itemType.setText(model.getItemType());
        holder.username.setText(model.getUsername());

        Log.e("adhithi", model.getImageUrl());
        Picasso.get().load(model.getImageUrl()).into(holder.image);

        //holder.likeButton.setImageResource(R.drawable.streamlinehq_interface_favorite_heart_interface_essential_600__1_);



        //wardrobeItems.get(position).initLiked();

        //OkHttp3CookieHelper cookieHelper = new OkHttp3CookieHelper();
        //final OkHttpClient client = new OkHttpClient().newBuilder().cookieJar(cookieHelper.cookieJar()).build();

        CompletableFuture<List<WardrobeItem>> likedFuture = getLikes();
        boolean liked = false;
        try {

            List<WardrobeItem> likedItems = likedFuture.get();
            for (WardrobeItem item : likedItems) {
                if (item.getId().equals(model.getId())) {
                    liked = true;
                    //
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        Log.e("adhithi", Boolean.toString(liked));
        if (liked) {
            holder.likeButton.setImageResource(R.drawable.streamlinehq_interface_favorite_heart_interface_essential_6200);
        }
        else {
            holder.likeButton.setImageResource(R.drawable.streamlinehq_interface_favorite_heart_interface_essential_600__1_);
        }

        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                TimeToLike timeToLike = TimeToLike.getInstance();
                if (!timeToLike.getFirstLike()){
                    timeToLike.setFirstLike(true);
                    timeToLike.setFirstLikeTime(LocalDateTime.now());
                }

                CompletableFuture<List<WardrobeItem>> likedFuture = getLikes();
                boolean liked = false;
                try {

                    List<WardrobeItem> likedItems = likedFuture.get();
                    for (WardrobeItem item : likedItems) {
                        if (item.getId().equals(model.getId())) {
                            liked = true;
                            //
                        }
                    }
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                Request request;
                String url;

                if (liked) {
                    url = "https://drp02-backend.herokuapp.com/likes/unlike/" + wardrobeItems.get(position).getId().toString();
                }
                else {
                    url = "https://drp02-backend.herokuapp.com/likes/like/" + wardrobeItems.get(position).getId().toString();
                }

                request = new Request.Builder()
                        .url(url)
                        .post(RequestBody.create(null, new byte[0]))
                        .build();
                cookieHelper.setCookie(url,"uid", StaticUser.getUidStr());

                final boolean likedBefore = liked;

                //List<String> results = Collections.emptyList();.get()
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("thaarukanisannoying", "failed "+ StaticUser.getUidStr().toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.e("thaarukanisannoying", StaticUser.getUidStr());
                        if (likedBefore) {
                            holder.likeButton.setImageResource(R.drawable.streamlinehq_interface_favorite_heart_interface_essential_600__1_);
                        }
                        else {
                            holder.likeButton.setImageResource(R.drawable.streamlinehq_interface_favorite_heart_interface_essential_6200);

                        }
                    }
                });
            }});
        ImageButton profileButton = view.findViewById(R.id.feed_profile_icon);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //bundle things to go to other profile screen but pass on wardrobe item class
                Bundle newBundle = new Bundle();
                newBundle.putInt("uid", model.getUid());
                newBundle.putString("username", model.getUsername());
                newBundle.putInt("friends", model.getNumFriends());
                NavHostFragment.findNavController(fragment)
                        .navigate(R.id.action_global_otherProfileFragment, newBundle);
            }});
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return wardrobeItems.size();
    }

    // View holder class for initializing of
    // your views such as TextView and Imageview.
    public static class Viewholder extends RecyclerView.ViewHolder {
        private final ImageView image;
        private final TextView description, itemType, username;
        private final ImageButton likeButton;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.feed_image);
            description = itemView.findViewById(R.id.feed_description);
            //itemName = itemView.findViewById(R.id.item_name);
            itemType = itemView.findViewById(R.id.item_type);
            likeButton = itemView.findViewById(R.id.likeButtonFeed);
            username = itemView.findViewById(R.id.user_name);
        }
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private CompletableFuture<List<WardrobeItem>> getLikes() {
        String url = "https://drp02-backend.herokuapp.com/likes/liked_items";
        Request request = new Request.Builder()
                .url(url)
                .build();
        cookieHelper.setCookie(url,"uid",StaticUser.getUidStr());
        CompletableFuture<List<WardrobeItem>> result = new CompletableFuture<>();



        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.completeExceptionally(e);
                e.printStackTrace();
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Type listType = new TypeToken<ArrayList<WardrobeItem>>() {
                }.getType();
                List<WardrobeItem> likedItems = new Gson().fromJson(response.body().string(), listType);
                result.complete(likedItems);


            }});
        return result;
    }
}









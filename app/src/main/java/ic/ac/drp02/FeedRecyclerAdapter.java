package ic.ac.drp02;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
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
import ic.ac.drp02.databinding.FeedBinding;
import ic.ac.drp02.databinding.PostLayoutBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import com.squareup.picasso.Picasso;

import org.riversun.okhttp3.OkHttp3CookieHelper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    public FeedRecyclerAdapter(Context context, ArrayList<WardrobeItem> wardrobeItems, Fragment fragment) {
        this.context = context;
        this.wardrobeItems = wardrobeItems;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public FeedRecyclerAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        //binding = FeedBinding.inflate(inflater, container, false);
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_layout, parent, false);



        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedRecyclerAdapter.Viewholder holder, @SuppressLint("RecyclerView") int position) {
        // to set data to textview and imageview of each card layout SORT THIS SHIT OUT
        WardrobeItem model = wardrobeItems.get(position);
        holder.description.setText(model.getDescription());
        holder.itemName.setText(model.getItemName());
        holder.itemType.setText(model.getItemType());
        Log.e("adhithi", model.getImageUrl());
        Picasso.get().load(model.getImageUrl()).into(holder.image);
        ImageButton likeButton = view.findViewById(R.id.likeButtonFeed);
        if (wardrobeItems.get(position).getLikes() > 0) { //CHANGE THIS SO ITS IF USER HAS LIKED
            likeButton.setImageResource(R.drawable.streamlinehq_interface_favorite_heart_interface_essential_6200);

        }

        //do we actually want to be able to click on a post and go to item details? don't think so
        //cos if we did, we'd have to make a seperate screen that includes profiles

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // open another activity on item click
//
//                Bundle bundle = new Bundle();
//                bundle.putString("image", wardrobeItems.get(position).getImageUrl());
//                bundle.putString("description", wardrobeItems.get(position).getDescription());
//                bundle.putString("tags", wardrobeItems.get(position).getTagString());
//
//
//                NavHostFragment.findNavController(fragment)
//                        .navigate(R.id.action_feedFragment_to_itemDetails, bundle);
//            }
//        });


        likeButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                TimeToLike timeToLike = TimeToLike.getInstance();
                if (!timeToLike.getFirstLike()){
                    timeToLike.setFirstLike(true);
                    timeToLike.setFirstLikeTime(LocalDateTime.now());
                }

                boolean likedBefore = false; //wardrobeItems.get(position).getLikes() > 0;
                Request request;
                String url;

                if (likedBefore) {
                    url = "https://drp02-backend.herokuapp.com/likes/dislike/" + wardrobeItems.get(position).getId().toString();
                }
                else {
                    url = "https://drp02-backend.herokuapp.com/likes/like/" + wardrobeItems.get(position).getId().toString();
                }

                request = new Request.Builder()
                        .url(url)
                        .post(RequestBody.create(null, new byte[0]))
                        .build();
                cookieHelper.setCookie(url,"uid",User.getUid());

                //List<String> results = Collections.emptyList();.get()
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("thaarukanisannoying", "failed "+User.getUid().toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.e("thaarukanisannoying", User.getUid());
                        if (likedBefore) {
                            likeButton.setImageResource(R.drawable.streamlinehq_interface_favorite_heart_interface_essential_600__1_);

                        }
                        else {
                            likeButton.setImageResource(R.drawable.streamlinehq_interface_favorite_heart_interface_essential_6200);

                        }
                    }
                });
            }
        });


        ImageButton profileButton = view.findViewById(R.id.feed_profile_icon);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //bundle things to go to other profile screen but pass on wardrobe item class
                NavHostFragment.findNavController(fragment)
                        .navigate(R.id.action_feedFragment_to_otherProfileFragment);
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
        private final TextView description, itemName, itemType;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.feed_image);
            description = itemView.findViewById(R.id.feed_description);
            itemName = itemView.findViewById(R.id.item_name);
            itemType = itemView.findViewById(R.id.item_type);
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
}









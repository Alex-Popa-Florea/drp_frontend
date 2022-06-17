package ic.ac.drp02;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import ic.ac.drp02.databinding.FeedBinding;
import ic.ac.drp02.databinding.PostLayoutBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FeedRecyclerAdapter  extends RecyclerView.Adapter<FeedRecyclerAdapter.Viewholder>{
    private Context context;
    private ArrayList<WardrobeItem> wardrobeItems;
    Fragment fragment;
    PostLayoutBinding binding;
    View view;
    private final OkHttpClient client = new OkHttpClient();

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
    public void onBindViewHolder(@NonNull FeedRecyclerAdapter.Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout SORT THIS SHIT OUT
        WardrobeItem model = wardrobeItems.get(position);
        holder.description.setText(model.getDescription());
        holder.itemName.setText(model.getItemName());
        holder.itemType.setText(model.getItemType());
        Picasso.get().load(model.getImageUrl()).into(holder.image);

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

        ImageButton likeButton = view.findViewById(R.id.likeButtonFeed);
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Request request = new Request.Builder()
                        .url("https://drp02-backend.herokuapp.com/like_item/" + wardrobeItems.get(position).getId().toString())

                        .build();
                //List<String> results = Collections.emptyList();.get()
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                    }
                });
                Log.e("adhithi", "hihihi");
            }
        });
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
}









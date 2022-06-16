package ic.ac.drp02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FeedRecyclerAdapter  extends RecyclerView.Adapter<FeedRecyclerAdapter.Viewholder>{
    private Context context;
    private ArrayList<WardrobeItem> wardrobeItems;
    Fragment fragment;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_layout, parent, false);
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









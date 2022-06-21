package ic.ac.drp02;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ic.ac.drp02.databinding.PostLayoutBinding;
import okhttp3.OkHttpClient;

public class AddFriendAdapter extends RecyclerView.Adapter<AddFriendAdapter.Viewholder>{
    private Context context;
    private ArrayList<Friend> friendsToAdd;
    Fragment fragment;
    PostLayoutBinding binding;
    View view;
    private final OkHttpClient client = new OkHttpClient();

    // Constructor
    public AddFriendAdapter(Context context, ArrayList<Friend> friendsToAdd, Fragment fragment) {
        this.context = context;
        this.friendsToAdd = friendsToAdd;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public AddFriendAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_friend_elem, parent, false);
        return new AddFriendAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddFriendAdapter.Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout SORT THIS SHIT OUT
        Friend model = friendsToAdd.get(position);
        Log.i("thaarukan",holder.rating.toString());
        holder.rating.setText(model.getRating());
        holder.name.setText(model.getName());

        ImageButton addButton = view.findViewById(R.id.add_friend_add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Request request = new Request.Builder()
//                        .url("https://drp02-backend.herokuapp.com/like_item/" + friendsToAdd.get(position).getId().toString())
//
//                        .build();
//                //List<String> results = Collections.emptyList();.get()
//                client.newCall(request).enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        //likeButton.setImageDrawable(put ur file name here);
//                    }
//                });
//                Log.e("adhithi", "hihihi");
            }
        });

        ImageButton profileButton = view.findViewById(R.id.add_friend_user_icon);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(fragment)
                        .navigate(R.id.action_addFriend_to_otherProfileFragment);
            }});
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return friendsToAdd.size();
    }

    // View holder class for initializing of
    // your views such as TextView and Imageview.
    public static class Viewholder extends RecyclerView.ViewHolder {
        private final TextView rating;
        private final TextView name;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            rating = itemView.findViewById(R.id.add_friend_user_rating);
            name = itemView.findViewById(R.id.add_friend_user_name);
        }
    }
}

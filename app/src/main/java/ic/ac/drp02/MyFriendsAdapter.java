package ic.ac.drp02;

import android.content.Context;
import android.os.Bundle;
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


import ic.ac.drp02.databinding.MyFriendsElemBinding;
import okhttp3.OkHttpClient;

public class MyFriendsAdapter extends RecyclerView.Adapter<MyFriendsAdapter.Viewholder>{
    private Context context;
    private ArrayList<User> friends;
    Fragment fragment;
    MyFriendsElemBinding binding;
    View view;
    private final OkHttpClient client = new OkHttpClient();

    // Constructor
    public MyFriendsAdapter(Context context, ArrayList<User> friends, Fragment fragment) {
        this.context = context;
        this.friends = friends;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public MyFriendsAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_friends_elem, parent, false);
        return new MyFriendsAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyFriendsAdapter.Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout SORT THIS SHIT OUT
        User model = friends.get(position);
        holder.name.setText(model.getName());

        ImageButton profileButton = view.findViewById(R.id.my_friend_user_icon);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //display other users profile
                //might need to send uid with a bundle
                // ill do that bit
                Bundle bundle = new Bundle();
                bundle.putInt("uid", model.getUid());
                NavHostFragment.findNavController(fragment)
                        .navigate(R.id.action_myFriends_to_otherProfileFragment, bundle);
            }});



    }

    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return friends.size();
    }

    // View holder class for initializing of
    // your views such as TextView and Imageview.
    public static class Viewholder extends RecyclerView.ViewHolder {
        private final TextView rating;
        private final TextView name;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            rating = itemView.findViewById(R.id.my_friend_user_rating);
            name = itemView.findViewById(R.id.my_friend_user_name);
        }
    }
}

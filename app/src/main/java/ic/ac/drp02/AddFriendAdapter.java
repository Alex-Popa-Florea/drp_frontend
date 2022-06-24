package ic.ac.drp02;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
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

import org.riversun.okhttp3.OkHttp3CookieHelper;

import java.io.IOException;
import java.util.ArrayList;

import ic.ac.drp02.databinding.PostLayoutBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddFriendAdapter extends RecyclerView.Adapter<AddFriendAdapter.Viewholder>{
    private Context context;
    private ArrayList<User> friendsToAdd;
    Fragment fragment;
    PostLayoutBinding binding;
    View view;

    private OkHttp3CookieHelper cookieHelper = new OkHttp3CookieHelper();
    private final OkHttpClient client = new OkHttpClient().newBuilder().cookieJar(cookieHelper.cookieJar()).build();

    // Constructor
    public AddFriendAdapter(Context context, ArrayList<User> friendsToAdd, Fragment fragment) {
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
    public void onBindViewHolder(@NonNull AddFriendAdapter.Viewholder holder, @SuppressLint("RecyclerView") int position) {
        // to set data to textview and imageview of each card layout SORT THIS SHIT OUT
        User model = friendsToAdd.get(position);
        Log.i("thaarukan",holder.rating.toString());
        holder.name.setText(model.getName());

        ImageButton addButton = view.findViewById(R.id.add_friend_add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url =  "https://drp02-backend.herokuapp.com/user/follow/" + friendsToAdd.get(position).getUid().toString();
                Request request = new Request.Builder()
                        .url(url)
                        .post(RequestBody.create(null,new byte[0]))
                        .build();
                cookieHelper.setCookie(url,"uid",StaticUser.getUid());
                //List<String> results = Collections.emptyList();.get()
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //Log.e("adhithi", )
                    }
                });
                Log.e("adhithi", "hihihi");
            }
        });

        ImageButton profileButton = view.findViewById(R.id.add_friend_user_icon);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //display other users profile
                //might need to send uid with a bundle
                // ill do that bit
                Bundle bundle = new Bundle();
                bundle.putInt("uid", model.getUid());
                bundle.putString("username", model.getName());
                NavHostFragment.findNavController(fragment)
                        .navigate(R.id.action_addFriend_to_otherProfileFragment, bundle);
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

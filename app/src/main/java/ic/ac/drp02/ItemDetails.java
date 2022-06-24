package ic.ac.drp02;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.riversun.okhttp3.OkHttp3CookieHelper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import com.google.gson.Gson;
import ic.ac.drp02.databinding.FragmentSecondBinding;
import ic.ac.drp02.databinding.ItemDetailsBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ItemDetails extends Fragment {

    private ItemDetailsBinding binding;
    ImageView selectedImage;
    TextView description;
    TextView itemName;
    TextView itemType;
    TextView numLikes;
    int id;
    private OkHttp3CookieHelper cookieHelper = new OkHttp3CookieHelper();
    private final OkHttpClient client = new OkHttpClient().newBuilder().cookieJar(cookieHelper.cookieJar()).build();


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = ItemDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        selectedImage = (ImageView) binding.getRoot().findViewById(R.id.imageView); // init a ImageView
        description = (TextView) binding.getRoot().findViewById(R.id.description);
        itemName = (TextView) binding.getRoot().findViewById(R.id.details_item_name);
        itemType = (TextView) binding.getRoot().findViewById(R.id.details_item_type);
        numLikes = (TextView) binding.getRoot().findViewById(R.id.likesNumber);


        Bundle bundle = this.getArguments();

        if (bundle != null) {
            Picasso.get().load(bundle.getString("image")).into(selectedImage);
            description.setText(bundle.getString("description"));
            itemName.setText(bundle.getString("itemName"));
            itemType.setText(bundle.getString("itemType"));
            numLikes.setText(bundle.getString("numLikes"));
            id = bundle.getInt("id");

        }

        ImageButton deletePost = binding.getRoot().findViewById(R.id.deletePost);

        deletePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //put stoopid request here
                String url = "https://drp02-backend.herokuapp.com/items/delete/"+Integer.toString(id);
                cookieHelper.setCookie(url,"uid", StaticUser.getUid());
                Request request = new Request.Builder()
                        .url(url)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //List<String> results = Collections.emptyList();
                        e.printStackTrace();
                        Log.i("ahhh", "its an error");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.i("delete", response.body().string());
                    }
                });

            }});





    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
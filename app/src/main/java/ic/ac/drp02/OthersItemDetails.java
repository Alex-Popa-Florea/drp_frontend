package ic.ac.drp02;

import android.os.Bundle;
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

import com.squareup.picasso.Picasso;

import org.riversun.okhttp3.OkHttp3CookieHelper;

import java.io.IOException;

import ic.ac.drp02.databinding.ItemDetailsBinding;
import ic.ac.drp02.databinding.OthersItemDetailsBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OthersItemDetails extends Fragment {

    private OthersItemDetailsBinding binding;
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

        binding = OthersItemDetailsBinding.inflate(inflater, container, false);
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


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
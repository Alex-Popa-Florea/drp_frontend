package ic.ac.drp02;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import ic.ac.drp02.databinding.FragmentSecondBinding;
import ic.ac.drp02.databinding.ItemDetailsBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ItemDetails extends Fragment {

    private ItemDetailsBinding binding;
    ImageView selectedImage;
    TextView description;
    TextView tags;


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
        tags = (TextView) binding.getRoot().findViewById(R.id.tags);


        Bundle bundle = this.getArguments();

        if (bundle != null) {
            Picasso.get().load(bundle.getString("image")).into(selectedImage);
            description.setText(bundle.getString("description"));
            tags.setText(bundle.getString("tags"));
        }



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
package ic.ac.drp02;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import ic.ac.drp02.databinding.AddItemBinding;
import ic.ac.drp02.databinding.FragmentSecondBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AddItem extends Fragment {

    private AddItemBinding binding;
    private final OkHttpClient client = new OkHttpClient();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = AddItemBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText itemDescription  = (EditText) binding.getRoot().findViewById(R.id.editTextTextPersonName);
                String itemToAdd = itemDescription.getText().toString();
//                Request request = new Request.Builder()
//                        .url("https://drp02-backend.herokuapp.com/insert/"+itemToAdd)
//                        .build();
//                Log.e("ahhh", "its an error");
//                client.newCall(request).enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        //List<String> results = Collections.emptyList();
//                        e.printStackTrace();
//                        Log.i("ahhh", "its an error");
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        Log.i("ahhh", response.body().string());
//                    }
//                });
                getActivity().onBackPressed();

            }});

        binding.uploadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
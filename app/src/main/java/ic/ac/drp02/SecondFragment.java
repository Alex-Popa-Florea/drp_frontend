package ic.ac.drp02;

import android.os.Bundle;
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

import ic.ac.drp02.databinding.FragmentSecondBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private final OkHttpClient client = new OkHttpClient();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editName  = (EditText) binding.getRoot().findViewById(R.id.textview_second);
                String itemToAdd = editName.getText().toString();
                Request request = new Request.Builder()
                        .url("https://drp02-backend.herokuapp.com/insert/"+itemToAdd)
                        .build();
                Log.e("ahhh", "its an error");
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //List<String> results = Collections.emptyList();
                        e.printStackTrace();
                        Log.i("ahhh", "its an error");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.i("ahhh", response.body().string());
                     }
            });
                binding.getRoot().findViewById(R.id.textView2).setVisibility(View.VISIBLE);

    }});}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
package ic.ac.drp02;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import ic.ac.drp02.databinding.FragmentFirstBinding;
import okhttp3.ResponseBody;

public class FirstFragment extends Fragment {
    private final OkHttpClient client = new OkHttpClient();
    private FragmentFirstBinding binding;
    private Handler mHandler;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        String[] mobileArray = {"Light blue skinny jeans", "Gray formal trousers", "White t-shirt", "Black shirt",
                "Denim jacket", "Stripey t-shirt", "Cargo trousers", "Puffer coat", "Blazer", "White trainers", "Boots", "Burgundy jumper", "Black flared jeans", "Dark blue straight-leg jeans", "Cream turtleneck jumper", "Black high neck jumper", "Gray sweatshirt", "Navy hoodie", "Long-sleeve t-shirt", "Blue t-shirt"};

        View contentView = inflater.inflate(R.layout.fragment_first, container, false);
        ListView listView = binding.getRoot().findViewById(R.id.lv1);
        getWardrobe(listView);


        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    class CustomAdapter extends BaseAdapter {
        List<String> items;

        public CustomAdapter(List<String> items) {
            super();
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return items.get(i).hashCode();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView textView = new TextView(getContext());
            textView.setText(items.get(i));
            return textView;
        }
    }

    private void getWardrobe(ListView listView) {
        mHandler = new Handler(Looper.getMainLooper());
        Request request = new Request.Builder()
                .url("https://drp02-backend.herokuapp.com/wardrobe")
                .get()
                .build();
        //List<String> results = Collections.emptyList();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                List<String> results = Collections.emptyList();
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                try (ResponseBody responseBody = response.body()) {
//                    if (!response.isSuccessful())
//                        throw new IOException("Unexpected code " + response);
//
//                    List<String> results = Collections.emptyList();
//                    if (responseBody != null) {
//
//                        results = Arrays.asList(responseBody.string().split(","));
//                        //results.remove(results.size() - 1);
//                    }
//
//
//                }

                List<String> results = Arrays.asList(response.body().string().split(","));
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        CustomAdapter listAdapter = new CustomAdapter(results);
                        listView.setAdapter(listAdapter);
                    }
                });
            }
        });

    }


}
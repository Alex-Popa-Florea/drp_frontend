package ic.ac.drp02;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ic.ac.drp02.databinding.AddItemBinding;
import okhttp3.OkHttpClient;

public class AddItem extends Fragment {

    private AddItemBinding binding;
    private final OkHttpClient client = new OkHttpClient();
    static final int REQUEST_IMAGE_CAPTURE = 1;
    Bitmap imageBitmap;
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
                EditText editDescription  = (EditText) binding.getRoot().findViewById(R.id.itemToAddDescription);
                String descriptionToAdd = editDescription.getText().toString();
                EditText editName = (EditText) binding.getRoot().findViewById(R.id.itemName);
                String nameToAdd = editDescription.getText().toString();
                EditText editType = (EditText) binding.getRoot().findViewById(R.id.itemType);
                String typeToAdd = editDescription.getText().toString();

                //do shit with image bitmap
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
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                try {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            ImageView image  = binding.getRoot().findViewById(R.id.imageView2);
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            image.setImageBitmap(imageBitmap);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
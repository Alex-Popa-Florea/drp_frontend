package ic.ac.drp02;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ic.ac.drp02.databinding.AddItemBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddItem extends Fragment {

    private AddItemBinding binding;
    private final OkHttpClient client = new OkHttpClient();
    static final int REQUEST_IMAGE_CAPTURE = 1;
    Bitmap imageBitmap;
    File photo;
    String mCurrentPhotoPath;
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


                try {

                    RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                            .addFormDataPart("file", photo.getName(),
                                    RequestBody.create(MediaType.parse("image/jpeg"), photo))
                            .addFormDataPart("some-field", "some-value")
                            .build();

                    Request request = new Request.Builder()
                            .url("https://drpbucket.s3.eu-west-2.amazonaws.com/" + photo.getName())
                            .post(requestBody)
                            .build();

                    client.newCall(request).enqueue(new Callback() {

                        @Override
                        public void onFailure(final Call call, final IOException e) {
                            // Handle the error
                            // no
                            Log.e("lskdgjlkd", "oh no");
                        }

                        @Override
                        public void onResponse(final Call call, final Response response) throws IOException {
                            if (!response.isSuccessful()) {
                                Log.e("lskdgjlkd", "oh noo");
                            }

                        }
                    });


                } catch (Exception ex) {
                    // Handle the error
                }


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
                    photo = createImageFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photo);

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
            Bitmap bmImg = BitmapFactory.decodeFile(mCurrentPhotoPath);
//
//            Bundle extras = data.getExtras();
//            imageBitmap = (Bitmap) extras.get("data");
            image.setImageBitmap(bmImg);
//
//        }

//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            try {
//                imageBitmap = MediaStore.Images.Media.getBitmap(binding.getRoot().getContext().getContentResolver(), Uri.parse(mCurrentPhotoPath));
//                ImageView image  = binding.getRoot().findViewById(R.id.imageView2);
//                image.setImageBitmap(imageBitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.e("adhithi", mCurrentPhotoPath);

        return image;
    }

}
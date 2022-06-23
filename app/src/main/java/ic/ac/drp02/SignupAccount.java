package ic.ac.drp02;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.gson.Gson;

import ic.ac.drp02.databinding.LoginScreenBinding;
import ic.ac.drp02.databinding.SignupScreenBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import org.riversun.okhttp3.OkHttp3CookieHelper;

import java.io.IOException;

public class SignupAccount extends Fragment {

    private SignupScreenBinding binding;

    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = SignupScreenBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText editEmail  = (EditText) binding.getRoot().findViewById(R.id.email_text_input);
                String email = editEmail.getText().toString();

                EditText editName  = (EditText) binding.getRoot().findViewById(R.id.name_text_input);
                String name = editName.getText().toString();

                EditText editPassword  = (EditText) binding.getRoot().findViewById(R.id.password_text_input);
                String password = editPassword.getText().toString();

                EditText editConfirmPassword  = (EditText) binding.getRoot().findViewById(R.id.confirm_password_text_input);
                String confirmPassword = editConfirmPassword.getText().toString();

                //check password and confirmpassword are same

                NewUserData newUserData = new NewUserData(name, email, password);
                Gson gson = new Gson();
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                String url = "https://drp02-backend.herokuapp.com/user/signup";

                Request request = new Request.Builder()
                        .url(url)
                        .post(RequestBody.create(JSON, gson.toJson(newUserData)))
                        .build();

                Log.e("data",gson.toJson(newUserData));
                OkHttpClient client = new OkHttpClient();

                client.newCall(request).enqueue(new Callback() {
                    @Override public void onFailure(Call call, IOException e) {
                        Log.e("thaarukanisannoying", "User.getUid()");
                        e.printStackTrace();
                    }

                    @Override public void onResponse(Call call, Response response) throws IOException {
                        try (ResponseBody responseBody = response.body()) {
                            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                            Headers responseHeaders = response.headers();
                            User user = User.getInstance();
                            String uid = responseHeaders.values("Set-Cookie").get(0).split(";")[0].split("=")[1];
                            User.setUid(uid);

                        }
                    }
                });

                NavHostFragment.findNavController(SignupAccount.this)
                        .navigate(R.id.action_signupAccount_to_feedFragment);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}

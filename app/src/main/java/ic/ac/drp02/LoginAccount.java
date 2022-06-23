package ic.ac.drp02;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.gson.Gson;

import java.io.IOException;

import ic.ac.drp02.databinding.LoginScreenBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LoginAccount extends Fragment {

    private LoginScreenBinding binding;
    private Handler mHandler;

    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = LoginScreenBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText editUsername  = (EditText) binding.getRoot().findViewById(R.id.login_email_text_input);
                String username = editUsername.getText().toString();
                EditText editPassword  = (EditText) binding.getRoot().findViewById(R.id.login_password_text_input);
                String password = editPassword.getText().toString();

                LoginData loginData = new LoginData(username,password);
                Gson gson = new Gson();

                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                String url = "https://drp02-backend.herokuapp.com/user/login";

                Request request = new Request.Builder()
                        .url(url)
                        .post(RequestBody.create(JSON, gson.toJson(loginData)))
                        .build();
                OkHttpClient client = new OkHttpClient();
                Log.e("Thaarukan","heree");
                client.newCall(request).enqueue(new Callback() {
                    @Override public void onFailure(Call call, IOException e) {
                        Log.e("thaarukanisannoying", "User.getUid()");
                        e.printStackTrace();
                    }

                    @Override public void onResponse(Call call, Response response) throws IOException {
                        try (ResponseBody responseBody = response.body()) {
                            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                            Headers responseHeaders = response.headers();
                            String uid = responseHeaders.values("Set-Cookie").get(0).split(";")[0].split("=")[1];
                            Log.e("Thaarukan",uid);
                            StaticUser.setUid(uid);
                            Log.e("Thaarukan", StaticUser.getUid());

                            mHandler = new Handler(Looper.getMainLooper());
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    NavHostFragment.findNavController(LoginAccount.this)
                                            .navigate(R.id.action_loginAccount_to_feedFragment);
                                }
                            });

                        }
                    }


                });
            }
        });

        binding.createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LoginAccount.this)
                        .navigate(R.id.action_loginAccount_to_signupAccount);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}

package com.salesianostriana.jldominguez.moveright;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.salesianostriana.jldominguez.moveright.model.LoginResponse;
import com.salesianostriana.jldominguez.moveright.retrofit.UtilToken;
import com.salesianostriana.jldominguez.moveright.retrofit.generator.ServiceGenerator;
import com.salesianostriana.jldominguez.moveright.retrofit.services.LoginService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPass;
    Button bLogin,bSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etLogEmail);
        etPass = findViewById(R.id.etLogPass);
        bLogin = findViewById(R.id.bLogLogin);
        bSignup = findViewById(R.id.bLogSignup);

        bSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = etEmail.getText().toString();
                String pass = etPass.getText().toString();

                LoginService service = ServiceGenerator.createService(LoginService.class, email, pass);

                Call<LoginResponse> call = service.doLogin();

                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.code() != 201) {
                            Log.e("RequestError", response.message());
                        } else {

                            UtilToken.setToken(LoginActivity.this, response.body().getToken());
                            Log.d("Testing", response.body().getToken());
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();

                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.e("onFailure", t.getMessage());
                    }
                });

            }
        });
    }
}

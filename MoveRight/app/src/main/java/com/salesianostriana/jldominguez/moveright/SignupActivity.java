package com.salesianostriana.jldominguez.moveright;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.salesianostriana.jldominguez.moveright.dto.UserDTO;
import com.salesianostriana.jldominguez.moveright.model.LoginResponse;
import com.salesianostriana.jldominguez.moveright.retrofit.UtilToken;
import com.salesianostriana.jldominguez.moveright.retrofit.generator.ServiceGenerator;
import com.salesianostriana.jldominguez.moveright.retrofit.services.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    EditText etEmail, etName, etPass;
    Button bLogin, bSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etEmail= findViewById(R.id.etSignEmail);
        etName = findViewById(R.id.etSignName);
        etPass = findViewById(R.id.etSignPass);
        bLogin = findViewById(R.id.bSignLogin);
        bSignup = findViewById(R.id.bSignSignup);

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        bSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserDTO dto = new UserDTO(etEmail.getText().toString(),etPass.getText().toString(), etName.getText().toString());
                UserService service = ServiceGenerator.createService(UserService.class);

                Call<LoginResponse> call = service.registerUser(dto);

                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.code() != 201) {
                            Log.e("RequestError", response.message());
                        } else {
                            UtilToken.setToken(SignupActivity.this, response.body().getToken());
                            startActivity(new Intent(SignupActivity.this, MainActivity.class));
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

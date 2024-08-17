package com.example.easychat;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easychat.model.UserModel;
import com.example.easychat.utils.AndroidUtil;
import com.example.easychat.utils.FirebaseUtil;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (FirebaseUtil.isLoggedIn() && getIntent().getExtras()!= null) {

            String userId = getIntent().getExtras().getString("userId");
            FirebaseUtil.allUserCollectionReference().document(userId).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            if(isConnected())
                                Toast.makeText(getApplicationContext(),"INTERNET CONNECTED",Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(getApplicationContext(),"NO INTERNET",Toast.LENGTH_SHORT).show();


                            UserModel model =task.getResult().toObject(UserModel.class);

                            Intent mainIntent=new Intent(this,MainActivity.class);
                            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(mainIntent);

                            Intent intent = new Intent(this,ChatActivity.class);
                            AndroidUtil.passUserModelAsIntent(intent, model);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }

                    });

        } else {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (FirebaseUtil.isLoggedIn()) {

                        if(isConnected())
                            Toast.makeText(getApplicationContext(),"INTERNET CONNECTED",Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(),"NO INTERNET",Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(SplashActivity.this, MainActivity.class));




                    } else {
                        if(isConnected())
                            Toast.makeText(getApplicationContext(),"INTERNET CONNECTED",Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(),"NO INTERNET",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SplashActivity.this, LoginPhoneNumberActivity.class));

                    }
                    finish();
                }
            }, 1000);

        }

    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo!=null){
            if(networkInfo.isConnected())
                return true;
            else
                return false;
        }else
            return false;
    }
}
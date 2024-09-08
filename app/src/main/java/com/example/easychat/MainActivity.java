package com.example.easychat;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easychat.utils.FirebaseUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

/** @noinspection ALL*/
public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ImageButton searchButton;


    ChatFragment chatFragment;
    ProfileFragment profileFragment;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chatFragment = new ChatFragment();
        profileFragment = new ProfileFragment();





        bottomNavigationView = findViewById(R.id.bottom_navigation);
        searchButton = findViewById(R.id.main_search_btn);

        searchButton.setOnClickListener((V)-> startActivity(new Intent(MainActivity.this,SearchUserActivity.class)));

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId()==R.id.menu_chat){
                if(isConnected())
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, chatFragment).commit();
                else
                    Toast.makeText(getApplicationContext(),"NO INTERNET",Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, chatFragment).commit();

            }


            if(item.getItemId()==R.id.menu_profile) {


                if(isConnected())
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, profileFragment).commit();
                else
                    Toast.makeText(getApplicationContext(),"NO INTERNET",Toast.LENGTH_SHORT).show();
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, profileFragment).commit();

            }
            return true;
        });
        bottomNavigationView.setSelectedItemId(R.id.menu_chat);

        getFCMToken();

    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo!=null){
            return networkInfo.isConnected();
        }else
            return false;
    }

    void getFCMToken(){
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                String token = task.getResult();
                FirebaseUtil.currentUserDetails().update("fcmToken",token);
            }
        });
    }

}

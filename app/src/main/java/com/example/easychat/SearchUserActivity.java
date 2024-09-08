package com.example.easychat;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easychat.model.UserModel;
import com.example.easychat.utils.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class SearchUserActivity extends AppCompatActivity {

    EditText searchInput;
    ImageButton searchButton;

    ImageButton backButton;
    RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        searchInput = findViewById(R.id.search_username_input);
        searchButton = findViewById(R.id.search_user_btn);
        backButton = findViewById(R.id.back_btn);
        recyclerView = findViewById(R.id.search_user_recycler_view);

        searchInput.requestFocus();

        backButton.setOnClickListener(v -> {
            onBackPressed();
        });

        searchButton.setOnClickListener(v -> {
            String searchTerm = searchInput.getText().toString();
            if (searchTerm.isEmpty() || searchTerm.length() < 3) {
                searchInput.setError("Invalid Username");
                return;
            }
            setupSearchRecyclerview(searchTerm);
        });
    }

    void setupSearchRecyclerview(String searchTerm) {

        Query query = FirebaseUtil.allUserCollectionReference()
                .whereGreaterThanOrEqualTo("username", searchTerm)
                .whereGreaterThanOrEqualTo("username", searchTerm)
                .whereLessThanOrEqualTo("username", searchTerm + '\uf8ff');


        FirestoreRecyclerOptions<UserModel> options = new FirestoreRecyclerOptions.Builder<UserModel>().setQuery(query, UserModel.class).build();



    }
}
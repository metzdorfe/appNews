package com.example.appnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.appnews.Listeners.OnFetchDataListener;
import com.example.appnews.Listeners.SelectListener;
import com.example.appnews.Models.APIStatus;
import com.example.appnews.Models.NewsApiResponse;
import com.example.appnews.Models.NewsHeadline;

import java.util.List;

public class NewsActivity extends AppCompatActivity implements SelectListener, View.OnClickListener {
    RecyclerView recyclerView;
    CustomAdapter customAdapter;
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7;
    ProgressDialog progressDialog;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias);

        searchView = findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressDialog.setTitle("Encontrando noticias de " + query);
                progressDialog.show();
                RequestManager manager = new RequestManager(NewsActivity.this);
                manager.getNewsHeadlines(listener, "general", query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Encontrando Noticias...");
        progressDialog.show();

        btn1 = findViewById(R.id.btn_1);
        btn1.setOnClickListener(this);
        btn2 = findViewById(R.id.btn_2);
        btn2.setOnClickListener(this);
        btn3 = findViewById(R.id.btn_3);
        btn3.setOnClickListener(this);
        btn4 = findViewById(R.id.btn_4);
        btn4.setOnClickListener(this);
        btn5 = findViewById(R.id.btn_5);
        btn5.setOnClickListener(this);
        btn6 = findViewById(R.id.btn_6);
        btn6.setOnClickListener(this);
        btn7 = findViewById(R.id.btn_7);
        btn7.setOnClickListener(this);


        RequestManager manager = new RequestManager(NewsActivity.this);
        manager.getNewsHeadlines(listener, "general", null);
    }

    private final OnFetchDataListener<NewsApiResponse> listener =new OnFetchDataListener<NewsApiResponse>() {

        @Override
        public void onFetchData(List<NewsHeadline> data,  String message) {
            showNews(data);
            progressDialog.dismiss();
            if (data.isEmpty()){
                Toast.makeText(NewsActivity.this, "Sem dados encontrados!", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onError(String message) {
            Toast.makeText(NewsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private void showNews(List<NewsHeadline> headlines) {
        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        customAdapter = new CustomAdapter(this, headlines, this);
        recyclerView.setAdapter(customAdapter);

    }

    @Override
    public void OnNewsClicked(NewsHeadline headline) {
        startActivity(new Intent(NewsActivity.this, DetailsActivity.class)
                .putExtra("data", headline));

    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String category = button.getText().toString();
        progressDialog.setTitle("Encontrando noticias de " + category);
        progressDialog.show();
        RequestManager manager = new RequestManager(NewsActivity.this);
        manager.getNewsHeadlines(listener, category, null);
    }
}
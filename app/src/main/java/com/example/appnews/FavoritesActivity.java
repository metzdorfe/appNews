package com.example.appnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.appnews.Database.NewsDAO;
import com.example.appnews.Models.NewsHeadline;

import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        recyclerView = findViewById(R.id.recycler_favorites);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        NewsDAO dao = new NewsDAO(this);
        List<NewsHeadline> favorites = dao.listAll();

        adapter = new CustomAdapter(this, favorites, headline -> {
        });

        recyclerView.setAdapter(adapter);
    }
}
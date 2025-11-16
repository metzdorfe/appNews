package com.example.appnews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appnews.Database.NewsDAO;
import com.example.appnews.Models.NewsHeadline;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailsActivity extends AppCompatActivity {

    NewsHeadline headline;
    TextView text_title, text_published, text_source, text_data;
    ImageView img_news;
    Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Recebendo a notícia
        headline = (NewsHeadline) getIntent().getSerializableExtra("data");

        text_data = findViewById(R.id.text_desc_full);
        text_published = findViewById(R.id.text_desc_published);
        text_source = findViewById(R.id.text_desc_source);
        text_title = findViewById(R.id.text_desc_title);
        img_news = findViewById(R.id.img_news);
        btnSave = findViewById(R.id.btn_save); // ⬅ precisa existir no XML

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date = null;

        try {
            date = dateFormat.parse(headline.getPublishedAt());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateStr = date != null ? formatter.format(date) : "Data indisponível";

        text_title.setText(headline.getTitle());
        text_source.setText(headline.getSource().getName());
        text_published.setText(dateStr);
        text_data.setText(headline.getContent());

        Picasso.get().load(headline.getUrlToImage()).into(img_news);

        btnSave.setOnClickListener(v -> {
            NewsDAO dao = new NewsDAO(DetailsActivity.this);
            dao.save(headline);

            Toast.makeText(DetailsActivity.this,
                    "Salvo para ler depois!",
                    Toast.LENGTH_SHORT).show();
        });
    }
}

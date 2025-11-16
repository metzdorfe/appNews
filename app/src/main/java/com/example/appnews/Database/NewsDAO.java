package com.example.appnews.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appnews.Models.NewsHeadline;

import java.util.ArrayList;
import java.util.List;

public class NewsDAO {

    private SQLiteDatabase db;

    public NewsDAO(Context context) {
        NewsDatabase helper = new NewsDatabase(context);
        db = helper.getWritableDatabase();
    }

    // Inserir notícia
    public void save(NewsHeadline headline) {
        ContentValues cv = new ContentValues();
        cv.put("title", headline.getTitle());
        cv.put("url", headline.getUrl());
        cv.put("urlToImage", headline.getUrlToImage());
        cv.put("description", headline.getDescription());
        cv.put("sourceName", headline.getSource().getName());
        cv.put("publishedAt", headline.getPublishedAt());

        db.insert(NewsDatabase.TABLE_FAVORITES, null, cv);
    }

    // Listar notícias salvas
    public List<NewsHeadline> listAll() {
        List<NewsHeadline> list = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + NewsDatabase.TABLE_FAVORITES, null);

        if (cursor.moveToFirst()) {
            do {
                NewsHeadline h = new NewsHeadline();
                h.setTitle(cursor.getString(cursor.getColumnIndexOrThrow("title")));
                h.setUrl(cursor.getString(cursor.getColumnIndexOrThrow("url")));
                h.setUrlToImage(cursor.getString(cursor.getColumnIndexOrThrow("urlToImage")));
                h.setDescription(cursor.getString(cursor.getColumnIndexOrThrow("description")));
                h.setPublishedAt(cursor.getString(cursor.getColumnIndexOrThrow("publishedAt")));

                h.setSource(new com.example.appnews.Models.Source(cursor.getString(cursor.getColumnIndexOrThrow("sourceName"))));

                list.add(h);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }

    // Deletar notícia específica
    public void deleteByTitle(String title) {
        db.delete(NewsDatabase.TABLE_FAVORITES, "title = ?", new String[]{title});
    }
}


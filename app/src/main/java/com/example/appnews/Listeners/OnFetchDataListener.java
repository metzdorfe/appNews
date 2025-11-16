package com.example.appnews.Listeners;

import com.example.appnews.Models.APIStatus;
import com.example.appnews.Models.NewsHeadline;

import java.util.List;

public interface OnFetchDataListener<T> {
    void onFetchData(List<NewsHeadline> data,  String message);
    void onError(String message);

}
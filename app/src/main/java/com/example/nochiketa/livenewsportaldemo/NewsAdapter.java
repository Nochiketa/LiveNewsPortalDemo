package com.example.nochiketa.livenewsportaldemo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Nochiketa on 10/27/2017.
 */

public class NewsAdapter extends BaseAdapter {

    Context context;
    ArrayList<NewsItem> newsList;

    public NewsAdapter(Context context, ArrayList<NewsItem> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
        {
            convertView = View.inflate(context, R.layout.news_list_item_layout, null);
        }

        NewsItem currentNews = newsList.get(position);
        ImageView iv1 = (ImageView) convertView.findViewById(R.id.imageview_1);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.textview_1);
        TextView tvDate = (TextView) convertView.findViewById(R.id.textview_2);
        TextView tvDescription = (TextView) convertView.findViewById(R.id.textview_3);

        Picasso.with(context).load(currentNews.imagepath).placeholder(R.drawable.placeholder).into(iv1);
        tvTitle.setText(currentNews.title);
        tvDate.setText(currentNews.date);
        tvDescription.setText(currentNews.description);

        return convertView;
    }
}

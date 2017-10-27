package com.example.nochiketa.livenewsportaldemo;

import android.content.Intent;
import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<NewsItem> newsItemList;
    ListView lvNews;
    NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsItemList  = new ArrayList<>();
        lvNews = (ListView)findViewById(R.id.listview_news);

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest("http://rss.nytimes.com/services/xml/rss/nyt/HomePage.xml", new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                //Toast.makeText(MainActivity.this, "Data saved Successfully" + response , Toast.LENGTH_LONG).show();
                Document doc = Jsoup.parse(response);
                Elements itemElements = doc.getElementsByTag("item");
                for(int i = 0 ;i<itemElements.size();i++)
                {
                    Element item = itemElements.get(i);
                    String title = removeCdata(item.child(0).text());
                    String pubDate = item.child(2).text();
                    String guid = item.child(3).text();
                    String description = item.child(4).text();
                    Document doc2 = Jsoup.parse(description);
                    //String imageLink = doc2.getElementsByTag("img").first().attr("src");
                   // String txt = doc2.getElementsByTag("p").text();

                    NewsItem news = new NewsItem();
                    news.title = title;
                    news.date = pubDate;
                   // news.link = guid;
                   // news.imagepath = imageLink;
                   // news.description = description;
                    newsItemList.add(news);

                    Log.i("mytag","Title :"+ title);
                    Log.i("mytag", "PubDate:" + pubDate);
                    Log.i("mytag", "Guid : " + guid);
                   // Log.i("mytag", "Description" + description);
                    //Log.i("mytag", "Image" + imageLink);


                   // Log.i("mytag","Link :" + link);
                }
                Log.i("mytag", "Items found : "+ itemElements.size());
                Log.i("mytag", "Items in news List : "+ newsItemList.size());

                adapter = new NewsAdapter(MainActivity.this, newsItemList);
                lvNews.setAdapter(adapter);
                lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        NewsItem currentNews = newsItemList.get(position);
                        Intent intent = new Intent(MainActivity.this, NewsDetailsActivity.class);
                        intent.putExtra("newsitem", currentNews);
                        startActivity(intent);
                    }
                });
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_LONG).show();
            }
        });
        Toast.makeText(MainActivity.this, "Request sent, Please wait", Toast.LENGTH_LONG).show();
        queue.add(request);
    }

    String removeCdata(String data)
    {
        data = data.replace("<![CDATA[", "");
        data = data.replace("]]>", "");
        return data;
    }
}

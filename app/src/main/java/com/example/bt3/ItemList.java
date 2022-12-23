package com.example.bt3;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.parsers.ParserConfigurationException;

public class ItemList extends AppCompatActivity {
    public RSSParser parser = new RSSParser();
    private ArrayList<RSSItem> listItems = new ArrayList<RSSItem>();
    mAdapter adapter;
    private ArrayAdapter<RSSItem> adapter2;
    Context context;
    ProgressDialog mProgressDialog;
    ArrayList<String> rssname = new ArrayList<>();
    private String url;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_list);
        ListView listView = (ListView) findViewById(R.id.listview_fl);
        mProgressDialog = ProgressDialog.show(this, "Please wait","Long operation starts...", true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    url = extras.getString("url");
                }
                try {
                    listItems = parser.getXmlFromUrl(url);
                    mProgressDialog.dismiss();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter2= new ArrayAdapter<RSSItem>(getApplicationContext(), android.R.layout.simple_list_item_1, listItems);
                        adapter = new mAdapter(getApplicationContext(),listItems);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                openDialog( adapter.getItem(i));
                            }
                        });

                    }
                });
            }

        }).start();
    }
    public void openDialog(RSSItem item){
        custom_dialog cdd = new custom_dialog(this,item);
        cdd.show();
    }

}

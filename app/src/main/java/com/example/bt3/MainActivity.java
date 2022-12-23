package com.example.bt3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LauncherActivity;
import android.content.Context;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity{
    Context context;
    private ArrayList<String> listItems = new ArrayList<String>(Arrays.asList("Protein","Amino Acid","Grain And Starches","Fibers And Legume","Vitamins","Minerals"));
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.listview);
        context = this;
        adapter=new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1,
                listItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                itemclick(i);
            }
        });
    }
   public void itemclick(int i){
        String url = "";
        switch (i){
            case 0:
                url = "https://www.petfoodindustry.com/rss/topic/292-proteins";
                break;
            case 1:
                url = "https://www.petfoodindustry.com/rss/topic/293-amino-acids";
                break;
            case 2:
                url = "https://www.petfoodindustry.com/rss/topic/294-grains-and-starches";
                break;
            case 3:
                url = "https://www.petfoodindustry.com/rss/topic/295-fibers-and-legumes";
                break;
            case 4:
                url = "https://www.petfoodindustry.com/rss/topic/296-vitamins";
                break;
            case 5:
                url = "https://www.petfoodindustry.com/rss/topic/297-minerals";
                break;
        }
       Intent intent = new Intent(this,ItemList.class);
       intent.putExtra("url",url);
       startActivity(intent);
   }

}
package com.example.bt3;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class custom_dialog extends Dialog implements
        android.view.View.OnClickListener {

    public String Des;
    public Activity c;
    public Dialog d;
    public Button yes, no;
    public RSSItem item;
    public custom_dialog(Activity a,RSSItem item1) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        item = item1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        TextView title = (TextView) findViewById(R.id.dialog_title);
        title.setText(item.getTitle());
        TextView description = (TextView) findViewById(R.id.dialog_description);
        Des = item.getDescription().replace("<p>","");
        Des = Des.replace("</p>","");
        description.setText(Des);
        TextView img_info = (TextView) findViewById(R.id.dialog_img_des);
        img_info.setText(item.getPubdate().substring(0,item.getPubdate().length()-5) + "\n" + item.getImg_type());
        ImageView imageView = findViewById(R.id.dialog_img);
        if(!item.img.isEmpty())
            Picasso.get().load(item.img).fit().into(imageView);
        else
            imageView.setImageResource(R.drawable.logo_23273);
        yes = (Button) findViewById(R.id.more_btn);
        no = (Button) findViewById(R.id.cancel_btn);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more_btn:
                Intent in = new Intent(c.getApplicationContext(), WebActivity.class);
                String page_url = item.link;
                in.putExtra("url", page_url);
                c.startActivity(in);
                break;
            case R.id.cancel_btn:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
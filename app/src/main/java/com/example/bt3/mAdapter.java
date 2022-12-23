package com.example.bt3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.List;
public class mAdapter extends BaseAdapter {
    private List<RSSItem> rssItemList = new ArrayList<RSSItem>();
    private final Context mcontext;
    private static final String TAG = "BT2Log";

    public mAdapter(Context context,ArrayList<RSSItem> arrayList) {
        rssItemList = arrayList;
        mcontext = context;
    }

    public void add(RSSItem note) {

        rssItemList.add(note);
        notifyDataSetChanged();

    }
    public void set(int i,RSSItem n){
        rssItemList.set(i,n);
    }

    // Clears the list adapter of all items.

    public void clear() {

        rssItemList.clear();
        notifyDataSetChanged();

    }
    public void remove(int i){
        rssItemList.remove(i);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return rssItemList.size();
    }

    @Override
    public RSSItem getItem(int i) {
        return rssItemList.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final RSSItem item = (RSSItem) getItem(i);
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        CardView itemLayout = (CardView) inflater.inflate(R.layout.item_title, null);
        final TextView titleView = itemLayout.findViewById(R.id.title_textview);
        titleView.setText(item.getTitle());
        return itemLayout;
    }
}

package com.example.sleyterangulo.marketpymes.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.sleyterangulo.marketpymes.R;
import com.example.sleyterangulo.marketpymes.model.ItemSlideMenu;
import java.util.List;

import static android.graphics.Color.*;
import static android.graphics.Color.RED;

/**
 * Created by sleyterangulo on 11/9/16.
 */

public class SlidingMenuAdapter extends BaseAdapter {
    private Context context;
    private List<ItemSlideMenu> lstItem;

    public SlidingMenuAdapter(Context context, List<ItemSlideMenu> lstItem) {
        this.context = context;
        this.lstItem = lstItem;
    }

    @Override
    public int getCount() {
        return lstItem.size();
    }

    @Override
    public Object getItem(int position) {
        return lstItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Aqui es donde pinto en pantalla el menú
        View v = View.inflate(context, R.layout.item_sliding_menu, null);
        ImageView img = (ImageView)v.findViewById(R.id.item_img);
        TextView tv = (TextView)v.findViewById(R.id.item_title);

        ItemSlideMenu item = lstItem.get(position);
        img.setImageResource(item.getImgId());
        tv.setText(item.getTitle());
        tv.setTextColor(Color.WHITE);


        return v;
    }
}

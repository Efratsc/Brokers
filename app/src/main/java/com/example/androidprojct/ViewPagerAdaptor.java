package com.example.androidprojct;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class ViewPagerAdaptor extends PagerAdapter {
    Context context;
    int images[]={
            R.drawable.image1,
            R.drawable.image1,
            R.drawable.image1,
            R.drawable.image1,
    };
    int headings[]={
            R.string.heading_one,
            R.string.heading_two,
            R.string.heading_three,
            R.string.heading_four,
    };
    int descriptions[]={
            R.string.description,
            R.string.description_two,
            R.string.description_three,
            R.string.description_four,
    };
    public ViewPagerAdaptor(Context context){
        this.context=context;
    }
    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==(LinearLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.slider_layout,container,false);
        ImageView slideimagetitle=(ImageView) view.findViewById(R.id.title_img);
        TextView slidetext=(TextView) view.findViewById(R.id.text_title);
        TextView text_discription=(TextView) view.findViewById(R.id.text_discription);

        slideimagetitle.setImageResource(images[position]);
        slidetext.setText(headings[position]);
        text_discription.setText(descriptions[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}


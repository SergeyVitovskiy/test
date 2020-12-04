package com.example.apicategory;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {

    List<Category> mCategoryList;
    Context mContext;

    public CategoryAdapter(List<Category> mCategoryList, Context mContext) {
        this.mCategoryList = mCategoryList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mCategoryList.size();
    }

    @Override
    public Object getItem(int i) {
        return mCategoryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mCategoryList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(mContext, R.layout.category_item, null);

        TextView id_text = v.findViewById(R.id.id_text);
        TextView title_text = v.findViewById(R.id.title_text);
        TextView image_text = v.findViewById(R.id.image_text);
        TextView displayed_text = v.findViewById(R.id.displayed_text);
        ImageView image = v.findViewById(R.id.image);

        Category category = mCategoryList.get(i);
        id_text.setText("id: " + category.getId());
        title_text.setText("title: " + category.getTitle());
        image_text.setText("image: " + category.getImage());
        displayed_text.setText("displayed: " + category.getDisplayed());

        Picasso.with(mContext).load("http://anndroidankas.h1n.ru/Image/ImageCategory/"
                + category.getImage()).into(image);


        return v;
    }
}

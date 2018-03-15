package com.example.anirban.kramah.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.anirban.kramah.superAdmin.ImageUploadInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.anirban.kramah.user.GridViewImageDisplay.list;

/**
 * Created by anirban on 18/2/18.
 */

public class ImageAdapter extends BaseAdapter
{
    private Context context;
    public ImageAdapter(Context c, List<ImageUploadInfo> list)
    {
        context = c;
    }

    //---returns the number of images---
    public int getCount() {
        return list.size();
    }

    //---returns the ID of an item---
    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    //---returns an ImageView view---
    @SuppressLint("ResourceType")
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(215, 215));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(2,5,2,5);

        } else {
            imageView = (ImageView) convertView;
        }
        Picasso.with(context).load(list.get(position).getImageURL()).into(imageView);
        return imageView;
    }
}
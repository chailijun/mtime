package com.chailijun.mtime.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.chailijun.mtime.R;
import com.chailijun.mtime.utils.Imager;

public class NetworkImageHolderView implements Holder<String> {

    private ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
//        imageView.setBackgroundResource(R.drawable.img_default_300x200);
        Imager.load(context,data,imageView,R.drawable.img_default_300x200);
    }
}

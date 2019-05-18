package br.com.achapet.Activity.Pet.Detalhe.SlideImage;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.facebook.drawee.view.SimpleDraweeView;

import java.net.URI;
import java.util.List;

import br.com.achapet.R;

public class SlideImagePagerAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<Uri> mResources;

    public SlideImagePagerAdapter(Context context, List resources) {
        mContext = context;
        mResources = resources;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mResources.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View slideImege = mLayoutInflater.inflate(R.layout.pet_detalhe_slide, container, false);

        Uri uri = mResources.get(position);
        SimpleDraweeView slideImegeView = (SimpleDraweeView) slideImege.findViewById(R.id.detalhe_slide_image);
        slideImegeView.setImageURI(uri);

        container.addView(slideImege);

        return slideImege;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }
}
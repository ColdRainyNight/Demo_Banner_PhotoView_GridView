package com.demo_banner_photoview_gridview.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo_banner_photoview_gridview.R;
import com.demo_banner_photoview_gridview.base.Data;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * 类描述：
 * 创建人：xuyaxi
 * 创建时间：2017/7/26 10:34
 */
public class MyAdapter extends BaseAdapter {

    private List<Data.DataBean.ComicsBean> list;
    private Context mContext;
    ImageLoader imageloader;
    DisplayImageOptions options;

    public MyAdapter(List<Data.DataBean.ComicsBean> list, Context context) {
        this.list = list;
        mContext = context;
        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(context);
        //将configuration配置到imageloader中
        imageloader = ImageLoader.getInstance();
        imageloader.init(configuration);

        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .showImageOnLoading(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .imageScaleType(ImageScaleType.EXACTLY)
                .build();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = View.inflate(mContext, R.layout.homitem, null);
            holder = new ViewHolder();
            holder.h_image = (ImageView) view.findViewById(R.id.h_image);
            holder.h_title = (TextView) view.findViewById(R.id.h_title);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Data.DataBean.ComicsBean bean = list.get(i);
        holder.h_title.setText(bean.getLabel_text());
        // 图片加载不出来给个默认的图片
        holder.h_image.setImageResource(R.mipmap.ic_launcher);
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(mContext);
        ImageLoader image = ImageLoader.getInstance();
        image.init(configuration);
        image.displayImage(bean.getCover_image_url(), holder.h_image);

        return view;
    }

    class ViewHolder {
        ImageView h_image;
        TextView h_title;
    }
}

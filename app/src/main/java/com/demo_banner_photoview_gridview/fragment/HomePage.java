package com.demo_banner_photoview_gridview.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.demo_banner_photoview_gridview.ImageActivity;
import com.demo_banner_photoview_gridview.R;
import com.demo_banner_photoview_gridview.adapter.MyAdapter;
import com.demo_banner_photoview_gridview.base.Data;
import com.demo_banner_photoview_gridview.utils.UrlBanner;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 * 创建人：xuyaxi
 * 创建时间：
 */
public class HomePage extends Fragment {

    private GridView gridview;
    private String url = "http://api.kkmh.com/v1/daily/comic_lists/0?since=0&gender=0&sa_event=eyJwcm9qZWN0Ijoia3VhaWthbl9hcHAiLCJ0aW1lIjoxNDg3NzQyMjQwNjE1LCJwcm9wZXJ0aWVzIjp7IkhvbWVwYWdlVGFiTmFtZSI6IueDremXqCIsIlZDb21tdW5pdHlUYWJOYW1lIjoi54Ot6ZeoIiwiJG9zX3ZlcnNpb24iOiI0LjQuMiIsIkdlbmRlclR5cGUiOiLlpbPniYgiLCJGcm9tSG9tZXBhZ2VUYWJOYW1lIjoi54Ot6ZeoIiwiJGxpYl92ZXJzaW9uIjoiMS42LjEzIiwiJG5ldHdvcmtfdHlwZSI6IldJRkkiLCIkd2lmaSI6dHJ1ZSwiJG1hbnVmYWN0dXJlciI6ImJpZ25veCIsIkZyb21Ib21lcGFnZVVwZGF0ZURhdGUiOjAsIiRzY3JlZW5faGVpZ2h0IjoxMjgwLCJIb21lcGFnZVVwZGF0ZURhdGUiOjAsIlByb3BlcnR5RXZlbnQiOiJSZWFkSG9tZVBhZ2UiLCJGaW5kVGFiTmFtZSI6IuaOqOiNkCIsImFidGVzdF9ncm91cCI6MTEsIiRzY3JlZW5fd2lkdGgiOjcyMCwiJG9zIjoiQW5kcm9pZCIsIlRyaWdnZXJQYWdlIjoiSG9tZVBhZ2UiLCIkY2FycmllciI6IkNoaW5hIE1vYmlsZSIsIiRtb2RlbCI6IlZQaG9uZSIsIiRhcHBfdmVyc2lvbiI6IjMuNi4yIn0sInR5cGUiOiJ0cmFjayIsImRpc3RpbmN0X2lkIjoiQTo2YWRkYzdhZTQ1MjUwMzY1Iiwib3JpZ2luYWxfaWQiOiJBOjZhZGRjN2FlNDUyNTAzNjUiLCJldmVudCI6IlJlYWRIb21lUGFnZSJ9";
    private List<Data.DataBean.ComicsBean> list = new ArrayList<>();
    private MyAdapter adapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String result = msg.obj.toString();
            Gson gson = new Gson();
            Data data = gson.fromJson(result, Data.class);
            list.addAll(data.getData().getComics());
            adapter.notifyDataSetChanged();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.homepage, null);
        gridview = (GridView) view.findViewById(R.id.gridview);
        initLoad();
        adapter = new MyAdapter(list, getActivity());
        gridview.setAdapter(adapter);

        //把图片传到下一个页面进行缩放
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(getContext(), ImageActivity.class);
                Data.DataBean.ComicsBean bean = list.get(i);
                it.putExtra("image", bean.getCover_image_url());
                startActivity(it);
            }
        });

        //进入屏道管理
//        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                List<String> lis = new ArrayList<String>();
//                for (Data.DataBean.ComicsBean bean : list) {
//                    lis.add(bean.getLabel_text());
//                }
//                List<ChannelBean> listbe = new ArrayList<ChannelBean>();
//                for (i = 0; i < lis.size(); i++) {
//                    ChannelBean b = new ChannelBean(lis.get(i), i < 10 ? true : false);
//                    listbe.add(b);
//                }
//                ChannelActivity.startChannelActivity((AppCompatActivity) getActivity(), listbe);
//            }
//        });
        return view;
    }

    private void initLoad() {
        new Thread() {
            @Override
            public void run() {
                String result = UrlBanner.getUrlConnect(url);
                Message msg = Message.obtain();
                msg.obj = result;
                handler.sendMessage(msg);
            }
        }.start();
    }
}

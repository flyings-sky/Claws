package wang.fly.com.claws.Fragments;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wang.fly.com.claws.R;
import wang.fly.com.claws.util.MyAdapter;
import wang.fly.com.claws.util.PullToRefreshGridView;
import wang.fly.com.claws.util.mImageButtonData;

/*
 * Created by 兆鹏 on 2016/9/9.
 */
public class MainFragment extends Fragment {
    private BluetoothAdapter bluetoothAdapter = null;
    private PullToRefreshGridView gridView;
    public static LinearLayout head;
    private List<Map<String,Object>> datas;
    private MyAdapter adapter;
    private String [] text = {"10","20","30","40","50","60"};
    private int [] image = {R.color.colorAccent,R.color.colorAccent,R.color.colorAccent,
            R.color.colorAccent,R.color.colorAccent,R.color.colorAccent};
    private mImageButtonData [] mImageButtonDatas = {
            new mImageButtonData("睡眠",R.mipmap.ic_launcher),
            new mImageButtonData("卡路里",R.mipmap.ic_launcher),
            new mImageButtonData("心情",R.mipmap.ic_launcher),
            new mImageButtonData("健康",R.mipmap.ic_launcher),
            new mImageButtonData("体重",R.mipmap.ic_launcher),
            new mImageButtonData("心率",R.mipmap.ic_launcher)};

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mfragment_layout,container,false);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter == null){
            Toast.makeText(getContext(),"此设备不支持蓝牙",Toast.LENGTH_LONG).show();
        }
        if(!bluetoothAdapter.isEnabled()){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("系统提示");
            builder.setMessage("检测到您没有开启蓝牙，请问是否允许打开蓝牙");
            builder.setNegativeButton("否", (dialog,which) -> bluetoothAdapter.disable());
            builder.setPositiveButton("是", (dialog, which) -> bluetoothAdapter.enable());
            AlertDialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);//设置点击屏幕Dialog不消失
            dialog.show();
        }
        initView(view);
        initEvent();
        return view;
    }

    private void initEvent() {
        final String [] from ={"mbt","image","text"};
        final int [] to = {R.id.mbt_grid_item_1,R.id.im_grid_item_1,R.id.tv_grid_item_1};
        adapter = new MyAdapter(getContext(), datas, R.layout.gridview_item_1, from, to);
        //配置适配器
        gridView.setAdapter(adapter);
        gridView.setonRefreshListener(() -> new AsyncTask<Void, Void, Void>() {
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                datas = null;
                datas = new ArrayList<>();
                int t = 0;
                for(int i = 0;i < text.length;i++){
                    if(Integer.parseInt(text[i]) <= 10){
                        text[i] = 50+"";
                        continue;
                    }
                    if(Integer.parseInt(text[i]) >= 90){
                        text[i] = 50 + "";
                        continue;
                    }
                    if((i%2 == 1)&&Integer.parseInt(text[i]) != 0){
                        try {
                            t = Integer.parseInt(text[i]);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        text[i] = t - 10 + "";
                    }else {
                        try {
                            t = Integer.parseInt(text[i]);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }

                        text[i] = t + 20 + "";
                    }
                }
                getData();
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                adapter.notifyDataSetChanged(datas);
                gridView.onRefreshComplete();
            }
        }.execute());
    }

    private void initView(View v){
        gridView = (PullToRefreshGridView) v.findViewById(R.id.appGrid);
        head = (LinearLayout) v.findViewById(R.id.head);
        FrameLayout.LayoutParams p = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        //获取PullToRefreshGridView里面的head布局
        head.addView(gridView.getView(), p);
        datas = new ArrayList<>();
        getData();
    }

    private void  getData() {
        for(int i=0;i<text.length;i++){
            Map<String, Object> map = new HashMap<>();
            map.put("mImageButtonDatas",mImageButtonDatas[i]);
            map.put("text", text[i]);
            Log.e("sss",text[i]);
            map.put("image",image[i]);
            datas.add(map);
        }
    }
}

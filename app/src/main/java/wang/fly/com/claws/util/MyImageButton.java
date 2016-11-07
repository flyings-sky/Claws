package wang.fly.com.claws.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import wang.fly.com.claws.R;


/*
 * 自定义控件:左边图片右边文字
 * Created by 兆鹏 on 2016/5/13.
 */
public class MyImageButton extends LinearLayout {
    private ImageView myim = null;
    private TextView mytv = null;
    public MyImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.myimbt,this,true);
        myim = (ImageView) findViewById(R.id.imbt_im);
        mytv = (TextView) findViewById(R.id.imbt_tv);
    }

    public void setPicture(int resid){
        myim.setImageResource(resid);
    }

    public void setText(String text){
        mytv.setText(text);
    }

    public void setTextSize(float size){
        mytv.setTextSize(size);
    }

    public void setTextColors(int color)
    {
        mytv.setTextColor(color);
    }
}

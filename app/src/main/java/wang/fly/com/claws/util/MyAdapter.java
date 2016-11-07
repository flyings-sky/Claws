package wang.fly.com.claws.util;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import wang.fly.com.claws.R;

/*
 * Created by 兆鹏 on 2016/9/9.
 */
public class MyAdapter extends BaseAdapter {
    public Context context;
    public List<Map<String,Object>> list;
    public int layout;
    public String[] from;
    public int[] to;

    public MyAdapter(Context context, List<Map<String, Object>> list, int layout, String[] from, int[] to) {
        this.context = context;
        this.list = list;
        this.layout = layout;
        this.from = from;
        this.to = to;
    }

    public void notifyDataSetChanged(List<Map<String,Object>> list) {
        super.notifyDataSetChanged();
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        if(convertView == null){//如果当前对象不存在，就创建一个
            view = LayoutInflater.from(context).inflate(
                    R.layout.gridview_item_1,null);
            holder = new ViewHolder();
            holder.text= (TextView) view.findViewById(R.id.tv_grid_item_1);
            holder.imageView = (ImageView) view.findViewById(R.id.im_grid_item_1);
            holder.myImageButton = (MyImageButton) view.findViewById(R.id.mbt_grid_item_1);
            // 将holder与view进行绑定
            view.setTag(holder);
        }else{
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        performAnimate(holder.imageView, 10, 4 * (Integer.parseInt(list.get(position).get("text").toString())));
        holder.imageView.setImageResource((Integer) list.get(position).get("image"));
        holder.text.setText(list.get(position).get("text").toString());
        Log.e("sss1",list.get(position).get("text").toString());
        holder.myImageButton.setText(((mImageButtonData)list.get(position).get("mImageButtonDatas")).getmBtText());
        holder.myImageButton.setPicture(((mImageButtonData)list.get(position).get("mImageButtonDatas")).getmBtImage());
        return view;
    }

    private void performAnimate(final View target, final int start, final int end) {
        Log.e("ani","111");
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            //持有一个IntEvaluator对象，方便下面估值的时候使用
            private IntEvaluator mEvaluator = new IntEvaluator();
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                //获得当前动画的进度值，整型，1-100之间
                int currentValue = (Integer) animator.getAnimatedValue();
                //计算当前进度占整个动画过程的比例，浮点型，0-1之间
                float fraction = currentValue / 100f;
                //直接调用整型估值器通过比例计算出宽度，然后再设给Button
                target.getLayoutParams().height = mEvaluator.evaluate(fraction, start, end);
                target.requestLayout();
            }
        });
        valueAnimator.setDuration(2000).start();
    }

    private static class ViewHolder {
        private MyImageButton myImageButton;
        private TextView text;
        private ImageView imageView;
    }
}

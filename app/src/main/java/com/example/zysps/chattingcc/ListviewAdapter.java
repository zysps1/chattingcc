package com.example.zysps.chattingcc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zysps on 2017/6/16 0016.
 */
public class ListviewAdapter extends BaseAdapter {
//自定义listview适配器
    private Context context;
    private List<MsgInfo> datas = new ArrayList<>();

    private ViewHolder viewHolder;

    public void addDataToAdapter(MsgInfo e) {
        datas.add(e);
    }

    public ListviewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return datas.size();
    }
//获取数据大小
    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//getview（）方法中的converview 参数，将之前加载好的布局进行缓存
        if (convertView == null) {
//判断是否为空，减少不必要的view的创建，然后加载数据
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item, null);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//设置数据
        String left = datas.get(position).getLeft_text();
        String right = datas.get(position).getRight_text();
//获取数据的左右
        if (left == null) {
            viewHolder.text_right.setText(right);
            viewHolder.right.setVisibility(View.VISIBLE);
            viewHolder.left.setVisibility(View.INVISIBLE);
        }
//如果是在右边，则显示右边的相关信息-visible，左边的隐藏-invisible
        if (right == null) {
            viewHolder.text_left.setText(left);
            viewHolder.left.setVisibility(View.VISIBLE);
            viewHolder.right.setVisibility(View.INVISIBLE);
        }
//如果是在左边，则显示左边的相关信息-visible，右边的隐藏-invisible
        return convertView;
    }

    public static class ViewHolder {
        public View rootView;
        public TextView text_left;
        public LinearLayout left;
        public TextView text_right;
        public LinearLayout right;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.text_left = (TextView) rootView.findViewById(R.id.text_left);
            this.left = (LinearLayout) rootView.findViewById(R.id.left);
            this.text_right = (TextView) rootView.findViewById(R.id.text_right);
            this.right = (LinearLayout) rootView.findViewById(R.id.right);
        }
//viewholder-一个临时的储存器，对控件的实例进行缓存
    }
}

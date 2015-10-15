package com.whl.hp.baidumusic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.whl.hp.baidumusic.bean.Channel;
import com.whl.hp.baidumusic.tool.ImageUtils;
import com.whl.hp.baidumusic.tool.NUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by hp-whl on 2015/9/20.
 */
public class ChanelAdapter extends BaseAdapter {
    private Context context;
    private List<Channel> list;

    public ChanelAdapter(Context context, List<Channel> list) {
        this.context = context;
        this.list = list;

    }

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
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.chanel_item, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.chanelIV);
            holder.textView = (TextView) convertView.findViewById(R.id.chanelTV);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(list.get(position).getName());
        holder.imageView.setImageResource(R.drawable.xiao);

        Bitmap b    =null;
        if (list.get(position).getThumb()!=null){

            b = ImageUtils.getImg(list.get(position).getThumb());
        }
        if (b!=null){
            holder.imageView.setImageBitmap(b);
        }else {
            NUtils.get(NUtils.TYPE_IMG, list.get(position).getThumb(), new NUtils.AbsCallback() {
                @Override
                public void response(String url, byte[] bytes) {
                    super.response(url, bytes);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    try {
                        ImageUtils.saveImg(url,bytes);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    holder.imageView.setImageBitmap(bitmap);
                }
            });
        }


        return convertView;
    }

    class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}

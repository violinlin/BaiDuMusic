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

import com.whl.hp.baidumusic.bean.SoungList;
import com.whl.hp.baidumusic.tool.ImageUtils;
import com.whl.hp.baidumusic.tool.NUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by hp-whl on 2015/9/20.
 */
public class MusicAdapter extends BaseAdapter {
    private Context context;
    private List<SoungList> list;

    public MusicAdapter(Context context, List<SoungList> list) {
        this.context = context;
        this.list = list;
    }

    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return Long.parseLong(list.get(position).getSongid());
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(list.get(position).getSongid());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.music_item, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.musicIV);
            holder.textView = (TextView) convertView.findViewById(R.id.musicTV);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        SoungList song = list.get(position);
        holder.textView.setText(song.getTitle() + "\n" + song.getArtist());
        holder.imageView.setImageResource(R.drawable.xiao);
        Bitmap b = null;
        if (song.getThumb() != null) {

            b = ImageUtils.getImg(song.getThumb());
        }

        if (b != null) {
            holder.imageView.setImageBitmap(b);
        } else {
            NUtils.get(NUtils.TYPE_IMG, song.getThumb(), new NUtils.AbsCallback() {
                @Override
                public void response(String url, byte[] bytes) {
                    super.response(url, bytes);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    try {
                        ImageUtils.saveImg(url, bytes);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    holder.imageView.setImageBitmap(bitmap);
                }
            });
        }
//        NUtils.get(NUtils.TYPE_IMG,song.getThumb(),new NUtils.AbsCallback(){
//            @Override
//            public void response(String url, byte[] bytes) {
//                super.response(url, bytes);
//                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//                holder.imageView.setImageBitmap(bitmap);
//            }
//        });
        return convertView;
    }

    class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}

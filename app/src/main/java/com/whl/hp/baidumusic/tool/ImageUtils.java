package com.whl.hp.baidumusic.tool;

/**
 * Created by hp-whl on 2015/9/20.
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class ImageUtils {
    public static final String CACHEDIR=Environment.getExternalStorageDirectory()+"/lznews/images";


    public static boolean isMounted(){
        return Environment.MEDIA_MOUNTED.equals(
                Environment.getExternalStorageState());
    }

    public static void saveImg(String url,byte[] bytes) throws IOException{
        if(!isMounted())  return;

        File dir=new File(CACHEDIR);
        if(!dir.exists()) dir.mkdirs();

        FileOutputStream fos=new FileOutputStream(new File(dir,getName(url)));
        fos.write(bytes);
        fos.close();

    }

    public static Bitmap getImg(String url){
        if(!isMounted())  return null;


        File imgFile=new File(CACHEDIR,getName(url));
        if(imgFile.exists()){
            return BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        }

        return null;
    }

    public static String getName(String url){
        return url.substring(url.lastIndexOf("/")+1);
    }

    public static String getName(String url,int end){
        return url.substring(url.lastIndexOf("/")+1,end);
    }


}
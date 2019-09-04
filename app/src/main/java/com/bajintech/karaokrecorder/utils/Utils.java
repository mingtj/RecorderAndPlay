package com.bajintech.karaokrecorder.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Created by mtj on 18/1/23.
 */
public class Utils {
   public static void copyMvtoData(Context context)
    {
        InputStream in = null;
        FileOutputStream out = null;
        String path = context.getApplicationContext().getFilesDir()
                .getAbsolutePath() + "/test44.wav"; // data/data目录
        System.out.println(path);
        File file = new File(path);
        if (!file.exists()) {
            try
            {
                in = context.getAssets().open("test44.wav"); // 从assets目录下复制
                out = new FileOutputStream(file);
                int length = -1;
                byte[] buf = new byte[1024];
                while ((length = in.read(buf)) != -1)
                {
                    out.write(buf, 0, length);
                }
                out.flush();
                System.out.println("file  test44.wav copy success");
            }
            catch (Exception e)
            {
            	System.out.println("file  test44.wav open fail the file is exist???");
                e.printStackTrace();
            }
            finally{
                if (in != null)
                {
                    try {

                        in.close();

                    } catch (IOException e1) {

                        // TODO Auto-generated catch block
                    	System.out.println("file  test44.wav open fail the file is exist???");
                        e1.printStackTrace();
                    }
                }
                if (out != null)
                {
                    try {
                        out.close();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                    	System.out.println("file  test44.wav open fail the file is exist???");
                        e1.printStackTrace();
                    }
                }
//                System.out.println("file  test44.wav copy success");
//                button.setText("拷贝完成");
//                button.setClickable(false);
//                copyMvtoData_1(context);
            }
        }else{
        	System.out.println("test44.wav file is exist");
        }
    }
   static void copyMvtoData_1(Context context)
   {
       InputStream in = null;
       FileOutputStream out = null;
       String path = context.getApplicationContext().getFilesDir()
               .getAbsolutePath() + "/micdata"; // data/data目录
       File file = new File(path);
       if (!file.exists()) {
           try
           {
               in = context.getAssets().open("micdata"); // 从assets目录下复制
               out = new FileOutputStream(file);
               int length = -1;
               byte[] buf = new byte[1024];
               while ((length = in.read(buf)) != -1)
               {
                   out.write(buf, 0, length);
               }
               out.flush();
           }
           catch (Exception e)
           {
               e.printStackTrace();
           }
           finally{
               if (in != null)
               {
                   try {

                       in.close();

                   } catch (IOException e1) {

                       // TODO Auto-generated catch block

                       e1.printStackTrace();
                   }
               }
               if (out != null)
               {
                   try {
                       out.close();
                   } catch (IOException e1) {
                       // TODO Auto-generated catch block
                       e1.printStackTrace();
                   }
               }
           }
       }
   }
    public static boolean fileIsExists(Context context){
        String path = context.getApplicationContext().getFilesDir()
                .getAbsolutePath() + "/micdata"; // data/data目录
        try{
            File f=new File(path);
            if(!f.exists()){
                return false;
            }

        }catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return true;
    }
    
    
    public static boolean audioFileIsExist(Context context){
    	String path = context.getApplicationContext().getFilesDir()
                .getAbsolutePath() + "/test44.wav"; // data/data目录
    	
    	  try{
              File f=new File(path);
              if(!f.exists()){
                  return false;
              }

          }catch (Exception e) {
              // TODO: handle exception
              return false;
          }
          return true;
    }
    
    public static String int2ip(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }
    public static String getWifiIpAddress(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int i = wifiInfo.getIpAddress();
            return int2ip(i);
        } catch (Exception ex) {
        }
        return null;
    }
}

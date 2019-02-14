package com.wuadam.awesomewebview.helpers;

/**
 * Created by wuzongheng on 2018/2/18.
 */

import android.os.AsyncTask;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * 图片下载的工具类
 */
public  class DownPicUtil {

    /**
     * Download the pic
     * @param url
     */
    public static void downPic(String url,DownFinishListener downFinishListener){
        // 获取存储卡的目录
        String filePath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filePath + File.separator + Environment.DIRECTORY_DOWNLOADS);
        if(!file.exists()){
            file.mkdirs();
        }

        loadPic(file.getPath(),url,downFinishListener);

    }

    private static void loadPic(final String filePath, final String url, final DownFinishListener downFinishListener) {
        new AsyncTask<Void,Void,String>(){
            String fileName;
            InputStream is;
            OutputStream out;

            @Override
            protected String doInBackground(Void... voids) {

                // 下载文件的名称
                String[] split = url.split("/");
                fileName = split[split.length - 1];
                // 创建目标文件,不是文件夹
                File picFile = new File(filePath + File.separator + fileName);
                if(! picFile.exists()) {
                    // if file exists, do not download again
                    try {
                        URL picUrl = new URL(url);
                        //通过图片的链接打开输入流
                        is = picUrl.openStream();
                        if (is == null) {
                            return null;
                        }
                        out = new FileOutputStream(picFile);
                        byte[] b = new byte[1024];
                        int end;
                        while ((end = is.read(b)) != -1) {
                            out.write(b, 0, end);
                        }

                        if (is != null) {
                            is.close();
                        }

                        if (out != null) {
                            out.close();
                        }

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        return null;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }

                String[] extensions = fileName.split("\\.");
                int length = extensions.length;
                if (length == 0 ||
                        !extensions[length - 1].equals("jpg") &&
                        !extensions[length - 1].equals("jpeg") &&
                        !extensions[length - 1].equals("jpe") &&
                        !extensions[length - 1].equals("jif") &&
                        !extensions[length - 1].equals("jfif") &&
                        !extensions[length - 1].equals("jfi") &&
                        !extensions[length - 1].equals("png") &&
                        !extensions[length - 1].equals("webp") &&
                        !extensions[length - 1].equals("gif")) {
                    // with no image extension
                    String extension = FormatHelper.getExtension(picFile);
                    if (extension == null) {
                        return picFile.getPath();
                    }
                    String newFileName = fileName + "." + extension;
                    File newFile = new File(filePath + File.separator + newFileName);
                    if (newFile.exists()) {
                        // if file of new name exists, use the existing file
                        return newFile.getPath();
                    }
                    if (rename(picFile, newFileName)) {
                        return newFile.getPath();
                    } else {
                        return null;
                    }
                }

                return picFile.getPath();
            }

            @Override
            protected void onPostExecute(String path) {
                super.onPostExecute(path);
                if(path!=null){
                    downFinishListener.onDownFinish(path);
                } else {
                    downFinishListener.onError();
                }
            }
        }.execute();
    }

    private static boolean rename(final File file, final String newName) {
        // file is null then return false
        if (file == null) return false;
        // file doesn't exist then return false
        if (!file.exists()) return false;
        // the new name is space then return false
        if (TextUtils.isEmpty(newName)) return false;
        // the new name equals old name then return true
        if (newName.equals(file.getName())) return true;
        File newFile = new File(file.getParent() + File.separator + newName);
        // the new name of file exists then return false
        return !newFile.exists()
                && file.renameTo(newFile);
    }


    //下载完成回调的接口
    public interface DownFinishListener{
        void onDownFinish(String path);
        void onError();
    }
}
package com.wuadam.awesomewebview.helpers;

/**
 * Created by wuzongheng on 2018/2/18.
 */

import android.os.AsyncTask;
import android.os.Environment;
import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 图片下载的工具类
 */
public  class DownPicUtil {

    /**
     * Download the pic
     * @param url
     */
    public static void downPic(String url, DownFinishListener downFinishListener){
        downPic(url, null, null, null, downFinishListener);
    }

    /**
     * Download the pic
     * @param url
     */
    public static void downPic(String url, String userAgent, String referer, String cookie, DownFinishListener downFinishListener){
        // 获取存储卡的目录
        String filePath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filePath + File.separator + Environment.DIRECTORY_DOWNLOADS);
        if(!file.exists()){
            file.mkdirs();
        }

        loadPic(file.getPath(), url, userAgent, referer, cookie, downFinishListener);

    }

    private static void loadPic(final String filePath, final String url, final String userAgent, final String referer, final String cookie, final DownFinishListener downFinishListener) {
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
                String now = String.valueOf(System.currentTimeMillis());
                File picFile = new File(filePath + File.separator + now);
                if(! picFile.exists()) {
                    // if file exists, do not download again
                    try {
                        Base64ImgHelper base64ImgHelper = new Base64ImgHelper(url);
                        if (base64ImgHelper.isBase64Img()) {
                            fileName = now;
                            byte[] image = base64ImgHelper.decode();
                            is = new ByteArrayInputStream(image);   //处理服务器的响应结果
                        } else {
                            URL picUrl = new URL(url);
                            //通过图片的链接打开输入流
                            HttpURLConnection httpURLConnection = (HttpURLConnection) picUrl.openConnection();
                            httpURLConnection.setConnectTimeout(10000);           //设置连接超时时间
                            httpURLConnection.setReadTimeout(30000);
                            httpURLConnection.setDoInput(true);                  //打开输入流，以便从服务器获取数据
                            httpURLConnection.setDoOutput(false);                 //Get请求不需要DoOutPut
                            httpURLConnection.setRequestMethod("GET");          //设置以Get方式请求数据
                            httpURLConnection.setUseCaches(false);               //不使用缓存
                            //设置请求体的类型是文本类型
                            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                            if (!TextUtils.isEmpty(userAgent)) {
                                httpURLConnection.setRequestProperty("User-Agent", userAgent);
                            }
                            if (!TextUtils.isEmpty(referer)) {
                                httpURLConnection.setRequestProperty("Referer", referer);
                            }
                            if (!TextUtils.isEmpty(cookie)) {
                                httpURLConnection.setRequestProperty("Cookie", cookie);
                            }
                            httpURLConnection.connect();

                            int response = httpURLConnection.getResponseCode();            //获得服务器的响应码
                            if (response == HttpURLConnection.HTTP_OK || response == HttpURLConnection.HTTP_NOT_MODIFIED) {
                                is = httpURLConnection.getInputStream();   //处理服务器的响应结果
                                if (is == null) {
                                    return null;
                                }
                            } else {
                                return null;
                            }
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

                // 提取无拓展名的文件名
                String extension = FormatHelper.getExtension(picFile);
                String[] extensions = fileName.split("\\.");
                int splitLength = extensions.length;
                String newFileNameNoExtension;
                if (splitLength > 1) {
                    newFileNameNoExtension = fileName.substring(0, fileName.length() - extensions[splitLength - 1].length() - 1);
                } else {
                    newFileNameNoExtension = fileName;
                }

                if (extension == null) {
                    // 不支持解析的格式，使用原拓展名
                    if (splitLength > 1) {
                        // 有拓展名，在原文件名基础上递增重命名
                        return renamePic(picFile, filePath, newFileNameNoExtension, extensions[splitLength - 1]);
                    } else {
                        // 无拓展名，整个文件名递增重命名
                        return renamePic(picFile, filePath, newFileNameNoExtension, null);
                    }
                }
                // 支持解析的格式
                return renamePic(picFile, filePath, newFileNameNoExtension, extension);
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

    private static String renamePic(File picFile, String filePath, String newFileNameNoExtension, String extension) {
        String extensionWithPoint = TextUtils.isEmpty(extension)? "": "." + extension;
        String newFileName = newFileNameNoExtension + extensionWithPoint;
        File newFile = new File(filePath + File.separator + newFileName);
        int count = 1;
        while (newFile.exists()) {
            // if file of new name exists, add (count) in filename;
            newFileName = newFileNameNoExtension + "(" + count + ")" + extensionWithPoint;
            newFile = new File(filePath + File.separator + newFileName);
            count ++;
        }
        if (rename(picFile, newFileName)) {
            return newFile.getPath();
        } else {
            return null;
        }
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
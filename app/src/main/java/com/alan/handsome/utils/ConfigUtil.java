package com.alan.handsome.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.yanzhenjie.permission.AndPermission;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 类说明：
 * 作者：qiujialiu
 * 时间：2018/11/26
 */

public class ConfigUtil {
    /**
     * 判断SDCard是否存在,并可写
     *
     * @return
     */
    public static boolean checkSDCard() {
        String  flag = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(flag);
    }

    /**
     * 获取缓存数据大小 包括数据目录和音乐目录
     */
    public static void getCacheFileSize(Context context, FileSizeComputeListener listener) {
        Observable.create(new ObservableOnSubscribe<Long[]>() {
            @Override
            public void subscribe(ObservableEmitter<Long[]> e) throws Exception {
                long size = 0;
                long sizeMusic = 0;
                File file2 = context.getCacheDir();
                File file1 = context.getFilesDir();
                File file5 = new File(context.getCacheDir(),"ACache");
                File file6 = new File(context.getFilesDir(), "ctb_music");
                File file8 = new File(context.getFilesDir(), "ctb_image");
                size = getFolderSize(file2) - getFolderSize(file5) + getFolderSize(file1) + getFolderSize(file8);
                sizeMusic = getFolderSize(file6);
                if (AndPermission.hasPermissions(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    File file3 = context.getExternalCacheDir();
                    File file4 = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
                    File file7 = context.getExternalFilesDir(Environment.DIRECTORY_DCIM);
                    size = size + getFolderSize(file3) + getFolderSize(file7);
                    sizeMusic = sizeMusic + getFolderSize(file4);
                }
                e.onNext(new Long[]{size,sizeMusic});
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long[]>() {
                    @Override
                    public void accept(Long[] longs) throws Exception {
                        if (listener != null) {
                            listener.onSuccess(longs[0],longs[1]);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (listener != null) {
                            listener.onFail(throwable.getMessage());
                        }
                        throwable.printStackTrace();
                    }
                });

    }

    /**
     * 删除文件
     * @param type 类型 1数据 2音乐
     * @param context
     * @param listener
     */
    public static void clearCache(int type,Context context,ClearCacheListener listener) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
//                File file2 = context.getCacheDir();
//                File file5 = new File(context.getCacheDir(),"ACache");
//                File file6 = new File(context.getFilesDir(), "ctb_music");
                if (type == 1) {
                    File file2 = context.getCacheDir();
                    File file5 = new File(context.getCacheDir(),"ACache");
                    File file = new File(context.getFilesDir(),"ctb_image");
                    deleteDir(file2,file5);
                    deleteDir(file);
                    if (AndPermission.hasPermissions(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        File file3 = context.getExternalCacheDir();
                        File file1 = context.getExternalFilesDir(Environment.DIRECTORY_DCIM);
                        deleteDir(file1);
                        deleteDir(file3);
                    }
                }else if (type == 2) {
                    File file6 = new File(context.getFilesDir(), "ctb_music");
                    deleteDir(file6);
                    if (AndPermission.hasPermissions(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        File file4 = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
                        deleteDir(file4);
                    }
                }
                e.onNext(null);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                if (listener != null) {
                    listener.onComplete();
                }
            }
        }, throwable -> {
            if (listener != null) {
                listener.onFail();
            }
        });

    }

    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

    @SuppressLint("NewApi")
    public static boolean isNotificationEnabled(Context context) {
        if (Build.VERSION.SDK_INT >= 19) {
            if(Build.VERSION.SDK_INT < 24) {
                AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
                ApplicationInfo appInfo = context.getApplicationInfo();
                String pkg = context.getApplicationContext().getPackageName();
                int uid = appInfo.uid;

                Class appOpsClass = null;
      /* Context.APP_OPS_MANAGER */
                try {
                    appOpsClass = Class.forName(AppOpsManager.class.getName());
                    Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                            String.class);
                    Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

                    int value = (Integer) opPostNotificationValue.get(Integer.class);
                    return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

                } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                return false;
            }else {
                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
                boolean areNotificationsEnabled = notificationManagerCompat.areNotificationsEnabled();
                return areNotificationsEnabled;
            }
        }else {
            return true;
        }
    }


    public static void openAppInfo(Context context) {
        Uri packageURI = Uri.parse("package:" + context.getPackageName());
        context.startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI));
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * 删除文件
     * @param dir 目标文件
     * @param ignore 忽略文件
     * @return
     */
    private static boolean deleteDir(File dir,File ... ignore) {
        List<File> list = ignore==null?new ArrayList<>(): Arrays.asList(ignore);
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                if (!list.contains(new File(dir,children[i]))) {
                    boolean success = deleteDir(new File(dir, children[i]));
                    if (!success) {
                        return false;
                    }
                }
            }
            return dir.delete();
        }else if (dir != null){
            if (list.contains(dir)) {
                return true;
            }else {
                return dir.delete();
            }
        }
        return true;
    }

    public static long getFolderSize(File file){
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return size;
    }

    /**
     * 格式化单位
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
//            return size + "Byte";
            return "0KB";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }


    /**
     * 复制单个文件
     *
     * @param oldPath$Name String 原文件路径+文件名 如：data/user/0/com.test/files/abc.txt
     * @param newPath$Name String 复制后路径+文件名 如：data/user/0/com.test/cache/abc.txt
     * @return <code>true</code> if and only if the file was copied;
     *         <code>false</code> otherwise
     */
    public static boolean copyFile(String oldPath$Name, String newPath$Name) {
        try {
            File oldFile = new File(oldPath$Name);
            if (!oldFile.exists()) {
                Log.e("--Method--", "copyFile:  oldFile not exist.");
                return false;
            } else if (!oldFile.isFile()) {
                Log.e("--Method--", "copyFile:  oldFile not file.");
                return false;
            } else if (!oldFile.canRead()) {
                Log.e("--Method--", "copyFile:  oldFile cannot read.");
                return false;
            }

            /* 如果不需要打log，可以使用下面的语句
            if (!oldFile.exists() || !oldFile.isFile() || !oldFile.canRead()) {
                return false;
            }
            */

            FileInputStream fileInputStream = new FileInputStream(oldPath$Name);
            FileOutputStream fileOutputStream = new FileOutputStream(newPath$Name);
            byte[] buffer = new byte[1024];
            int byteRead;
            while (-1 != (byteRead = fileInputStream.read(buffer))) {
                fileOutputStream.write(buffer, 0, byteRead);
            }
            fileInputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 复制文件夹及其中的文件
     *
     * @param oldPath String 原文件夹路径 如：data/user/0/com.test/files
     * @param newPath String 复制后的路径 如：data/user/0/com.test/cache
     * @return <code>true</code> if and only if the directory and files were copied;
     *         <code>false</code> otherwise
     */
    public static boolean copyFolder(String oldPath, String newPath) {
        try {
            File newFile = new File(newPath);
            if (!newFile.exists()) {
                if (!newFile.mkdirs()) {
                    Log.e("--Method--", "copyFolder: cannot create directory.");
                    return false;
                }
            }
            File oldFile = new File(oldPath);
            String[] files = oldFile.list();
            File temp;
            for (String file : files) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file);
                } else {
                    temp = new File(oldPath + File.separator + file);
                }

                if (temp.isDirectory()) {   //如果是子文件夹
                    copyFolder(oldPath + "/" + file, newPath + "/" + file);
                } else if (!temp.exists()) {
                    Log.e("--Method--", "copyFolder:  oldFile not exist.");
                    return false;
                } else if (!temp.isFile()) {
                    Log.e("--Method--", "copyFolder:  oldFile not file.");
                    return false;
                } else if (!temp.canRead()) {
                    Log.e("--Method--", "copyFolder:  oldFile cannot read.");
                    return false;
                } else {
                    FileInputStream fileInputStream = new FileInputStream(temp);
                    FileOutputStream fileOutputStream = new FileOutputStream(newPath + "/" + temp.getName());
                    byte[] buffer = new byte[1024];
                    int byteRead;
                    while ((byteRead = fileInputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, byteRead);
                    }
                    fileInputStream.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }

                /* 如果不需要打log，可以使用下面的语句
                if (temp.isDirectory()) {   //如果是子文件夹
                    copyFolder(oldPath + "/" + file, newPath + "/" + file);
                } else if (temp.exists() && temp.isFile() && temp.canRead()) {
                    FileInputStream fileInputStream = new FileInputStream(temp);
                    FileOutputStream fileOutputStream = new FileOutputStream(newPath + "/" + temp.getFilePath());
                    byte[] buffer = new byte[1024];
                    int byteRead;
                    while ((byteRead = fileInputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, byteRead);
                    }
                    fileInputStream.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
                 */
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public interface FileSizeComputeListener{
        void onFail(String message);
        void onSuccess(long size,long sizeMusic);
    }

    /**
     * 类说明：
     * 作者：qiujialiu
     * 时间：2018/9/7
     */

    public interface ClearCacheListener {
        void onComplete();
        void onFail();
    }
}

package com.fingerth.basislib.utils.storage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;

import androidx.core.os.EnvironmentCompat;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Administrator on 2018-11-03.
 */
public class StorageUtils {

    private static final String TAG = StorageUtils.class.getSimpleName();


    public static String getInnerCardPath(Context context) {
        return Objects.requireNonNull(context.getExternalFilesDir(null)).getAbsolutePath();
    }


    public static ArrayList<StorageBean> getStorageData(Context pContext) {
        final StorageManager storageManager = (StorageManager) pContext.getSystemService(Context.STORAGE_SERVICE);
        try {
            //得到StorageManager中的getVolumeList()方法的对象
            final Method getVolumeList = storageManager.getClass().getMethod("getVolumeList");
            //---------------------------------------------------------------------

            //得到StorageVolume类的对象
            final Class<?> storageValumeClazz = Class.forName("android.os.storage.StorageVolume");
            //---------------------------------------------------------------------
            //获得StorageVolume中的一些方法
            final Method getPath = storageValumeClazz.getMethod("getPath");
            Method isRemovable = storageValumeClazz.getMethod("isRemovable");

            Method mGetState = null;
            //getState 方法是在4.4_r1之后的版本加的，之前版本（含4.4_r1）没有
            // （http://grepcode.com/file/repository.grepcode.com/java/ext/com.google.android/android/4.4_r1/android/os/Environment.java/）
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                try {
                    mGetState = storageValumeClazz.getMethod("getState");
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
            //---------------------------------------------------------------------

            //调用getVolumeList方法，参数为：“谁”中调用这个方法
            final Object invokeVolumeList = getVolumeList.invoke(storageManager);
            //---------------------------------------------------------------------
            final int length = Array.getLength(invokeVolumeList);
            ArrayList<StorageBean> list = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                final Object storageValume = Array.get(invokeVolumeList, i);//得到StorageVolume对象
                final String path = (String) getPath.invoke(storageValume);
                final boolean removable = (Boolean) isRemovable.invoke(storageValume);
                String state = null;
                if (mGetState != null) {
                    state = (String) mGetState.invoke(storageValume);
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        state = Environment.getExternalStorageState(new File(path));
                    } else {
                        if (removable) {
                            state = EnvironmentCompat.getStorageState(new File(path));
                        } else {
                            //不能移除的存储介质，一直是mounted
                            state = Environment.MEDIA_MOUNTED;
                        }
                        //final File externalStorageDirectory = Environment.getExternalStorageDirectory();
                        //Log.e(TAG, "externalStorageDirectory==" + externalStorageDirectory);
                    }
                }
                long totalSize = 0;
                long availaleSize = 0;
                if (Environment.MEDIA_MOUNTED.equals(state)) {
                    totalSize = StorageUtils.getTotalSize(path);
                    availaleSize = StorageUtils.getAvailableSize(path);
                }
//                final String msg = "path==" + path
//                        + " ,removable==" + removable
//                        + ",state==" + state
//                        + ",total size==" + totalSize + "(" + StorageUtils.fmtSpace(totalSize) + ")"
//                        + ",availale size==" + availaleSize + "(" + StorageUtils.fmtSpace(availaleSize) + ")";
                //Log.e(TAG, msg);
                list.add(new StorageBean(path, state, removable, totalSize, availaleSize));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static long getTotalSize(String path) {
        try {
            final StatFs statFs = new StatFs(path);
            long blockSize = 0;
            long blockCountLong = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                blockSize = statFs.getBlockSizeLong();
                blockCountLong = statFs.getBlockCountLong();
            } else {
                blockSize = statFs.getBlockSize();
                blockCountLong = statFs.getBlockCount();
            }
            return blockSize * blockCountLong;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static long getAvailableSize(String path) {
        try {
            final StatFs statFs = new StatFs(path);
            long blockSize = 0;
            long availableBlocks = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                blockSize = statFs.getBlockSizeLong();
                availableBlocks = statFs.getAvailableBlocksLong();
            } else {
                blockSize = statFs.getBlockSize();
                availableBlocks = statFs.getAvailableBlocks();
            }
            return availableBlocks * blockSize;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static final double A_GB = 1073741824;
    public static final double A_MB = 1048576;
    public static final double A_KB = 1024;

    @SuppressLint("DefaultLocale")
    public static String fmtSpace(long space) {
        if (space <= 0) {
            return "0";
        }
        double gbValue = space / A_GB;
        if (gbValue >= 1) {
            return String.format("%.2fGB", gbValue);
        } else {
            double mbValue = space / A_MB;
            //Log.e("GB", "gbvalue=" + mbValue);
            if (mbValue >= 1) {
                return String.format("%.2fMB", mbValue);
            } else {
                double kbValue = space / A_KB;
                return String.format("%.2fKB", kbValue);
            }
        }
    }


    public static class StorageBean {
        private String path;
        public String mounted;
        public boolean removable;
        public long totalSize;
        public long availableSize;
        public boolean isChecked; //每条item的状态

        public String getSavePath() {
            String rootPath = path;
            if (removable) {
                rootPath = path + "/Android/data/com.jiankangbao/";
            }
            return rootPath;
        }

        public StorageBean(String path, String mounted, boolean removable, long totalSize, long availableSize) {
            this.path = path;
            this.mounted = mounted;
            this.removable = removable;
            this.totalSize = totalSize;
            this.availableSize = availableSize;
        }

        @Override
        public String toString() {
            return "StorageBean{" +
                    "path='" + path + '\'' +
                    ", mounted='" + mounted + '\'' +
                    ", removable=" + removable +
                    ", totalSize=" + totalSize +
                    ", availableSize=" + availableSize +
                    ", isChecked=" + isChecked +
                    '}';
        }
    }
}

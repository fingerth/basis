package com.fingerth.basis.utils;

import android.util.Log;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.nio.charset.Charset;

//import net.lingala.zip4j.ZipFile;
//import net.lingala.zip4j.exception.ZipException;

public class CompressOperate_zip4j {

    public static void unCompressZip4j(String zipFilePath, String filePath, String password) {
        File zipFile_ = new File(zipFilePath);
        File sourceFile = new File(filePath);
        Log.i("unCompressZip4j", "压缩文件 位置  = " + zipFile_.getAbsolutePath());

        ZipUtil.unpack(zipFile_, sourceFile);
//        try {
//            ZipFile zipFile = new ZipFile(zipFile_);
//            zipFile.setCharset(Charset.forName("GBK"));
//            //zipFile.setFileNameCharset("GBK"); //设置编码格式（支持中文）
//            if (!zipFile.isValidZipFile()) {   //检查输入的zip文件是否是有效的zip文件
//                Log.i("unCompressZip4j", "压缩文件不合法,可能被损坏.");
//                throw new ZipException("压缩文件不合法,可能被损坏.");
//            }
//            if (sourceFile.isDirectory() && !sourceFile.exists()) {
//                sourceFile.mkdir();
//            }
//            if (zipFile.isEncrypted()) {
//                zipFile.setPassword(password.toCharArray());
//            }
//            zipFile.extractAll(filePath); //解压
//            Log.i("unCompressZip4j", "uncompressZip4j: 解压成功");
//
//        } catch (ZipException e) {
//            Log.e("unCompressZip4j", "uncompressZip4j: 异常：" + e);
//        }
    }

}

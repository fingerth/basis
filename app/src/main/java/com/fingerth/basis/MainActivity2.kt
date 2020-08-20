package com.fingerth.basis

import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import com.fingerth.basis.utils.CompressOperate_zip4j

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        //val zipFilePath =  getExternalFilesDir(null)?.absolutePath + "/DoctorAideImg.zip"
//        val zipFilePath =  getExternalFilesDir(null)?.absolutePath + "/201404281143581132.zip"
//        val zipFilePath = Environment.getExternalStorageDirectory().absolutePath + "/DoctorAideImg.zip"
//        val filePath =  getExternalFilesDir(null)?.absolutePath// + "/DoctorAideImg"
        //String zipFilePath, String filePath, String password
//        CompressOperate_zip4j.unCompressZip4j(zipFilePath,filePath,"123")

    }
}
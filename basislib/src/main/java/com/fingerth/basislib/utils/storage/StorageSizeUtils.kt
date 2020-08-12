package com.fingerth.basislib.utils.storage

import android.content.Context
import android.os.Build
import android.os.Environment
import android.os.StatFs


class StorageSizeUtils {

    fun availableSize(con: Context, unit: UnitType): Double {
        var size = 0.0
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            val sf = StatFs(con.getExternalFilesDir(null)?.absolutePath)
            //val totalSize = sf.blockSizeLong * sf.blockCountLong
            val availableSize = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                sf.blockSizeLong * sf.availableBlocksLong
            } else {
                sf.blockSize * sf.availableBlocks.toLong()
            }
            size = when (unit) {
                UnitType.A_GB -> availableSize / StorageUtils.A_GB
                UnitType.A_MB -> availableSize / StorageUtils.A_MB
                UnitType.A_KB -> availableSize / StorageUtils.A_KB
            }
        }
        return size
    }


    fun fmtSpace(space: Long): String {
        if (space <= 0) {
            return "0"
        }
        val gbValue = space / StorageUtils.A_GB
        return if (gbValue >= 1) String.format("%.2fGB", gbValue) else {
            val mbValue = space / StorageUtils.A_MB
            if (mbValue >= 1) String.format("%.2fMB", mbValue) else {
                String.format("%.2fKB", space / StorageUtils.A_KB)
            }
        }
    }
}

enum class UnitType {
    A_GB, A_MB, A_KB
}



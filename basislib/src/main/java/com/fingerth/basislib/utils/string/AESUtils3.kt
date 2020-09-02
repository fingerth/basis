package com.fingerth.basislib.utils.string

import android.annotation.SuppressLint
import java.io.UnsupportedEncodingException
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec


object AESUtils3 {

//    val enStr = AESUtils3.encrypt(sb.toString(), "123456")
//    val decStr = AESUtils3.decrypt(enStr ?: "", "123456")
    private const val cipherMode = "AES/ECB/PKCS5Padding"

    /* 创建密钥 */
    private fun createKey(password: String): SecretKeySpec {
        var data: ByteArray? = null
        try {
            data = StringBuffer(32).apply {
                append(password)
                while (length < 32) {
                    append("0")
                }
                if (length > 32) {
                    setLength(32)
                }
            }.toString().toByteArray(Charsets.UTF_8)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return SecretKeySpec(data, "AES")
    }

    /* 加密字节数据 */
    @SuppressLint("GetInstance")
    fun encrypt(content: ByteArray?, password: String): ByteArray? {
        try {
            val key = createKey(password)
            return Cipher.getInstance(this.cipherMode).run {
                init(Cipher.ENCRYPT_MODE, key)
                doFinal(content)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /*加密(结果为16进制字符串)  */
    fun encrypt(content: String, password: String): String? {
        var data: ByteArray? = null
        try {
            data = content.toByteArray(Charsets.UTF_8)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        data = encrypt(data, password)
        return byte2hex(data ?: "".toByteArray())
    }

    /*解密字节数组*/
    @SuppressLint("GetInstance")
    fun decrypt(content: ByteArray?, password: String): ByteArray? {
        try {
            val key = createKey(password)
            return Cipher.getInstance(this.cipherMode).run {
                init(Cipher.DECRYPT_MODE, key)
                doFinal(content)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return null
    }


    /*解密16进制的字符串为字符串  */
    fun decrypt(content: String, password: String): String? {
        var data: ByteArray? = null
        try {
            data = hex2byte(content)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        data = decrypt(data, password)
        if (data == null) return null
        var result: String? = null
        try {
            result = String(data, Charsets.UTF_8)

        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return result
    }

    /*字节数组转成16进制字符串  */
    fun byte2hex(b: ByteArray): String? = StringBuffer(b.size * 2).run {
        var tmp = ""
        for (n in b.indices) {
            // 整数转成十六进制表示
            tmp = Integer.toHexString(b[n].toInt() and 0XFF)
            if (tmp.length == 1) append("0")
            append(tmp)
        }
        toString().toUpperCase(Locale.ROOT) // 转成大写
    }

    /*将hex字符串转换成字节数组 */
    private fun hex2byte(inputString: String?): ByteArray? {
        if (inputString.isNullOrBlank() || inputString.length < 2) {
            return ByteArray(0)
        }
        val iStr = inputString.toLowerCase(Locale.ROOT)
        val result = ByteArray(iStr.length / 2)
        for (i in result.indices) {
            val tmp = iStr.substring(2 * i, 2 * i + 2)
            result[i] = (tmp.toInt(16) and 0xFF).toByte()
        }
        return result
    }
}
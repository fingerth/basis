package com.fingerth.basislib

import android.annotation.SuppressLint
import android.app.Activity
import android.content.*
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.fingerth.basislib.common.adapter.CommonFragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

infix fun View.click(block: () -> Unit) = setOnClickListener { block() }
infix fun TabLayout.setup(vp: ViewPager) = setupWithViewPager(vp)
infix fun Window.alpha(a: Float) = run { this.attributes = this.attributes.apply { alpha = a } }
infix fun Log.log(str: String) = Log.v("fingerth", str)
fun String.showToast(context: Context, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(context, this, duration).show()

fun SharedPreferences.put(block: SharedPreferences.Editor.() -> Unit) {
    val editor = edit()
    editor.block()
    editor.apply()
}


//switch
fun dp2px(context: Context, dp: Float): Int =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
        .toInt()

fun px2dp(context: Context, pxValue: Float): Int =
    (pxValue / context.resources.displayMetrics.density + 0.5f).toInt()

//format
@SuppressLint("SimpleDateFormat")
fun formatData(date: Date): String = SimpleDateFormat("yyyy-MM-dd HH:mm").format(date)

@SuppressLint("SimpleDateFormat")//获取系统时间
fun getSystemTime(): String =
    SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(System.currentTimeMillis()))

//UI
inline fun <reified T : Activity> ktStartActivity(context: Context) {
    val intent = Intent(context, T::class.java)
    context.startActivity(intent)
}

inline fun <reified T : Activity> ktStartActivity(context: Context, block: Intent.() -> Unit) {
    val intent = Intent(context, T::class.java)
    intent.block()
    context.startActivity(intent)
}

fun startPhotoZoom(
    activity: Activity?,
    code: Int,
    uri: Uri?,
    outputX: Int = 400,
    outputY: Int = 400,
    tempFile: File
) {
    val intent = Intent("com.android.camera.action.CROP").apply {
        setDataAndType(uri, "image/*")
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        putExtra("crop", "true")
        // aspectX aspectY 是宽高的比例
        putExtra("aspectX", outputX)
        putExtra("aspectY", outputY)
        // outputX,outputY 是剪裁图片的宽高
        putExtra("outputX", outputX)
        putExtra("outputY", outputY)
        putExtra("scale", true) //是否保存比例
        putExtra("return-data", false) //剪裁是否返回bitmap
        putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile))
        putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
    }
    activity?.startActivityForResult(intent, code)
}

fun <T : Fragment> newFrag(frag: T, block: Bundle.() -> Unit): T {
    val bundle = Bundle()
    bundle.block()
    frag.arguments = bundle
    return frag
}

fun showFragment(
    tabLayout: TabLayout,
    viewPager: ViewPager,
    manager: FragmentManager,
    titles: List<String>,
    isCustomView: Boolean = false,
    mode: Int = TabLayout.MODE_FIXED,
    getTabView: (Int) -> View? = { null },
    changeTabSelect: (TabLayout.Tab?) -> Unit = {},
    addFragBlack: ArrayList<Fragment>.() -> Unit
) {
    val listFrag = ArrayList<Fragment>()
    listFrag.addFragBlack()
    viewPager.adapter = CommonFragmentPagerAdapter(manager, listFrag, titles.toTypedArray())
    tabLayout setup viewPager
    tabLayout.tabMode = mode
    viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(p0: Int) {}
        override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}
        override fun onPageSelected(pos: Int) {
            if (viewPager.offscreenPageLimit < pos + 1) {
                viewPager.offscreenPageLimit = pos + 1
            }
        }
    })
    if (isCustomView) {
        for (i in 0 until tabLayout.tabCount) {
            tabLayout.getTabAt(i)?.customView = getTabView(i)
        }
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) = changeTabSelect(tab)
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }
}


fun isMainThread(): Boolean {
    return Looper.getMainLooper().thread === Thread.currentThread()
}

fun copyText(context: Context, str: String?) {
    val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    //cm.text = str
    cm.setPrimaryClip(ClipData.newPlainText(null, str))
}

fun getDateAfter(d: Date, day: Int): Date = Calendar.getInstance().run {
    time = d
    set(Calendar.DATE, get(Calendar.DATE) + day)
    time
}

fun hideKeyboard(act: Activity) {
    val imm: InputMethodManager =
        act.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    // 隐藏软键盘
    imm.hideSoftInputFromWindow(act.window.decorView.windowToken, 0)
}


fun hideKeyboard(vararg et: EditText) {
    var focusEt = et[0]
    for (e in et) {
        if (e.isFocused) {
            focusEt = e
        }
    }
    val imm: InputMethodManager =
        focusEt.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    // 隐藏软键盘
    imm.hideSoftInputFromWindow(focusEt.windowToken, 0)
}


fun formatAddressStr(province: String?, city: String?): String {
    var address = ""
    if (!province.isNullOrBlank()) address = province
    if (!city.isNullOrBlank() && address != city) address += "-$city"

    return address
}


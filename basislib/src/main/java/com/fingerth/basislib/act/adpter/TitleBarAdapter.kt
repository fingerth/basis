//package com.fingerth.basislib.act.adpter
//
//import android.app.Activity
//import android.os.Build
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import com.fingerth.basislib.R
//import com.fingerth.basislib.act.LoadingHelper
//
///**
// * ======================================================
// * Created by admin fingerth on 2020/07/21.
// * <p/>
// */
//class TitleBarAdapter(private val title: String, private val right1: String, private val right2: String, private val block2: () -> Unit = {}, private val block1: () -> Unit = {}) : LoadingHelper.Adapter<TitleBarAdapter.ViewHolder>() {
//
//    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder = ViewHolder(inflater.inflate(
//        R.layout.public_activity_bar, parent, false))
//
//    override fun onBindViewHolder(holder: ViewHolder) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            holder.activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//        }
//        holder.barBack click { holder.activity.finish() }
//        holder.titleTv.text = title
//
//        if (right1.isBlank() && right2.isBlank()) {
//            holder.rightTv.visibility = View.GONE
//            holder.rightTipTv.visibility = View.GONE
//        } else {
//            if (!right1.isBlank()) {
//                holder.rightTv.text = right1
//                holder.rightTv click block1
//            }
//            if (!right2.isBlank()) {
//                holder.rightTv2.visibility = View.VISIBLE
//                holder.rightTipTv2.visibility = View.GONE
//                holder.rightTv2.text = right2
//                holder.rightTv2 click block2
//            } else {
//                holder.rightTv2.visibility = View.GONE
//                holder.rightTipTv2.visibility = View.GONE
//            }
//        }
//
//    }
//
//
//    class ViewHolder(v: View) : LoadingHelper.ViewHolder(v) {
//        val activity: Activity
//            get() = rootView.context as Activity
//
//        val barBack: View = rootView.findViewById(R.id.bar_back)
//        val titleTv: TextView = rootView.findViewById(R.id.bar_title)
//        val rightTv: TextView = rootView.findViewById(R.id.bar_right_1)
//        val rightTv2: TextView = rootView.findViewById(R.id.bar_right_2)
//        val rightTipTv: TextView = rootView.findViewById(R.id.bar_right_tip_1)
//        val rightTipTv2: TextView = rootView.findViewById(R.id.bar_right_tip_2)
//
//    }
//}
package zhuangzhi.android.baking.util

import android.annotation.SuppressLint
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

@SuppressLint("CommitTransaction")
fun addFragmentToActivity(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        frameId: Int) {
    fragmentManager.beginTransaction().run {
        add(frameId, fragment)
        commit()
    }
}

@SuppressLint("CommitTransaction")
fun replaceFragmentToActivity(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        frameId: Int) {
    fragmentManager.beginTransaction().run {
        replace(frameId, fragment)
        commit()
    }
}
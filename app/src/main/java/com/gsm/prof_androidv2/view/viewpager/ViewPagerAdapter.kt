package com.gsm.prof_androidv2.view.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gsm.prof_androidv2.view.AllPostFragment
import com.gsm.prof_androidv2.view.ErrorFragment
import com.gsm.prof_androidv2.view.MyPostFragment

class ViewPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa){
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> AllPostFragment()
            1 -> MyPostFragment()
            else -> ErrorFragment()
        }
    }
    override fun getItemCount():Int = 2
}
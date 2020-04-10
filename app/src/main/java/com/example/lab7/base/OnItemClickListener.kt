package com.example.lab7.base

import android.view.View
import android.widget.CheckBox

interface OnItemClickListener {
    fun onItemClick(position: Int, view: View)
    fun onHeartClick(position: Int, savedJobs: CheckBox)
}
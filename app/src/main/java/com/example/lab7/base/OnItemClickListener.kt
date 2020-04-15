package com.example.lab7.base

import android.view.View

interface OnItemClickListener {
    fun onItemClick(position: Int, view: View)
    fun onHeartClick(position: Int, view: View)
}
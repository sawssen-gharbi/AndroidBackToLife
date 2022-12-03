package com.example.backtolife

import android.content.Context
import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView



abstract class SwipeToDeleteCallBack(context: Context) : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }


}




package com.dnod.tasksmanajinnee.ui.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import com.paginate.Paginate

class PaginateRecycleView : RecyclerView {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    fun setPaginateListener(listener: Paginate.Callbacks) {
        Paginate.with(this, listener)
                .addLoadingListItem(false).build()
    }
}

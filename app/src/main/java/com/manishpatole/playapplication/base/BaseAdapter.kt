package com.manishpatole.playapplication.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, VH : BaseItemViewHolder<T, out BaseItemViewModel<T>>>(
    parentLifecycle: Lifecycle,
    protected val dataList: ArrayList<T>
) : RecyclerView.Adapter<VH>() {

    init {
        parentLifecycle.addObserver(object : LifecycleObserver {

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onParentDestroy() {
                recyclerView?.run {
                    for (i in 0 until childCount) {
                        getChildAt(i)?.let {
                            (getChildViewHolder(it) as BaseItemViewHolder<*, *>).run {
                                onDestroy()
                                viewModel.onManualClear()
                            }
                        }
                    }
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            fun onParentStop() {
                recyclerView?.run {
                    for (i in 0 until childCount) {
                        getChildAt(i)?.let {
                            (getChildViewHolder(it) as BaseItemViewHolder<*, *>).onStop()
                        }
                    }
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            fun onParentStart() {
                recyclerView?.run {
                    if (layoutManager is LinearLayoutManager) {
                        val layoutManager = layoutManager as LinearLayoutManager
                        val first = layoutManager.findFirstVisibleItemPosition()
                        val last = layoutManager.findLastVisibleItemPosition()

                        if (first in 0..last) {
                            for (i in first..last) {
                                findViewHolderForAdapterPosition(i)?.let {
                                    (it as BaseItemViewHolder<*, *>).onStart()
                                }
                            }
                        }
                    }

                }
            }

        })
    }

    private var recyclerView: RecyclerView? = null

    override fun onViewAttachedToWindow(holder: VH) {
        super.onViewAttachedToWindow(holder)
        holder.onStart()
    }

    override fun onViewDetachedFromWindow(holder: VH) {
        super.onViewDetachedFromWindow(holder)
        holder.onStop()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.recyclerView = null
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(dataList[position])
    }

    fun appendData(dataList: ArrayList<T>) {
        val oldCount = itemCount
        this.dataList.addAll(dataList)
        val newCount = itemCount

        if (oldCount == 0 && newCount > 0) {
            notifyDataSetChanged()
        } else if (oldCount in 1 until newCount) {
            notifyItemRangeChanged(oldCount - 1, newCount - oldCount)
        }
    }
}
package com.kcode.gankotlin.ui.fragment

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kcode.gankotlin.R
import com.kcode.gankotlin.repository.Article
import com.kcode.gankotlin.utils.toast
import kotlinx.android.synthetic.main.fragment_base.*

/**
 * Created by caik on 2017/5/31.
 */
class WelfareFragment : BaseFragment(){

    var adapter: GirlAdapter? = null

    override fun initRecyclerView() {
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
        adapter = GirlAdapter(activity!!.applicationContext,R.layout.item_girl)
        recyclerView.adapter = adapter

        adapter!!.setOnLoadMoreListener({
            pageNumber++
            isRefresh = false
            loadData(pageSize,pageNumber)
        },recyclerView)
    }

    override fun loadError() {
        if (activity == null) {
            activity!!.toast(R.string.load_failed)
        }
    }

    override fun loadSuccess(data: List<Article>) {
        setUp(data)
    }

    private fun setUp(data: List<Article>) {
        if (isRefresh) {
            adapter!!.setNewData(data)
        }else{
            adapter!!.addData(data)
        }
    }

    override fun loadFinish() {
        if (swipeLayout!!.isRefreshing) {
            swipeLayout!!.isRefreshing = false
        }

        adapter!!.loadMoreComplete()
    }

    override fun getType(): String {
        return "福利"
    }

    class GirlAdapter(var context: Context,layoutId:Int): BaseQuickAdapter<Article, BaseViewHolder>(layoutId){

        override fun convert(viewHolder: BaseViewHolder?, article: Article?) {
            val imageView = viewHolder!!.getView<ImageView>(R.id.image)
            Glide.with(context).load(article!!.url).into(imageView)
        }

    }

}
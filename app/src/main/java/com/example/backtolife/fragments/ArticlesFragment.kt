package com.example.backtolife.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.backtolife.R
import com.example.backtolife.viewModels.ArticleViewModel
import dagger.hilt.android.AndroidEntryPoint

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.backtolife.Adapter.ArticlesAdapter
import com.example.backtolife.utils.HelperFunctions.toastMsg
import com.example.backtolife.utils.Status

@AndroidEntryPoint
class ArticlesFragment: Fragment(R.layout.fragment_articles) {

    private val articlesVM: ArticleViewModel by viewModels()

    private lateinit var articlesRV: RecyclerView

    private lateinit var swipeContainer: SwipeRefreshLayout

    private lateinit var noNetLayout: ConstraintLayout

    private lateinit var articlesLm: LinearLayoutManager
    private lateinit var articlesAdapter: ArticlesAdapter

    private lateinit var scrollListener: RecyclerView.OnScrollListener

    private var pageNumber: Int = 1
    private var pagedItemsCount: Int = 0
    private var totalPages: Int = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        articlesRV = view.findViewById(R.id.articlesRV)
        swipeContainer = view.findViewById(R.id.swipeContainer)
        noNetLayout = view.findViewById(R.id.noNetLayout)

        articlesAdapter = ArticlesAdapter(mutableListOf())
        articlesLm = LinearLayoutManager(view.context)

        articlesRV.apply {
            adapter = articlesAdapter
            layoutManager = articlesLm
        }

        loadData(pageNumber)

        swipeContainer.setOnRefreshListener {
            articlesAdapter.clear()
            loadData(pageNumber)
            articlesRV.refreshDrawableState()
        }

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(
            R.color.purple_500,
            R.color.purple_700,
        )

        setRecyclerViewScrollListener()
    }

    private fun setRecyclerViewScrollListener() {

        scrollListener = object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager

                if (
                    linearLayoutManager.findLastCompletelyVisibleItemPosition() == recyclerView.layoutManager?.itemCount?.minus(
                        1
                    )
                    &&
                    pageNumber < totalPages
                ) {
                    pageNumber++
                    loadData(pageNumber, false)
                }
            }
        }

        articlesRV.addOnScrollListener(scrollListener)
    }

    private fun loadData(page: Int, initialRun: Boolean? = true) {
        articlesVM.loadRssArticles(page).observe(requireActivity()) {
            it?.let { rs ->
                when (rs.status) {
                    Status.SUCCESS -> {
                        rs.data?.apply {
                            pagedItemsCount = this.pageSize
                            pageNumber = this.page
                            totalPages = this.pages
                            if (initialRun == true) swipeContainer.isRefreshing = false
                            articlesAdapter.addAll(this.articles)
                        }
                    }
                    Status.LOADING -> {
                        if (initialRun == true)
                            swipeContainer.isRefreshing = true

                    }
                    Status.ERROR -> {
                        if (initialRun == true) swipeContainer.isRefreshing = false
                        toastMsg(view?.context!!, it.message!!)
                    }
                }
            }
        }
    }
}
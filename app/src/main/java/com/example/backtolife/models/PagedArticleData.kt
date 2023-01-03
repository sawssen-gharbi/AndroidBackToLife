package com.example.backtolife.models

data class PagedArticlesData(
    val page: Int,
    val pageSize: Int,
    val total: Int,
    val pages: Int,
    val articles: MutableList<Article>
)

package com.example.backtolife.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.backtolife.R
import com.example.backtolife.models.Article
import com.example.backtolife.utils.HelperFunctions

class ArticlesAdapter constructor(val data: MutableList<Article>) :
    ClassicAdapter<ArticlesAdapter.ArticleViewHolder, Article>(data) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder =
        ArticleViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_article, parent, false)
        )

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {

        val article = data[position]

        holder.apply {
            articleTitle!!.text = article.title
            articleDescription!!.text = article.content

   //HelperFunctions.usePicasso(article.image!!,
               //R.drawable.ic_baseline_downloading_24, articleImage!!)

            itemView.setOnClickListener {

                HelperFunctions.launchURL(
                    itemView.context,
                    data[position].link!!
                )

            }
        }
    }

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var articleTitle: TextView? = null
        var articleDescription: TextView? = null
        var articleImage: ImageView? = null

        init {
            articleTitle = itemView.findViewById(R.id.artTitle)
            articleDescription = itemView.findViewById(R.id.artDescription)
            articleImage = itemView.findViewById(R.id.artImage)
        }
    }
}
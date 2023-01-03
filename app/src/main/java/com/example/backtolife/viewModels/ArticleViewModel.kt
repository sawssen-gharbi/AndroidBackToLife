package com.example.backtolife.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.liveData
import com.example.backtolife.API.UserApi
import com.example.backtolife.utils.GenericResp
import com.example.backtolife.utils.Resource
import com.example.backtolife.utils.ResponseConverter
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(): ViewModel() {

    fun loadRssArticles(pageNumber: Int) =
        liveData(Dispatchers.IO) {

            val client = UserApi.create()

            emit(Resource.loading(data = null))
            try {
                val response = client.downloadArticles(pageNumber)
                if (response.isSuccessful)
                    emit(
                        Resource.success(
                            data = response.body()
                        )
                    )
                else
                    emit(
                        Resource.error(
                            data = null,
                            message = ResponseConverter.convert<GenericResp>(
                                response.errorBody()!!.string()
                            ).data?.error!!
                        )
                    )
            } catch (ex: Exception) {
                emit(
                    Resource.error(
                        data = null,
                        message = "Unable to retrieve articles at the moment, please try again later."
                    )
                )
            }
        }

}
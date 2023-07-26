package com.moon.mytools.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import com.moon.common.ui.component.MBaseFragment
import com.moon.mytools.R
import com.moon.mytools.test.BannerData
import com.moon.mytools.test.HotkeyData
import com.moon.mytools.utils.DoubleClickListener
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Authenticator
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.io.IOException

/**
 * Date: 9/29/21 5:27 PM
 * Author: Moon
 * Desc: 首页
 */
class HomePageFragment : MBaseFragment() {

    private val client by lazy {
        OkHttpClient.Builder()
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.wanandroid.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mLayoutView.findViewById<View>(R.id.btn_top_tab)
            .setOnClickListener(object : DoubleClickListener() {
                override fun onClicked(v: View?) {
//                    ARouter.getInstance().build("/profile/detail").navigation(context)
                    loadDataByRetrofit()
                    loadDataByOkHttp()
                }
            })
    }


    private fun loadDataByOkHttp() {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor {
                Log.d("OKHTTP", it)
            })
            .authenticator(object :Authenticator{
                override fun authenticate(route: Route?, response: okhttp3.Response): Request? {
                    return null
                }
            })
            .proxyAuthenticator(object : Authenticator{
                override fun authenticate(route: Route?, response: okhttp3.Response): Request? {
                    return null
                }
            })
            .build()

        val request = Request.Builder()
            .url("https://api.github.com/users/rengwuxian/repos")
            .get()
            .build()

        okHttpClient.newCall(request)
            .enqueue(object : okhttp3.Callback {
                override fun onFailure(call: okhttp3.Call, e: IOException) {

                }

                override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {

                }
            })
    }

    fun loadDataByRetrofit() {

        val wanAndroidApi = retrofit.create(WanAndroidApi::class.java)

        val articleDataCall = wanAndroidApi.getHomeArticles(1)
        articleDataCall.enqueue(object : Callback<ArticleData> {
            override fun onResponse(call: Call<ArticleData>, response: Response<ArticleData>) {
                response.body()?.datas?.get(0)?.let { println(it.id) }
            }

            override fun onFailure(call: Call<ArticleData>, t: Throwable) {
            }
        })

        wanAndroidApi.getBanners()
            .subscribeOn(Schedulers.newThread())
            .subscribe { response ->
                response.body()?.data?.get(0)?.let { println(it.id) }
            }

        GlobalScope.launch {
            val hotkeyData = wanAndroidApi.getHotkeys()
            println(hotkeyData.data[0].id)
        }
    }

    interface WanAndroidApi {

        @GET("article/list/{page}/json")
        fun getHomeArticles(@Path("page") page: Int): Call<ArticleData>

        @GET("banner/json")
        fun getBanners(): Observable<Response<BannerData>>

        @GET("hotkey/json")
        suspend fun getHotkeys(): HotkeyData
    }

    data class ArticleData(
        val curPage: Int = 0,
        val datas: MutableList<ArticleBean> = mutableListOf(),
        val offset: Int = 0,
        val over: Boolean = false,
        val pageCount: Int = 0,
        val size: Int = 0,
        val total: Int = 0
    )

    data class ArticleBean(
        val apkLink: String,
        val audit: Int,
        val author: String,
        val canEdit: Boolean,
        val chapterId: Int,
        val chapterName: String,
        var collect: Boolean,
        val courseId: Int,
        val desc: String,
        val descMd: String,
        val envelopePic: String,
        val fresh: Boolean,
        val id: Int,
        val link: String,
        val niceDate: String,
        val niceShareDate: String,
        val origin: String,
        val prefix: String,
        val projectLink: String,
        val publishTime: Long,
        val selfVisible: Int,
        val shareDate: Long,
        val shareUser: String,
        val superChapterId: Int,
        val superChapterName: String,
        val tags: List<Tag>,
        val title: String,
        val type: Int,
        val userId: Int,
        val visible: Int,
        val zan: Int
    )

    data class Tag(
        val name: String = "",
        val url: String = ""
    )
}


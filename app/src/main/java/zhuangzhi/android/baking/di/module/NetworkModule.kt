package zhuangzhi.android.baking.di.module

import android.app.Application
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import zhuangzhi.android.baking.BuildConfig
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
open class NetworkModule {

    @Provides
    @Singleton
    open fun providesOkHttpCache(application: Application): Cache {
        val cacheSize: Long = 10*1024*1024
        return Cache(application.cacheDir, cacheSize)
    }

    @Provides
    @Singleton
    open fun providesOkHttpClient(cache: Cache): OkHttpClient {
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
        builder.readTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
        builder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)

        // Network debugging
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logging)
        }
        builder.cache(cache)
        return builder.build()
    }

    companion object {
        const val CONNECTION_TIMEOUT: Long = 60*1000
    }

}
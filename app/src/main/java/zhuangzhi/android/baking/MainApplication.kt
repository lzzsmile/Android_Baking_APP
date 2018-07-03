package zhuangzhi.android.baking

import android.app.Application
import zhuangzhi.android.baking.di.component.AppComponent
import zhuangzhi.android.baking.di.component.DaggerAppComponent
import zhuangzhi.android.baking.di.module.AppModule


class MainApplication: Application() {
    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}
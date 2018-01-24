package vj.com.myapplication

import android.app.Application
import io.realm.Realm

/**
 *
 * Created by VJ on 1/23/2018.
 */

class AppController : Application() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
    }
}

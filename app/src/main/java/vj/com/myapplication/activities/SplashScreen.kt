package vj.com.myapplication.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import vj.com.myapplication.BuildConfig
import vj.com.myapplication.R
import vj.com.myapplication.Util
import vj.com.myapplication.databases.RealmController
import vj.com.myapplication.interfaces.ResourceJsonParserCallback
import vj.com.myapplication.models.City

/**
 *
 * Created by VJ on 1/23/2018.
 */

class SplashScreen : AppCompatActivity(), ResourceJsonParserCallback {

    @BindView(R.id.splash_text) lateinit var variant: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)

        ButterKnife.bind(this)

        variant.text = BuildConfig.BUILD_TYPE

        ResourceJsonParser(this, this).execute(R.raw.city)
    }

    @SuppressLint("StaticFieldLeak")
    class ResourceJsonParser(private var context: Context, private var callback: ResourceJsonParserCallback?) : AsyncTask<Int, Void, Void>() {

        override fun onPreExecute() {
        }

        override fun doInBackground(vararg params: Int?): Void? {

            val json: String = Util.loadJSONFromAsset(params[0]!!, context)!!

            val cities: List<City> = Gson().fromJson(json, object: TypeToken<List<City>>(){ }.type)

            RealmController.with().insertOrUpdate(cities)

            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            callback?.parseDone()
        }
    }

    override fun parseDone() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}

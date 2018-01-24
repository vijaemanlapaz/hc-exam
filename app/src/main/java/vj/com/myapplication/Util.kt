package vj.com.myapplication

import android.content.Context
import java.io.IOException
import java.nio.charset.Charset

/**
 *
 * Created by VJ on 1/23/2018.
 */
class Util {

    companion object {
        fun loadJSONFromAsset(resourceId: Int, context: Context): String? {
            var json: String?
            try {
                val `is` = context.resources.openRawResource(resourceId)
                val size = `is`.available()
                val buffer = ByteArray(size)
                `is`.read(buffer)
                `is`.close()
                json = String(buffer, Charset.forName("UTF-8"))
            } catch (ex: IOException) {
                ex.printStackTrace()
                return null
            }
            return json
        }
    }
}
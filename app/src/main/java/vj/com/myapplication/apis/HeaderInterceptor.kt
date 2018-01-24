package vj.com.myapplication.apis

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 *
 * Created by VJ on 8/10/2017.
 */

internal class HeaderInterceptor : Interceptor {
    private val TAG = HeaderInterceptor::class.java.simpleName

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.e(TAG, "=== START INTERCEPTOR ===")

        var request = chain.request()
        request = request.newBuilder()
                .addHeader("x-api-key", "e7728e3cb5a1cc46d7bf8b5d6af68e2a")
                .build()

        Log.e(TAG, "Url: " + request.url())
        Log.e(TAG, "Method: " + request.method())

        Log.e(TAG, "---> Request headers <---")
        for (name in request.headers().names()) {
            Log.e(TAG, "\t" + name + " : " + request.header(name))
        }
        Log.e(TAG, "---> Request headers <---")
        /*try {
            Log.e(TAG, "Body: " + Util.bodyToString(request.body()));
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }*/

        //        Response response = chain.proceed(request);
        //        Log.e(TAG, "---> Response headers <---");
        //        for(String name : response.headers().names()) {
        //            Log.e(TAG, "\t" + name + " : " + response.header(name));
        //        }
        //        Log.e(TAG, "---> Response headers <---");

        Log.e(TAG, "=== END INTERCEPTOR ===")
        return chain.proceed(request)
    }
}

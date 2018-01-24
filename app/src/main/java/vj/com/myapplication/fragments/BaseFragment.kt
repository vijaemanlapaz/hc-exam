package vj.com.myapplication.fragments;

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import butterknife.ButterKnife
import com.vj.mykotlinapp.apis.ApiClient
import vj.com.myapplication.activities.MainActivity


/**
 *
 * Created by VJ on 12/30/2017.
 */

abstract class BaseFragment : Fragment() {

    val apiClient by lazy { ApiClient.create() }

    lateinit var mainActivity : MainActivity

    override fun onAttach(context : Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ButterKnife.bind(this, view)
    }

    fun changeFragment(fragment : Fragment, tag : String) {
        mainActivity.changeFragment(fragment, tag)
    }

    fun onBackPressed() {
        mainActivity.onBackPressed()
    }
}

package vj.com.myapplication.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import vj.com.myapplication.R
import vj.com.myapplication.fragments.SearchListFragment

class MainActivity : AppCompatActivity(), FragmentManager.OnBackStackChangedListener {
    private val TAG: String = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.common)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_HOME or ActionBar.DISPLAY_HOME_AS_UP or ActionBar.DISPLAY_SHOW_TITLE
        supportFragmentManager.addOnBackStackChangedListener(this)

        addFragment(SearchListFragment(), SearchListFragment.TAG)
    }

    override fun onBackStackChanged() {
        if(supportFragmentManager.backStackEntryCount > 1) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_vector)
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_vector)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == android.R.id.home) {
            if(supportFragmentManager.backStackEntryCount > 1) {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }

    private fun addFragment(fragment : Fragment, tag : String) {

        val fragmentManager : FragmentManager = supportFragmentManager

        val frg: Fragment? = fragmentManager.findFragmentByTag(tag)
        if(frg == null) {
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            transaction.add(R.id.fragment_container, fragment, tag)
            transaction.addToBackStack(null)
            transaction.commit()

        } else {
            Log.e(TAG, tag + "is visible: " + fragment.isVisible)
            if (!fragment.isVisible) {
                fragmentManager.popBackStack()
            }
        }
    }

    fun changeFragment(fragment: Fragment?, tag: String) {
        if(fragment != null) {
            val frg : Fragment? = supportFragmentManager.findFragmentByTag(tag)
            if(frg == null) {
                supportFragmentManager.beginTransaction().run {
                    replace(R.id.fragment_container, fragment, tag)
                    addToBackStack(null)
                    commit()
                }
            } else {
                Log.e(TAG, "Is visible: " + frg.isVisible)
                if (!frg.isVisible) {
                    supportFragmentManager.popBackStack()
                }
            }
        }
    }
}

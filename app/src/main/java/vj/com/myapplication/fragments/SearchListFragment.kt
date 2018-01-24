package vj.com.myapplication.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ListView
import butterknife.BindView
import butterknife.ButterKnife
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken
import com.vj.mykotlinapp.adapters.SearchListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.Sort
import vj.com.myapplication.R
import vj.com.myapplication.models.City
import vj.com.myapplication.models.Weather

/**
 *
 * Created by VJ on 12/30/2017.
 */

class SearchListFragment : BaseFragment(), AdapterView.OnItemClickListener {
    companion object {
        val TAG: String = SearchListFragment::class.java.simpleName
    }

    @BindView(R.id.search_list_fragment_filter) lateinit var searchInput: EditText
    @BindView(R.id.search_list_fragment_listview) lateinit var listView: ListView

    var searchAdapter: SearchListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.search_list_fragment, container, false)
    }

    var cities1: List<City>? = emptyList()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ButterKnife.bind(this, view)

        cities1 = Realm.getDefaultInstance().where(City::class.java).findAll().sort("name", Sort.ASCENDING)
        searchAdapter = SearchListAdapter(mainActivity, cities1!!)

        listView.apply {
            onItemClickListener = this@SearchListFragment
            adapter = searchAdapter
        }

        searchInput.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) { }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                when(s.isNullOrBlank()) {
                    true -> {
                        val cities: List<City> = Realm.getDefaultInstance()
                                .where(City::class.java)
                                .findAll()
                                .sort("name")
                        searchAdapter?.setPersonList(cities)
                    }
                    false -> {
                        findCity(s.toString())
                    }
                }
            }
        })
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        val model = parent?.adapter?.getItem(position)

        if(model is City) {
            changeFragment(DetailsFragment()
                    .addArguments(Bundle().apply {
                        putInt("cityId", model.id)
                    }),
                    DetailsFragment.TAG)
        } else if(model is Weather) {
            changeFragment(DetailsFragment()
                    .addArguments(Bundle().apply {
                        putSerializable("weather", model)
                    }),
                    DetailsFragment.TAG)
        }
    }

    private var disposable: Disposable? = null

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    private fun findCity(query: String?) {
        disposable = apiClient.searchCity(query).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            try {
                                val status: Int = it.get("cod").asInt
                                if(status == 0) {
                                    Log.e(TAG, "Ooops something went wrong.")
                                }

                                val result: JsonArray = it.getAsJsonArray("list")

                                val cities: List<Weather> = Gson().fromJson(result, object: TypeToken<List<Weather>>(){ }.type)

                                searchAdapter?.setPersonList(cities)


                            } catch (exception: Exception) {
                                Log.e(DetailsFragment.TAG, exception.message)
                            }
                        },
                        {
                            Log.e(DetailsFragment.TAG, "Exception: " + it.localizedMessage)
                        }
                )
    }
}
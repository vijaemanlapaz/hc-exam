package vj.com.myapplication.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import vj.com.myapplication.R
import vj.com.myapplication.models.Weather

/**
 *
 * Created by VJ on 12/30/2017.
 */
class DetailsFragment : BaseFragment() {
    companion object {
        val TAG: String = DetailsFragment::class.java.simpleName
    }

    @BindView(R.id.details_layout_weather_icon) lateinit var weatherIcon: ImageView
    @BindView(R.id.details_layout_description) lateinit var weatherDescription : TextView
    @BindView(R.id.details_layout_temp) lateinit var weatherTemperature: TextView
    @BindView(R.id.details_layout_pressure) lateinit var weatherPressure : TextView
    @BindView(R.id.details_layout_humidity) lateinit var weatherHumidity : TextView
    @BindView(R.id.details_layout_visibility) lateinit var weatherVisibility : TextView

    private var disposable: Disposable? = null

    private var weather : Weather? = null
    private var cityId : Int? = 0

    fun addArguments(bundle : Bundle?) : DetailsFragment {
        arguments = bundle
        return this
    }

    override fun onAttach(context : Context) {
        super.onAttach(context)
        cityId = arguments?.getInt("cityId").takeIf { it != null }
        weather = arguments.getSerializable("weather").takeIf { it != null } as? Weather
    }

    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?) : View {
        return inflater.inflate(R.layout.details_fragment, container, false)
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(weather != null) {
            populate(weather)
        } else {
            getWeather()
        }
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    private fun getWeather() {
        disposable = apiClient.getWeather(cityId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            try {
                                populate(it)
                            } catch (exception: Exception) {
                                Log.e(TAG, exception.message)
                            }
                        },
                        {
                            Log.e(TAG, "Exception: " + it.localizedMessage)
                        }
                )
    }

    private fun populate(weather: Weather?) {
        weather.takeIf { it != null }.run {
            Picasso.with(context)
                    .load("http://openweathermap.org/img/w/" + weather?.weather?.get(0)?.icon + ".png")
                    .fit()
                    .placeholder(android.R.color.transparent)
                    .into(weatherIcon)

            weatherDescription.text = weather?.weather?.get(0)?.description
            weatherTemperature.text = String.format("Temperature: %s \u2109 ", weather?.main?.temp.toString())
            weatherPressure.text = String.format("Pressure: %s", weather?.main?.pressure.toString())

            weatherHumidity.text = String.format("Humidity: %s ", weather?.main?.humidity.toString())
            weatherVisibility.text = String.format("Visibility: %s", weather?.visibility.toString())
        }
    }
}

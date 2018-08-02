package tr.xip.timetable

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import org.apache.commons.io.IOUtils
import tr.xip.timetable.Timetable_API.TimetableAPI
import tr.xip.timetable.model.Class
import java.util.*
import android.R.attr.data
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_profile.*
import retrofit2.Call
import tr.xip.timetable.model.Timetableclass
import kotlin.collections.ArrayList
import java.util.Arrays.asList
import android.R.attr.data
import java.util.Arrays.asList
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val extras = intent.extras ?: return
        val resdeptname = extras.getString("deptname")
        val data = extras.getString("data")



        Toast.makeText(this@MainActivity, resdeptname, Toast.LENGTH_SHORT).show()
        /*val timetable = Gson().fromJson<java.util.ArrayList<Class>>(data,
                object : TypeToken<java.util.ArrayList<Class>>() {}.type
        )

        if (timetable != null && timetable.isNotEmpty()) {
            pager.adapter = DaysPagerAdapter(timetable, supportFragmentManager)
            tabs.setupWithViewPager(pager)

            // Normalize day value - our adapter works with five days, the first day (0) being Monday.
            val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 2
            pager.currentItem = if (today in 0..4) today else 0
        }*/

        /* val apiService = TimetableAPI.create()
        val callResponse = apiService.retrivetimetable("mca2")

        val response = callResponse.execute()

        if (response.isSuccessful) {
            val timetable = response.body()
            if (timetable != null && timetable.isNotEmpty()) {
                pager.adapter = DaysPagerAdapter(timetable, supportFragmentManager)
                tabs.setupWithViewPager(pager)

                // Normalize day value - our adapter works with five days, the first day (0) being Monday.
                val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 2
                pager.currentItem = if (today in 0..5) today else 0
            }
        } else {
            Toast.makeText(this,response.errorBody().toString(),Toast.LENGTH_LONG).show()
        }*/


        val deptname = extras.getString("deptname")
        val dept = extras.getString("dept")
        val year = extras.getString("year")

        val apiService = TimetableAPI.create()
        val callResponse = apiService.retrivetimetable(deptname)

        val response = callResponse.execute()

        this.setTitle(dept)
            val timetable = Gson().fromJson<java.util.ArrayList<Class>>(
                    IOUtils.toString(assets.open( "ise2.json"), "UTF-8"),
                    object : TypeToken<java.util.ArrayList<Class>>() {}.type
            )
            if (timetable != null && timetable.isNotEmpty()) {
                pager.adapter = DaysPagerAdapter(timetable, supportFragmentManager)
                tabs.setupWithViewPager(pager)

                // Normalize day value - our adapter works with five days, the first day (0) being Monday.
                val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 2
                pager.currentItem = if (today in 0..5) today else 0
            }
    }
}


package tr.xip.timetable.Timetable_API

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.POST
import retrofit2.http.Query
import tr.xip.timetable.endPoint
import tr.xip.timetable.model.Class
import java.util.ArrayList

interface TimetableAPI {
    @POST("timetable.php")
    fun retrivetimetable(@Query("tablename") tablename: String)
        : Call<ArrayList<Class>>;

    companion object Factory {
        fun create(): TimetableAPI {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(endPoint.URL_ROOT)
                    .build()

            return retrofit.create(TimetableAPI::class.java);
        }
    }
}

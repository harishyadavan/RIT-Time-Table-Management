package tr.xip.timetable.Timetable_API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Wilsonwwd on 2/20/2018.
 */

public class RetroApi implements Constants {

    public  static Retrofit geturl(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();
        return retrofit;
    }
}

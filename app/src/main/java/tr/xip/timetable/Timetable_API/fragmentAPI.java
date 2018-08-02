package tr.xip.timetable.Timetable_API;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import tr.xip.timetable.model.Class;
import tr.xip.timetable.model.Timetableclass;

/**
 * Created by Wilsonwwd on 2/20/2018.
 */

public interface fragmentAPI {

    @POST("timetable.php")
    @FormUrlEncoded
    Call<ArrayList<Class>> RetriveTimeTable(@Field("tablename") String deptname);
}
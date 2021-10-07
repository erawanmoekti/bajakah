package id.erris.bajakah.retrofit;

import java.sql.Time;

import id.erris.bajakah.response.RegisterResponse;
import id.erris.bajakah.response.ReminderResponse;
import id.erris.bajakah.response.SaveResponse;
import io.reactivex.Observable;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("user/register")
    Observable<RegisterResponse> register(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("reminders")
    Observable<ReminderResponse> getReminder(
            @Field("user_id") String userId
    );

    @FormUrlEncoded
    @POST("reminder/create")
    Observable<SaveResponse> createReminder(
            @Field("user_id") String userId,
            @Field("keterangan_id") String keterangan_id,
            @Field("keterangan_en") String keterangan_en,
            @Field("jam") Time jam,
            @Field("sunday") boolean sunday,
            @Field("monday") boolean monday,
            @Field("tuesday") boolean tuesday,
            @Field("wednesday") boolean wednesday,
            @Field("thursday") boolean thursday,
            @Field("friday") boolean friday,
            @Field("saturday") boolean saturday
    );

    @FormUrlEncoded
    @PUT("reminder/{id}")
    Observable<SaveResponse> updateReminder(
            @Path("id") String id,
            @Query("keterangan_id") String keterangan_id,
            @Query("keterangan_en") String keterangan_en,
            @Query("jam") Time jam,
            @Query("sunday") boolean sunday,
            @Query("monday") boolean monday,
            @Query("tuesday") boolean tuesday,
            @Query("wednesday") boolean wednesday,
            @Query("thursday") boolean thursday,
            @Query("friday") boolean friday,
            @Query("saturday") boolean saturday
    );

    @DELETE("reminder/{id}")
    Observable<SaveResponse> deleteReminder(
            @Path("id") String id
    );

    @FormUrlEncoded
    @POST("feedback/create")
    Observable<SaveResponse> createFeedback(
            @Field("user_id") String userId,
            @Field("keterangan_id") String nama,
            @Field("keterangan_en") String email,
            @Field("saran") String saran
    );

}

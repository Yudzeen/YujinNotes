package ebj.awesome.yujinnotes.data.web;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Yujin on 02/10/2017.
 */

public interface YujinNotesService {

    @GET("get_note_details.php")
    Call<JsonResponse> getNote(@Query("id") String id);

    @GET("get_all_notes.php")
    Call<JsonResponse> getNotes();

    @FormUrlEncoded
    @POST("create_note.php")
    Call<JsonResponse> insertNote(@Field("id") String id,
                                  @Field("title") String title,
                                  @Field("description") String description,
                                  @Field("position") int position);

    @FormUrlEncoded
    @POST("update_note.php")
    Call<JsonResponse> updateNote(@Field("id") String id,
                                  @Field("title") String title,
                                  @Field("description") String description,
                                  @Field("position") int position);

    @FormUrlEncoded
    @POST("delete_note.php")
    Call<JsonResponse> deleteNote(@Field("id") String id);

}

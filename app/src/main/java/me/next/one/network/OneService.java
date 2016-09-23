package me.next.one.network;

import me.next.one.home.model.ApiResponse;
import me.next.one.home.model.HomeModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by NeXT on 16/9/23.
 */

public interface OneService {
    @FormUrlEncoded
    @POST("getHp_N")
    Call<ApiResponse<HomeModel>> getHomeModel(@Field("strDate") String strDate, @Field("strRow") String strRow);
}

package me.next.one.network;

import me.next.one.constants.OneApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by NeXT on 16/9/23.
 */

public class OneRetrofit {

    private OneService oneService;

    public OneRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OneApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        oneService = retrofit.create(OneService.class);
    }

    public OneService getOneService() {
        return oneService;
    }

}

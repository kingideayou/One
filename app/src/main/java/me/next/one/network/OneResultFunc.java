package me.next.one.network;

import me.next.one.home.model.ApiResponse;
import rx.exceptions.Exceptions;
import rx.functions.Func1;

/**
 * Created by NeXT on 16/9/23.
 */
public class OneResultFunc<T> implements Func1<ApiResponse<T>, T> {
    @Override
    public T call(ApiResponse<T> tApiResponse) {
        if (tApiResponse.result.equals("SUCCESS")) {
            return tApiResponse.hpEntity;
        } else {
            throw Exceptions.propagate(new Throwable(tApiResponse.message));
        }
    }
}
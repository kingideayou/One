package me.next.one;

import me.next.one.network.OneRetrofit;
import me.next.one.network.OneService;

/**
 * Created by NeXT on 16/9/23.
 */

public class OneFactory {

    static OneService oneApi;

    public static OneService getOneServiceSingleton() {
        if (oneApi == null) {
            synchronized (OneFactory.class) {
                if (oneApi == null)
                    oneApi = new OneRetrofit().getOneService();
            }
        }
        return oneApi;
    }

}

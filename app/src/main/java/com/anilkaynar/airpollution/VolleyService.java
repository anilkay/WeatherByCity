package com.anilkaynar.airpollution;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by anilkaynar on 19.02.2018.
 */

public class VolleyService {
    private static RequestQueue requestQueue;

    public RequestQueue singleton(Context context){
        if(requestQueue==null) {
            requestQueue = Volley.newRequestQueue(context);
        }
        return requestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        if(requestQueue!=null)
        requestQueue.add(req);
    }
}

package com.example.lasthope.ui.home;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MyRequestQueue {
    static RequestQueue queue = null;

    public static RequestQueue getInstance(Context ctx){
        if(queue == null){
            return Volley.newRequestQueue(ctx);
        }
        return queue;
    }

}


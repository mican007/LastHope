package com.example.lasthope.ui.dashboard;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.lasthope.R;
import com.example.lasthope.ui.home.MyRequestQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    private TextView datum;
    private TextView txtJmeno1;
    private TextView txtJmeno2;
    private RequestQueue mQueue;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        textView.setTextColor(Color.rgb(0,0,0));
        datum= root.findViewById(R.id.txtDatum);
        txtJmeno1 = root.findViewById(R.id.txtJmeno1);
        txtJmeno2 = root.findViewById(R.id.txtJmeno2);
        Date date = new Date();
        datum.setText("Dnes je : "+new SimpleDateFormat("\tMM/DD/YY").format(date).toString());
        datum.setTextColor(Color.rgb(0,0,0));
        mQueue = MyRequestQueue.getInstance(getActivity().getApplicationContext());



        dashboardViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });





zistiSvatek();


        return root;
    }


    private void zistiSvatek(){
        // Do something


        Date date = new Date();
      String datum1=  new SimpleDateFormat("ddMM").format(date).toString();


        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        date = c.getTime();

        String datum2=  new SimpleDateFormat("ddMM").format(date).toString();

        Log.v("MYAPP", datum1);
        Log.v("MYAPP", datum2);

        String url ="http://svatky.adresa.info/json?date="+datum1;
        String ur2 ="http://svatky.adresa.info/json?date="+datum2;


        Log.v("MYAPP", url);

        final JsonArrayRequest jsonObjReq1 = new
            JsonArrayRequest(Request.Method.GET, url, null,
            new com.android.volley.Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {


                    JSONObject jresponse = null;
                    try {
                        jresponse = response.getJSONObject(0);

                        String nickname = jresponse.getString("name");
                        Log.d("MYAPP",nickname);
                        txtJmeno1.setText("Dneska má svátek  "+nickname);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
        }
    });

// Add the request to the RequestQueue.
        mQueue.add(jsonObjReq1);
        final JsonArrayRequest jsonObjReq2 = new
                JsonArrayRequest(Request.Method.GET, ur2, null,
                new com.android.volley.Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {


                        JSONObject jresponse = null;
                        try {
                            jresponse = response.getJSONObject(0);

                            String nickname = jresponse.getString("name");
                            Log.d("MYAPP",nickname);
                            txtJmeno2.setText("Zítra má svátek  "+nickname);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }




                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

// Add the request to the RequestQueue.
        mQueue.add(jsonObjReq2);
    }
}

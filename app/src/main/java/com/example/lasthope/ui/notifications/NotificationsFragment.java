package com.example.lasthope.ui.notifications;

import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.lasthope.R;
import com.example.lasthope.ui.home.MyRequestQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class NotificationsFragment extends Fragment {
    private Button generuj;
    private RequestQueue mQueue;
    private TextView txttext;
    private Button uloz;
    private Button nacti;
    private EditText txtEditor;
    private NotificationsViewModel notificationsViewModel;








    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        mQueue = MyRequestQueue.getInstance(getActivity().getApplicationContext());
        generuj = root.findViewById(R.id.button);
        uloz= root.findViewById(R.id.button2);
        nacti= root.findViewById(R.id.button3);
        txttext = root.findViewById(R.id.editText);
        String text=txttext.getText().toString();
       generujcitat(uloz,generuj,txttext);

        nacti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                nacti(txttext);

            }});

        notificationsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);

            }
        });
        return root;

    }

    private  void uloz(TextView txtOutput){

        String text=txtOutput.getText().toString();
        SharedPreferences settings = getActivity().getSharedPreferences("citatyTemp", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("test", text).commit();
        Log.v("MYAPP", "Button clicked"+text);

    }

    private  void nacti(TextView txtOutput){

    // Nacitani adresy ulozene v pameti
    SharedPreferences settings2 = getActivity().getSharedPreferences("citatyTemp", 0);
    String silent = settings2.getString("test","Citaty....");
        Log.v("MYAPP", "nacitani"+silent);
txtOutput.setText(silent);






    }



    private void generujcitat(Button uloz,Button generuj, final TextView txtOutput) {

        final String url = "http://api.forismatic.com/api/1.0/?method=getQuote&key=457653&format=json&lang=en";
        Log.d("MYAPP",url.toString());
       uloz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                uloz(txtOutput);

            }});

        generuj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                // Do something
                Log.v("MYAPP", "Button clicked");

                txtOutput.setPaintFlags(View.INVISIBLE);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {


                                    String citat = response.getString("quoteText");
                                    String autor = response.getString("quoteAuthor");
                                    txtOutput.append("Citát :"+citat+"\n"+"Autor :  "+autor+"\n"+"\n");




                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                txtOutput.setText(error.toString());
                            }
                        });

// Add the request to the RequestQueue.
                mQueue.add(jsonObjectRequest);

            }
        });
}



}
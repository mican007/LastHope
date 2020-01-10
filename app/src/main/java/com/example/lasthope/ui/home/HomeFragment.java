package com.example.lasthope.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.lasthope.R;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Button mButton;
    private RequestQueue mQueue;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mQueue = MyRequestQueue.getInstance(getActivity().getApplicationContext());



        final TextView lblResult = root.findViewById(R.id.txtOutput);
        homeViewModel.getTranslationResult().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                lblResult.setText(s);
            }
        });



        return root;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mButton = getView().findViewById(R.id.btnTranslate);
        final TextView Textik= getView().findViewById(R.id.txtOutput);
        final TextView test=getView().findViewById(R.id.txtInput);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                // Do something
                Log.v("MYAPP", "Button clicked");

                EditText input = getView().findViewById(R.id.txtInput);
                Log.v("MYAPP", input.getText().toString());
                String url ="https://api.mymemory.translated.net/get?q="+input.getText().toString()+"&langpair=en|cs";

                final TextView txtOutput = getView().findViewById(R.id.txtOutput);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject responseData = response.getJSONObject("responseData");
                                    String translatedText = responseData.getString("translatedText");

                                    homeViewModel.setTranslationResult(translatedText);

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


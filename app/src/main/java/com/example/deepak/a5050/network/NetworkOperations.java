package com.example.deepak.a5050.network;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.deepak.a5050.MainActivity;
import com.example.deepak.a5050.R;
import com.example.deepak.a5050.database.DBHelper;
import com.example.deepak.a5050.datamodels.DailyWord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NetworkOperations {
    private static NetworkOperations networkOperations = null;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public NetworkOperations(MainActivity activity) {
        sharedPreferences=activity.getSharedPreferences(activity.getPackageName(), Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    public static NetworkOperations getInstance(MainActivity activity) {
        if (networkOperations == null) {
            networkOperations = new NetworkOperations(activity);
        }
        return networkOperations;
    }
    public void fetchDailyWord(final MainActivity activity) {
        Log.e("fetchDailyWord()", "fetching Daily Word");
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        StringRequest StringRequest = new StringRequest(Request.Method.POST, activity.getResources().getString(R.string.API_dailyword),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DailyWord.getDailyWord().getCompletedList().clear();
                        DailyWord.getDailyWord().getTempList().clear();
                        DailyWord.getDailyWord().getWordList().clear();

                        Log.e("RESPONSE", response);

                        try {
                            JSONArray jsonArray=new JSONArray(response);
                            JSONObject jsonObject=jsonArray.getJSONObject(0);
                            DailyWord.getDailyWord().setWord(jsonObject.getString("word"));
                            DailyWord.getDailyWord().setHint(jsonObject.getString("hint"));
                            DailyWord.getDailyWord().setDate(jsonObject.getString("date"));
                            DailyWord.getDailyWord().setWordlistStr(jsonObject.getString("wordlist").toLowerCase());
                            DailyWord.getDailyWord().setId(Integer.valueOf(jsonObject.getString("id")));
                            DailyWord.getDailyWord().populateLists();
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    activity.getScoreCountView().setText("0");
                                    activity.getWordCountView().setText("0");
                                    activity.getDailyWordView().setText(DailyWord.getDailyWord().getWord());
                                }
                            });
                            editor.putString("wordofTheDay", DailyWord.getDailyWord().getWord());
                            if (activity.getSplashLoading().isShowing()){
                                activity.getSplashLoading().dismiss();
                                //editor.commit();
                                //TODO: remove comment after connecting Database
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("JSONOBJ ERROR", e + "");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity, "Can't Connect, Retry.", Toast.LENGTH_SHORT).show();
                    }

                });
        requestQueue.add(StringRequest);
        if (activity.getSplashLoading().isShowing()){
        }

    }
    public void checkDailyWord(final MainActivity activity, String word) {
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        StringRequest StringRequest = new StringRequest(Request.Method.GET, activity.getResources().getString(R.string.API_checkDailyword) + word,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("RESPONSE", response);

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            if (!jsonObject.getString("status").equals("ok")){
                                Log.e("checkDailyWord()", "not ok");
                                fetchDailyWord(activity);
                            }else {
                                Log.e("checkDailyWord()", "ok");
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("JSONOBJ ERROR", e + "");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity, "Can't Connect, Retry.", Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(StringRequest);
    }

}
    /*

ImageLoader imageLoader=AppSingleton.getInstance(getApplicationContext()).getImageLoader();

imageLoader.get(url, new ImageLoader.ImageListener(){
	@Override
	public void onResponse(ImageLoader.ImageContainer, boolean arg1){
		if(response.getBitmap()!=null){

		}
	}
	@Override
	public void onErrorResponse(Volley error){

	}
});




*/

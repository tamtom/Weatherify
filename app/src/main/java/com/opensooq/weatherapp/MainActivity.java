package com.opensooq.weatherapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.opensooq.weatherapp.Activities.CityListActivity;
import com.opensooq.weatherapp.api.ApiClient;
import com.opensooq.weatherapp.api.ApiInterface;
import com.opensooq.weatherapp.common.Const;
import com.opensooq.weatherapp.model.Weather;

import java.io.IOException;
import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NoInternetConnectionFragment.onRefreshButtonListener ,WeatherFragment.OnItemClickedListener{
    private static final String TAG = "GO BABY";
    private ApiInterface apiService;
    private Weather weather;
    private FrameLayout container;
    private ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        container = (FrameLayout) findViewById(R.id.fragmentContainer);
        mProgressBar = (ProgressBar) findViewById(R.id.loading_progress);
        getData();
    }
public void getData(){
    new getWeatherAsync().execute();

}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_city) {
            startActivity(new Intent(this, CityListActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
       getData();
    }

    @Override
    public void showDetails(Uri uri) {

    }

    class getWeatherAsync extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            container.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Response<Weather> response = null;
            Call<Weather> call = apiService.getWeather(Const.API_KEY, "Amman", "5");
            try {
                response = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (response != null) {
                weather = response.body();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mProgressBar.setVisibility(View.GONE);
            container.setVisibility(View.VISIBLE);
            if (weather == null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new NoInternetConnectionFragment()).commit();
            }
            else {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, WeatherFragment.newInstance(weather)).commit();
            }
        }
    }
}
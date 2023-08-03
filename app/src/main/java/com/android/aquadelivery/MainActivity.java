package com.android.aquadelivery;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;

import com.android.aquadelivery.adapters.WaterListAdapter;
import com.android.aquadelivery.model.WaterModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements WaterListAdapter.RestaurantListClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("DASHBOARD");

        //Bottom Navigation Bar

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_home:
                    return true;
                case R.id.bottom_cart:
                    startActivity(new Intent(getApplicationContext(), WaterMenuActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_location:
                    startActivity(new Intent(getApplicationContext(), GPSActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_account:
                    startActivity(new Intent(getApplicationContext(), AccountActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
            }
            return false;
        });

        List<WaterModel> restaurantModelList =  getWaterData();

        initRecyclerView(restaurantModelList);
    }

    private void initRecyclerView(List<WaterModel> restaurantModelList ) {
        RecyclerView recyclerView =  findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        WaterListAdapter adapter = new WaterListAdapter(restaurantModelList, this);
        recyclerView.setAdapter(adapter);
    }
//to get water data from json file
    private List<WaterModel> getWaterData() {
        InputStream is = getResources().openRawResource(R.raw.water);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try{
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while(( n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0,n);
            }
        }catch (Exception e) {

        }

        String jsonStr = writer.toString();
        Gson gson = new Gson();
        WaterModel[] restaurantModels =  gson.fromJson(jsonStr, WaterModel[].class);
        List<WaterModel> restList = Arrays.asList(restaurantModels);

        return  restList;

    }
//when click an item navigated to the WaterMenuActivity
    @Override
    public void onItemClick(WaterModel restaurantModel) {
        Intent intent = new Intent(MainActivity.this, WaterMenuActivity.class);
        intent.putExtra("RestaurantModel", restaurantModel);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


}
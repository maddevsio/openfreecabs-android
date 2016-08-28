package io.maddevs.openfreecabs.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import io.maddevs.openfreecabs.R;
import io.maddevs.openfreecabs.adapters.NearCabsAdapter;

/**
 * Created by rustam on 28.08.16.
 */
public class NearCabListActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_cabs_list);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new NearCabsAdapter());
    }
}

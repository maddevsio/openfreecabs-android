package io.maddevs.openfreecabs.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import io.maddevs.openfreecabs.R;
import io.maddevs.openfreecabs.adapters.ContactsAdapter;
import io.maddevs.openfreecabs.models.ContactModel;
import io.maddevs.openfreecabs.utils.views.DividerItemDecoration;

/**
 * Created by man on 01.10.16.
 */
public class ContactsActivity extends AppCompatActivity implements ContactsAdapter.OnItemClickListener {
    String title;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list);

        if (getIntent().getExtras() != null) {
            title = getIntent().getExtras().getString("companyName", "");
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle(!title.isEmpty() ? title : getString(R.string.app_name));
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(this, R.drawable.divider)));
        recyclerView.setAdapter(new ContactsAdapter(this));
    }

    @Override
    public void onClick(ContactModel item) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        switch (item.type) {
            case ContactModel.Sms:
                intent.setData(Uri.parse("sms:" + item.contact));
                break;
            case ContactModel.Phone:
                intent.setData(Uri.parse("tel:" + item.contact));
                break;
            case ContactModel.Website:
                intent.setData(Uri.parse(item.contact));
                break;
            case ContactModel.Android:
                intent.setData(Uri.parse(item.contact));
                break;
            case ContactModel.Apple:
                intent.setData(Uri.parse(item.contact));
                break;
        }
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

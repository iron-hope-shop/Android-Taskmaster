package com.example.projecttwo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.List;

public class DataDisplayActivity extends AppCompatActivity {

    private AppCompatActivity activity = DataDisplayActivity.this;
    private RecyclerView recyclerViewEvents;
    private AppCompatTextView textViewName;
    private TableLayout tableViewEvents;
    private List<EventModel> listEvents;
    private EventAdapter eventAdapter;
    private EventController databaseHelper;
//    private

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_display_screen);
        getSupportActionBar().setTitle("Events");

        initViews();
//        initListeners();
        initObjects();

    }

    private void initViews() {
        textViewName = (AppCompatTextView) findViewById(R.id.textViewName);
        recyclerViewEvents = (RecyclerView) findViewById(R.id.recyclerViewUsers);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        listEvents = new ArrayList<>();
        eventAdapter = new EventAdapter(listEvents);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewEvents.setLayoutManager(mLayoutManager);
        recyclerViewEvents.setItemAnimator(new DefaultItemAnimator());
        recyclerViewEvents.setHasFixedSize(true);
        recyclerViewEvents.setAdapter(eventAdapter);
        databaseHelper = new EventController(activity);

        String emailFromIntent = getIntent().getStringExtra("EMAIL");
        textViewName.setText(emailFromIntent);

        getDataFromSQLite();
    }

    private void getDataFromSQLite() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listEvents.clear();
                listEvents.addAll(databaseHelper.getAllEvents());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                eventAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

//    private void initListeners() {
//        buttonLoginButton.setOnClickListener(this);
//        buttonRegisterButton.setOnClickListener(this);
//    }
}
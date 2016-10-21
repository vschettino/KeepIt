package com.rockinghorse.keepit.views;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.rockinghorse.keepit.R;
import com.thebluealliance.spectrum.SpectrumDialog;

public class TaskCreateActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    AppCompatActivity context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Add New task");

        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Save Task Action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

    public void showDateDialog(View v) {

        TimePickerFragment newFragment = new TimePickerFragment(this);
        newFragment.show(getSupportFragmentManager(), "date_picker");

    }

    public void showColorDialog(View v) {
        final View view = v;
        new SpectrumDialog.Builder(this)
                .setColors(R.array.picker_colors)
                .setSelectedColorRes(R.color.md_blue_500)
                .setDismissOnColorSelected(true)
                .setOutlineWidth(2)
                .setOnColorSelectedListener(new SpectrumDialog.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(boolean positiveResult, @ColorInt int color) {
                        Snackbar.make(view, "#" + Integer.toHexString(color).toUpperCase(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }).build().show(getSupportFragmentManager(), "dialog_demo_1");
    }

}

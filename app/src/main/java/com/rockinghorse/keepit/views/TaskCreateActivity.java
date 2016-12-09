package com.rockinghorse.keepit.views;

import android.app.DatePickerDialog;
import android.graphics.PorterDuff;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rockinghorse.keepit.R;
import com.rockinghorse.keepit.models.Project;
import com.rockinghorse.keepit.models.Task;
import com.thebluealliance.spectrum.SpectrumDialog;

public class TaskCreateActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    AppCompatActivity context = this;
    private ImageView colorPicker;
    private int colorCode = 0x2196f3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // color.getDrawable().setColorFilter(0x2196f3, PorterDuff.Mode.MULTIPLY);
        this.colorPicker = (ImageView) findViewById(R.id.imageView);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Add New task");

        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleNewTask();
                finish();
            }
        });
    }


    public void handleNewTask() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("tasks");
        String id = ref.push().getKey();

        EditText edtTitulo = (EditText) findViewById(R.id.edtTask);
        EditText edtInfo = (EditText) findViewById(R.id.edtAditionalInfo);
        TextView txtDate = (TextView) findViewById(R.id._due_date);
        String title = edtTitulo.getText().toString();
        String info = edtInfo.getText().toString();
        String date = txtDate.getText().toString();
        String project_id = getIntent().getStringExtra(ProjectListActivity.ARG_PROJECT_ID);

        Task t = new Task(
                id,
                title,
                date,
                colorCode,
                info,
                project_id
        );

        System.out.println(t.toMap().toString());
        ref.child(id).setValue(t.toMap());
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
        TextView txtDate = (TextView) findViewById(R.id._due_date);
        txtDate.setText(month + "/" + dayOfMonth + "/" + year);
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
                        colorCode = color;
                        colorPicker.getDrawable().setTint(color);
                    }
                }).build().show(getSupportFragmentManager(), "dialog_demo_1");
    }

}

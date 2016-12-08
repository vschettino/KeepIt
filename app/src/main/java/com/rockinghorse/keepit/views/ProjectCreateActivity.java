package com.rockinghorse.keepit.views;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rockinghorse.keepit.R;
import com.rockinghorse.keepit.models.GitHubProjects;
import com.rockinghorse.keepit.models.Project;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProjectCreateActivity extends AppCompatActivity {

    ArrayList<GitHubProjects> mProjects;
    EditText vProjectTitle;
    TextView githubSearchLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Add New project");
        }
        vProjectTitle = (EditText) findViewById(R.id.edtProject);
        githubSearchLink = (TextView) findViewById(R.id.gitHubSearch);
        githubSearchLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText edittext = new EditText(ProjectCreateActivity.this);
                AlertDialog.Builder alert = new AlertDialog.Builder(ProjectCreateActivity.this)
                        .setTitle("Informe seu username no github")
                        .setPositiveButton("Buscar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String githubAccount = edittext.getText().toString();
                                Log.i("accouNT", "acc " + githubAccount);
                                handleGithubSearch(githubAccount);
                            }
                        });
                alert.setView(edittext);
                alert.show();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleNewProject(vProjectTitle.getText().toString());
                finish();

            }
        });

    }

    private void displayGithubProjectsList() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        builderSingle.setIcon(R.drawable.common_google_signin_btn_icon_dark);
        builderSingle.setTitle("Select Your Project");
        final ArrayAdapter<GitHubProjects> arrayAdapter = new ArrayAdapter<GitHubProjects>(
                this,
                android.R.layout.select_dialog_singlechoice);
        arrayAdapter.addAll(mProjects);


        builderSingle.setAdapter(
                arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GitHubProjects p = arrayAdapter.getItem(which);
                        vProjectTitle.setText(p.toString());

                    }
                });
        builderSingle.show();
    }

    private void handleGithubSearch(String githubAccount) {
        final ProgressDialog progressDialog = new ProgressDialog(ProjectCreateActivity.this);
        progressDialog.setMessage("Downloading your data...");
        progressDialog.show();
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface arg0) {
            }
        });
        RequestQueue queue = Volley.newRequestQueue(ProjectCreateActivity.this);
        String url = "https://api.github.com/users/" + githubAccount + "/repos";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("content", "r " + response);
                        Gson gson = new Gson();
                        mProjects = gson.fromJson(response, new TypeToken<List<GitHubProjects>>() {
                        }.getType());
                        progressDialog.hide();
                        displayGithubProjectsList();

                    }

                }, new Response.ErrorListener(


        ) {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(ProjectCreateActivity.this.githubSearchLink, "Username not found", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                progressDialog.hide();

            }
        });
        queue.add(stringRequest);
    }

    public void handleNewProject(String title) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("projects");
        String key = ref.push().getKey();
        Project p = new Project(
                key,
                title,
                true,
                getIntent().getExtras().getString(LoginActivity.RC_ACCOUNT_ID)
        );
        ref.child(key).setValue(p);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

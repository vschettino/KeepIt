package com.rockinghorse.keepit.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rockinghorse.keepit.R;
import com.rockinghorse.keepit.models.DoingStatus;
import com.rockinghorse.keepit.models.DoneStatus;
import com.rockinghorse.keepit.models.Project;
import com.rockinghorse.keepit.models.Task;
import com.rockinghorse.keepit.models.TaskStatus;
import com.rockinghorse.keepit.models.ToDoStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static final String ARG_SECTION_NUMBER = "section_number";
    public static final String ARG_FRAGMENT_TITLE = "fragment_title";
    public static final String ARG_TASK_TITLE = "task_title";
    private RecyclerView recyclerView;
    private ArrayList<Task> mTasks = new ArrayList<>();

    public TaskFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TaskFragment newInstance(int sectionNumber, CharSequence pageTitle, String projectId) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putString(ARG_FRAGMENT_TITLE, pageTitle.toString());
        args.putString(ProjectListActivity.ARG_PROJECT_ID, projectId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.task_fragment, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.task_list);
        assert recyclerView != null;
        fetchProjects();
        setupRecyclerView(recyclerView);
        return rootView;
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new TaskFragment.SimpleItemRecyclerViewAdapter(mTasks));
    }

    private void fetchProjects() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("tasks");
        ref.orderByChild("project_id").equalTo(getArguments().getString(ProjectListActivity.ARG_PROJECT_ID)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Task> td = new HashMap<>();
                for (DataSnapshot jobSnapshot : dataSnapshot.getChildren()) {
                    Task t = jobSnapshot.getValue(Task.class);
                    if (t.getStatus().getLabel() == getArguments().getString(ARG_FRAGMENT_TITLE))
                        td.put(jobSnapshot.getKey(), t);
                }
                mTasks.clear();
                mTasks.addAll(new ArrayList<>(td.values()));
                recyclerView.getAdapter().notifyDataSetChanged();
                for (Task t : mTasks) {
                    Log.d("firebase", t.getStatus().getActionText());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<TaskFragment.SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Task> mValues;

        public SimpleItemRecyclerViewAdapter(List<Task> items) {
            mValues = items;
        }

        @Override
        public TaskFragment.SimpleItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.task_list_content, parent, false);
            return new TaskFragment.SimpleItemRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final TaskFragment.SimpleItemRecyclerViewAdapter.ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mContentView.setText(mValues.get(position).getLabel());
            holder.mContentView.setTextColor(Color.parseColor(mValues.get(position).getColorLabel()));
            holder.mActionMessageView.setText(mValues.get(position).getActionLabel());


            holder.mContentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Context context = v.getContext();
                    Intent intent = new Intent(context, TaskDetailActivity.class);
                    intent.putExtra(ARG_TASK_TITLE, holder.mItem.getLabel());

                    context.startActivity(intent);
                }
            });
            holder.mActionMessageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Snackbar.make(v, "Move a Task Action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mContentView;
            public final TextView mActionMessageView;
            public Task mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mContentView = (TextView) view.findViewById(R.id.content);
                mActionMessageView = (TextView) view.findViewById(R.id.action_label);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}


package com.example.shom853.aitasklist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.api.services.tasks.model.Task;

import java.util.List;

/**
 * Created by Sandy on 8/30/2016.
 *
 * This class is for the custom view for displaying the info of a simple Task
 */
public class TaskItemAdapter extends ArrayAdapter<Task> {

	public TaskItemAdapter(Context context, List<Task> tasksList){
		super(context, 0, tasksList);
	}

	public View getView(int position, View convertView, ViewGroup parent){
		// get data item from ArrayList for position
		final Task one_task = getItem(position);

		// inflate layout for list item if it doesn't exist
		if(convertView == null){
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_item, parent, false);
		}

		// Lookup views for data population
		TextView title = (TextView) convertView.findViewById(R.id.task_item_title);
		TextView note = (TextView) convertView.findViewById(R.id.task_item_notes);
		CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.task_item_completed_checkbox);

		// Populate data
		title.setText(one_task.getTitle());
		note.setText(one_task.getNotes());
		String status = one_task.getStatus();
		if(status != null && status.equals("completed")){
			checkbox.setChecked(true);
		}
		else {
			checkbox.setChecked(false);
		}

		// create completed check box listener
		checkbox.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				// set setStatus to inverse
				String status = one_task.getStatus();
				if(status != null && status.equals("completed")){
					one_task.setStatus("needsAction");
					one_task.setCompleted(null);
					// Task's completed attribute must be set to null when changing status from completed to not-completed because server will give an 400 error and the update won't be applied
				}
				else {
					one_task.setStatus("completed");
				}

				AsyncUpdateTask.run((TasksActivity) getContext(), one_task);
				// update listView
				TaskItemAdapter.this.notifyDataSetChanged();
			}
		});

		return convertView;

	}
}

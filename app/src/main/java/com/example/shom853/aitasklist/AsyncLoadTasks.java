package com.example.shom853.aitasklist;

import com.google.api.services.tasks.model.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sandy on 6/29/2016.
 */
class AsyncLoadTasks extends CommonAsyncTask {

	private final String listID;

	AsyncLoadTasks(TasksActivity tasksSample, String listID) {
		super(tasksSample);
		this.listID = listID;
	}

	@Override
	protected void doInBackground() throws IOException {
		List<Task> result = new ArrayList<>();
		List<Task> tasks =
				client.tasks().list(listID).execute().getItems();
		if (tasks != null) {
			for (Task task : tasks) {
				result.add(task);
			}
		}
		activity.tasksList = result;
	}

	static void run(TasksActivity tasksSample, String listID) {
		new AsyncLoadTasks(tasksSample, listID).execute();
	}
}

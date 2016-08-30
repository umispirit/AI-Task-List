package com.example.shom853.aitasklist;

import com.google.api.services.tasks.model.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sandy on 6/29/2016.
 */
class AsyncLoadTasks extends CommonAsyncTask {

	AsyncLoadTasks(TasksActivity tasksSample) {
		super(tasksSample);
	}

	@Override
	protected void doInBackground() throws IOException {
		List<String> result = new ArrayList<String>();
		List<Task> tasks =
				client.tasks().list("@default").setFields("items/title").execute().getItems();
		if (tasks != null) {
			for (Task task : tasks) {
				result.add(task.getTitle());
			}
		} else {
			result.add("No tasks.");
		}
		activity.tasksList = result;
	}

	static void run(TasksActivity tasksSample) {
		new AsyncLoadTasks(tasksSample).execute();
	}
}

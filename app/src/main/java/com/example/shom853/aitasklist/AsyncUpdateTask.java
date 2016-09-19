package com.example.shom853.aitasklist;

import com.google.api.services.tasks.model.Task;

import java.io.IOException;

/**
 * Created by Sandy on 9/19/2016.
 */
public class AsyncUpdateTask extends CommonAsyncTask{

		private final Task task;
		private final String listID;

		AsyncUpdateTask(TasksActivity tasksSample, Task task) {
			super(tasksSample);
			this.task = task;
			listID = tasksSample.currentListID;
		}

		@Override
		protected void doInBackground() throws IOException {
			client.tasks().update(listID, task.getId(), task).execute();
		}

		static void run(TasksActivity tasksSample, Task task) {
			new AsyncUpdateTask(tasksSample, task).execute();
		}
	}
package com.example.shom853.aitasklist;

import android.os.AsyncTask;
import android.view.View;

import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;

import java.io.IOException;

/**
 * Asynchronous task that also takes care of common needs, such as displaying progress,
 * authorization, exception handling, and notifying UI when operation succeeded.
 *
 * @author Yaniv Inbar
 */
abstract class CommonAsyncTask extends AsyncTask<Void, Void, Boolean> {

	final TasksActivity activity;
	final com.google.api.services.tasks.Tasks client;
	private final View progressBar;

	CommonAsyncTask(TasksActivity activity) {
		this.activity = activity;
		client = activity.service;
		progressBar = activity.findViewById(R.id.title_refresh_progress);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		activity.numAsyncTasks++;
		progressBar.setVisibility(View.VISIBLE);
	}

	@Override
	protected final Boolean doInBackground(Void... ignored) {
		try {
			doInBackground();
			return true;
		} catch (final GooglePlayServicesAvailabilityIOException availabilityException) {
			activity.showGooglePlayServicesAvailabilityErrorDialog(
					availabilityException.getConnectionStatusCode());
		} catch (UserRecoverableAuthIOException userRecoverableException) {
			activity.startActivityForResult(
					userRecoverableException.getIntent(), TasksActivity.REQUEST_AUTHORIZATION);
		} catch (IOException e) {
			Utils.logAndShow(activity, TasksActivity.TAG, e);
		}
		return false;
	}

	@Override
	protected final void onPostExecute(Boolean success) {
		super.onPostExecute(success);
		if (0 == --activity.numAsyncTasks) {
			progressBar.setVisibility(View.GONE);
		}
		if (success) {
			activity.refreshView();
		}
	}

	abstract protected void doInBackground() throws IOException;
}

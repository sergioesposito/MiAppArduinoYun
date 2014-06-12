package upm.miw.saesposito.miarduinoyun.utils;

import org.apache.http.HttpResponse;


import android.app.ProgressDialog;
import android.os.AsyncTask;

public abstract class AsyncTaskBase extends AsyncTask<Void, Void, HttpResponse> {
	protected ProgressDialog pDialog;
	protected String url;
	protected CharSequence messagePDialog;
	protected String messageWebService;
	private GestorWebServices gestorWebServices;

	public AsyncTaskBase(ProgressDialog pDialog,  CharSequence messagePDialog, String url) {
		this.pDialog = pDialog;
		this.url = url;
		this.messagePDialog = messagePDialog;
		gestorWebServices = GestorWebServices.getGestorWebServices();
	}

	/**
	 * Before starting background thread Show Progress Dialog
	 * */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pDialog.setMessage(messagePDialog);
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(true);
		pDialog.show();
	}

	/**
	 * Obtaining info
	 * 
	 * @return
	 * */
	@Override
	protected HttpResponse doInBackground(Void... params) {
		HttpResponse response = gestorWebServices
				.doInBackgroundAsincrono(url);
		return response;
	}

	/**
	 * After completing background task Dismiss the progress dialog
	 * **/
	@Override
	protected void onPostExecute(HttpResponse response) {
		// dismiss the dialog once done
		pDialog.dismiss();
		messageWebService = gestorWebServices.postExecuteAsincrono(response);
	
	}
}

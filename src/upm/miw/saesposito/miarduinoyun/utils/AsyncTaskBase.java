package upm.miw.saesposito.miarduinoyun.utils;

import org.apache.http.HttpResponse;


import android.app.ProgressDialog;
import android.os.AsyncTask;

public abstract class AsyncTaskBase extends AsyncTask<Void, Void, HttpResponse> {
	protected ProgressDialog pDialog; //para mostrar al usuario el mensaje de espera 
	protected String url;  //url del servicio web a acceder
	protected CharSequence messagePDialog; //texto del mensaje de espera
	protected String messageWebService; //respuesta del web service
	private GestorWebServices gestorWebServices; //se le asignará la única instancia del GestorWebServices

	//Constructor público
	public AsyncTaskBase(ProgressDialog pDialog,  CharSequence messagePDialog, String url) {
		this.pDialog = pDialog;
		this.url = url;
		this.messagePDialog = messagePDialog;
		gestorWebServices = GestorWebServices.getGestorWebServices();
	}

	//Tareas previas a ejecutar en la interfaz gráfica antes de la tarea en background: preparar y mostrar el mensaje de espera
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pDialog.setMessage(messagePDialog);
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(true);
		pDialog.show();
	}

	//Tarea a ejecutar en background
	@Override
	protected HttpResponse doInBackground(Void... params) {
		HttpResponse response = gestorWebServices
				.ejecutaLlamadaHttpGet(url);
		return response;
	}

	//Tareas a ejecutar cuando termine la tarea en background: quitar de la interfaz el mensaje de espera
	//y convertir la respuesta de la llamada httpget a string en formato json
	@Override
	protected void onPostExecute(HttpResponse response) {
		// dismiss the dialog once done
		pDialog.dismiss();
		messageWebService = gestorWebServices.convierteHttpResponseAString(response);
	
	}
}

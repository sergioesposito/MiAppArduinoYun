package upm.miw.saesposito.miarduinoyun.utils;

import org.apache.http.HttpResponse;

import upm.miw.saespositi.miarduinoyun.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public abstract class ActivityRedSocial extends ActivityBase {
	protected String url; //utilizado en la construcción de la url del servicio web
	
	//Sobreescritura del método onCreate de la clase ActivityBase
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {

			urlPrefsConexion = extras.getString("urlPrefsConexion");

		}

	}

	protected String getUrl() {
		return url;
	}

	protected void setUrl(String url) {
		this.url = url;
	}

	//Implementación del método getLayoutResourceId de la clase ActivityBase
	@Override
	protected int getLayoutResourceId() {
		// TODO Auto-generated method stub
		return 0;
	}

	//Método que asigna un string vacío a un EditText 
	protected void limpiarCampoTexto(View view, int idCampoTexto) {
		EditText campoTexto = (EditText) findViewById(idCampoTexto);
		campoTexto.setText("");
	}
	
	//Método que compone la url requerida para acceder al servicio web,
	//y accede al mismo instanciando a la clase anidada 
	//AccesoRedSocial (la cual hereda de la clase AsyncTaskBase) y ejecutando
	//su método execute() 
	protected void actualizarRedSocial(View view, int idTextoSubirRedSocial,
			Context context, int idToastOK, int idToastErr) {
		EditText textoSubirRedSocial = (EditText) findViewById(idTextoSubirRedSocial);
		if (textoSubirRedSocial.length() > 0) {
			ProgressDialog pDialog = new ProgressDialog(context);

			String miUrl = urlPrefsConexion + url
					+ textoSubirRedSocial.getText() + TERMINADOR;
			miUrl = miUrl.replaceAll(" ", ESPACIOENCODED);
			new AccesoRedSocial(pDialog, 
					getString(R.string.progress_title), miUrl, idToastOK,
					idToastErr).execute();

		} else {
			Toast.makeText(getApplicationContext(), R.string.campoVacio,
					Toast.LENGTH_SHORT).show();
		}
	}

	class AccesoRedSocial extends AsyncTaskBase {
		private int idToastOK; //id del mensaje al usuario en caso de operación exitosa
		private int idToastErr; //id del mensaje al usuario en caso de operación con error

		//constructor público
		public AccesoRedSocial(ProgressDialog pDialog, 
				CharSequence messagePDialog, String url, int idToastOK,
				int idToastErr) {
			super(pDialog,  messagePDialog, url);
			this.idToastOK = idToastOK;
			this.idToastErr = idToastErr;
		}

		//Sobreescribe el método onPostExecute: ejecuta el de su superclase y ejecuta el parsing del json de respuesta
		@Override
		protected void onPostExecute(HttpResponse response) {
			super.onPostExecute(response);
			parseResponseStringResultado(this.messageWebService,
					this.idToastOK, this.idToastErr);

		}

	}
}

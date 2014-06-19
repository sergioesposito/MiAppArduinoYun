package upm.miw.saesposito.miarduinoyun.activities;

import org.apache.http.HttpResponse;
import upm.miw.saespositi.miarduinoyun.R;
import upm.miw.saesposito.miarduinoyun.utils.ActivityBase;
import upm.miw.saesposito.miarduinoyun.utils.AsyncTaskBase;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class ConsultarStatusWifi extends ActivityBase {
	private static final String URLBASE = "controlar/wifi/status";//constante utilizada para la url de acceso al servicio web
	private static final String KEYJSON = "statuswifi";//clave a buscar en el json de respuesta

	//Se sobreescribe el método onCreate, ejecutando el de la superclase,  
	//recuperando la url de conexion de los extras, 
	// construyendo la url de acceso 
	//al servicio web Status wifi, y finalmente accediendo al 
	//servicio, instanciando a la clase AccesoStatusWifi 
	//y ejecutando su método execute
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {

			urlPrefsConexion = extras.getString("urlPrefsConexion");

		}
		ProgressDialog pDialog = new ProgressDialog(ConsultarStatusWifi.this);

		new AccesoStatusWifi(pDialog, 
				getString(R.string.progress_title), urlPrefsConexion + URLBASE)
				.execute();
	}

	//Sobreescribe el método de su superclase, retornando el Id del layout
	@Override
	protected int getLayoutResourceId() {
		// TODO Auto-generated method stub
		return R.layout.activity_consultar_status_wifi;
	}

	//Ejecuta el parsing del json de respuesta y actualiza el layout
	private void parseResponseString(String responseString) {
		String statusWifi =  parseResponseStringKey(responseString, KEYJSON);
		if(!statusWifi.equals(ERRORPARSING)) {
			Log.i("parseResponseString", "entrando"); 
			Log.i("parseResponseString-statusWifi", statusWifi);

			EditText campoStatusWifi = (EditText) findViewById(R.id.editTextWifiStatus);
			campoStatusWifi.setText(statusWifi);
			Log.i("parseResponseString", "asignado statusWifi, saliendo");
		} else {
			Toast.makeText(getApplicationContext(), R.string.errorServicio,
					Toast.LENGTH_SHORT).show();
		}

	}

	class AccesoStatusWifi extends AsyncTaskBase {

		//Constructor público
		public AccesoStatusWifi(ProgressDialog pDialog, 
				CharSequence messagePDialog, String url) {
			super(pDialog,  messagePDialog, url);
			// TODO Auto-generated constructor stub
		}

		//Sobreescribe el método onPostExecute: ejecuta el de su superclase 
		//y procesa la respuesta del servicio web 
		@Override
		protected void onPostExecute(HttpResponse response) {
			super.onPostExecute(response);
			parseResponseString(this.messageWebService);

		}

	}
}

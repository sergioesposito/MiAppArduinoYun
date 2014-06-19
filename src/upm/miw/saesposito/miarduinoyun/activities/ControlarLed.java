package upm.miw.saesposito.miarduinoyun.activities;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import upm.miw.saesposito.miarduinoyun.utils.ActivityBase;
import upm.miw.saesposito.miarduinoyun.utils.AsyncTaskBase;

import upm.miw.saespositi.miarduinoyun.R;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ControlarLed extends ActivityBase {

	private static final String URLBASE = "controlar/led/"; //constante utilizada para la url de acceso al servicio web
	private static final String ACCIONLEER = "leer"; //constante utilizada para la url de acceso al servicio web
	private static final String ACCIONON = "1"; //constante utilizada para la url de acceso al servicio web
	private static final String ACCIONOFF = "0"; //constante utilizada para la url de acceso al servicio web
	private String url; //url de acceso al servicio web
	private ProgressDialog pDialog; //para mostrar al usuario el mensaje de espera

	//Se sobreescribe el método onCreate, ejecutando el de la superclase, 
	//recuperando los extras de la actividad anterior, construyendo la url de acceso 
	//al servicio web Controlar Led con la acción leer, y finalmente accediendo al 
	//servicio, instanciando a la clase AccesoLed y ejecutando su método execute
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {

			urlPrefsConexion = extras.getString("urlPrefsConexion");

		}
		pDialog = new ProgressDialog(ControlarLed.this);
		url = urlPrefsConexion + URLBASE + ACCIONLEER;
		Log.i("ControlarLed.onCreate, url= ", url);
		new AccesoLed(pDialog, 
				getString(R.string.progress_title), url).execute();
	}

	//Sobreescribe el método de su superclase, retornando el Id del layout 
	@Override
	protected int getLayoutResourceId() {
		// TODO Auto-generated method stub
		return R.layout.activity_controlar_led;
	}

	//Captura del layout la acción del usuario sobre el botón Toggle, y dependiendo
	//del valor obtenido  construye la url de acceso 
	//al servicio web Controlar Led con la acción 1(ON) ó 0(OFF) y finalmente accede al 
	//servicio, instanciando a la clase AccesoLed y ejecutando su método execute
	public void onOffLed(View view) {
		Log.i("onOffLed", "onOffLed");
		ToggleButton toggleButtonOnOff = (ToggleButton) findViewById(R.id.toggleButton_on_off);
		if (!toggleButtonOnOff.isChecked()) {
			Log.i("onOffLed", "encendido, apagamos");
			url = urlPrefsConexion + URLBASE + ACCIONOFF;
		} else {
			Log.i("onOffLed", "apagado, encendemos ");
			url = urlPrefsConexion + URLBASE + ACCIONON;
		}
		Log.i("onOffLed", url);
		new AccesoLed(pDialog, getString(R.string.progress_title), url)
				.execute();
	}

	//Ejecuta el parsing del json de respuesta y actualiza el layout
	private void parseResponseString(String responseString) {
		JSONArray auxJa = null;
		int resourceBulb;
		boolean valorToggle;

		try {
			Log.i("parseResponseString", "entrando");
			auxJa = new JSONArray(responseString);
			JSONObject jo = auxJa.getJSONObject(0);
			String statusLed = jo.getString("statusled");
			Log.i("parseResponseString-statusLed", statusLed);
			if (statusLed.equals("1")) {
				Log.i("parseResponseString", "asignamos true al toggle");
				valorToggle = true;
				resourceBulb = R.drawable.bulb_on;
			} else {
				Log.i("parseResponseString", "asignamos false al toggle");
				valorToggle = false;
				resourceBulb = R.drawable.bulb_off;

			}
			Log.i("parseResponseString", "imagen bombillo");
			ImageView imageViewStatusLed = (ImageView) findViewById(R.id.imageViewStatusLed);
			imageViewStatusLed.setImageResource(resourceBulb);
			Log.i("parseResponseString", "imagen bombillo ready");

			Log.i("parseResponseString", "imagen toggle");
			ToggleButton toggleButtonOnOff = (ToggleButton) findViewById(R.id.toggleButton_on_off);
			toggleButtonOnOff.setChecked(valorToggle);
			if (toggleButtonOnOff.isChecked()) {
				Log.i("parseResponseString",
						"toggleButtonOnOff ha quedado encendido");

			} else {
				Log.i("parseResponseString",
						"toggleButtonOnOff ha quedado apagado");

			}
			Log.i("parseResponseString", "imagen toggle ready");

			Log.i("parseResponseString", "imagenes listas");
		} catch (JSONException e) {
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), R.string.errorServicio,
					Toast.LENGTH_SHORT).show();
		}

	}

	class AccesoLed extends AsyncTaskBase {

		//Constructor público
		public AccesoLed(ProgressDialog pDialog, CharSequence messagePDialog,
				String url) {
			super(pDialog, messagePDialog, url);
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

package upm.miw.saesposito.miarduinoyun.activities;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import upm.miw.saespositi.miarduinoyun.R;
import upm.miw.saesposito.miarduinoyun.utils.AsyncTaskBase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MiArduinoYunPrincipal extends Activity {
	private static final int CONTROLARYUN = 1;
	private static final int SERVICIOSYUN = 2;
	private static final int PREFERENCIAS = 3;
	private static final String URLBASE = "controlar/led/";
	private static final String ACCIONLEER = "leer";
	private String urlPrefsConexion;
	private String url;
	private ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mi_arduino_yun_principal);
		comprobarConexion();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mi_arduino_yun_principal, menu);
		return true;
	}

	private void comprobarConexion() {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		urlPrefsConexion = preferences.getString("url", "");
		if (!urlPrefsConexion.equals("")) {
			pDialog = new ProgressDialog(MiArduinoYunPrincipal.this);
			url = urlPrefsConexion + URLBASE + ACCIONLEER;
			Log.i("Principal.comprobarConexion", url);
			new PruebaConexion(pDialog,
					getString(R.string.progress_conectando), url).execute();

		} else {
			Toast.makeText(this, R.string.servicioNoConectado,
					Toast.LENGTH_SHORT).show();
		}
	}

	int parseResponseString(String responseString) {
		JSONArray auxJa = null;

		try {
			Log.i("Principal.parseResponseString", "entrando");
			auxJa = new JSONArray(responseString);
			JSONObject jo = auxJa.getJSONObject(0);
			String statusLed = jo.getString("statusled");
			Log.i("parseResponseString-statusLed", statusLed);
			if (statusLed.equals("1") || statusLed.equals("0")) {
				return 0;
			} else {
				return -1;
			}

		} catch (JSONException e) {
			e.printStackTrace();
			return -1;
		}

	}

	private Intent agregarExtras(Intent i) {
		Bundle extras = new Bundle();
		extras.putString("urlPrefsConexion", urlPrefsConexion);
		i.putExtras(extras);
		return i;
	}

	public void controlarYun(View v) {
		Intent i = new Intent(this, ControlarYun.class);
		agregarExtras(i);
		startActivityForResult(i, CONTROLARYUN);
	}

	public void serviciosYun(View v) {
		Intent i = new Intent(this, ServiciosYun.class);
		agregarExtras(i);
		startActivityForResult(i, SERVICIOSYUN);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			startActivityForResult(new Intent(this, Preferencias.class),
					PREFERENCIAS);
			return true;

		}
		return false;
	}

	class PruebaConexion extends AsyncTaskBase {

		public PruebaConexion(ProgressDialog pDialog, 
				CharSequence messagePDialog, String url) {
			super(pDialog, messagePDialog, url);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onPostExecute(HttpResponse response) {
			super.onPostExecute(response);
			parseResponseString(this.messageWebService);
			int resultado = parseResponseString(this.messageWebService);
			if (resultado == 0) {
				Toast.makeText(getApplicationContext(),
						R.string.servicioConectado, Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(),
						R.string.servicioNoConectado, Toast.LENGTH_SHORT)
						.show();

			}

		}

	}
}

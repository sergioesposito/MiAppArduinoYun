package upm.miw.saesposito.miarduinoyun.activities;

import org.apache.http.HttpResponse;
import upm.miw.saespositi.miarduinoyun.R;
import upm.miw.saesposito.miarduinoyun.utils.ActivityBase;
import upm.miw.saesposito.miarduinoyun.utils.AsyncTaskBase;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class ConsultarTemperatura extends ActivityBase {

	private static final String URLBASE = "controlar/temperatura/";
	private static final String KEYJSON = "temperatura";
	private String escalaTemperatura;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {

			urlPrefsConexion = extras.getString("urlPrefsConexion");

		}
		ProgressDialog pDialog = new ProgressDialog(ConsultarTemperatura.this);
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		escalaTemperatura = preferences.getString("escalatemperatura", "");
		
		String miUrl = urlPrefsConexion + URLBASE + escalaTemperatura;
		Log.i("ConsultarTemperatura-onCreate", "miUrl=" + miUrl);
		
		new AccesoSensorTemperatura(pDialog, 
				getString(R.string.progress_title), miUrl).execute();
	}

	@Override
	protected int getLayoutResourceId() {
		// TODO Auto-generated method stub
		return R.layout.activity_consultar_temperatura;
	}

	private void parseResponseString(String responseString) {
		String temperatura = parseResponseStringKey(responseString, KEYJSON);
		if (!temperatura.equals(ERRORPARSING)) {
			Log.i("parseResponseString-temperatura", temperatura);

			Typeface myTypeface = Typeface.createFromAsset(this.getAssets(),
					"DS-DIGI.TTF");

			EditText campoTemperatura = (EditText) findViewById(R.id.editText1);
			campoTemperatura.setText(temperatura + "º" + escalaTemperatura);
			campoTemperatura.setTypeface(myTypeface);

			Log.i("parseResponseString", "asignada temperatura, saliendo");
		} else {
			Toast.makeText(getApplicationContext(), R.string.errorServicio,
					Toast.LENGTH_SHORT).show();
		}

	}

	class AccesoSensorTemperatura extends AsyncTaskBase {

		public AccesoSensorTemperatura(ProgressDialog pDialog, 
				CharSequence messagePDialog, String url) {
			super(pDialog, messagePDialog, url);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onPostExecute(HttpResponse response) {
			super.onPostExecute(response);
			parseResponseString(this.messageWebService);

		}

	}

}
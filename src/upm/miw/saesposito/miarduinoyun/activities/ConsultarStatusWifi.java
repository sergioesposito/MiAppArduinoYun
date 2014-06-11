package upm.miw.saesposito.miarduinoyun.activities;

import org.apache.http.HttpResponse;
import upm.miw.saespositi.miarduinoyun.R;
import upm.miw.saesposito.miarduinoyun.utils.ActivityBase;
import upm.miw.saesposito.miarduinoyun.utils.AsyncTaskBase;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class ConsultarStatusWifi extends ActivityBase {
	private static final String URLBASE = "controlar/wifi/status";
	private static final String KEYJSON = "statuswifi";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {

			urlPrefsConexion = extras.getString("urlPrefsConexion");

		}
		ProgressDialog pDialog = new ProgressDialog(ConsultarStatusWifi.this);

		new AccesoStatusWifi(pDialog, ConsultarStatusWifi.this,
				getString(R.string.progress_title), urlPrefsConexion + URLBASE)
				.execute();
	}

	@Override
	protected int getLayoutResourceId() {
		// TODO Auto-generated method stub
		return R.layout.activity_consultar_status_wifi;
	}

	void parseResponseString(String responseString) {
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

		public AccesoStatusWifi(ProgressDialog pDialog, Context context,
				CharSequence messagePDialog, String url) {
			super(pDialog, context, messagePDialog, url);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onPostExecute(HttpResponse response) {
			super.onPostExecute(response);
			parseResponseString(this.messageWebService);

		}

	}
}

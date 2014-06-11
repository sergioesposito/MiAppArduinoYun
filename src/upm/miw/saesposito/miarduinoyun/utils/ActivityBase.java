package upm.miw.saesposito.miarduinoyun.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import upm.miw.saespositi.miarduinoyun.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

public abstract class ActivityBase extends Activity {
	protected String urlPrefsConexion;
	protected static final String ERRORPARSING = "ERRORPARSING";
	protected static final String TERMINADOR = "%13";
	protected static final String SEPARADOR = "&";
	protected static final String ESPACIOENCODED = "%20";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutResourceId());
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
					.detectNetwork().permitNetwork().build());
		}
	}

	protected abstract int getLayoutResourceId();

	protected String parseResponseStringKey(String responseString, String key) {
		JSONArray auxJa = null;

		try {
			auxJa = new JSONArray(responseString);
			JSONObject jo = auxJa.getJSONObject(0);
			String resultado = jo.getString(key);
			return resultado;
		} catch (JSONException e) {
			e.printStackTrace();
			return ERRORPARSING;
		}
	}

	protected void parseResponseStringResultado(String responseString,
			int idToastOK, int idToastErr) {
		JSONArray auxJa = null;

		try {
			Log.i("parseResponseStringResultado", "entrando");
			Log.i("parseResponseStringResultado-responseString", responseString);
			auxJa = new JSONArray(responseString);
			JSONObject jo = auxJa.getJSONObject(0);
			String resultado = jo.getString("Resultado");
			Log.i("parseResponseStringResultado", resultado);
			if (resultado.equals("0")) {
				Toast.makeText(getApplicationContext(), idToastOK,
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(), idToastErr,
						Toast.LENGTH_SHORT).show();

			}

		} catch (JSONException e) {
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), R.string.errorServicio,
					Toast.LENGTH_SHORT).show();
		}
	}

}

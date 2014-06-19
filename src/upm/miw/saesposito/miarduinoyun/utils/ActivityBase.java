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
	protected String urlPrefsConexion; //para que las actividades reciban el valor de la url base de acceso a los servicios web 
	protected static final String ERRORPARSING = "ERRORPARSING"; //valor a retornar si en el json de la respuesta no se encuentra la clave buscada
	protected static final String TERMINADOR = "%13"; //utilizado en la construcción de la url de acceso a los servicios
	protected static final String SEPARADOR = "&"; //utilizado en la construcción de la url de acceso a los servicios
	protected static final String ESPACIOENCODED = "%20";//utilizado en la construcción de la url de acceso a los servicios

	//Sobreescritura del método onCreate de la clase Activity
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutResourceId());
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
					.detectNetwork().permitNetwork().build());
		}
	}

	//Este método será implementado por las clases concretas, en cada caso retornará el Id del layout de la actividad
	protected abstract int getLayoutResourceId();

	//Método que obtiene el valor asociado a una clave dentro de una cadena con formato json
	//Será utilizado por las clases concretas que consuman un servicio web cuyo
	//json de respuesta no sea de la forma [{"Resultado":"n"}]
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

	//Método que obtiene el valor asociado a la clave "Resultado" dentro de una cadena con formato json
	//y que dependiendo del mismo muestra al usuario un mensaje u otro
	//Será utilizado por las clases concretas que consuman un servicio web cuyo
	//json de respuesta sea de la forma [{"Resultado":"n"}]
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

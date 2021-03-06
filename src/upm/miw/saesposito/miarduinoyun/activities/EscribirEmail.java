package upm.miw.saesposito.miarduinoyun.activities;

import org.apache.http.HttpResponse;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import upm.miw.saespositi.miarduinoyun.R;
import upm.miw.saesposito.miarduinoyun.utils.ActivityBase;
import upm.miw.saesposito.miarduinoyun.utils.AsyncTaskBase;

public class EscribirEmail extends ActivityBase {
	private static final String URLBASE = "servicios/email" + "%3F";//constante utilizada para la url de acceso al servicio web
	private static final String NOMBREPARAM1 = "de="; //primer parametro de la llamada httpget
	private static final String NOMBREPARAM2 = "para="; //segundo parametro de la llamada httpget
	private static final String NOMBREPARAM3 = "asunto="; //tercer parametro de la llamada httpget
	private static final String NOMBREPARAM4 = "texto="; //cuarto parametro de la llamada httpget
	

	//Se sobreescribe el m�todo onCreate, ejecutando el de la superclase,  
	//recuperando la url de conexion de los extras, 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {

			urlPrefsConexion = extras.getString("urlPrefsConexion");

		}
	}

	//Sobreescribe el m�todo de su superclase, retornando el Id del layout
	@Override
	protected int getLayoutResourceId() {
		return R.layout.activity_escribir_email;
	}

	//valida si la cadena de caracteres recibida tiene el formato de una direccion email 
	private final static boolean isValidEmail(CharSequence target) {
		return !TextUtils.isEmpty(target)
				&& android.util.Patterns.EMAIL_ADDRESS.matcher(target)
						.matches();
	}

	//Extrae del layout la direccion destino y de las preferencias la direccion origen,
	//si ambas tienen formato correcto extrae del layout el asunto y el texto,
	//forma la url de acceso al servicio, y ejecuta el acceso al mismo
	//instanciando a la clase AccesoEscribirEmail 
	//y ejecutando su m�todo execute
	public void escribirEmail(View view) {
		EditText para = (EditText) findViewById(R.id.editTextPara);
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		String emailDe = preferences.getString("cuentaemail", "");
		if (isValidEmail(emailDe)) {
			if (isValidEmail(para.getText())) {
				ProgressDialog pDialog = new ProgressDialog(EscribirEmail.this);

				EditText asunto = (EditText) findViewById(R.id.editTextAsunto);
				EditText texto = (EditText) findViewById(R.id.editTextTexto);

				String miUrl = urlPrefsConexion + URLBASE + NOMBREPARAM1
						+ emailDe + SEPARADOR + NOMBREPARAM2
						+ para.getText() + SEPARADOR + NOMBREPARAM3
						+ asunto.getText() + SEPARADOR + NOMBREPARAM4
						+ texto.getText() + TERMINADOR;
				miUrl = miUrl.replaceAll(" ", ESPACIOENCODED);
				Log.i("escribirEmail", "miUrl=" + miUrl);
				new AccesoEscribirEmail(pDialog, 
						getString(R.string.progress_title), miUrl).execute();

			} else {
				Toast.makeText(getApplicationContext(),
						R.string.direccionDestinoInvalida, Toast.LENGTH_SHORT)
						.show();
			}
		} else {
			Toast.makeText(getApplicationContext(),
					R.string.direccionOrigenInvalida, Toast.LENGTH_SHORT)
					.show();
		}

	}

	
	class AccesoEscribirEmail extends AsyncTaskBase {

		//Constructor p�blico
		public AccesoEscribirEmail(ProgressDialog pDialog, 
				CharSequence messagePDialog, String url) {
			super(pDialog, messagePDialog, url);
			// TODO Auto-generated constructor stub
		}

		//Sobreescribe el m�todo onPostExecute: ejecuta el de su superclase 
		//y procesa la respuesta del servicio web
		@Override
		protected void onPostExecute(HttpResponse response) {
			super.onPostExecute(response);
			parseResponseStringResultado(this.messageWebService,
					R.string.emailEnviado, R.string.emailNoEnviado);

		}

	}
}

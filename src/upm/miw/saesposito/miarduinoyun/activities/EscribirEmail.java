package upm.miw.saesposito.miarduinoyun.activities;

import org.apache.http.HttpResponse;

import android.app.ProgressDialog;
import android.content.Context;
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
	private static final String URLBASE = "servicios/email" + "%3F";
	private static final String NOMBREPARAM1 = "de=";
	private static final String NOMBREPARAM2 = "para=";
	private static final String NOMBREPARAM3 = "asunto=";
	private static final String NOMBREPARAM4 = "texto=";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {

			urlPrefsConexion = extras.getString("urlPrefsConexion");

		}
	}

	@Override
	protected int getLayoutResourceId() {
		return R.layout.activity_escribir_email;
	}

	public final static boolean isValidEmail(CharSequence target) {
		return !TextUtils.isEmpty(target)
				&& android.util.Patterns.EMAIL_ADDRESS.matcher(target)
						.matches();
	}

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
				new AccesoEscribirEmail(pDialog, EscribirEmail.this,
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

		public AccesoEscribirEmail(ProgressDialog pDialog, Context context,
				CharSequence messagePDialog, String url) {
			super(pDialog, context, messagePDialog, url);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onPostExecute(HttpResponse response) {
			super.onPostExecute(response);
			parseResponseStringResultado(this.messageWebService,
					R.string.emailEnviado, R.string.emailNoEnviado);

		}

	}
}

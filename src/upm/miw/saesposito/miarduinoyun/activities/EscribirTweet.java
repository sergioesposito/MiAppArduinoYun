package upm.miw.saesposito.miarduinoyun.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import upm.miw.saespositi.miarduinoyun.R;
import upm.miw.saesposito.miarduinoyun.utils.ActivityRedSocial;

public class EscribirTweet extends ActivityRedSocial {
	private static final String URLBASE = "servicios/twitter" + "%3F" + "texto="; //constante utilizada para la url de acceso al servicio web
	private static final int MAXIMALONGITUDTWEET = 140; //para controlar la longitud del texto del tweet
	private int caracteresRestantes; //n�mero de caracteres que faltan para alcanzar la m�xima longitud

	//Sobreescribe el m�todo onCreate, ejecutando el de la superclase,
	//inicializando el n�mero de caracteres restantes en el m�ximo
	//e invocando al m�todo que crea el listener que controlar�
	//la longitud del texto del tweet
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.caracteresRestantes = MAXIMALONGITUDTWEET;
		crearTextWatcherControlCaracteresTweet();

	}

	//Sobreescribe el m�todo de su superclase, retornando el Id del layout
	@Override
	protected int getLayoutResourceId() {
		// TODO Auto-generated method stub
		return R.layout.activity_escribir_tweet;
	}

	//Crea un listener que monitorear� los cambios en el texto
	//del tweet para controlar que la longitud del mismo no
	//exceda la m�xima permitida
	private void crearTextWatcherControlCaracteresTweet() {
		final EditText ET = (EditText) findViewById(R.id.editText1);
		final EditText ET2 = (EditText) findViewById(R.id.editText2);
		ET.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				caracteresRestantes = MAXIMALONGITUDTWEET
						- ET.getText().length();
				ET2.setText(Integer.toString(caracteresRestantes));
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	//Asigna un string vac�o al EditText del layout donde se escribe el texto del tweet
	public void limpiarTextoTweet(View view) {
		limpiarCampoTexto(view, R.id.editText1);
	}

	//Utiliza los m�todos de su superclase para asignar la url del servicio web, 
	//acceder al mismo y mostrar en el layout el resultado 
	public void escribirTweet(View view) {
		super.setUrl(URLBASE);
		actualizarRedSocial(view, R.id.editText1, EscribirTweet.this,
				R.string.tweetEnviado, R.string.tweetNoEnviado);

	}

}

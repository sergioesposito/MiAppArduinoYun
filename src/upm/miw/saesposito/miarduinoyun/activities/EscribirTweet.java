package upm.miw.saesposito.miarduinoyun.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import upm.miw.saespositi.miarduinoyun.R;
import upm.miw.saesposito.miarduinoyun.utils.ActivityRedSocial;

public class EscribirTweet extends ActivityRedSocial {
	private static final String URLBASE = "servicios/twitter" + "%3F" + "texto=";
	private static final int MAXIMALONGITUDTWEET = 140;
	private int caracteresRestantes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.caracteresRestantes = MAXIMALONGITUDTWEET;
		crearTextWatcherControlCaracteresTweet();

	}

	@Override
	protected int getLayoutResourceId() {
		// TODO Auto-generated method stub
		return R.layout.activity_escribir_tweet;
	}

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

	public void limpiarTextoTweet(View view) {
		limpiarCampoTexto(view, R.id.editText1);
	}

	public void escribirTweet(View view) {
		super.setUrl(URLBASE);
		actualizarRedSocial(view, R.id.editText1, EscribirTweet.this,
				R.string.tweetEnviado, R.string.tweetNoEnviado);

	}

}

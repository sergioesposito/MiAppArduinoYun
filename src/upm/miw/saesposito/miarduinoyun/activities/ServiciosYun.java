package upm.miw.saesposito.miarduinoyun.activities;

import upm.miw.saespositi.miarduinoyun.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

public class ServiciosYun extends Activity {
	private final int ENVIAREMAIL = 1; //constante que identifica a la actividad Escribir Email
	private final int ESCRIBIRTWEET = 2; //constante que identifica a la actividad Escribir Tweet
	private final int STATUSFACEBOOK = 3; //constante que identifica a la actividad Status Facebook
	private Bundle extras; //para recoger los extras de la actividad anterior y pasarlos a la siguiente 

	//Sobreescribe el onCreate, ejecuta el de la superclase, despliega el layout 
	//y recoje los extras de la actividad principal 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_servicios_yun);
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
					.detectNetwork().permitNetwork().build());
		}
		extras = getIntent().getExtras();
	}

	//Arranca la actividad Escribir Email
	public void escribirEmail(View v) {
		Intent i = new Intent(this, EscribirEmail.class);
		i.putExtras(extras);
		startActivityForResult(i, ENVIAREMAIL);
	}

	//Arranca la actividad Escribir Tweet 
	public void escribirTweet(View v) {
		Intent i = new Intent(this, EscribirTweet.class);
		i.putExtras(extras);
		startActivityForResult(i, ESCRIBIRTWEET);
	}

	//Arranca la actividad Status Facebook
	public void statusFacebook(View v) {
		Intent i = new Intent(this, StatusFacebook.class);
		i.putExtras(extras);
		startActivityForResult(i, STATUSFACEBOOK);
	}

}

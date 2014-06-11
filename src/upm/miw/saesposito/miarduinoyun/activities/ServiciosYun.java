package upm.miw.saesposito.miarduinoyun.activities;

import upm.miw.saespositi.miarduinoyun.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

public class ServiciosYun extends Activity {
	private final int ENVIAREMAIL = 1;
	private final int ESCRIBIRTWEET = 2;
	private final int STATUSFACEBOOK = 3;
	private Bundle extras;

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

	public void escribirEmail(View v) {
		Intent i = new Intent(this, EscribirEmail.class);
		i.putExtras(extras);
		startActivityForResult(i, ENVIAREMAIL);
	}

	public void escribirTweet(View v) {
		Intent i = new Intent(this, EscribirTweet.class);
		i.putExtras(extras);
		startActivityForResult(i, ESCRIBIRTWEET);
	}

	public void statusFacebook(View v) {
		Intent i = new Intent(this, StatusFacebook.class);
		i.putExtras(extras);
		startActivityForResult(i, STATUSFACEBOOK);
	}

}

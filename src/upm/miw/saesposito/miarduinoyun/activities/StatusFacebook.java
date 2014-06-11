package upm.miw.saesposito.miarduinoyun.activities;

import android.view.View;
import upm.miw.saespositi.miarduinoyun.R;
import upm.miw.saesposito.miarduinoyun.utils.ActivityRedSocial;

public class StatusFacebook extends ActivityRedSocial {
	private static final String URLBASE = "servicios/facebook" + "%3F" + "texto=";

	@Override
	protected int getLayoutResourceId() {
		// TODO Auto-generated method stub
		return R.layout.activity_status_facebook;
	}

	public void limpiarTextoStatusFB(View view) {
		limpiarCampoTexto(view, R.id.editText1);
	}

	public void statusFacebook(View view) {
		super.setUrl(URLBASE);
		actualizarRedSocial(view, R.id.editText1, StatusFacebook.this,
				R.string.statusActualizado, R.string.statusNoActualizado);
	}

}

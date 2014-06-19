package upm.miw.saesposito.miarduinoyun.activities;

import android.view.View;
import upm.miw.saespositi.miarduinoyun.R;
import upm.miw.saesposito.miarduinoyun.utils.ActivityRedSocial;

public class StatusFacebook extends ActivityRedSocial {
	private static final String URLBASE = "servicios/facebook" + "%3F" + "texto="; //constante utilizada para la url de acceso al servicio web

	//Sobreescribe el m�todo de su superclase, retornando el Id del layout
	@Override
	protected int getLayoutResourceId() {
		// TODO Auto-generated method stub
		return R.layout.activity_status_facebook;
	}

	//Asigna un string vac�o al EditText del layout donde se escribe el texto del status que subir� a Facebook
	public void limpiarTextoStatusFB(View view) {
		limpiarCampoTexto(view, R.id.editText1);
	}

	
	//Utiliza los m�todos de su superclase para asignar la url del servicio web, 
	//acceder al mismo y mostrar en el layout el resultado 
	public void statusFacebook(View view) {
		super.setUrl(URLBASE);
		actualizarRedSocial(view, R.id.editText1, StatusFacebook.this,
				R.string.statusActualizado, R.string.statusNoActualizado);
	}

}

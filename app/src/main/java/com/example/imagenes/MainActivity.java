package com.example.imagenes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {
	Button btnCamara;
	Button btnGaleria;
	ImageView img;

	static final int RESULTADO_CAMARA		= 0;
	static final int RESULTADO_GALERIA		= 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnCamara = findViewById( R.id.btnCamara );
		btnGaleria = findViewById( R.id.btnGaleria );
		img = findViewById( R.id.img );

		btnCamara.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
				if( intent.resolveActivity( getPackageManager() ) != null )
					startActivityForResult( intent, RESULTADO_CAMARA );
				else	Toasty.error( getApplicationContext(), "No existe acceso a la cámara.", Toast.LENGTH_SHORT, true ).show();
			}
		});

		btnGaleria.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
				//startActivity( intent );
				startActivityForResult( intent, RESULTADO_GALERIA );	// >>> onActivityResult
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch( requestCode ){
			case RESULTADO_GALERIA: {
				if( resultCode == Activity.RESULT_OK )
						ponerFoto( data.getDataString() );
				else	Toasty.error( getApplicationContext(), "No se ha podido cargar la foto.", Toast.LENGTH_SHORT, true ).show();
				break;
			}
			case RESULTADO_CAMARA: {
				if( resultCode == Activity.RESULT_OK ){
					Bundle extras = data.getExtras();
					Bitmap bitmap = (Bitmap)extras.get( "data" );	// View -> Tool Windows -> Device File Explorer >>> data/data/app
					img.setImageBitmap( bitmap );
				}
				else	Toasty.error( getApplicationContext(), "No se ha podido cargar la foto.", Toast.LENGTH_SHORT, true ).show();
				break;
			}
		}
	}

	public void ponerFoto( String uri ) {
		if( uri != null && ! uri.isEmpty() && ! uri.equals( "null" ) ){
			img.setImageURI( Uri.parse( uri ) );
		}
	}
}

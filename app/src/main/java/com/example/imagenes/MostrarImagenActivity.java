package com.example.imagenes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class MostrarImagenActivity extends AppCompatActivity {

	Button btnMostrar;
	ImageView imgMostrar;
	int idImage = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mostrar_imagen);

		btnMostrar = findViewById( R.id.btnMostrar );
		imgMostrar = findViewById( R.id.imgMostrar );

		btnMostrar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if( Datos.listaImagenes.size() < 1 ){
					Toasty.warning( getApplicationContext(), "No hay ninguna foto.", Toast.LENGTH_SHORT, true ).show();
					return;
				}
				if( idImage >= Datos.listaImagenes.size() )		idImage = 0;
				imgMostrar.setImageBitmap( Datos.listaImagenes.get( idImage++ ) );
			}
		});
	}
}

package com.example.andre_000.mrservice;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class MyActivity extends Activity  {

        static boolean errored = false;

        Button entrar;
        TextView registro;
        EditText contrasena,cedula;
        String contra,cedu;
        TextView statusTV;
        ProgressBar webservicePG;
        boolean loginStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        entrar = (Button) findViewById(R.id.ingresar);
        registro = (TextView) findViewById(R.id.registrar );
        //statusTV = (TextView) findViewById(R.id.tv_result);
        //webservicePG = (ProgressBar) findViewById(R.id.progressBar1);
        contrasena=(EditText) findViewById(R.id.contrasena);
        cedula=(EditText) findViewById(R.id.cedula);
    }

    public void registrar(View view) {
        Intent i = new Intent (this, registro.class);
        startActivity(i);
    }

    public void administrador(View v) {
        Intent i = new Intent (this, Adminswipe.class);
        startActivity(i);
    }

    public void mapa(View v){
        Intent i= new Intent (this, UserMain.class);
        startActivity(i);
    }

    public void usuarioadmin(View v){
        Intent i= new Intent (this, Usuarioadmin.class);
        startActivity(i);
    }

    public void borrar(View v){
        contrasena.setText("");
        cedula.setText("");
    }

        public void login(View view){

        //AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Administracion", null, 1);
        //SQLiteDatabase bd = admin.getWritableDatabase();

        /*cedu = cedula.getText().toString();
        contra = contrasena.getText().toString();
        String contra2= encryptacion.md5(contra);
        if (contra.equals("") || cedu.equals("")){
            Toast.makeText(this, "Llene todos los campos", Toast.LENGTH_SHORT).show();
        }
        else {

            Cursor fila = bd.rawQuery("SELECT * FROM usuarios WHERE  cedula='"+cedu+"' AND password='"+contra2+"'",null);
            if (fila.moveToFirst()){
                fila.moveToFirst();
                cedu = fila.getString(0);
                cedu = contra;

                Toast.makeText(this, "Usuario Correcto", Toast.LENGTH_SHORT).show();
                Intent i= new Intent (this, Index.class);
                startActivity(i);

                contrasena.setText("");
                cedula.setText("");

            }
            else{
                Toast.makeText(this, "Usuario no Registrado", Toast.LENGTH_SHORT).show();
                bd.close();
            }

        }*/

    }

}

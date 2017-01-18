package com.example.andre_000.mrservice;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


public class registro extends Activity {

    EditText nombre,apellido,email,cedula,address,phone,password,password2;
    String name,lname,correo,ci,direccion,telefono,pass,pass2;
    String id_rol;
    private UserSignUpTask mAuthTask = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nombre=(EditText) findViewById(R.id.nombre);
        apellido=(EditText) findViewById(R.id.apellido);
        email=(EditText) findViewById(R.id.email);
        cedula=(EditText) findViewById(R.id.cedula);
        address=(EditText) findViewById(R.id.address);
        phone=(EditText) findViewById(R.id.phone);
        password=(EditText) findViewById(R.id.password);
        password2=(EditText) findViewById(R.id.password2);

    }

    public void StartActivity(){
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    public void UserSignUp(View view) {
        name = nombre.getText().toString();
        lname = apellido.getText().toString();
        correo = email.getText().toString();
        ci = cedula.getText().toString();
        direccion = address.getText().toString();
        telefono = phone.getText().toString();
        pass = password.getText().toString();
        pass2 = password2.getText().toString();
        id_rol = "2";

        mAuthTask = new UserSignUpTask(name, lname, correo, ci, direccion, telefono, pass, pass2,id_rol);
        mAuthTask.execute((Void) null);
    }

    public class UserSignUpTask extends AsyncTask<Void, Void, Boolean> {

        private final String mNombre;
        private final String mApellido;
        private final String mCorreo;
        private final String mCedula;
        private final String mDireccion;
        private final String mTelefono;
        private final String mIdrol;
        private final String mPassword;
        private final String mPassword2;


        UserSignUpTask(String nombre, String apellido, String correo, String cedula, String direccion, String telefono, String password, String password2, String idrol ) {
            mNombre = nombre;
            mApellido = apellido;
            mCorreo = correo;
            mCedula = cedula;
            mDireccion = direccion;
            mTelefono = telefono;
            mPassword = password;
            mPassword2 = password2;
            mIdrol = idrol;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Map<String, String> map = new HashMap<>();
            map.put("name", mNombre);
            map.put("apellido", mApellido);
            map.put("email", mCorreo);
            map.put("cedula", mCedula);
            map.put("direccion", mDireccion);
            map.put("telefono", mTelefono);
            map.put("id_rol", mIdrol);
            map.put("password", mPassword);
            map.put("password2", mPassword2);

            WebRequest request = new WebRequest("android/registro");
            request.setParameters(map);

            String response = null;
            try {
                response = request.getResponse();
            } catch (IOException | URISyntaxException e) {
                return false;
            }

            if(response.equals("fallo"))
                return false;
            else{
                //user = new Usuario();
                return true;
            }

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            //showProgress(false);

            if (success) {
                Toast.makeText(getApplicationContext(), "Usuario Registrado!", Toast.LENGTH_SHORT).show();
                StartActivity();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Llenar todos los campos", Toast.LENGTH_SHORT).show();
            }
        }


        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }


}

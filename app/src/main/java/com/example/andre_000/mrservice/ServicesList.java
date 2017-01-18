package com.example.andre_000.mrservice;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class ServicesList extends Activity {

    //public static ArrayList<Servicios> services;
    private ServicesTask mAuthTask = null;
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

    public static ArrayList<Servicios> listservicios; // Aqui es donde debe ir, para no dar tantas vueltas para accederlo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_list);


        /*items.add(new Anime(R.drawable.angel, "Angel Beats", 230));
        items.add(new Anime(R.drawable.death, "Death Note", 456));
        items.add(new Anime(R.drawable.fate, "Fate Stay Night", 342));
        items.add(new Anime(R.drawable.nhk, "Welcome to the NHK", 645));
        items.add(new Anime(R.drawable.suzumiya, "Suzumiya Haruhi", 459));*/

       // ArrayList<Servicios> services = new ArrayList<>(); // Esto no lo asignas en ningun lado, no lo estas usando

        // Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // Primero asignas el onclick listener, aunque pensandolo bien, creo si podria ir en el oncreate
        recycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // coño sera ese? no veo el indice seleccionado, como obtenerlo
            }
        });

        // Crear un nuevo adaptador
        // Si lo pones aqui, el array services estara nulo, por eso creo que da el error
       // adapter = new ServiceAdapter(services); // Por que aqui?, ya el postexecute lo hacn
        //recycler.setAdapter(adapter);

        ServicesTask conexion = new ServicesTask(getApplicationContext());
        conexion.execute();
    }

    public class ServicesTask extends AsyncTask<Void, Void, String> {

        private Context context;

        public ServicesTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(Void... params) {

            WebRequest request = new WebRequest("android/verservicio");
            //String nombre, rif, direccion, telefono;
            String response;
            try {
                response = request.getResponse();
            } catch (IOException | URISyntaxException e) {
                return null;
            }

            if (response.equals("fallo"))
                return null;
            else {
                return response;
            }

        }

        @Override
        protected void onPostExecute(final String success) {
            mAuthTask = null;
            //showProgress(false);

            if (success != null) {
                listservicios = StringToServicios(success);
                recycler.setAdapter(new ServiceAdapter(listservicios));
                //Toast.makeText(getApplicationContext(), "Usuario Registrado!", Toast.LENGTH_SHORT).show();

                //finishba a();


            } else {
                Toast.makeText(getApplicationContext(), "Llenar todos los campos", Toast.LENGTH_SHORT).show();
            }
        }


        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }

        private ArrayList<Servicios> StringToServicios(String result) {
            ArrayList<Servicios> listservicios = new ArrayList<>();

            try {
                JSONArray datos = new JSONArray(result);

                for (int i = 0; i < datos.length(); i++) {
                    Servicios services = new Servicios();
                    services.FillFromJson(datos.getJSONObject(i).toString());

                    listservicios.add(services);

                }
            } catch(JSONException e){
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            return listservicios;
        }

    }

}

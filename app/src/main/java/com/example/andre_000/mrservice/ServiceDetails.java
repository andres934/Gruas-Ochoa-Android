package com.example.andre_000.mrservice;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class ServiceDetails extends Activity {
    public static int indiceSeleccionado;
    public static ArrayList<Choferes> listchoferes;

    TextView Origen, Reforigen, Destino, Refdestino, Tiposervicio, Tipocliente, Monto;
    Spinner Chofer;
    private ChoferesTask mChoferesTask = null;
    Button enviarservicio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);

        Origen = (TextView)findViewById(R.id.Origen);
        Reforigen = (TextView) findViewById(R.id.Reforigen);
        Destino = (TextView) findViewById(R.id.Destino);
        Refdestino = (TextView) findViewById(R.id.Reforigen);
        Tiposervicio = (TextView) findViewById(R.id.Tiposervicio);
        Tipocliente = (TextView) findViewById(R.id.Tipocliente);
        Monto = (TextView) findViewById(R.id.Monto);
        Chofer = (Spinner)findViewById(R.id.Chofer);
        enviarservicio = (Button)findViewById(R.id.enviarservicio);

        Origen.setText(ServicesList.listservicios.get(indiceSeleccionado).origen);
        Reforigen.setText(ServicesList.listservicios.get(indiceSeleccionado).reforigen);
        Destino.setText(ServicesList.listservicios.get(indiceSeleccionado).destino);
        Refdestino.setText(ServicesList.listservicios.get(indiceSeleccionado).refdestino);
        Tiposervicio.setText(ServicesList.listservicios.get(indiceSeleccionado).tipo_servicio);
        Tipocliente.setText(ServicesList.listservicios.get(indiceSeleccionado).tipo_cliente);
        Monto.setText(ServicesList.listservicios.get(indiceSeleccionado).monto);


        // Luego aqui vas llenando:
        //ServicesList.listservicios.get(indiceSeleccionado).users_id
        // Blah blah

        ChoferesTask conexion = new ChoferesTask(getApplicationContext());
        conexion.execute();
    }

    public class ChoferesTask extends AsyncTask<Void, Void, String> {
        private Context context;

        public ChoferesTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(Void... params) {

            WebRequest request = new WebRequest("android/verchofer");
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
            mChoferesTask = null;
            //showProgress(false);

            if (success != null) {
                listchoferes = StringToChoferes(success);
                ArrayAdapter<Choferes> adapter = new ArrayAdapter<>(this.context,android.R.layout.simple_spinner_item, listchoferes);

                Chofer.setAdapter(adapter);
                Toast.makeText(getApplicationContext(), "Choferes Recogidas!", Toast.LENGTH_SHORT).show();
                //finish();
            } else {
                Toast.makeText(getApplicationContext(), "Error en choferes", Toast.LENGTH_SHORT).show();
            }
        }


        @Override
        protected void onCancelled() {
            mChoferesTask = null;
        }

        private ArrayList<Choferes> StringToChoferes(String result) {
            ArrayList<Choferes> listchoferes = new ArrayList<>();

            try {
                JSONArray datos = new JSONArray(result);

                for (int i = 0; i < datos.length(); i++) {
                    // Aqui debes instanciar la clase empresa, estaba nula, por lo tanto no podias acceder al metodo fillFromJson
                    // Tampoco es necesaria que la declares static arriba, ya que se sustituye en cada ciclo aqui
                    // Solo la listEmpresas debe ser static, que es la que asignas en el Task
                    Choferes driver = new Choferes();
                    driver.FillFromJson(datos.getJSONObject(i).toString());

                    listchoferes.add(driver);

                }
            } catch(JSONException | IllegalAccessException e){
                e.printStackTrace();
            }

            return listchoferes;
        }

    }

    public void mensaje(View v){
        Toast.makeText(getApplicationContext(), "Servicio Enviado Exitosamente!", Toast.LENGTH_SHORT).show();
    }

}

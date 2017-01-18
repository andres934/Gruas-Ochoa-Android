package com.example.andre_000.mrservice;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;


public class FragmentUsuarios extends Fragment {
    View rootView;

    private ChoferesTask mChoferesTask = null;
    public static ArrayList<Choferes> listchoferes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_usuarios, container, false);
        PieChart chart = (PieChart)rootView.findViewById(R.id.chart);
        chart.setDescription("Informacion guardada: ");
        //chart.setBackgroundColor(Color.LTGRAY);
        chart.setDescriptionTextSize(15);
        //Crear un arraylist con string y nombres
        ArrayList<String> valores1 = new ArrayList<String>();
        valores1.add("Choferes \nPagados");
        valores1.add("Servicios \nRealizados");
        //Crea un arraylist con entradas que seran las que se muestren


        /*AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila1 = bd.rawQuery("SELECT count(cedula) FROM usuarios  ",null);
        fila1.moveToFirst();
        int usuarios = Integer.parseInt(fila1.getString(0));



        AdminSQLiteOpenHelper admin2 = new AdminSQLiteOpenHelper(this, "Administracion", null, 1);
        SQLiteDatabase bd1 = admin2.getWritableDatabase();
        Cursor fila2 = bd1.rawQuery("SELECT count(id_chofer) FROM registrochoferes ",null);
        fila2.moveToFirst();
        int usuarios4 = Integer.parseInt(fila2.getString(0));
        //Me calentastes el aguacate mamawebo




        AdminSQLiteOpenHelper admin3 = new AdminSQLiteOpenHelper(this, "Administracion", null, 1);
        SQLiteDatabase bd2 = admin3.getWritableDatabase();
        Cursor fila3 = bd2.rawQuery("SELECT count(servicio) FROM empresas  ",null);
        fila3.moveToFirst();
        int usuarios3 = Integer.parseInt(fila3.getString(0));

*/      ChoferesTask conexion = new ChoferesTask(getActivity().getApplicationContext());
        conexion.execute();

        ArrayList<Entry> entrys2 = new ArrayList<>();
        Entry valor4 = new Entry(2,4);//Potrero Modificar el 8 por el cursor de la base de datos
        Entry valor6 = new Entry(3,6); //Compras

        entrys2.add(valor4);
        entrys2.add(valor6);



        //Se crea el PieDataSet
        PieDataSet setPie2 = new PieDataSet(entrys2,"");

        //Se crea la PieData
        PieData data = new PieData(valores1,setPie2);
        setPie2.setColors(ColorTemplate.COLORFUL_COLORS);

        //Se asigna la pie data
        chart.setData(data);


        return rootView;
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

                Toast.makeText(getActivity().getApplicationContext(), "Choferes Recogidas!", Toast.LENGTH_SHORT).show();
                //finish();
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Error en choferes", Toast.LENGTH_SHORT).show();
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
                    Choferes chofer = new Choferes();
                    chofer.FillFromJson(datos.getJSONObject(i).toString());

                    listchoferes.add(chofer);

                }
            } catch(JSONException | IllegalAccessException e){
                e.printStackTrace();
            }

            return listchoferes;
        }

    }
}

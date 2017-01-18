package com.example.andre_000.mrservice;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Usermain2 extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    private static final String LOG_TAG = "MainActivity";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    Spinner nombreEmpresas;
    String origen, reforigen, tiposervicio, destino, refdestino, placarro, total ;
    EditText destinoref, placa;
    TextView precio, kilometros, Origen, Reforigen, Tiposervicio;
    AutoCompleteTextView autocompleteView;
    private UserSignUpTask mAuthTask = null;
    private CompanyTask mCompanyTask = null;
   // public static Empresa company;
    public LatLng DestinoPosition, OrigenPosition;
    public double Lat,Lng, distancia;
    Button Servicio;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usermain2);

        Bundle extras = getIntent().getExtras();
        origen = extras.getString("Origen");
        reforigen = extras.getString("Reforigen");
        tiposervicio = extras.getString("TipoServicio");
        Lat = extras.getDouble("Latitud");
        Lng = extras.getDouble("Longitud");
        OrigenPosition = new LatLng(Lat,Lng);


        destinoref = (EditText) findViewById(R.id.destinoref);
        placa = (EditText) findViewById(R.id.placa);
        precio = (TextView) findViewById(R.id.precio);
       // kilometros = (TextView) findViewById(R.id.kilometros);
        Origen = (TextView) findViewById(R.id.Origen);
        Reforigen = (TextView) findViewById(R.id.Reforigen);
        Tiposervicio = (TextView) findViewById(R.id.Tiposervicio);
        Servicio = (Button) findViewById(R.id.Servicio);
        Servicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        "Servicio solcitado con exito!",
                        Toast.LENGTH_LONG).show();
            }
        });

        Origen.setText(origen);
        Reforigen.setText(reforigen);
        Tiposervicio.setText(tiposervicio);

        nombreEmpresas = (Spinner)findViewById(R.id.Empresas);
        String []opciones={"Seleccionar","Particular","Empresa"};

        //Place autocomplete
        mGoogleApiClient = new GoogleApiClient.Builder(Usermain2.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();
        autocompleteView = (AutoCompleteTextView) findViewById(R.id.autocomplete2);
        autocompleteView.setThreshold(3);
        autocompleteView.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        autocompleteView.setAdapter(mPlaceArrayAdapter);

/*
        autocompleteView = (AutoCompleteTextView) findViewById(R.id.autocomplete2);
        autocompleteView.setAdapter(new PlacesAutoCompleteAdapter(this, R.layout.autocomplete_list_item));
        autocompleteView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get data associated with the specified position
                // in the list (AdapterView)
                String description = (String) parent.getItemAtPosition(position);
                int coordinates = parent.getSelectedItemPosition();
                //map.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(position), 14.0f) );
                Toast.makeText(getApplicationContext(), String.valueOf(coordinates), Toast.LENGTH_SHORT).show();
            }
        });*/

        CompanyTask empresas = new CompanyTask(getApplicationContext());
        empresas.execute();
    }

    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(LOG_TAG, "Selected: " + item.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            Log.i(LOG_TAG, "Fetching details for ID: " + item.placeId);
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e(LOG_TAG, "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }
            // Selecting the first object buffer.
            final Place place = places.get(0);
            CharSequence attributions = places.getAttributions();

            /*mNameTextView.setText(Html.fromHtml(place.getName() + ""));
            mAddressTextView.setText(Html.fromHtml(place.getAddress() + ""));
            mIdTextView.setText(Html.fromHtml(place.getId() + ""));
            mPhoneTextView.setText(Html.fromHtml(place.getPhoneNumber() + ""));
            mWebTextView.setText(place.getWebsiteUri() + "");*/
            DestinoPosition = place.getLatLng();
            distancia = CalculationByDistance(OrigenPosition, DestinoPosition);
            precio.setText(String.valueOf(distancia*200+"Bsf"));
//            kilometros.setText(String.valueOf(distancia));
            if (attributions != null) {
                //mAttTextView.setText(Html.fromHtml(attributions.toString()));
            }
        }
    };

    @Override
    public void onConnected(Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(LOG_TAG, "Google Places API connected.");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(LOG_TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e(LOG_TAG, "Google Places API connection suspended.");
    }

    public class CompanyTask extends AsyncTask<Void, Void, String> {
        public ArrayList<Empresa> listempresas;
        private Context context;

        public CompanyTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(Void... params) {

            WebRequest request = new WebRequest("android/verempresa");
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
            mCompanyTask = null;
            //showProgress(false);

            if (success != null) {
                listempresas = StringToEmpresas(success);
                ArrayAdapter<Empresa> adapter = new ArrayAdapter<>(this.context,android.R.layout.simple_spinner_item, listempresas);

                nombreEmpresas.setAdapter(adapter);
                Toast.makeText(getApplicationContext(), "Empresas Recogidas!", Toast.LENGTH_SHORT).show();
                //finish();
            } else {
                Toast.makeText(getApplicationContext(), "Error en empresas", Toast.LENGTH_SHORT).show();
            }
        }


        @Override
        protected void onCancelled() {
            mCompanyTask = null;
        }

        private ArrayList<Empresa> StringToEmpresas(String result) {
            ArrayList<Empresa> listempresas = new ArrayList<>();

            try {
                JSONArray datos = new JSONArray(result);

                for (int i = 0; i < datos.length(); i++) {
                    // Aqui debes instanciar la clase empresa, estaba nula, por lo tanto no podias acceder al metodo fillFromJson
                    // Tampoco es necesaria que la declares static arriba, ya que se sustituye en cada ciclo aqui
                    // Solo la listEmpresas debe ser static, que es la que asignas en el Task
                    Empresa company = new Empresa();
                    company.FillFromJson(datos.getJSONObject(i).toString());

                    listempresas.add(company);

                }
            } catch(JSONException | IllegalAccessException e){
                e.printStackTrace();
            }

            return listempresas;
        }

    }



    public void UserSignUp(View view) {
        destino = autocompleteView.getText().toString();
        refdestino = destinoref.getText().toString();
        placarro = placa.getText().toString();
        total = precio.getText().toString();

        mAuthTask = new UserSignUpTask(origen, reforigen, tiposervicio, destino, refdestino, placarro, total);
        mAuthTask.execute((Void) null);
    }

    public class UserSignUpTask extends AsyncTask<Void, Void, Boolean> {

        private final String mOrigen;
        private final String mReforigen;
        private final String mTiposervicio;
        private final String mDestino;
        private final String mRefdestino;
        private final String mPlaca;
        private final String mTotal;


        UserSignUpTask(String origen, String reforigen, String tiposervicio, String destino, String refdestino, String placarro, String total) {
            mOrigen = origen;
            mReforigen = reforigen;
            mTiposervicio = tiposervicio;
            mDestino = destino;
            mRefdestino = refdestino;
            mPlaca = placarro;
            mTotal = total;

            }

        @Override
        protected Boolean doInBackground(Void... params) {
            Map<String, String> map = new HashMap<>();
            map.put("name", mOrigen);
            map.put("apellido", mReforigen);
            map.put("email", mTiposervicio);
            map.put("cedula", mDestino);
            map.put("direccion", mRefdestino);
            map.put("telefono", mPlaca);
            map.put("id_rol", mTotal);

            WebRequest request = new WebRequest("android/servicios");
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
                Toast.makeText(getApplicationContext(), "Servicio Registrado!", Toast.LENGTH_SHORT).show();
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

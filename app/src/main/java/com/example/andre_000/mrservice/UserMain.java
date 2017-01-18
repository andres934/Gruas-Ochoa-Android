package com.example.andre_000.mrservice;

import android.app.Activity;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
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
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;


public class UserMain extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {
private static final String LOG_TAG = "MainActivity";
private static final int GOOGLE_API_CLIENT_ID = 0;
private GoogleApiClient mGoogleApiClient;
private PlaceArrayAdapter mPlaceArrayAdapter;
private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
        new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));

    private GoogleMap map; // Might be null if Google Play services APK is not available.
    MapView mMapView;
    LatLng myPosition;
    LatLng OrigenPosition;
    double Lat, Lng;
    Button Solicitar;
    Spinner Tiposervicio;
    AutoCompleteTextView autocompleteView;
    EditText origenref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_main);
        Solicitar = (Button) findViewById(R.id.Solicitar);
        Tiposervicio = (Spinner) findViewById(R.id.Tiposervicio);
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.setMyLocationEnabled(true);
        origenref = (EditText) findViewById(R.id.origenref);

            //Place autocomplete
            mGoogleApiClient = new GoogleApiClient.Builder(UserMain.this)
                    .addApi(Places.GEO_DATA_API)
                    .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                    .addConnectionCallbacks(this)
                    .build();
            autocompleteView = (AutoCompleteTextView) findViewById(R.id.autocomplete);
            autocompleteView.setThreshold(3);
            autocompleteView.setOnItemClickListener(mAutocompleteClickListener);
            mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
                    BOUNDS_MOUNTAIN_VIEW, null);
            autocompleteView.setAdapter(mPlaceArrayAdapter);


        // Getting LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        Solicitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Coordenadas();
                user2(v);
            }
        });

        Tiposervicio = (Spinner) findViewById(R.id.Tiposervicio);
        String[] opciones = {"Seleccionar", "Remolque", "Asistencia"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opciones);
        Tiposervicio.setAdapter(adapter);

        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Getting the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Getting Current Location
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            // Getting latitude of the current location
            //latitude = location.getLatitude();

            // Getting longitude of the current location
            //longitude = location.getLongitude();

            // Creating a LatLng object for the current location
            //LatLng latLng = new LatLng(latitude, longitude);

            //myPosition = new LatLng(latitude, longitude);

            map.addMarker(new MarkerOptions().position(myPosition).title("title"));
        }


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
            OrigenPosition = place.getLatLng();
            Lat = OrigenPosition.latitude;
            Lng = OrigenPosition.longitude;
            CameraUpdate center=
                    CameraUpdateFactory.newLatLng(OrigenPosition);
            CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
            map.addMarker(new MarkerOptions().position(OrigenPosition).title(place.getName().toString()));
            map.moveCamera(center);
            map.animateCamera(zoom);
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

    public void user2(View v){

        String origen, reforigen, tiposervicio;
        origen = autocompleteView.getText().toString();
        reforigen = origenref.getText().toString();
        tiposervicio = Tiposervicio.getSelectedItem().toString();
        Intent i = new Intent(this, Usermain2.class);
        i.putExtra("Origen",origen);
        i.putExtra("Reforigen",reforigen);
        i.putExtra("TipoServicio",tiposervicio);
        i.putExtra("Latitud",Lat);
        i.putExtra("Longitud",Lng);
        startActivity(i);
        //Toast.makeText(this, OrigenPosition.toString(), Toast.LENGTH_SHORT).show();
    }

    public void profile(View v){
        Intent i = new Intent(this, UserProfile.class);
        startActivity(i);
    }

    public void Coordenadas(){
        //Toast.makeText(this, "Latitud: "+latitude+"\nLongitud: "+longitude, Toast.LENGTH_SHORT).show();
    }


}

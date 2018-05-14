package gustavus.mcs270.gustavusinteractivemap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;

import java.util.Locale;

public class MapsActivity extends AppCompatActivity implements
        OnMapReadyCallback {

    private static final String TAG = MapsActivity.class.getSimpleName();

    //call these so we know if we should display the marks of specific things or not.
    public static boolean ENABLE_OR_DISABLE_ACADEMIC_BUILIDINGS = false;
    public static boolean ENABLE_OR_DISABLE_HOUSING_BUILDINGS = false;
    public static boolean ENABLE_OR_DISABLE_POINTSOFINTEREST = false;

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    public static final float INITIAL_ZOOM = 12f;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready
        // to be used.
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mapFragment).commit();
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Change the map type based on the user's selection.
        switch (item.getItemId()) {
            case R.id.normal_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case R.id.hybrid_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case R.id.satellite_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;
            case R.id.terrain_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Triggered when the map is ready to be used.
     *
     * @param googleMap The GoogleMap object representing the Google Map.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Pan the camera to the center of Gustavus
        LatLng gustavus = new LatLng(44.322979, -93.972344);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gustavus, INITIAL_ZOOM));

        setMapLongClick(mMap); // Set a long click listener for the map;
        setPoiClick(mMap); // Set a click listener for points of interest.
        setMapStyle(mMap); // Set the custom map style.
        enableMyLocation(mMap); // Enable location tracking.
        //initMapPOIs(mMap);
        // Enable going into StreetView by clicking on an InfoWindow from a
        // point of interest.
        setInfoWindowClickToPanorama(mMap);
    }

    /**
     * Adds a blue marker to the map when the user long clicks on it.
     *
     * @param map The GoogleMap to attach the listener to.
     */

    private void setMapLongClick(final GoogleMap map) {

        // Add a blue marker to the map when the user performs a long click.
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                String snippet = String.format(Locale.getDefault(),
                        getString(R.string.lat_long_snippet),
                        latLng.latitude,
                        latLng.longitude);

                map.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(getString(R.string.dropped_pin))
                        .snippet(snippet)
                        .icon(BitmapDescriptorFactory.defaultMarker
                                (BitmapDescriptorFactory.HUE_BLUE)));
            }
        });
    }

    /**
     * Adds housing points of interest to the map
     *
     * @param googleMap the GoogleMap that will be populated
     */
    private void initHousingPOIs(GoogleMap googleMap){

        //housing buildings
        LatLng prairieView = new LatLng(44.322979, -93.972344);
        LatLng southwest = new LatLng(44.322979, -93.972344);
        LatLng norelius = new LatLng(44.322979, -93.972344);
        LatLng pittman = new LatLng(44.322979, -93.972344);
        LatLng sohre = new LatLng(44.322979, -93.972344);
        LatLng collegeView = new LatLng(44.322979, -93.972344);
        LatLng arborView = new LatLng(44.322979, -93.972344);
        LatLng chapelView = new LatLng(44.322979, -93.972344);
        LatLng complex = new LatLng(44.322979, -93.972344);
        LatLng internationalHouse = new LatLng(44.322979, -93.972344);
        LatLng uhler = new LatLng(44.322979, -93.972344);
        LatLng rundstrom = new LatLng(44.322979, -93.972344);

        //add the markers of academic buildings //can add.title to give more description
        googleMap.addMarker(new MarkerOptions().position(prairieView));
        googleMap.addMarker(new MarkerOptions().position(southwest));
        googleMap.addMarker(new MarkerOptions().position(norelius));
        googleMap.addMarker(new MarkerOptions().position(pittman));
        googleMap.addMarker(new MarkerOptions().position(sohre));
        googleMap.addMarker(new MarkerOptions().position(collegeView));
        googleMap.addMarker(new MarkerOptions().position(arborView));
        googleMap.addMarker(new MarkerOptions().position(chapelView));
        googleMap.addMarker(new MarkerOptions().position(complex));
        googleMap.addMarker(new MarkerOptions().position(internationalHouse));
        googleMap.addMarker(new MarkerOptions().position(uhler));
        googleMap.addMarker(new MarkerOptions().position(rundstrom));

    }

    /**
     * Adds land mark points of interest to the map
     *
     * @param googleMap the GoogleMap that will be populated
     */
    private void initLandMarkPOIS(GoogleMap googleMap){

        //POI buildings and landmarks
        LatLng arboretum = new LatLng(44.322979, -93.972344);
        LatLng christChapel = new LatLng(44.322979, -93.972344);
        LatLng campusCenter = new LatLng(44.322979, -93.972344);
        LatLng studentUnion = new LatLng(44.322979, -93.972344);
        LatLng tennisBubble = new LatLng(44.322979, -93.972344);
        LatLng footballField = new LatLng(44.322979, -93.972344);
        LatLng soccerField = new LatLng(44.322979, -93.972344);
        LatLng baseballField = new LatLng(44.322979, -93.972344);
        LatLng softballField = new LatLng(44.322979, -93.972344);
        LatLng golfBuilding = new LatLng(44.322979, -93.972344);
        LatLng greenhouse = new LatLng(44.322979, -93.972344);
        LatLng bigHillFarm = new LatLng(44.322979, -93.972344);
        LatLng physicalPlant = new LatLng(44.322979, -93.972344);
        LatLng presidentsHouse = new LatLng(44.322979, -93.972344);

        //add the markers of academic buildings //can add.title to give more description
        googleMap.addMarker(new MarkerOptions().position(arboretum));
        googleMap.addMarker(new MarkerOptions().position(christChapel));
        googleMap.addMarker(new MarkerOptions().position(campusCenter));
        googleMap.addMarker(new MarkerOptions().position(studentUnion));
        googleMap.addMarker(new MarkerOptions().position(tennisBubble));
        googleMap.addMarker(new MarkerOptions().position(footballField));
        googleMap.addMarker(new MarkerOptions().position(soccerField));
        googleMap.addMarker(new MarkerOptions().position(baseballField));
        googleMap.addMarker(new MarkerOptions().position(softballField));
        googleMap.addMarker(new MarkerOptions().position(golfBuilding));
        googleMap.addMarker(new MarkerOptions().position(greenhouse));
        googleMap.addMarker(new MarkerOptions().position(bigHillFarm));
        googleMap.addMarker(new MarkerOptions().position(physicalPlant));
        googleMap.addMarker(new MarkerOptions().position(presidentsHouse));
    }

    /**
     * Adds academic builiding points of interest to the map
     *
     * @param googleMap the GoogleMap that will be populated
     */
    private void initAcademicPOIs(GoogleMap googleMap){

        //academic buildings.
        LatLng olin = new LatLng(44.322979, -93.972344);
        LatLng nobel = new LatLng(44.322979, -93.972344);
        LatLng lund = new LatLng(44.322979, -93.972344);
        LatLng bjorling = new LatLng(44.322979, -93.972344);
        LatLng library = new LatLng(44.322979, -93.972344);
        LatLng beck = new LatLng(44.322979, -93.972344);
        LatLng conferVickner = new LatLng(44.322979, -93.972344);
        LatLng mattsonHall = new LatLng(44.322979, -93.972344);
        LatLng anderson = new LatLng(44.322979, -93.972344);
        LatLng schaeffer = new LatLng(44.322979, -93.972344);
        LatLng interpretiveCenter = new LatLng(44.322979, -93.972344);

        //add the markers of academic buildings //can add.title to give more description
        googleMap.addMarker(new MarkerOptions().position(olin));
        googleMap.addMarker(new MarkerOptions().position(nobel));
        googleMap.addMarker(new MarkerOptions().position(lund));
        googleMap.addMarker(new MarkerOptions().position(bjorling));
        googleMap.addMarker(new MarkerOptions().position(library));
        googleMap.addMarker(new MarkerOptions().position(beck));
        googleMap.addMarker(new MarkerOptions().position(conferVickner));
        googleMap.addMarker(new MarkerOptions().position(mattsonHall));
        googleMap.addMarker(new MarkerOptions().position(anderson));
        googleMap.addMarker(new MarkerOptions().position(schaeffer));
        googleMap.addMarker(new MarkerOptions().position(interpretiveCenter));

    }

    /**
     * Adds a marker when a place of interest (POI) is clicked with the name of
     * the POI and immediately shows the info window.
     *
     * @param map The GoogleMap to attach the listener to.
     */
    private void setPoiClick(final GoogleMap map) {
        map.setOnPoiClickListener(new GoogleMap.OnPoiClickListener() {
            @Override
            public void onPoiClick(PointOfInterest poi) {
                Marker poiMarker = map.addMarker(new MarkerOptions()
                        .position(poi.latLng)
                        .title(poi.name));
                poiMarker.showInfoWindow();
                poiMarker.setTag(getString(R.string.poi));
            }
        });
    }

    /**
     * Loads a style from the map_style.json file to style the Google Map. Log
     * the errors if the loading fails.
     *
     * @param map The GoogleMap object to style.
     */
    private void setMapStyle(GoogleMap map) {
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = map.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.map_style));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
    }

    /**
     * Checks for location permissions, and requests them if they are missing.
     * Otherwise, enables the location layer.
     */
    private void enableMyLocation(GoogleMap map) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
    }

    /**
     * Starts a Street View panorama when an info window containing the poi tag
     * is clicked.
     *
     * @param map The GoogleMap to set the listener to.
     */
    private void setInfoWindowClickToPanorama(GoogleMap map) {
        map.setOnInfoWindowClickListener(
                new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        // Check the tag
                        if (marker.getTag() == "poi") {

                            // Set the position to the position of the marker
                            StreetViewPanoramaOptions options =
                                    new StreetViewPanoramaOptions().position(
                                            marker.getPosition());

                            SupportStreetViewPanoramaFragment streetViewFragment
                                    = SupportStreetViewPanoramaFragment
                                    .newInstance(options);

                            // Replace the fragment and add it to the backstack
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container,
                                            streetViewFragment)
                                    .addToBackStack(null).commit();
                        }
                    }
                });
    }
}

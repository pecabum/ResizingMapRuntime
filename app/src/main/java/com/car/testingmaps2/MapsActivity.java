package com.car.testingmaps2;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, MapPanel.PanelSlideListener {

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    ViewGroup rootLayout;
    private int height;
    private float dY;
    private LatLng sydney;
    MapPanel slidingLayout;
    private View resizingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        rootLayout = (ViewGroup) findViewById(R.id.root);
        slidingLayout = (MapPanel) findViewById(R.id.sliding_layout);
        resizingView = findViewById(R.id.resizingView);

        resizingView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        slidingLayout.setTouchEnabled(true);//TouchEnabled(false);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        slidingLayout.setTouchEnabled(false);
                        break;

                }

                return false;
            }
        });


        slidingLayout.setPanelSlideListener(this);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ViewTreeObserver vto = rootLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                height = rootLayout.getMeasuredHeight();
                Log.d("D1", height + "");
            }
        });

//        slidingLayout.setTouchEnabled(true);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(sydney.latitude - 7, sydney.longitude), 5));

        mapFragment.getView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        slidingLayout.setTouchEnabled(false);//TouchEnabled(false);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        slidingLayout.setTouchEnabled(true);
                        break;

                }

                return false;
            }
        });
    }

    @Override
    public void onPanelSlide(View panel, float slideOffset) {
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        Log.d("d1", "panel slide: " + slideOffset);;
    }

    @Override
    public void onPanelCollapsed(View panel) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mMap.getCameraPosition().target.latitude - 7, mMap.getCameraPosition().target.longitude), 5));
    }

    @Override
    public void onPanelExpanded(View panel) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(mMap.getCameraPosition().target.latitude + 7,
                        mMap.getCameraPosition().target.longitude), 5));
        Log.d("d1", "onPanelExpanded");
    }

    @Override
    public void onPanelAnchored(View panel) {
        Log.d("d1", "onPanelAnchored");
    }

    @Override
    public void onPanelHidden(View panel) {
        Log.d("d1", "onPanelHidden");
    }
}

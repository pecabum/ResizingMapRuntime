package com.car.testingmaps2;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, SlidingUpPanelLayout.PanelSlideListener { //}, View.OnTouchListener {

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    ViewGroup rootLayout;
    private int height;
    private float dY;
    private LatLng sydney;
    SlidingUpPanelLayout slidingLayout;
//    private View resizingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        rootLayout = (ViewGroup) findViewById(R.id.root);
        slidingLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        slidingLayout.setPanelSlideListener(this);
//        resizingView = rootLayout.findViewById(R.id.resizingView);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
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
//        resizingView.setOnTouchListener(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * //     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(sydney.latitude - 7, sydney.longitude), 5));
    }

    @Override
    public void onPanelSlide(View panel, float slideOffset) {
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        Log.d("d1", "panel slide: " + slideOffset);;
    }

    @Override
    public void onPanelCollapsed(View panel) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mMap.getCameraPosition().target.latitude-7, mMap.getCameraPosition().target.longitude),5));
//        mapFragment.getView().getLayoutParams().height = 260;
//        mapFragment.getView().requestLayout();
    }

    @Override
    public void onPanelExpanded(View panel) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mMap.getCameraPosition().target.latitude+7, mMap.getCameraPosition().target.longitude),5));
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

//    @Override
//    public boolean onTouch(View view, MotionEvent event) {
//
//        switch (event.getActionMasked()) {
//
//            case MotionEvent.ACTION_DOWN:
//                dY = view.getY() - event.getRawY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//
//                view.animate()
//                        .y(event.getRawY() + dY)
//                        .setStartDelay(0).setDuration(0)
//                        .start();
//
//                int lHeight = (int) (height - event.getRawY() + 30);
//                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, lHeight);
//                lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//                mapFragment.getView().setLayoutParams(lp);
//                mapFragment.getView().requestLayout();
//                break;
//            default:
//                return false;
//
//        }
//        return true;
//    }


}

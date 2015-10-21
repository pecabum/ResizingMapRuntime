package com.car.testingmaps2;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, MapPanel.PanelSlideListener {

    private static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    ViewGroup rootLayout;
    private int height;
    private float dY;
    private LatLng sydney;
    private MapPanel slidingLayout;
    private float pxOfDp;
    private View resizingView;
    private Handler mainHandler;
    private LinearLayout resizedLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        // Get a handler that can be used to post to the main thread
        mainHandler = new Handler(getMainLooper());


        pxOfDp = convertDpToPixels(1, getApplicationContext());
        rootLayout = (ViewGroup) findViewById(R.id.root);
        slidingLayout = (MapPanel) findViewById(R.id.sliding_layout);
        resizingView = findViewById(R.id.resizingView);
        resizedLayout = (LinearLayout) findViewById(R.id.resizedLayout);

        resizedLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "onTouch: " + (v.getClass().getName()));
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

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        sydney = new LatLng(43.211722, 27.916240);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(sydney.latitude - 7, sydney.longitude), 5));
    }

    @Override
    public void onPanelSlide(View panel, final float slideOffset) {

    }

    @Override
    public void onPanelDragged(View panel, final float slideOffset) {
        Log.d("d1", "panel drag: " + Math.round(slideOffset));
//        mainHandler.post(new Runnable() {
//            @Override
//            public void run() {
////                mMap.moveCamera(CameraUpdateFactory.scrollBy(0, slideOffset / 2));
//            }
//        });
    }

    @Override
    public void onPanelCollapsed(View panel) {
//        slidingLayout.setTouchEnabled(false);
//          mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mMap.getCameraPosition().target.latitude - 7, mMap.getCameraPosition().target.longitude), 5));
    }

    @Override
    public void onPanelExpanded(View panel) {
//        slidingLayout.setTouchEnabled(false);
//
////        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
////                new LatLng(mMap.getCameraPosition().target.latitude + 7,
////                        mMap.getCameraPosition().target.longitude), 5));
//        Log.d("d1", "onPanelExpanded");
    }

    @Override
    public void onPanelAnchored(View panel) {
        Log.d("d1", "onPanelAnchored");
    }

    @Override
    public void onPanelHidden(View panel) {
        Log.d("d1", "onPanelHidden");
    }

    public static int convertDpToPixels(float dp, Context context) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
        return px;
    }
}

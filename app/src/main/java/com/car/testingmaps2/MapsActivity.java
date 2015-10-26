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

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, SlidingMapPanel.PanelSlideListener {

    private static final String TAG = MapsActivity.class.getSimpleName();
    private static final float DEFAULT_ZOOM_LEVEL = 14;
    private static final double DEFAULT_COLLAPSED_OFFSET = 7;

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private ViewGroup rootLayout;
    private int height;
    private LatLng sydney;
    private SlidingMapPanel slidingLayout;
    private float pxOfDp;
    private View resizingView;
    private Handler mainHandler;
    private LinearLayout resizedLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        initialize();
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

    private void initialize() {

        // Get a handler that can be used to post to the main thread
        mainHandler = new Handler(getMainLooper());

        pxOfDp = convertDpToPixels(1, getApplicationContext());
        rootLayout = (ViewGroup) findViewById(R.id.root);
        slidingLayout = (SlidingMapPanel) findViewById(R.id.sliding_layout);
        resizingView = findViewById(R.id.resizingView);

        slidingLayout.setPanelSlideListener(this);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        sydney = new LatLng(43.211722, 27.916240);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, DEFAULT_ZOOM_LEVEL));
    }

    @Override
    public void onPanelSlide(View panel, final float slideOffset) {

    }

    @Override
    public void onPanelDragged(View panel, final float slideOffset) {
        Log.d("d1", "panel drag: " + Math.round(slideOffset));
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                mMap.moveCamera(CameraUpdateFactory.scrollBy(0, slideOffset / 2));
            }
        });
    }

    @Override
    public void onPanelCollapsed(View panel) {
    }

    private void goToLastMarker() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(sydney);

        LatLngBounds newBounds = builder.build();
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(newBounds, 0);
        mMap.animateCamera(cu);
    }

    @Override
    public void onPanelExpanded(View panel) {

        LatLngBounds bounds = mMap.getProjection().getVisibleRegion().latLngBounds;
        Log.d(TAG, "onMapReady: " + bounds.toString());
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 8));
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

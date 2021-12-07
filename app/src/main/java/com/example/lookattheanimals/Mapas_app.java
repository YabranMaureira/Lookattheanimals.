package com.example.lookattheanimals;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

import android.os.Bundle;

import com.example.lookattheanimals.databinding.ActivityInicioAppBinding;

public class Mapas_app extends AppCompatActivity {


            private MapView mapa = null;

            @Override
            public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.main);


                mapa = (MapView)findViewById(R.id.mapa);


                mapa.setBuiltInZoomControls(true);
            }


            protected boolean isRouteDisplayed() {
                return false;
            }
        }



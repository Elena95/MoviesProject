package com.example.movies

import com.example.movies.Utils.MyUtils
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movies.R
import com.example.movies.data.remote.Ubication
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.movies.data.remote.entities.LocationModel

class UbicacionesMapaActivity : AppCompatActivity(), Ubication.UbicacionDAOInterface, OnMapReadyCallback {

    private lateinit var map:GoogleMap
    private  lateinit var listLocation: MutableList<LocationModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)


        val mapFragment = supportFragmentManager.findFragmentById(R.id.fragmentMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    //AQUI OBTENEMOS LAS UBICACIONES REGISTRADAS EN LA BASE DE FIREBASE
    override fun UbicacionDAOOnDataChange(listUbicacionesFB: MutableList<LocationModel>) {
        listLocation=listUbicacionesFB
        agregarMarcadores()
    }

    override fun onMapReady(p0: GoogleMap) {
        map=p0
        //solicitamos las ubicaciones registradas
        Ubication.getAllLocations(this)
    }

    fun agregarMarcadores(){
        map.clear()
        if(listLocation?.size>0){
            var ultimaUbicacion: LocationModel? =null
            for(ubicacion in listLocation){
                MyUtils.log("UbicacionesMapaActivity Ubicacion registrada el "+ubicacion.fecha)
                createMarker(ubicacion)
                ultimaUbicacion=ubicacion
            }
            if(ultimaUbicacion!=null){
                val coordenadas= LatLng(ultimaUbicacion.latitud,ultimaUbicacion.longitud)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(coordenadas,18f),4000,null)
            }

        }
    }

    fun createMarker(ubicacion:LocationModel){
        val coordenadas= LatLng(ubicacion.latitud,ubicacion.longitud)
        val marker:MarkerOptions= MarkerOptions().position(coordenadas).title(ubicacion.fecha)
        map.addMarker(marker)
    }
}
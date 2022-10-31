package com.example.movies

import com.example.movies.Utils.MyUtils
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.movies.R
import com.example.movies.data.remote.service.Servicio


class LocationActivity : AppCompatActivity() {
    companion object {
        //Que tipo de permisos pedir
        private const val MY_PERMISSIONS_REQUEST_LOCATION = 99
        private const val MY_PERMISSIONS_REQUEST_BACKGROUND_LOCATION = 66
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
//No me genero el DataBinding por eso ocupe el metodo findViewById
        val btnStart = findViewById<Button>(R.id.btnStart)
        val btnStop = findViewById<Button>(R.id.btnStop)
        val btnMap = findViewById<Button>(R.id.btnMap)

        btnStart.setOnClickListener(View.OnClickListener {
//Iniciar servicio,.. se piden permisos de ubicacion en primer y segundo plano
            checkLocationPermission()
        })
//Detenemos servicio de ubicacion primer y segundo plano
        btnStop.setOnClickListener(View.OnClickListener {
            stopServicio()
        })
//Mostramos el mapa con las ubicaciones
        btnMap.setOnClickListener(View.OnClickListener {
            mostrarMapa()
        })
    }


    fun startServicio(){
        actionOnService("start")
    }

    fun stopServicio(){
        actionOnService("stop")
    }

    //Inicializo mi Activity de ubicaciones
    fun mostrarMapa(){
        val intent = Intent(this, UbicacionesMapaActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
    }
    // Inicializo el servicio
    private fun actionOnService( action:String) {
        Intent(this, Servicio::class.java).also {
            it.action=action

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                MyUtils.log("Starting the service in >=26 Mode")
                startForegroundService(it)
                return
            }
            MyUtils.log("Starting the service in < 26 Mode")
            startService(it)
        }
    }

//Reviso si existen permisos en primer plano, si no se piden
    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                //Una alerta avisando al usuario que necesitamos los permisos, esto para la primera vez
                AlertDialog.Builder(this)
                    .setTitle("Location Permission Needed")
                    .setMessage("This app needs the Location permission, please accept to use location functionality")
                    .setPositiveButton(
                        "OK"
                    ) { _, _ ->
                        //Solicitar permisos una vez que ya avisamos al usuario
                        requestLocationPermission()
                    }
                    .create()
                    .show()
            } else {
                // No es necesaria la explicacion asi que se manda directamente a pedir los permisos
                requestLocationPermission()
            }
        } else {
            checkBackgroundLocation()
        }
    }

    //funcion para validar si existen permisos en segundo plano
    private fun checkBackgroundLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestBackgroundLocationPermission()
        }else{
            startServicio()
        }
    }
// Permisos ubicacion primer plano
    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
            ),
            MY_PERMISSIONS_REQUEST_LOCATION
        )
    }
//Permisos Ubicacion segundo Plano
    private fun requestBackgroundLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ),
                MY_PERMISSIONS_REQUEST_BACKGROUND_LOCATION
            )
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MY_PERMISSIONS_REQUEST_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                // Si se cancela la solicitud, la matrix result estara vacia
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                   //Se obtuevieron los permisos de ubicacion en primer plano
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {


                        // Verificamos la ubicacion en segundo plano
                        checkBackgroundLocation()
                    }

                } else {

                    //Permisos ubicacion primer plano denegados, se necesitan para continuar
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show()

                   //Revisamos si el usuario denego los permisos y selecciono la opcion de no volver a preguntar
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    ) {
                        startActivity(
                            Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", this.packageName, null),
                            ),
                        )
                    }
                }
                return
            }
            MY_PERMISSIONS_REQUEST_BACKGROUND_LOCATION -> {

                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                     //Se aceptaron los permisos de segundo plano
                        startServicio()
                        Toast.makeText(
                            this,
                            "Granted Background Location Permission",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {

                    //Permisos denegados de Ubicacion en segundo plano
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show()
                }
                return

            }
        }
    }
}
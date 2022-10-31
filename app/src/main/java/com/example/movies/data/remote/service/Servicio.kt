package com.example.movies.data.remote.service

import android.Manifest
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.movies.LocationActivity
import com.example.movies.Utils.MyUtils
import com.example.movies.data.remote.Ubication
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*


class Servicio : Service() {

        private var wakeLock: PowerManager.WakeLock? = null
        private var isServiceStarted = false
        private var locationManager : LocationManager? = null
        private lateinit var mjob: Job
        private lateinit var locationListener: LocationListener
        private lateinit var mContext:Context// guardamos el contexto para revisar los permisos de ubicacion
        val notificationChannelId = "ENDLESS SERVICE CHANNEL"


    override fun onBind(intent: Intent): IBinder? {
            MyUtils.log("Some component want to bind with the service")
            // We don't provide binding, so return null
            return null
        }

        override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
            MyUtils.log("onStartCommand executed with startId: $startId")
            if (intent != null) {
                val action = intent.action
                MyUtils.log("using an intent with action $action")
                when (action) {
                    "start" -> startService()
                    "stop" -> stopService()
                    else -> MyUtils.log("This should never happen. No action in the received intent")
                }
            } else {
                MyUtils.log(
                    "with a null intent. It has been probably restarted by the system."
                )
            }
            // by returning this we make sure the service is restarted if the system kills the service
            return START_STICKY
        }

        override fun onCreate() {
            super.onCreate()
            MyUtils.log("The service has been created")
            var notification = createNotification()
            startForeground(1, notification)
        }

        override fun onDestroy() {
            super.onDestroy()
            MyUtils.log("The service has been destroyed")
            Toast.makeText(this, "Service destroyed", Toast.LENGTH_SHORT).show()
        }

        private fun startService() {
            if (isServiceStarted) return
            MyUtils.log("Starting the foreground service task")
            Toast.makeText(this, "Service starting its task", Toast.LENGTH_SHORT).show()
            isServiceStarted = true
            mContext=this

            // we need this lock so our service gets not affected by Doze Mode
            wakeLock =
                (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
                    newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "EndlessService::lock").apply {
                        acquire()
                    }
                }
            MyUtils.log(" ===  startService  ===")

            locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?

            //AQUI RECIBIMOS LA UBICACION
           locationListener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    registrarUbicacion(location)
                    locationManager?.removeUpdates(locationListener)


                }
                override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
                override fun onProviderEnabled(provider: String) {}
                override fun onProviderDisabled(provider: String) {}
            }

            // we're starting a loop in a coroutine
            mjob = GlobalScope.launch(Dispatchers.IO) {
                while (isServiceStarted) {
                    launch(Dispatchers.IO) {
                        //LOS PERMISOS SE PIDEN ANTES DE INICIAR EL SERVICIO
                        if (ActivityCompat.checkSelfPermission(
                                mContext,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                mContext,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            try {
                                // Request location updates
                                locationManager?.requestLocationUpdates(
                                    LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener,
                                    Looper.getMainLooper()
                                )
                            } catch (ex: SecurityException) {
                                MyUtils.log("Security Exception, no location available")
                            }
                        }
                    }
                  //  delay(1 * 60 * 1000)
                   delay(5000)
                }
                MyUtils.log("End of the loop for the service")
            }
        }



        private fun stopService() {
            MyUtils.log("Stopping the foreground service")
            Toast.makeText(this, "Service stopping", Toast.LENGTH_SHORT).show()
            isServiceStarted = false
            try {
                wakeLock?.let {
                    if (it.isHeld) {
                        it.release()
                    }
                }
                //CANCELAMOS LA RUTINA
                mjob?.cancel()
                //CANCELAMOS EL LOCATION LISTENER
                locationManager?.removeUpdates(locationListener)
                //DETENEMOS EL SERVICIO FOREGROUND
                stopForeground(true)
                stopSelf()

            } catch (e: Exception) {
                MyUtils.log("Service stopped without being started: ${e.message}")
            }
          //  isServiceStarted = false
           // setServiceState(this, ServiceState.STOPPED)
        }


        private fun createNotification(): Notification {
            // depending on the Android API that we're dealing with we will have
            // to use a specific method to create the notification
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager;
                val channel = NotificationChannel(
                    notificationChannelId,
                    "Endless Service notifications channel",
                    NotificationManager.IMPORTANCE_HIGH
                ).let {
                    it.description = "Endless Service channel"
                    it.enableLights(true)
                    it.lightColor = Color.RED
                    it.enableVibration(true)
                    it.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
                    it
                }
                notificationManager.createNotificationChannel(channel)
            }
            var pendingIntent:PendingIntent
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                pendingIntent = Intent(this, LocationActivity::class.java).let { notificationIntent ->
                    PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_MUTABLE)
                }
            }else {
                pendingIntent = Intent(this, LocationActivity::class.java).let { notificationIntent ->
                    PendingIntent.getActivity(this, 0, notificationIntent, 0)
                }
            }

            val builder: Notification.Builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) Notification.Builder(
                this,
                notificationChannelId
            ) else Notification.Builder(this)

            return builder
                .setContentTitle("Endless Service")
                .setContentText("This is your favorite endless service working")
                .setContentIntent(pendingIntent)
               // .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("Ticker text")
                .build()
        }



    private fun notificacionUbicacionGuardada(){

        // depending on the Android API that we're dealing with we will have
        // to use a specific method to create the notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager;
            val channel = NotificationChannel(
                notificationChannelId,
                "Endless Service notifications channel",
                NotificationManager.IMPORTANCE_HIGH
            ).let {
                it.description = "Endless Service channel"
                it.enableLights(true)
                it.lightColor = Color.RED
                it.enableVibration(true)
                it.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
                it
            }
            notificationManager.createNotificationChannel(channel)
        }
        var pendingIntent:PendingIntent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = Intent(this, LocationActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_MUTABLE)
            }
        }else {
            pendingIntent = Intent(this, LocationActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent, 0)
            }
        }

        val builder: Notification.Builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) Notification.Builder(
            this,
            notificationChannelId
        ) else Notification.Builder(this)

         builder
            .setContentTitle("Ubicacion registrada")
            .setContentText("Se ha registrado una nueva ubicación")
            .setContentIntent(pendingIntent)
            //.setSmallIcon(R.mipmap.ic_launcher)
            .setTicker("Ticker text")

            .build()
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(1234, builder.build())
    }

    private fun registrarUbicacion(location:Location){
        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val gmtTime = df.format(Date())
        val ubicaion:String = ("" + location.longitude + ":" + location.latitude)
        MyUtils.log(" ============  PRUEBA     "+gmtTime.toString())
        MyUtils.log("-->"+ubicaion)
        Ubication.registrarUbicacion(location,gmtTime.toString())
        notificacionUbicacionGuardada()
    }
        
    }

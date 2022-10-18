package com.example.movies.data.remote

import android.location.Location
import com.example.movies.Utils.MyUtils
import com.google.firebase.database.*
import com.example.movies.data.remote.entities.LocationModel
import com.example.movies.data.remote.entities.UserModel

class Ubication {
    interface UbicacionDAOInterface{
        fun UbicacionDAOOnDataChange(listUbicaciones:MutableList<LocationModel>)
    }
    companion object {

        val baseDatos:String="Locations"
        fun registrarUsuario(location: Location, fecha:String) {
            val dbFireBase: DatabaseReference =
                FirebaseDatabase.getInstance().getReference(baseDatos)
            var usrId: String? = dbFireBase.push().key
            val color = MyUtils.randomColor()
            var listUbicaciones:MutableList<LocationModel>
            listUbicaciones= mutableListOf()
            var ubiId: String? = dbFireBase.push().key
            var ubicacionModel = LocationModel(ubiId.toString(),location.latitude,location.longitude,fecha)
            listUbicaciones.add(ubicacionModel)
            var usr = UserModel(usrId.toString(), listUbicaciones, color)
            dbFireBase.child(usrId.toString()).setValue(usr).addOnCompleteListener {
                MyUtils.log("UbicacionWS registrarUsuario informacion insertada ")
            }.addOnFailureListener { err ->
                MyUtils.log("UbicacionWS registrarUsuario ERROR " + err)
            }
        }

        //Instanciamos nuestra base de dato para registrar una nueva ubicacion
        fun  registrarUbicacion(location: Location, fecha:String) {
            val dbFireBase: DatabaseReference =
                FirebaseDatabase.getInstance().getReference(baseDatos)
            var ubiId: String? = dbFireBase.push().key
            var ubicacionModel = LocationModel(ubiId.toString(),location.latitude,location.longitude,fecha)

            dbFireBase.child(ubiId.toString()).setValue(ubicacionModel).addOnCompleteListener {
                MyUtils.log("UbicacionWS registrarUbicacion informacion insertada ")
            }.addOnFailureListener { err ->
                MyUtils.log("UbicacionWS registrarUbicacion ERROR " + err.message)
            }
        }
//Traemos todas las ubicaciones en una lista de nuestra base de datos en fireBase
        fun getAllLocations(listener:UbicacionDAOInterface) {
            val dbFireBase: DatabaseReference =
                FirebaseDatabase.getInstance().getReference(baseDatos)
            dbFireBase.addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()) {
                        var listUbicaciones: MutableList<LocationModel>
                        listUbicaciones = mutableListOf()

                        for(ubicaciones  in snapshot.children){
                            val ubicacion=ubicaciones.getValue(LocationModel::class.java)
                            listUbicaciones.add(ubicacion!!)
                        }
                        listener.UbicacionDAOOnDataChange(listUbicaciones)
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                   MyUtils.log(error.message)
                }
            })
        }
    }
}
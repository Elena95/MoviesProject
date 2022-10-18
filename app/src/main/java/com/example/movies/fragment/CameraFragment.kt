package com.example.movies.fragment

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.movies.R
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_camera.*
import kotlinx.android.synthetic.main.fragment_camera.view.*

@Suppress("UNREACHABLE_CODE")
class CameraFragment:Fragment() {
    private val database = Firebase.database
    private val myRef = database.getReference("user")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Infla el layout del framento camera, no ocupe Binding por que no me lo autogenero
        val view: View = inflater.inflate(R.layout.fragment_camera, container, false)
        view.buttonCap.setOnClickListener { view ->
            if (view.context != null) {
                requestPermission(view.context)
            }
            Toast.makeText(view.context, "Seleccionar Imagen", Toast.LENGTH_SHORT).show();

        }
        //regresa mi vista
        return view
    }

    //Funcion para verificar si tiene permisos para abrir galeria
private fun requestPermission(context: Context){
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
        when{
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED ->{
                pickPhotoFromGallery()
            }
            else->requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }else{
        pickPhotoFromGallery()
    }
}


private val startForActivityGallery = registerForActivityResult(
    ActivityResultContracts.StartActivityForResult()
){result->
    //Se escogio una foto de galeria
    if(result.resultCode == Activity.RESULT_OK){
        val data = result.data?.data
        if(data != null){
            fileUpload(data)
        }

    }

}
    //Tomamos imagen de galeria
    private fun pickPhotoFromGallery(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startForActivityGallery.launch(intent)
    }

    //Para ver si se aceptan o no los permisos
    private val requestPermissionLauncher= registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){isGranted->
        if (isGranted){
            pickPhotoFromGallery()
        }else{
            Toast.makeText(activity, "Se necesitan permisos", Toast.LENGTH_SHORT).show();
        }

    }

    //Almacenamos foto en database
    private fun fileUpload(mUri:Uri){
        val folder: StorageReference = FirebaseStorage.getInstance().reference.child("User")
        val path = mUri?.lastPathSegment.toString()
        val fileName: StorageReference = folder.child(path.substring(path.lastIndexOf('/')+1))

        fileName.putFile(mUri).addOnCompleteListener{
            fileName.downloadUrl.addOnSuccessListener { uri->
                val hashMap = HashMap<String, String>()
                hashMap["link"] = java.lang.String.valueOf(uri)
                myRef.child(myRef.push().key.toString()).setValue(hashMap)
                Log.i("message", "file upload successfully")
            }
        }.addOnFailureListener{
            Log.i("mesage","file uoload error")
        }

    }
}
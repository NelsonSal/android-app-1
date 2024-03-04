package com.njso.manualsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Welcome : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        firebaseAuth=FirebaseAuth.getInstance()
        verBienvenida()
    }
    fun verBienvenida(){
        object : CountDownTimer(2000,1000){
            override fun onTick(millisUntilFinished: Long) {
                //No hace nada
            }

            override fun onFinish() {
                //Dirije a la MainActivity
                //val intent= Intent(this@Welcome, Elegir_rol::class.java)
                //startActivity(intent)
                //finishAffinity() Eliminada en 13 5:28
                ComprobarSesion()
            }

        }.start()

    }

    fun ComprobarSesion(){
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser==null){
            startActivity(Intent(this, Elegir_rol::class.java))
            finishAffinity()

        }else{
            val reference = FirebaseDatabase.getInstance().getReference("Usuarios")
            reference.child(firebaseUser.uid)
                .addListenerForSingleValueEvent(object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val rol = snapshot.child("rol").value
                        if (rol == "admin"){
                            startActivity(Intent(this@Welcome, MainActivity::class.java))
                            finishAffinity()

                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
        }
    }
}
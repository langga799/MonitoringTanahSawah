package com.example.monitoringtanahsawah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.monitoringtanahsawah.helper.SharedPreference
import com.example.monitoringtanahsawah.ui.MonitoringActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth

class EmptyActivity : AppCompatActivity() {

    private lateinit var preference: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty)
        supportActionBar?.hide() // menyembunyinkan action bar

        preference = SharedPreference(this)

        loginInBackground()
    }


    // Fungsi login dari background
    // dengan login maka bisa mengakses firebase tanpa ada waktu tenggat (Kebijakan baru Firebase)
    private fun loginInBackground(){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(
            "rahmatyogapratama171@gmail.com",
            "rahmatyogapratama"
        ).addOnSuccessListener {
            preference.saveLogin(true)
            Log.d("AUTHENTICATION", it.user?.uid.toString())

            startActivity(Intent(this, MonitoringActivity::class.java))

        }.addOnFailureListener {
            MaterialAlertDialogBuilder(this)
                .setMessage("Pastikan Anda memiliki koneksi internet yang stabil...")
                .setPositiveButton("Coba Lagi"){_, _ ->
                    loginInBackground()
                }
                .setNegativeButton("Keluar"){_, _ ->
                    finish()
                }
                .setCancelable(false)
                .show()
        }
    }
}
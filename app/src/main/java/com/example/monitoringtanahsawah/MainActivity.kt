package com.example.monitoringtanahsawah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.monitoringtanahsawah.helper.SharedPreference
import com.example.monitoringtanahsawah.ui.MonitoringActivity

class MainActivity : AppCompatActivity() {

    private lateinit var preference: SharedPreference // Untuk menyimpan nilai-nilai kecil dlm bentuk key=value

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        preference = SharedPreference(this) // variabel diinisialisasi

        when(preference.getLogin()){ //validasi jika sudah login
            true -> {
                Handler(mainLooper).postDelayed({ // handler untuk eksekusi sebuah program
                    startActivity(Intent(this, MonitoringActivity::class.java)) //pindah ke layout monitoring
                    finishAffinity() // untuk menghapus back stack
                }, 2000L) //delay 2 detik 2000L
            }
            false -> {
                Handler(mainLooper).postDelayed({
                    startActivity(Intent(this, EmptyActivity::class.java))
                    finishAffinity()
                }, 2000L)
            }
        }

    }
}
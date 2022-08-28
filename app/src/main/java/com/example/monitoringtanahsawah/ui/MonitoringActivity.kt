package com.example.monitoringtanahsawah.ui

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.monitoringtanahsawah.R
import com.example.monitoringtanahsawah.databinding.ActivityMonitoringBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.nio.Buffer

class MonitoringActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMonitoringBinding // Variabel untuk akses id dari layout
    private val databaseReference = Firebase.database.reference // Variabel untuk akses referensi DB Realtime Database Firebase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMonitoringBinding.inflate(layoutInflater)
        setContentView(binding.root) // Mengatur root dari layout xml nya


        // Get Realtime Database
        databaseReference.child("monitoring").child("kelembaban_tanah") // child untuk akses db
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val dataKelembabanTanah = snapshot.value.toString()
                    dataKelembabanTanah.toFloat().let { data ->
                        binding.kelembabanTanah.progress = data // Me-set nilai kelembaban tanah ke progress bar
                        ("$data%").also {
                            binding.tvKelembabanTanah.text = it // Set nilai kelembaban tanah ke text view
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@MonitoringActivity, error.message, Toast.LENGTH_SHORT)
                        .show() // jika db error menampilkan pesan error
                }

            })


        // Get nilai ph
        databaseReference.child("monitoring").child("ph") // child nilai ph
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.value.toString().toFloat().let { nilaiPH ->
                        binding.ph.apply {
                            when (nilaiPH) { // validasi nilai ph
                                in 6.0F..7.0F -> { // jika diantara 6-7 ph normal
                                    progressColor =
                                        ContextCompat.getColor(this@MonitoringActivity,
                                            R.color.green)// atur warna hijau progres bar PH
                                    "Normal".also { binding.tvStatus.text = it }
                                    binding.tvPh.text = nilaiPH.toString() // set nilai ph ke text view
                                }
                                in 0.0F..5.0F -> { // jika 0-5 ph asam
                                    progressColor =
                                        ContextCompat.getColor(this@MonitoringActivity,
                                            R.color.asam)// atur warna merah progres bar PH
                                    "Asam".also { binding.tvStatus.text = it }
                                    binding.tvPh.text = nilaiPH.toString()
                                }
                                in 8.0F..14.0F -> { // jika 8-14 ph basa
                                    progressColor =
                                        ContextCompat.getColor(this@MonitoringActivity,
                                            R.color.basa) // atur warna biru progres bar PH
                                    "Basa".also { binding.tvStatus.text = it }
                                    binding.tvPh.text = nilaiPH.toString()
                                }
                                else -> {
                                    "pH tidak diketahui".also { binding.tvStatus.text = it }
                                }
                            }

                            progress = nilaiPH.toFloat() // set ph ke progress bar


                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@MonitoringActivity, error.message, Toast.LENGTH_SHORT)
                        .show()
                }

            })



        // get nilai listrik dc
//        databaseReference.child("monitoring").child("listrik_dc")
//            .addValueEventListener(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    snapshot.value.toString().let { nilaiVoltDC ->
//                        binding.adc.text = nilaiVoltDC  //set nilai ke text view
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    Toast.makeText(this@MonitoringActivity, error.message, Toast.LENGTH_SHORT)
//                        .show()
//                }
//
//            })
    }
}
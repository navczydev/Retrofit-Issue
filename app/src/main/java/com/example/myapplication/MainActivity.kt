package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.api.ApiInterface
import com.example.myapplication.api.RetrofitClientTest
import com.google.gson.Gson
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    lateinit var txtData: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtData = findViewById(R.id.txtData)
        txtData.setOnClickListener {
            getUserList()
        }
        getUserList()
    }

    private fun getUserList() {
        // var retrofit = RetrofitClient.getInstance()

        val retrofitClientTest = RetrofitClientTest()
        val apiInterface = retrofitClientTest.retrofit.create(ApiInterface::class.java)

        // Access via Singleton
        // val apiInterface = RetrofitClient.retrofitInstance.create(ApiInterface::class.java)

        lifecycleScope.launchWhenCreated {
            try {
                val response = apiInterface.getAllUsers()
                if (response.isSuccessful) {
                    val json = Gson().toJson(response.body())
                    if (response.body()?.data?.size!! <= 0) {
                        Toast.makeText(
                            this@MainActivity,
                            "No Data ",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        txtData.text = json
                    }
                    // retrofitClientTest.retrofit.callFactory()
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        response.errorBody().toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (Ex: Exception) {
                Ex.localizedMessage?.let { Log.e("Error", it) }
            }
        }
    }
}

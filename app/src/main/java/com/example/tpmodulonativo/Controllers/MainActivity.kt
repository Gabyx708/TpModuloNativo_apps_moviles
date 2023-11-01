package com.example.tpmodulonativo.Controllers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import com.example.tpmodulonativo.R
import com.example.tpmodulonativo.navigation.AppNavigation

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
           AppNavigation()
       }
    }
}
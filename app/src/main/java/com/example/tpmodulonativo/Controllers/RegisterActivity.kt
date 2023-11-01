package com.example.tpmodulonativo.Controllers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import com.example.tpmodulonativo.screens.RegisterScreen
import com.example.tpmodulonativo.ui.theme.TpModuloNativoTheme

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            TpModuloNativoTheme {

            }
        }
    }
}
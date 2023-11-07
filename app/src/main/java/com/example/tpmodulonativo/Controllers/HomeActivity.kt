package com.example.tpmodulonativo.Controllers

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.tpmodulonativo.ui.theme.TpModuloNativoTheme

enum class  ProviderType{
    BASIC
}
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            TpModuloNativoTheme {
            }
        }

    }

}
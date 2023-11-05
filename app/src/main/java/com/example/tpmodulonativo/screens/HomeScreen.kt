import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController){

    Scaffold() {
        Column {
            DecorativeBar()
            UserProfile()
            Spacer(modifier = Modifier.height(10.dp))
            OptionContainer()
            Spacer(modifier = Modifier.weight(1f))
            DecorativeBar()
        }
    }

}

@Composable
fun UserProfile(userName: String = "test") {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "user",
            modifier = Modifier.size(48.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Bienvenido "+userName, style = TextStyle(fontWeight = FontWeight.Bold))
    }
}


@Composable
fun OptionContainer(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
    ) {
        Button(
            onClick = { /* ... */ },
            contentPadding = PaddingValues(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .sizeIn(minHeight = 56.dp)
                .padding(16.dp)
                .weight(1f)
                .then(Modifier.background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(12.dp)))
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Add",
                    modifier = Modifier.size(150.dp)
                )
                Spacer(Modifier.size(8.dp)) // Espacio entre el icono y el texto
                Text(
                    text = "Hacer una donación",
                    fontSize = 20.sp
                )
            }
        }

        Button(
            onClick = { /* ... */ },
            contentPadding = PaddingValues(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .sizeIn(minHeight = 56.dp)
                .padding(16.dp)
                .weight(1f)
                .then(Modifier.background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(12.dp)))
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Favorite",
                    modifier = Modifier.size(150.dp)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(
                    text = "Buscar una donación",
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Composable
fun DecorativeBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(25.dp)
            .background(MaterialTheme.colorScheme.primary) // Usa el color principal de tu tema
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun HomeScreenPreview() {
    Scaffold() {
        Column {
            DecorativeBar()
            UserProfile()
            Spacer(modifier = Modifier.height(10.dp))
            OptionContainer()
            Spacer(modifier = Modifier.weight(1f))
            DecorativeBar()
        }
    }
}
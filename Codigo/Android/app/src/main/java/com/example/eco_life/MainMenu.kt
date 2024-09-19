package com.example.eco_life

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.ui.graphics.vector.ImageVector
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eco_life.ui.theme.EcoLifeTheme
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.eco_life.VideogameMenu

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EcoLifeTheme {
                Navigation()
            }
        }
    }
}

//Barra de navegación
@Composable
fun Navigation() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "main_menu",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("main_menu") { StartMenu() }
            composable("calculator_menu") { CalculatorMenu() }
            composable("places_menu") { PlacesMenu() }
            composable("videogame") { VideogameMenu() }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        NavigationItem.MainMenu,
        NavigationItem.CalculatorMenu,
        NavigationItem.PlacesMenu,
        NavigationItem.VideogameMenu
    )
    BottomNavigation(
        backgroundColor = Color.Green,
        contentColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = { Text(text = item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route)
                    }
                }
            )
        }
    }
}

sealed class NavigationItem(var route: String, var icon: ImageVector, var title: String) {
    object MainMenu : NavigationItem("main_menu", Icons.Default.Home, "Menú Principal")
    object CalculatorMenu : NavigationItem("calculator_menu", Icons.Default.Add, "Calculadora")
    object PlacesMenu : NavigationItem("places_menu", Icons.Default.Place, "Lugares")
    object VideogameMenu : NavigationItem("videogame", Icons.Default.Face, "Videojuego")
}

//Función para crear el "Carrusel" de Consejos
@Composable
fun TextCarousel() {
    //Variables de funcionamiento
    var currentIndex by remember { mutableIntStateOf(0) }
    //Valores de recomendaciones
    val items = listOf(
        "Desconectar electrodomésticos",
        "Viajar en conjunto",
        "Usar rastrillos de metal",
        "Clasificar basura",
        "Llevar basura centros de reciclaje"
    )
    val contents = listOf(
        "En la noche desconecta tus electrodomésticos para reducir su consumo eléctrico",
        "Si tú y algún familiar o amigo van a destinos cercanos, viajen en conjunto para hacer menos viajes",
        "Resultan más duraderos que rastrillos de plástico y generan menos residuos",
        "Al separarse dependiendo de si son papel, plástico, metal o vidrio, facilitas su reciclaje.",
        "Ayudandoles a cumplir con su labor de reciclar basura, reduciendo el tiempo usado en recolectarla."
    )
    //Valores estéticos
    val lightGray = Color(194,194,194)
    val beige = Color(230,230,230)
    val headerGreen = Color(17,109,29)
    val rowHeight = 80.dp
    val buttonCornerRadius = 12.dp

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 32.dp,
                end = 32.dp
            )
    ) {
        IconButton(
            onClick = { if (currentIndex > 0) currentIndex-- },
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = buttonCornerRadius,
                        bottomStart = buttonCornerRadius
                    )
                )
                .background(lightGray)
                .height(rowHeight)
        ) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "Previous"
            )
        }

        LazyRow(
            modifier = Modifier
                .weight(1f)
                .height(rowHeight)
                .background(beige),
            horizontalArrangement = Arrangement.Center
        ) {
            items(items.size) { index ->
                if (index == currentIndex) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Título del consejo
                        Text(
                            text = items[index],
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = headerGreen
                        )
                        // Contenido del consejo
                        Text(
                            text = contents[index],
                            fontSize = 14.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }
        //Botón para avanzar a la siguiente recomendación
        IconButton(
            onClick = { if (currentIndex < items.size - 1) currentIndex++ },
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topEnd = buttonCornerRadius,
                        bottomEnd = buttonCornerRadius
                    )
                )
                .background(lightGray)
                .height(rowHeight)
        ) {
            Icon(
                Icons.Default.ArrowForward, contentDescription = "Next"
            )
        }
    }
}

@Composable
fun StartMenu() {
    //Valores estéticos
    val beige = Color(230,230,230)
    val textSize = 20.sp
    val textHeight = textSize.value.dp
    val customColor = Color(30, 132, 73 )
    val buttonCornerRadius = 12.dp

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .background(Color.Green)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Inicio",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = textSize,
                    color = Color.White,
                    modifier = Modifier
                        .padding(
                            top = 8.dp,
                            start = 16.dp
                        )
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End
            ) {
                Image(
                    painter = painterResource(id = R.drawable.eco_espacio),
                    contentDescription = "Eco Espacio Logo",
                    modifier = Modifier
                        .height(textHeight*2)
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp
                )
                .background(Color.White),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "Recomendaciones",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                color = customColor,
                fontSize = textSize*1.4
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            horizontalArrangement = Arrangement.Start
        ) {
            TextCarousel()
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .background(Color.White),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "Retos",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                color = customColor,
                fontSize = textSize*1.25,
                modifier = Modifier
                    .padding(start = 24.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 32.dp,
                    end = 32.dp
                )
                .clip(
                    RoundedCornerShape(
                        topStart = buttonCornerRadius,
                        topEnd = buttonCornerRadius
                    )
                )
                .background(beige),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "Reto Activo",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                color = customColor,
                fontSize = textSize,
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 16.dp
                    )
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 32.dp,
                    end = 32.dp
                )
                .background(beige),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "Usar una bolsa reutilizable para realizar las compras.",
                fontFamily = FontFamily.Serif,
                color = Color.Black,
                fontSize = textSize,
                modifier = Modifier
                    .padding(
                        top = 12.dp,
                        start = 16.dp
                    )
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 32.dp,
                    end = 32.dp
                )
                .background(beige),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "Racha de la semana",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                color = customColor,
                fontSize = textSize,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(
                        top = 12.dp,
                        start = 16.dp
                    )
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 32.dp,
                    end = 32.dp
                )
                .clip(
                    RoundedCornerShape(
                        bottomStart = buttonCornerRadius,
                        bottomEnd = buttonCornerRadius
                    )
                )
                .background(beige),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "Contenido final",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                color = customColor,
                fontSize = textSize,
                modifier = Modifier
                    .padding(
                        start = 16.dp
                    )
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EcoLifeTheme {
        StartMenu()
    }
}
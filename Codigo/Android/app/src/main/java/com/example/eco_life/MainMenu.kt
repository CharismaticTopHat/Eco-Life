package com.example.eco_life

import androidx.compose.ui.graphics.vector.ImageVector
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eco_life.ui.theme.EcoLifeTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import com.example.ecoshops.data.DataSource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import com.example.eco_life.data.DBHandler
import java.time.LocalDate
import java.time.DayOfWeek

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

//Barra de navegación (Presentación)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "main_menu",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("main_menu") { StartMenu() }
            composable("calculator_menu") { NavigationCalculator() }
            composable("places_menu") { EcoPlaceList(
                placeList = DataSource().loadEcoPlaces(),
                modifier = Modifier.padding(innerPadding)
            ) }
            composable("videogame") { VideogameMenu() }
        }
    }
}

//Barra de Navegación (Visual)
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
    data object MainMenu : NavigationItem("main_menu", Icons.Default.Home, "Menú Principal")
    data object CalculatorMenu : NavigationItem("calculator_menu", Icons.Default.Add, "Calculadora")
    data object PlacesMenu : NavigationItem("places_menu", Icons.Default.Place, "Lugares")
    data object VideogameMenu : NavigationItem("videogame", Icons.Default.Face, "Videojuego")
}

//Función para crear el "Carrusel" de Consejos
@Composable
fun TextCarousel() {
    //Variables de funcionamiento
    var currentIndex by remember { mutableIntStateOf(0) }
    var size by remember { mutableStateOf(IntSize.Zero) }
    //Títulos de consejos
    val items = listOf(
        stringResource(R.string.titleTip1),
        stringResource(R.string.titleTip2),
        stringResource(R.string.titleTip3),
        stringResource(R.string.titleTip4),
        stringResource(R.string.titleTip5)
    )
    //Descripción del Consejo
    val contents = listOf(
        stringResource(R.string.contentTip1),
        stringResource(R.string.contentTip2),
        stringResource(R.string.contentTip3),
        stringResource(R.string.contentTip4),
        stringResource(R.string.contentTip5)
    )
    //Valores estéticos
    val lightGray = Color(194,194,194)
    val beige = Color(230,230,230)
    val headerGreen = Color(17,109,29)
    val rowHeight = 120.dp
    val rowHeightPartial = 88.dp
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
        IconButton( //Botón para ver un consejo anterior
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
        //Contenedor de las recomendaciones
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .background(beige)
                .onSizeChanged {
                    size = it
                },
            //horizontalArrangement = Arrangement.Center
        ) {
            items(items.size) { index ->
                if (index == currentIndex) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .then(
                                with(LocalDensity.current) {
                                    Modifier.size(
                                        width = size.width.toDp(),
                                        height = rowHeightPartial,
                                    )
                                }
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text(
                            text = items[index],
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = headerGreen,
                            maxLines = 2,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(
                                    start = 4.dp,
                                    end = 4.dp
                                )
                        )
                        // Contenido del consejo
                        Text(
                            text = contents[index],
                            fontSize =12.sp,
                            color = Color.Black,
                            maxLines = 3,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier
                                .padding(
                                    top = 8.dp,
                                    start = 4.dp,
                                    end = 4.dp
                                )
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
    val headerGreen = Color(17,109,29)
    val textSize = 16.sp
    val textHeight = textSize.value.dp
    val customColor = Color(30, 132, 73 )
    val buttonCornerRadius = 12.dp
    //Variables de Funcionamiento
    var currentChallengeId by remember { mutableStateOf(R.string.challenge0) }
    val challengeIds = listOf(
        R.string.challenge1,
        R.string.challenge2,
        R.string.challenge3,
        R.string.challenge4,
        R.string.challenge5
    )
    val challengeState = listOf(
        R.string.challenge999,
        R.string.challenge1000,
        R.string.challenge1001
    )
    val coroutineScope = rememberCoroutineScope()
    val currentDate = LocalDate.now()
    val currentDayOfWeek = currentDate.dayOfWeek.value
    var lastCompletedDay by remember { mutableStateOf<LocalDate?>(null) }
    val context = LocalContext.current
    val dbHandler = remember { DBHandler(context) }
    val (initialDayStatus, initialCurrentStreak, initialHighestStreak) = remember { dbHandler.getUserProgress() }
    var dayStatus by remember { mutableStateOf(initialDayStatus) }
    var currentStreak by remember { mutableStateOf(initialCurrentStreak) }
    var highestStreak by remember { mutableStateOf(initialHighestStreak) }

    LaunchedEffect(currentDate) {
        val lastDay = lastCompletedDay // Assign to a local variable

        if (currentDayOfWeek == DayOfWeek.MONDAY.value) {
            if (lastDay == null || lastDay.isBefore(currentDate.minusDays(6))) {
                dayStatus = arrayOf("waiting", "waiting", "waiting", "waiting", "waiting", "waiting", "waiting")
                currentStreak = 0
            }
        }

        val previousDayStatus = dayStatus[currentDayOfWeek - 1]

        if (previousDayStatus == "completed") {
            if (lastDay != currentDate.minusDays(1)) {
                currentStreak = 1
            } else {
                currentStreak += 1
            }
            if (currentStreak > highestStreak) {
                highestStreak = currentStreak
            }
            lastCompletedDay = currentDate
        } else if (previousDayStatus == "waiting" || lastDay != currentDate.minusDays(1)) {
            currentStreak = 0
        }

        dbHandler.updateUserProgress(dayStatus, currentStreak, highestStreak)
    }
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
                fontSize = textSize*1.4,
                modifier = Modifier
                    .padding(start = 16.dp)
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
                    .padding(start = 32.dp)
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
                .padding(start = 32.dp, end = 32.dp)
                .background(beige),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = stringResource(id = currentChallengeId),
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black,
                fontSize = textSize,
                modifier = Modifier
                    .padding(top = 12.dp, start = 16.dp, end = 4.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp)
                .background(beige),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (currentChallengeId != R.string.challenge0) {
                Button(
                    onClick = {
                        currentChallengeId = R.string.challenge999
                        dayStatus[currentDayOfWeek - 1] = "completed"
                        coroutineScope.launch {
                            dbHandler.updateUserProgress(dayStatus, currentStreak, highestStreak)
                            delay(5000)
                            currentChallengeId = R.string.challenge0
                        }
                    },
                    modifier = Modifier
                        .defaultMinSize(minWidth = 120.dp)
                        .height(70.dp)
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Green,
                        contentColor = headerGreen
                    )
                ) {
                    Text(
                        text = "Completar reto",
                        fontSize = textSize,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                Button(
                    onClick = {
                        currentChallengeId = R.string.challenge1000
                        coroutineScope.launch {
                            delay(5000)
                            currentChallengeId = R.string.challenge0
                        }
                    },
                    modifier = Modifier
                        .defaultMinSize(minWidth = 120.dp)
                        .height(70.dp)
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Red,
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        text = "Omitir reto",
                        fontSize = textSize,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
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
                fontSize = textSize * 1.2,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(
                        top = 12.dp,
                        start = 16.dp
                    )
            )
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .padding(start = 32.dp, end = 32.dp)
                .background(beige)
        )
        LaunchedEffect(currentDayOfWeek) {
            dayStatus = dayStatus.mapIndexed { index, status ->
                if (index < currentDayOfWeek - 1 && status == "waiting") "failed" else status
            }.toTypedArray()
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp)
                .background(beige),
            horizontalArrangement = Arrangement.Start
        ) {
            for (i in 1..7) {
                val iconRes = when {
                    dayStatus[i - 1] == "completed" -> R.drawable.greenleaf
                    dayStatus[i - 1] == "failed" -> R.drawable.redleaf
                    else -> R.drawable.grayleaf
                }
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = "Icono para día $i",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(start = 16.dp)
                )
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .padding(start = 32.dp, end = 32.dp)
                .background(beige)
        )
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
                text = "Análisis mensual",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = textSize * 1.2,
                color = headerGreen,
                modifier = Modifier
                    .padding(start = 16.dp)
            )
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .padding(start = 32.dp, end = 32.dp)
                .background(beige)
        )
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
            Column {
                Text(
                    text = "Tú racha actual: ",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = textSize/1.25,
                    color = headerGreen,
                    modifier = Modifier
                        .padding(start = 16.dp)
                )
            }
            Column {
                Text(
                    text = "$currentStreak días ",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = textSize/1.5,
                    modifier = Modifier
                        .padding(start = 16.dp)
                )
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .padding(start = 32.dp, end = 32.dp)
                .background(beige)
        )
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
            Column {
                Text(
                    text = "Tu racha más alta: ",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = textSize/1.25,
                    color = headerGreen,
                    modifier = Modifier
                        .padding(start = 16.dp)
                )
            }
            Column {
                Text(
                    text = "$highestStreak días ",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = textSize/1.5,
                    modifier = Modifier
                        .padding(start = 16.dp)
                )
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
                .padding(start = 32.dp, end = 32.dp)
                .background(beige)
        )
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
            Button(
                onClick = {
                    if (currentChallengeId == R.string.challenge0) {
                        currentChallengeId = challengeIds.random()
                    } else {
                        coroutineScope.launch {
                            val previousChallengeId = currentChallengeId
                            currentChallengeId = R.string.challenge1001
                            delay(5000)
                            currentChallengeId = previousChallengeId
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(
                        RoundedCornerShape(buttonCornerRadius)
                    )
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Green,
                    contentColor = headerGreen
                )
            ) {
                Text(
                    text = "Generar un reto",
                    fontSize = textSize * 2,
                    fontWeight = FontWeight.ExtraBold
                )
            }
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
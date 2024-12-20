package com.example.eco_life

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eco_life.data.DBHandler
import java.time.LocalDate

class EmissionsTransportActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EcoLifeTheme {
                NavigationTransportEmissions()
            }
        }
    }
}

@Composable
fun NavigationTransportEmissions() {
    val navController = rememberNavController()
    Scaffold(
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "emissions_transport",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("calculator_menu") { CalculatorMenu(navController = navController) }
            composable("emissions_transport") { EmissionsTransportMenu(navController = navController) }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LandTransportEmissions(onValueSelected: (Double) -> Unit, navController: NavController) {
    val context = LocalContext.current
    val beige = Color(190,190,190)
    val headerGreen = Color(17,109,29)
    val textSize = 16.sp
    val green = Color(0, 154, 20)
    val buttonCornerRadius = 12.dp
    var emissionFactor by remember { mutableStateOf(0.0) }
    var emissionValue by remember { mutableStateOf(0.0) }
    val type = "Transport"
    var hours by remember { mutableStateOf("") }
    var isInputValid by remember { mutableStateOf(true) }
    var selectedButton by remember { mutableStateOf<Int?>(null) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 24.dp,
                start = 16.dp,
                end = 20.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        ButtonEnergyEmissions(
            text = "Auto (Gasolina)",
            onClick = {
                selectedButton = 0
                emissionFactor = 2.3
                emissionValue = 0.12
            },
            customColor = if (selectedButton == 0) green else beige
        )
        ButtonEnergyEmissions(
            text = "Auto (Diésel)",
            onClick = {
                selectedButton = 1
                emissionFactor = 2.7
                emissionValue = 0.1
            },
            customColor = if (selectedButton == 1) green else beige
        )
        ButtonEnergyEmissions(
            text = "Auto (Híbrido)",
            onClick = {
                selectedButton = 2
                emissionFactor = 0.8
                emissionValue = 0.04
            },
            customColor = if (selectedButton == 2) green else beige
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 100.dp,
                start = 16.dp,
                end = 20.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        ButtonEnergyEmissions(
            text = "Motocicleta",
            onClick = {
                selectedButton = 3
                emissionFactor = 1.7
                emissionValue = 0.05
            },
            customColor = if (selectedButton == 3) green else beige
        )
        ButtonEnergyEmissions(
            text = "Bicicleta (Eléctrica)",
            onClick = {
                selectedButton = 4
                emissionFactor = 0.05
                emissionValue = 0.01
            },
            customColor = if (selectedButton == 4) green else beige
        )
        ButtonEnergyEmissions(
            text = "Scooter (Eléctrico)",
            onClick = {
                selectedButton = 5
                emissionFactor = 0.02
                emissionValue = 0.005
            },
            customColor = if (selectedButton == 5) green else beige
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 180.dp,
                start = 16.dp,
                end = 20.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        ButtonEnergyEmissions(
            text = "Camioneta",
            onClick = {
                selectedButton = 6
                emissionFactor = 2.5
                emissionValue = 0.15
            },
            customColor = if (selectedButton == 6) green else beige
        )
        ButtonEnergyEmissions(
            text = "Camión de Transporte",
            onClick = {
                selectedButton = 7
                emissionFactor = 1.2
                emissionValue = 0.2
            },
            customColor = if (selectedButton == 7) green else beige
        )
        ButtonEnergyEmissions(
            text = "Metro",
            onClick = {
                selectedButton = 8
                emissionFactor = 0.1
                emissionValue = 0.03
            },
            customColor = if (selectedButton == 8) green else beige
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 100.dp,
                start = 16.dp,
                end = 20.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 170.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = hours,
                onValueChange = { input ->
                    hours = input
                    isInputValid = input.toDoubleOrNull() != null
                },
                label = { Text("Ingrese las horas (Decimal): ") },
                isError = !isInputValid,
                modifier = Modifier.fillMaxWidth()
            )
            if (!isInputValid) {
                Text(
                    text = "Por favor, ingrese un valor con decimal.",
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 100.dp,
                start = 16.dp,
                end = 20.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 240.dp,
                        start = 44.dp,
                        end = 20.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Button(
                        onClick = {
                            navController.navigate("calculator_menu")
                        },
                        modifier = Modifier
                            .height(80.dp)
                            .clip(RoundedCornerShape(buttonCornerRadius))
                            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = beige,
                            contentColor = headerGreen
                        )
                    ) {
                        Text(
                            text = "Cancelar",
                            fontSize = textSize,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black
                        )
                    }
                }

                Column {
                    Button(
                        onClick = {
                            val hoursDouble = hours.toDoubleOrNull()
                            if (emissionFactor != 0.0 && hoursDouble != null) {
                                saveToTransportEmissions(context, emissionFactor, emissionValue, type, hoursDouble)
                                navController.navigate("calculator_menu")
                            }
                        },
                        modifier = Modifier
                            .height(80.dp)
                            .clip(RoundedCornerShape(buttonCornerRadius))
                            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Green,
                            contentColor = headerGreen
                        )
                    ) {
                        Text(
                            text = "Continuar",
                            fontSize = textSize,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AirTransportEmissions(onValueSelected: (Double) -> Unit, navController: NavController) {
    val context = LocalContext.current
    val beige = Color(190,190,190)
    val headerGreen = Color(17,109,29)
    val textSize = 16.sp
    val green = Color(0, 154, 20)
    val buttonCornerRadius = 12.dp
    var emissionFactor by remember { mutableStateOf(0.0) }
    var emissionValue by remember { mutableStateOf(0.0) }
    val type = "Transport"
    var hours by remember { mutableStateOf("") }
    var isInputValid by remember { mutableStateOf(true) }
    var selectedButton by remember { mutableStateOf<Int?>(null) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 24.dp,
                start = 16.dp,
                end = 20.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        ButtonEnergyEmissions(
            text = "Avión (Comercial)",
            onClick = {
                selectedButton = 0
                emissionFactor = 0.25
                emissionValue = 0.04
            },
            customColor = if (selectedButton == 0) green else beige
        )
        ButtonEnergyEmissions(
            text = "Jet Privado",
            onClick = {
                selectedButton = 1
                emissionFactor = 0.8
                emissionValue = 0.1
            },
            customColor = if (selectedButton == 1) green else beige
        )
        ButtonEnergyEmissions(
            text = "Helicóptero",
            onClick = {
                selectedButton = 2
                emissionFactor = 1.5
                emissionValue = 0.15
            },
            customColor = if (selectedButton == 2) green else beige
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 20.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 170.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = hours,
                onValueChange = { input ->
                    hours = input
                    isInputValid = input.toDoubleOrNull() != null
                },
                label = { Text("Ingrese las horas (Decimal): ") },
                isError = !isInputValid,
                modifier = Modifier.fillMaxWidth()
            )
            if (!isInputValid) {
                Text(
                    text = "Por favor, ingrese un valor con decimal.",
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 20.dp,
                start = 16.dp,
                end = 20.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 240.dp,
                        start = 44.dp,
                        end = 20.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Button(
                        onClick = {
                            navController.navigate("calculator_menu")
                        },
                        modifier = Modifier
                            .height(80.dp)
                            .clip(RoundedCornerShape(buttonCornerRadius))
                            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = beige,
                            contentColor = headerGreen
                        )
                    ) {
                        Text(
                            text = "Cancelar",
                            fontSize = textSize,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black
                        )
                    }
                }

                Column {
                    Button(
                        onClick = {
                            val hoursDouble = hours.toDoubleOrNull()
                            if (emissionFactor != 0.0 && hoursDouble != null) {
                                saveToTransportEmissions(context, emissionFactor, emissionValue, type, hoursDouble)
                                navController.navigate("calculator_menu")
                            }
                        },
                        modifier = Modifier
                            .height(80.dp)
                            .clip(RoundedCornerShape(buttonCornerRadius))
                            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Green,
                            contentColor = headerGreen
                        )
                    ) {
                        Text(
                            text = "Continuar",
                            fontSize = textSize,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WaterTransportEmissions(onValueSelected: (Double) -> Unit, navController: NavController) {
    val context = LocalContext.current
    val beige = Color(190,190,190)
    val headerGreen = Color(17,109,29)
    val textSize = 16.sp
    val green = Color(0, 154, 20)
    val buttonCornerRadius = 12.dp
    var emissionFactor by remember { mutableStateOf(0.0) }
    var emissionValue by remember { mutableStateOf(0.0) }
    val type = "Transport"
    var hours by remember { mutableStateOf("") }
    var isInputValid by remember { mutableStateOf(true) }
    var selectedButton by remember { mutableStateOf<Int?>(null) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 24.dp,
                start = 16.dp,
                end = 20.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        ButtonEnergyEmissions(
            text = "Barco de Pasajeros",
            onClick = {
                selectedButton = 0
                emissionFactor = 4.5
                emissionValue = 0.5
            },
            customColor = if (selectedButton == 0) green else beige
        )
        ButtonEnergyEmissions(
            text = "Yate",
            onClick = {
                selectedButton = 1
                emissionFactor = 3.0
                emissionValue = 0.4
            },
            customColor = if (selectedButton == 1) green else beige
        )
        ButtonEnergyEmissions(
            text = "Ferri",
            onClick = {
                selectedButton = 2
                emissionFactor = 2.5
                emissionValue = 0.3
            },
            customColor = if (selectedButton == 2) green else beige
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 100.dp,
                start = 16.dp,
                end = 20.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        ButtonEnergyEmissions(
            text = "Buque de carga",
            onClick = {
                selectedButton = 3
                emissionFactor = 5.0
                emissionValue = 0.8
            },
            customColor = if (selectedButton == 3) green else beige
        )
        ButtonEnergyEmissions(
            text = "Canoa (Eléctrica)",
            onClick = {
                selectedButton = 4
                emissionFactor = 0.05
                emissionValue = 0.01
            },
            customColor = if (selectedButton == 4) green else beige
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 20.dp,
                start = 16.dp,
                end = 20.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 170.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = hours,
                onValueChange = { input ->
                    hours = input
                    isInputValid = input.toDoubleOrNull() != null
                },
                label = { Text("Ingrese las horas (Decimal): ") },
                isError = !isInputValid,
                modifier = Modifier.fillMaxWidth()
            )
            if (!isInputValid) {
                Text(
                    text = "Por favor, ingrese un valor con decimal.",
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 20.dp,
                start = 16.dp,
                end = 20.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 240.dp,
                        start = 44.dp,
                        end = 20.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Button(
                        onClick = {
                            navController.navigate("calculator_menu")
                        },
                        modifier = Modifier
                            .height(80.dp)
                            .clip(RoundedCornerShape(buttonCornerRadius))
                            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = beige,
                            contentColor = headerGreen
                        )
                    ) {
                        Text(
                            text = "Cancelar",
                            fontSize = textSize,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black
                        )
                    }
                }

                Column {
                    Button(
                        onClick = {
                            val hoursDouble = hours.toDoubleOrNull()
                            if (emissionFactor != 0.0 && hoursDouble != null) {
                                saveToTransportEmissions(context, emissionFactor, emissionValue, type, hoursDouble)
                                navController.navigate("calculator_menu")
                            }
                        },
                        modifier = Modifier
                            .height(80.dp)
                            .clip(RoundedCornerShape(buttonCornerRadius))
                            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Green,
                            contentColor = headerGreen
                        )
                    ) {
                        Text(
                            text = "Continuar",
                            fontSize = textSize,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OtherTransportEmissions(onValueSelected: (Double) -> Unit, navController: NavController) {
    val context = LocalContext.current
    val beige = Color(190, 190, 190)
    val green = Color(0, 154, 20)
    val headerGreen = Color(17,109,29)
    val textSize = 16.sp
    val customColor = Color(30, 132, 73 )
    val buttonCornerRadius = 12.dp
    var emissionFactor by remember { mutableStateOf(0.0) }
    var emissionValue by remember { mutableStateOf(0.0) }
    val type = "Transport"
    var hours by remember { mutableStateOf("") }
    var isInputValid by remember { mutableStateOf(true) }
    var selectedButton by remember { mutableStateOf<Int?>(null) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 24.dp,
                start = 16.dp,
                end = 20.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        listOf(
            "Patineta Eléctrica" to 0,
            "Vehículo (Todo Terreno)" to 1
        ).forEach { (label, index) ->
            ButtonTransportEmissions(
                text = label,
                onClick = {
                    selectedButton = index
                    emissionFactor = if (index == 0) 0.03 else 3.0
                    emissionValue = if (index == 0) 0.01 else 0.2
                },
                customColor = if (selectedButton == index) green else beige
            )
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 20.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 170.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = hours,
                onValueChange = { input ->
                    hours = input
                    isInputValid = input.toDoubleOrNull() != null
                },
                label = { Text("Ingrese las horas (Decimal): ") },
                isError = !isInputValid,
                modifier = Modifier.fillMaxWidth()
            )
            if (!isInputValid) {
                Text(
                    text = "Por favor, ingrese un valor con decimal.",
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 20.dp,
                start = 16.dp,
                end = 20.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 240.dp,
                        start = 44.dp,
                        end = 20.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Button(
                        onClick = {
                            navController.navigate("calculator_menu")
                        },
                        modifier = Modifier
                            .height(80.dp)
                            .clip(RoundedCornerShape(buttonCornerRadius))
                            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = beige,
                            contentColor = headerGreen
                        )
                    ) {
                        Text(
                            text = "Cancelar",
                            fontSize = textSize,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black
                        )
                    }
                }

                Column {
                    Button(
                        onClick = {
                            val hoursDouble = hours.toDoubleOrNull()
                            if (emissionFactor != 0.0 && hoursDouble != null) {
                                saveToTransportEmissions(context, emissionFactor, emissionValue, type, hoursDouble)
                                navController.navigate("calculator_menu")
                            }
                        },
                        modifier = Modifier
                            .height(80.dp)
                            .clip(RoundedCornerShape(buttonCornerRadius))
                            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Green,
                            contentColor = headerGreen
                        )
                    ) {
                        Text(
                            text = "Continuar",
                            fontSize = textSize,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EmissionsTransportMenu(navController: NavController) {
    // Valores estéticos
    val beige = Color(230, 230, 230)
    val green = Color(0, 154, 20)
    var emissionFactor by remember { mutableStateOf(0) }
    var selectedScreen by remember { mutableStateOf<@Composable () -> Unit>({}) }
    var selectedButton by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Tipo de Transporte",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    color = Color.Green,
                    modifier = Modifier
                        .padding(top = 16.dp, start = 16.dp)
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 16.dp, end = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val images = listOf(
                Pair(R.drawable.car, 1),
                Pair(R.drawable.plane, 2),
                Pair(R.drawable.boat, 3),
                Pair(R.drawable.more, 4)
            )

            images.forEach { (imageRes, value) ->
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(
                                color = if (selectedButton == value) green else beige,
                                shape = CircleShape
                            )
                            .clickable {
                                selectedButton = value
                                selectedScreen =
                                    getTransportScreen(value, navController) { selectedEmissionFactor ->
                                        emissionFactor = selectedEmissionFactor.toInt()
                                    }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = "Transport $value",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
        }

        Box(modifier = Modifier.fillMaxWidth()) {
            selectedScreen()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun saveToTransportEmissions(context: Context, emissionFactor: Double,emissionValue: Double, type: String, hours: Double) {
    val dbHandler = DBHandler(context)
    val currentDate = LocalDate.now().toString()

    dbHandler.addEmission(emissionFactor, emissionValue, currentDate, type, hours)
    Toast.makeText(
        context,
        "El impacto del transporte ha sido registrado",
        Toast.LENGTH_SHORT
    ).show()
}

@RequiresApi(Build.VERSION_CODES.O)
fun getTransportScreen(emissionFactor: Int, navController: NavController, onValueSelected: (Double) -> Unit): @Composable () -> Unit {
    return when (emissionFactor) {
        1 -> { { LandTransportEmissions(onValueSelected, navController) } }
        2 -> { { AirTransportEmissions(onValueSelected, navController) } }
        3 -> { { WaterTransportEmissions(onValueSelected, navController) } }
        4 -> { { OtherTransportEmissions(onValueSelected, navController) } }
        else -> { {} }
    }
}

@Composable
fun ButtonTransportEmissions(
    text: String,
    onClick: () -> Unit,
    buttonWidth: Dp = 110.dp,
    buttonHeight: Dp = 80.dp,
    maxFontSize: TextUnit = 11.sp,
    customColor: Color = Color(30, 132, 73),
    buttonCornerRadius: Dp = 12.dp,
    headerGreen: Color = Color(17, 109, 29)
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .size(width = buttonWidth, height = buttonHeight)
            .clip(RoundedCornerShape(buttonCornerRadius))
            .padding(8.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = customColor,
            contentColor = headerGreen
        )
    ) {
        Text(
            text = text,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            softWrap = true,
            style = TextStyle(
                fontSize = maxFontSize,
                fontWeight = FontWeight.ExtraBold
            ),
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun EmissionsTransportPreview() {
    EcoLifeTheme {
        NavigationTransportEmissions()
    }
}
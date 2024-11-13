package com.example.eco_life

import android.content.Context
import android.os.Build
import androidx.compose.ui.graphics.vector.ImageVector
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.width
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
import com.example.eco_life.VideogameMenu
import kotlinx.coroutines.launch
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.example.eco_life.data.DBHandler
import com.google.android.gms.maps.model.Circle
import java.time.LocalDate
import java.time.DayOfWeek

class EmissionsEnergyActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EcoLifeTheme {
                EmissionsEnergyMenu()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun KitchenEmissions(onValueSelected: (Double) -> Unit) {
    val context = LocalContext.current
    val beige = Color(190,190,190)
    val headerGreen = Color(17,109,29)
    val textSize = 16.sp
    val green = Color(30, 132, 73 )
    val buttonCornerRadius = 12.dp
    var emissionFactor by remember { mutableStateOf(0.0) }
    var emissionValue by remember { mutableStateOf(0.0) }
    val type = "Energy"
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
            text = "Abrelatas",
            onClick = {
                selectedButton = 0
                emissionFactor = 0.03
                emissionValue = 0.06
            },
            customColor = if (selectedButton == 0) green else beige
        )
        ButtonEnergyEmissions(
            text = "Exprimidor",
            onClick = {
                selectedButton = 1
                emissionFactor = 0.02
                emissionValue = 0.15
            },
            customColor = if (selectedButton == 1) green else beige
        )
        ButtonEnergyEmissions(
            text = "Batidora",
            onClick = {
                selectedButton = 2
                emissionFactor = 0.09
                emissionValue = 1.8
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
            text = "Licuadora",
            onClick = {
                selectedButton = 3
                emissionFactor = 0.03
                emissionValue = 0.06
            },
            customColor = if (selectedButton == 3) green else beige
        )
        ButtonEnergyEmissions(
            text = "Tostadora",
            onClick = {
                selectedButton = 4
                emissionFactor = 0.02
                emissionValue = 0.15
            },
            customColor = if (selectedButton == 4) green else beige
        )
        ButtonEnergyEmissions(
            text = "Horno Eléctrico",
            onClick = {
                selectedButton = 5
                emissionFactor = 0.09
                emissionValue = 1.8
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
            text = "Microondas",
            onClick = {
                selectedButton = 6
                emissionFactor = 0.03
                emissionValue = 0.06
            },
            customColor = if (selectedButton == 6) green else beige
        )
        ButtonEnergyEmissions(
            text = "Cafetera",
            onClick = {
                selectedButton = 7
                emissionFactor = 0.02
                emissionValue = 0.15
            },
            customColor = if (selectedButton == 7) green else beige
        )
        ButtonEnergyEmissions(
            text = "Plancha",
            onClick = {
                selectedButton = 8
                emissionFactor = 0.09
                emissionValue = 1.8
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
fun ElectrodomesticsEmissions(onValueSelected: (Double) -> Unit) {
    val context = LocalContext.current
    val beige = Color(190,190,190)
    val headerGreen = Color(17,109,29)
    val textSize = 16.sp
    val green = Color(30, 132, 73 )
    val buttonCornerRadius = 12.dp
    var emissionFactor by remember { mutableStateOf(0.0) }
    var emissionValue by remember { mutableStateOf(0.0) }
    val type = "Energy"
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
            text = "Cosedora",
            onClick = {
                selectedButton = 0
                emissionFactor = 0.12
                emissionValue = 2.3
            },
            customColor = if (selectedButton == 0) green else beige
        )
        ButtonEnergyEmissions(
            text = "Bomba de agua",
            onClick = {
                selectedButton = 1
                emissionFactor = 0.2
                emissionValue = 5.0
            },
            customColor = if (selectedButton == 1) green else beige
        )
        ButtonEnergyEmissions(
            text = "Lavadora",
            onClick = {
                selectedButton = 2
                emissionFactor = 0.25
                emissionValue = 13.0
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
            text = "Secadora",
            onClick = {
                selectedButton = 3
                emissionFactor = 0.3
                emissionValue = 9.0
            },
            customColor = if (selectedButton == 3) green else beige
        )
        ButtonEnergyEmissions(
            text = "Aspiradora (Horizontal)",
            onClick = {
                selectedButton = 4
                emissionFactor = 0.4
                emissionValue = 13.0
            },
            customColor = if (selectedButton == 4) green else beige
        )
        ButtonEnergyEmissions(
            text = "Aspiradora (Vertical=",
            onClick = {
                selectedButton = 5
                emissionFactor = 0.5
                emissionValue = 16.0
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
            text = "Abrelatas",
            onClick = {
                selectedButton = 7
                emissionFactor = 0.04
                emissionValue = 16.0
            },
            customColor = if (selectedButton == 7) green else beige
        )
        ButtonEnergyEmissions(
            text = "Exprimidor",
            onClick = {
                selectedButton = 8
                emissionFactor = 0.04
                emissionValue = 16.0
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
fun EntertainmentEmissions(onValueSelected: (Double) -> Unit) {
    val context = LocalContext.current
    val beige = Color(190,190,190)
    val headerGreen = Color(17,109,29)
    val textSize = 16.sp
    val green = Color(30, 132, 73 )
    val buttonCornerRadius = 12.dp
    var emissionFactor by remember { mutableStateOf(0.0) }
    var emissionValue by remember { mutableStateOf(0.0) }
    val type = "Energy"
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
            text = "Videocasetera",
            onClick = {
                selectedButton = 0
                emissionFactor = 0.1
                emissionValue = 1.2
            },
            customColor = if (selectedButton == 0) green else beige
        )
        ButtonEnergyEmissions(
            text = "Tocadiscos",
            onClick = {
                selectedButton = 1
                emissionFactor = 0.1
                emissionValue = 2.5
            },
            customColor = if (selectedButton == 1) green else beige
        )
        ButtonEnergyEmissions(
            text = "Estéreo",
            onClick = {
                selectedButton = 2
                emissionFactor = 0.04
                emissionValue = 9.0
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
            text = "Radiograbadora",
            onClick = {
                selectedButton = 3
                emissionFactor = 0.04
                emissionValue = 8.0
            },
            customColor = if (selectedButton == 3) green else beige
        )
        ButtonEnergyEmissions(
            text = "Consola (Videojuegos)",
            onClick = {
                selectedButton = 4
                emissionFactor = 0.25
                emissionValue = 30.0
            },
            customColor = if (selectedButton == 4) green else beige
        )
        ButtonEnergyEmissions(
            text = "Computadora",
            onClick = {
                selectedButton = 5
                emissionFactor = 0.3
                emissionValue = 36.0
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
            text = "TV (13\"-17\")",
            onClick = {
                selectedButton = 6
                emissionFactor = 0.05
                emissionValue = 10.0
            },
            customColor = if (selectedButton == 6) green else beige
        )
        ButtonEnergyEmissions(
            text = "TV (19\"-21\")",
            onClick = {
                selectedButton = 7
                emissionFactor = 0.07
                emissionValue = 21.0
            },
            customColor = if (selectedButton == 7) green else beige
        )
        ButtonEnergyEmissions(
            text = "TV (24\"-29\")",
            onClick = {
                selectedButton = 8
                emissionFactor = 0.12
                emissionValue = 22.0
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
fun RefrigerationEmissions(onValueSelected: (Double) -> Unit) {
    val context = LocalContext.current
    val beige = Color(190,190,190)
    val headerGreen = Color(17,109,29)
    val textSize = 16.sp
    val green = Color(30, 132, 73 )
    val buttonCornerRadius = 12.dp
    var emissionFactor by remember { mutableStateOf(0.0) }
    var emissionValue by remember { mutableStateOf(0.0) }
    val type = "Energy"
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
            text = "Refrigerador (11'-12')",
            onClick = {
                selectedButton = 0
                emissionFactor = 0.2
                emissionValue = 60.0
            },
            customColor = if (selectedButton == 0) green else beige
        )
        ButtonEnergyEmissions(
            text = "Refrigerador (14'-16')",
            onClick = {
                selectedButton = 1
                emissionFactor = 0.23
                emissionValue = 70.0
            },
            customColor = if (selectedButton == 1) green else beige
        )
        ButtonEnergyEmissions(
            text = "Refrigerador (18'-22')",
            onClick = {
                selectedButton = 2
                emissionFactor = 0.25
                emissionValue = 90.0
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
            text = "Congelador",
            onClick = {
                selectedButton = 3
                emissionFactor = 0.3
                emissionValue = 96.0
            },
            customColor = if (selectedButton == 3) green else beige
        )
        ButtonEnergyEmissions(
            text = "Climatizador (Minisplit)",
            onClick = {
                selectedButton = 4
                emissionFactor = 0.35
                emissionValue = 278.0
            },
            customColor = if (selectedButton == 4) green else beige
        )
        ButtonEnergyEmissions(
            text = "Climatizador (Ventana)",
            onClick = {
                selectedButton = 5
                emissionFactor = 0.3
                emissionValue = 288.0
            },
            customColor = if (selectedButton == 5) green else beige
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
                top = 28.dp,
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
fun EmissionsEnergyMenu(){
    //Valores estéticos
    val beige = Color(230,230,230)
    val headerGreen = Color(17,109,29)
    val textSize = 16.sp
    var emissionFactor by remember { mutableStateOf(0) }
    var selectedScreen by remember { mutableStateOf<@Composable () -> Unit>({}) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Consumo Eléctrico",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = textSize * 2,
                    color = Color.Green,
                    modifier = Modifier
                        .padding(
                            top = 16.dp,
                            start = 16.dp
                        )
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 24.dp,
                    start = 16.dp,
                    end = 20.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val images = listOf(
                Pair(R.drawable.kitchen, 1),
                Pair(R.drawable.tv, 2),
                Pair(R.drawable.laundry, 3),
                Pair(R.drawable.fridge, 4),
            )

            images.forEach { (imageRes, value) ->
                Column(
                    modifier = Modifier
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(beige, shape = CircleShape)
                            .clickable {
                                selectedScreen =
                                    getEnergyScreen(value) { selectedemissionFactor ->
                                        emissionFactor = selectedemissionFactor.toInt()
                                    }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = "Transport $value",
                            modifier = Modifier
                                .size(30.dp)
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
fun saveToEnergyEmissions(context: Context, emissionFactor: Double, emissionValue: Double, type: String, hours: Double) {
    val dbHandler = DBHandler(context)
    val currentDate = LocalDate.now().toString()

    dbHandler.addEmission(emissionFactor, emissionValue, currentDate, type, hours)
    Toast.makeText(
        context,
        "Factor: $emissionFactor, Value: $emissionValue on $currentDate of type $type with $hours hours",
        Toast.LENGTH_SHORT
    ).show()
}

@RequiresApi(Build.VERSION_CODES.O)
fun getEnergyScreen(emissionFactor: Int, onValueSelected: (Double) -> Unit): @Composable () -> Unit {
    return when (emissionFactor) {
        1 -> { { KitchenEmissions(onValueSelected) } }
        2 -> { { ElectrodomesticsEmissions(onValueSelected) } }
        3 -> { { EntertainmentEmissions(onValueSelected) } }
        4 -> { { RefrigerationEmissions(onValueSelected) } }
        else -> { {} }
    }
}

@Composable
fun ButtonEnergyEmissions(
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
fun EmissionsEnergyPreview() {
    EcoLifeTheme {
        EmissionsEnergyMenu()
    }
}
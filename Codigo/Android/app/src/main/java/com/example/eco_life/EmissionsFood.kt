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
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eco_life.data.DBHandler
import java.time.LocalDate

class EmissionsFoodActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EcoLifeTheme {
                NavigationFoodEmissions()
            }
        }
    }
}

@Composable
fun NavigationFoodEmissions() {
    val navController = rememberNavController()
    Scaffold(
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "emissions_food",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("calculator_menu") { CalculatorMenu(navController = navController) }
            composable("emissions_food") { EmissionsFoodMenu(navController = navController) }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MeatEmissions(onValueSelected: (Double) -> Unit, navController: NavController) {
    val context = LocalContext.current
    val beige = Color(190, 190, 190)
    val green = Color(0, 154, 20)
    val headerGreen = Color(17,109,29)
    val textSize = 16.sp
    val buttonCornerRadius = 12.dp
    var emissionFactor by remember { mutableStateOf(0.0) }
    var emissionValue by remember { mutableStateOf(0.0) }
    val type = "Food"
    var selectedButton by remember { mutableStateOf<Int?>(null) }
    var hours by remember { mutableStateOf(0.0) }
    var hoursInput by remember { mutableStateOf("") }
    var isInputValid by remember { mutableStateOf(true) }

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
        ButtonFoodEmissions(
            text = "Res",
            onClick = {
                selectedButton = 0
                emissionFactor = 20.0
                emissionValue = 1.0
            },
            customColor = if (selectedButton == 0) green else beige
        )
        ButtonFoodEmissions(
            text = "Cerdo",
            onClick = {
                selectedButton = 1
                emissionFactor = 7.0
                emissionValue = 1.0
            },
            customColor = if (selectedButton == 1) green else beige
        )
        ButtonFoodEmissions(
            text = "Pollo",
            onClick = {
                selectedButton = 2
                emissionFactor = 6.0
                emissionValue = 1.0
            },
            customColor = if (selectedButton == 2) green else beige
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 120.dp,
                start = 16.dp,
                end = 20.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        ButtonFoodEmissions(
            text = "Pescado",
            onClick = {
                selectedButton = 3
                emissionFactor = 5.0
                emissionValue = 1.0
            },
            customColor = if (selectedButton == 3) green else beige
        )
        ButtonFoodEmissions(
            text = "Cerdo",
            onClick = {
                selectedButton = 4
                emissionFactor = 4.0
                emissionValue = 1.0
            },
            customColor = if (selectedButton == 4) green else beige
        )
        ButtonFoodEmissions(
            text = "Pollo",
            onClick = {
                selectedButton = 5
                emissionFactor = 4.8
                emissionValue = 1.0
            },
            customColor = if (selectedButton == 5) green else beige
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
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 110.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = hoursInput,
                onValueChange = { input ->
                    hoursInput = input
                    val parsedValue = input.toDoubleOrNull()
                    if (parsedValue != null) {
                        hours = parsedValue
                        isInputValid = true
                    } else {
                        isInputValid = false
                    }
                },
                label = { Text("Ingrese los kilos consumidos (Decimal): ") },
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
                top = 80.dp,
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
                        top = 200.dp,
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
                            if (emissionFactor != 0.0 && hoursInput.isNotBlank() && isInputValid) {
                                saveToFoodEmissions(context, emissionFactor, emissionValue, type, hours)
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
fun DairyEmissions(onValueSelected: (Double) -> Unit, navController: NavController) {
    val context = LocalContext.current
    val beige = Color(190, 190, 190)
    val green = Color(0, 154, 20)
    val headerGreen = Color(17,109,29)
    val textSize = 16.sp
    val customColor = Color(30, 132, 73 )
    val buttonCornerRadius = 12.dp
    var emissionFactor by remember { mutableStateOf(0.0) }
    var emissionValue by remember { mutableStateOf(0.0) }
    val type = "Food"
    var hours by remember { mutableStateOf(0.0) }
    var hoursInput by remember { mutableStateOf("") }
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
        ButtonFoodEmissions(
            text = "Leche",
            onClick = {
                selectedButton = 0
                emissionFactor = 1.5
                emissionValue = 1.0
            },
            customColor = if (selectedButton == 0) green else beige
        )
        ButtonFoodEmissions(
            text = "Queso",
            onClick = {
                selectedButton = 1
                emissionFactor = 1.5
                emissionValue = 1.0
            },
            customColor = if (selectedButton == 1) green else beige
        )
        ButtonFoodEmissions(
            text = "Yogurt",
            onClick = {
                selectedButton = 2
                emissionFactor = 2.0
                emissionValue = 1.0
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
        verticalAlignment = Alignment.Top
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 120.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = hoursInput,
                onValueChange = { input ->
                    hoursInput = input
                    val parsedValue = input.toDoubleOrNull()
                    if (parsedValue != null) {
                        hours = parsedValue
                        isInputValid = true
                    } else {
                        isInputValid = false
                    }
                },
                label = { Text("Ingrese los kilos consumidos (Decimal): ") },
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
                        top = 100.dp,
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
                            if (emissionFactor != 0.0 && hoursInput.isNotBlank() && isInputValid) {
                                saveToFoodEmissions(context, emissionFactor, emissionValue, type, hours)
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
fun FruitsVetablesEmissions(onValueSelected: (Double) -> Unit, navController: NavController) {
    val context = LocalContext.current
    val beige = Color(190, 190, 190)
    val green = Color(0, 154, 20)
    val headerGreen = Color(17,109,29)
    val textSize = 16.sp
    val buttonCornerRadius = 12.dp
    var emissionFactor by remember { mutableStateOf(0.0) }
    var emissionValue by remember { mutableStateOf(0.0) }
    val type = "Food"
    var hours by remember { mutableStateOf(0.0) }
    var hoursInput by remember { mutableStateOf("") }
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
        ButtonFoodEmissions(
            text = "Vegetales",
            onClick = {
                selectedButton = 0
                emissionFactor = 0.5
                emissionValue = 1.0
            },
            customColor = if (selectedButton == 0) green else beige
        )
        ButtonFoodEmissions(
            text = "Frutas",
            onClick = {
                selectedButton = 1
                emissionFactor = 0.5
                emissionValue = 1.0
            },
            customColor = if (selectedButton == 1) green else beige
        )
        ButtonFoodEmissions(
            text = "Frutos secos",
            onClick = {
                selectedButton = 2
                emissionFactor = 3.0
                emissionValue = 1.0
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
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = hoursInput,
                onValueChange = { input ->
                    hoursInput = input
                    val parsedValue = input.toDoubleOrNull()
                    if (parsedValue != null) {
                        hours = parsedValue
                        isInputValid = true
                    } else {
                        isInputValid = false
                    }
                },
                label = { Text("Ingrese los kilos consumidos (Decimal): ") },
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
                        top = 180.dp,
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
                            if (emissionFactor != 0.0 && hoursInput.isNotBlank() && isInputValid) {
                                saveToFoodEmissions(context, emissionFactor, emissionValue, type, hours)
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
fun ProcessedEmissions(onValueSelected: (Double) -> Unit, navController: NavController) {
    val context = LocalContext.current
    val beige = Color(190, 190, 190)
    val green = Color(0, 154, 20)
    val headerGreen = Color(17,109,29)
    val textSize = 16.sp
    val buttonCornerRadius = 12.dp
    var emissionFactor by remember { mutableStateOf(0.0) }
    var emissionValue by remember { mutableStateOf(0.0) }
    val type = "Food"
    var hours by remember { mutableStateOf(0.0) }
    var hoursInput by remember { mutableStateOf("") }
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
        ButtonFoodEmissions(
            text = "Pan/Cereales",
            onClick = {
                selectedButton = 0
                emissionFactor = 1.2
                emissionValue = 1.0
            },
            customColor = if (selectedButton == 0) green else beige
        )
        ButtonFoodEmissions(
            text = "Procesados",
            onClick = {
                selectedButton = 1
                emissionFactor = 2.0
                emissionValue = 1.0
            },
            customColor = if (selectedButton == 1) green else beige
        )
        ButtonFoodEmissions(
            text = "Azúcar",
            onClick = {
                selectedButton = 2
                emissionFactor = 3.5
                emissionValue = 1.0
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
        ButtonFoodEmissions(
            text = "Chocolate",
            onClick = {
                selectedButton = 3
                emissionFactor = 8.5
                emissionValue = 1.0
            },
            customColor = if (selectedButton == 3) green else beige
        )
        ButtonFoodEmissions(
            text = "Refrescos",
            onClick = {
                selectedButton = 4
                emissionFactor = 1.0
                emissionValue = 1.0
            },
            customColor = if (selectedButton == 4) green else beige
        )
        ButtonFoodEmissions(
            text = "Cerveza",
            onClick = {
                selectedButton = 5
                emissionFactor = 3.5
                emissionValue = 1.0
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
        ButtonFoodEmissions(
            text = "Vino",
            onClick = {
                selectedButton = 6
                emissionFactor = 2.5
                emissionValue = 1.0
            },
            customColor = if (selectedButton == 6) green else beige
        )
        ButtonFoodEmissions(
            text = "Café",
            onClick = {
                selectedButton = 7
                emissionFactor = 7.0
                emissionValue = 1.0
            },
            customColor = if (selectedButton == 7) green else beige
        )
        ButtonFoodEmissions(
            text = "Té",
            onClick = {
                selectedButton = 8
                emissionFactor = 1.0
                emissionValue = 1.0
            },
            customColor = if (selectedButton == 8) green else beige
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 260.dp,
                start = 16.dp,
                end = 20.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        ButtonFoodEmissions(
            text = "Aceite de cocina",
            onClick = {
                selectedButton = 9
                emissionFactor = 4.0
                emissionValue = 1.0
            },
            customColor = if (selectedButton == 9) green else beige
        )
        ButtonFoodEmissions(
            text = "Pasta",
            onClick = {
                selectedButton = 10
                emissionFactor = 1.7
                emissionValue = 1.0
            },
            customColor = if (selectedButton == 10) green else beige
        )
        ButtonFoodEmissions(
            text = "Arroz",
            onClick = {
                selectedButton = 11
                emissionFactor = 2.7
                emissionValue = 1.0
            },
            customColor = if (selectedButton == 11) green else beige
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
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 240.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = hoursInput,
                onValueChange = { input ->
                    hoursInput = input
                    val parsedValue = input.toDoubleOrNull()
                    if (parsedValue != null) {
                        hours = parsedValue
                        isInputValid = true
                    } else {
                        isInputValid = false
                    }
                },
                label = { Text("Ingrese los kilos consumidos (Decimal): ") },
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
                top = 180.dp,
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
                            if (emissionFactor != 0.0 && hoursInput.isNotBlank() && isInputValid) {
                                saveToFoodEmissions(context, emissionFactor, emissionValue, type, hours)
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
fun EmissionsFoodMenu(navController: NavController){
    //Valores estéticos
    val beige = Color(230,230,230)
    val green = Color(0, 154, 20)
    val textSize = 16.sp
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
                    text = "Alimentos Desechados",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = textSize*2,
                    color = green,
                    modifier = Modifier
                        .padding(
                            top = 8.dp,
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
                Pair(R.drawable.meat, 1),
                Pair(R.drawable.milk, 2),
                Pair(R.drawable.fruitsvegetables, 3),
                Pair(R.drawable.processedfood, 4),
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
                                    getFoodScreen(value, navController) { selectedEmissionFactor ->
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
fun getFoodScreen(emissionFactor: Int, navController: NavController, onValueSelected: (Double) -> Unit): @Composable () -> Unit {
    return when (emissionFactor) {
        1 -> { { MeatEmissions(onValueSelected, navController) } }
        2 -> { { DairyEmissions(onValueSelected, navController) } }
        3 -> { { FruitsVetablesEmissions(onValueSelected, navController) } }
        4 -> { { ProcessedEmissions(onValueSelected, navController) } }
        else -> { {} }
    }
}

@Composable
fun ButtonFoodEmissions(
    text: String,
    onClick: () -> Unit,
    buttonWidth: Dp = 110.dp,
    buttonHeight: Dp = 80.dp,
    maxFontSize: TextUnit = 14.sp,
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
fun saveToFoodEmissions(context: Context, emissionFactor: Double, emissionValue: Double, type: String, hours: Double) {
    val dbHandler = DBHandler(context)
    val currentDate = LocalDate.now().toString()

    dbHandler.addEmission(emissionFactor, emissionValue, currentDate, type, hours)
    Toast.makeText(
        context,
        "El impato de los alimentos desechados ha sido registrado.",
        Toast.LENGTH_SHORT
    ).show()
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun EmissionsFoodPreview() {
    EcoLifeTheme {
        NavigationFoodEmissions()
    }
}
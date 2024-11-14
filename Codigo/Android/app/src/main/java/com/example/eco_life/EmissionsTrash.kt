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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eco_life.data.DBHandler
import com.example.eco_life.ui.theme.EcoLifeTheme
import java.time.LocalDate


class EmissionsTrashActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EcoLifeTheme {
                EmissionsTrashMenu()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RecycleEmissions(onValueSelected: (Double) -> Unit) {
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
            text = "Plásticos (Recicables)",
            onClick = {
                selectedButton = 0
                emissionFactor = 0.3
                emissionValue = 0.5
            },
            customColor = if (selectedButton == 0) green else beige
        )
        ButtonEnergyEmissions(
            text = "Latas (Aluminio)",
            onClick = {
                selectedButton = 1
                emissionFactor = 0.3
                emissionValue = 0.2
            },
            customColor = if (selectedButton == 1) green else beige
        )
        ButtonEnergyEmissions(
            text = "Papel/Cartón",
            onClick = {
                selectedButton = 2
                emissionFactor = 0.2
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
        ButtonEnergyEmissions(
            text = "Vidrio",
            onClick = {
                selectedButton = 3
                emissionFactor = 0.1
                emissionValue = 1.2
            },
            customColor = if (selectedButton == 3) green else beige
        )
        ButtonEnergyEmissions(
            text = "Envases de alimentos",
            onClick = {
                selectedButton = 4
                emissionFactor = 0.3
                emissionValue = 0.3
            },
            customColor = if (selectedButton == 4) green else beige
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 30.dp,
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
                top = 30.dp,
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
fun OrganicEmissions(onValueSelected: (Double) -> Unit) {
    val context = LocalContext.current
    val beige = Color(190,190,190)
    val headerGreen = Color(17,109,29)
    val textSize = 16.sp
    val green = Color(30, 132, 73 )
    val buttonCornerRadius = 12.dp
    var emissionFactor by remember { mutableStateOf(0.0) }
    var emissionValue by remember { mutableStateOf(0.0) }
    val type = "Trash"
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
            text = "Desechos Generales",
            onClick = {
                selectedButton = 0
                emissionFactor = 0.2
                emissionValue = 60.0
            },
            customColor = if (selectedButton == 0) green else beige
        )
        ButtonEnergyEmissions(
            text = "Desechos de jardunería",
            onClick = {
                selectedButton = 1
                emissionFactor = 0.23
                emissionValue = 70.0
            },
            customColor = if (selectedButton == 1) green else beige
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
            text = "Restos de comida",
            onClick = {
                selectedButton = 2
                emissionFactor = 0.5
                emissionValue = 1.5
            },
            customColor = if (selectedButton == 2) green else beige
        )
        ButtonEnergyEmissions(
            text = "Frutas/Vegetales",
            onClick = {
                selectedButton = 3
                emissionFactor = 0.5
                emissionValue = 2.0
            },
            customColor = if (selectedButton == 3) green else beige
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
fun InorganicEmissions(onValueSelected: (Double) -> Unit) {
    val context = LocalContext.current
    val beige = Color(190,190,190)
    val headerGreen = Color(17,109,29)
    val textSize = 16.sp
    val green = Color(30, 132, 73 )
    val buttonCornerRadius = 12.dp
    var emissionFactor by remember { mutableStateOf(0.0) }
    var emissionValue by remember { mutableStateOf(0.0) }
    val type = "Trash"
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
            text = "Electrodomesticos (Pequeños)",
            onClick = {
                selectedButton = 0
                emissionFactor = 1.5
                emissionValue = 0.4
            },
            customColor = if (selectedButton == 0) green else beige
        )
        ButtonEnergyEmissions(
            text = "Electrodomésticos (Grandes)",
            onClick = {
                selectedButton = 1
                emissionFactor = 2.0
                emissionValue = 10.0
            },
            customColor = if (selectedButton == 1) green else beige
        )
        ButtonEnergyEmissions(
            text = "Pilas",
            onClick = {
                selectedButton = 2
                emissionFactor = 2.5
                emissionValue = 0.05
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
            text = "Neumáticos",
            onClick = {
                selectedButton = 3
                emissionFactor = 1.1
                emissionValue = 3.0
            },
            customColor = if (selectedButton == 3) green else beige
        )
        ButtonEnergyEmissions(
            text = "Pintura",
            onClick = {
                selectedButton = 4
                emissionFactor = 1.0
                emissionValue = 0.5
            },
            customColor = if (selectedButton == 4) green else beige
        )
        ButtonEnergyEmissions(
            text = "Muebles",
            onClick = {
                selectedButton = 5
                emissionFactor = 0.9
                emissionValue = 20.0
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
            text = "Desechos de construcción",
            onClick = {
                selectedButton = 6
                emissionFactor = 0.7
                emissionValue = 15.0
            },
            customColor = if (selectedButton == 6) green else beige
        )
        ButtonEnergyEmissions(
            text = "Plásticos (No reciclables)",
            onClick = {
                selectedButton = 7
                emissionFactor = 0.7
                emissionValue = 0.4
            },
            customColor = if (selectedButton == 7) green else beige
        )
        ButtonEnergyEmissions(
            text = "Capsulas de café",
            onClick = {
                selectedButton = 8
                emissionFactor = 2.5
                emissionValue = 0.05
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
        ButtonEnergyEmissions(
            text = "Papel",
            onClick = {
                selectedButton = 9
                emissionFactor = 0.2
                emissionValue = 0.5
            },
            customColor = if (selectedButton == 9) green else beige
        )
        ButtonEnergyEmissions(
            text = "Ropa",
            onClick = {
                selectedButton = 10
                emissionFactor = 0.6
                emissionValue = 1.0
            },
            customColor = if (selectedButton == 10) green else beige
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
fun EmissionsTrashMenu(){
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
                    text = "Residuos Desechados",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = textSize*2,
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
                Pair(R.drawable.recycle, 1),
                Pair(R.drawable.organic, 2),
                Pair(R.drawable.inorganic, 3),
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
                                    getTrashScreen(value) { selectedemissionFactor ->
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
fun saveToTrashEmissions(context: Context, emissionFactor: Double, emissionValue: Double, type: String, hours: Double) {
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
fun getTrashScreen(emissionFactor: Int, onValueSelected: (Double) -> Unit): @Composable () -> Unit {
    return when (emissionFactor) {
        1 -> { { RecycleEmissions(onValueSelected) } }
        2 -> { { OrganicEmissions(onValueSelected) } }
        3 -> { { InorganicEmissions(onValueSelected) } }
        else -> { {} }
    }
}

@Composable
fun ButtonTrashEmissions(
    text: String,
    onClick: () -> Unit,
    buttonWidth: Dp = 110.dp,
    buttonHeight: Dp = 80.dp,
    maxFontSize: TextUnit = 11.sp,
    customColor: Color = Color(30, 132, 73 ),
    buttonCornerRadius: Dp = 12.dp,
    headerGreen: Color = Color(17,109,29)
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
fun EmissionsTrashPreview() {
    EcoLifeTheme {
        EmissionsTrashMenu()
    }
}
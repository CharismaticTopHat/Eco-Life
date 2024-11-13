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
    val beige = Color(230,230,230)
    val headerGreen = Color(17,109,29)
    val textSize = 16.sp
    val customColor = Color(30, 132, 73 )
    val buttonCornerRadius = 12.dp
    var emissionFactor by remember { mutableStateOf(0.0) }
    var emissionValue by remember { mutableStateOf(0.0) }
    val type = "Energy"
    var hours by remember { mutableStateOf("") }
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
        ButtonTrashEmissions("Plásticos (Reciclables)", onClick = { emissionFactor = 0.3; emissionValue = 0.5 })
        ButtonTrashEmissions("Latas de aluminio", onClick = { emissionFactor = 0.3; emissionValue = 0.2 })
        ButtonTrashEmissions("Papel/Cartón", onClick = { emissionFactor = 0.2; emissionValue = 1.0 })
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
        ButtonTrashEmissions("Vidrio", onClick = { emissionFactor = 0.1; emissionValue = 1.2 })
        ButtonTrashEmissions("Envases de alimentos", onClick = { emissionFactor = 0.3; emissionValue = 0.3 })
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
fun OrganicEmissions(onValueSelected: (Double) -> Unit) {
    val context = LocalContext.current
    val beige = Color(230,230,230)
    val headerGreen = Color(17,109,29)
    val textSize = 16.sp
    val customColor = Color(30, 132, 73 )
    val buttonCornerRadius = 12.dp
    var emissionFactor by remember { mutableStateOf(0.0) }
    var emissionValue by remember { mutableStateOf(0.0) }
    val type = "Energy"
    var hours by remember { mutableStateOf("") }
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
        ButtonTrashEmissions("Desechos generales", onClick = { emissionFactor = 0.5; emissionValue = 2.0 })
        ButtonTrashEmissions("Restos de comida", onClick = { emissionFactor = 0.5; emissionValue = 1.5 })
        ButtonTrashEmissions("Desechos de jardinería", onClick = { emissionFactor = 0.4; emissionValue = 3.0 })
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
        ButtonTrashEmissions("Comida", onClick = { emissionFactor = 0.5; emissionValue = 1.5 })
        ButtonTrashEmissions("Frutas & Vegetales", onClick = { emissionFactor = 0.5; emissionValue = 2.0 })
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
fun InorganicEmissions(onValueSelected: (Double) -> Unit) {
    val context = LocalContext.current
    val beige = Color(230,230,230)
    val headerGreen = Color(17,109,29)
    val textSize = 16.sp
    val customColor = Color(30, 132, 73 )
    val buttonCornerRadius = 12.dp
    var emissionFactor by remember { mutableStateOf(0.0) }
    var emissionValue by remember { mutableStateOf(0.0) }
    val type = "Energy"
    var hours by remember { mutableStateOf("") }
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
        ButtonTrashEmissions("Electrónicos (Pequeños)", onClick = { emissionFactor = 1.5; emissionValue = 0.4 })
        ButtonTrashEmissions("Electrónicos (Grandes)", onClick = { emissionFactor = 2.0; emissionValue = 10.0 })
        ButtonTrashEmissions("Pilas", onClick = { emissionFactor = 2.5; emissionValue = 0.05 })
    }
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
        ButtonTrashEmissions("Electrónicos (Pequeños)", onClick = { emissionFactor = 1.5; emissionValue = 0.4 })
        ButtonTrashEmissions("Electrónicos (Grandes)", onClick = { emissionFactor = 2.0; emissionValue = 10.0 })
        ButtonTrashEmissions("Pilas", onClick = { emissionFactor = 2.5; emissionValue = 0.05 })
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
        ButtonTrashEmissions("Comida", onClick = { emissionFactor = 0.5; emissionValue = 1.5 })
        ButtonTrashEmissions("Frutas & Vegetales", onClick = { emissionFactor = 0.5; emissionValue = 2.0 })
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
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
import com.example.eco_life.data.DBHandler
import java.time.LocalDate

class EmissionsTransportActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EcoLifeTheme {
                EmissionsTransportMenu()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CarEmissions(onValueSelected: (Double) -> Unit) {
    val context = LocalContext.current
    val beige = Color(230,230,230)
    val headerGreen = Color(17,109,29)
    val textSize = 16.sp
    val customColor = Color(30, 132, 73 )
    val buttonCornerRadius = 12.dp
    var selectedValue by remember { mutableStateOf(0.0) }
    val type = "Transport"
    var hours by remember { mutableStateOf(0.0) }

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
        Column {
            Button(
                onClick = {
                    selectedValue = 2.3
                    onValueSelected(selectedValue)
                },
                modifier = Modifier
                    .height(80.dp)
                    .clip(RoundedCornerShape(buttonCornerRadius))
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = customColor,
                    contentColor = headerGreen
                )
            ) {
                Text(
                    text = "Gasolina",
                    fontSize = textSize,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }
        }
        Column {
            Button(
                onClick = {
                    selectedValue = 2.7
                    onValueSelected(selectedValue)
                },
                modifier = Modifier
                    .height(80.dp)
                    .clip(RoundedCornerShape(buttonCornerRadius))
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = customColor,
                    contentColor = headerGreen
                )
            ) {
                Text(
                    text = "Diesel",
                    fontSize = textSize,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
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
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column {
            Button(
                onClick = {
                    selectedValue = 0.8
                    onValueSelected(selectedValue)
                },
                modifier = Modifier
                    .height(80.dp)
                    .clip(RoundedCornerShape(buttonCornerRadius))
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = customColor,
                    contentColor = headerGreen
                )
            ) {
                Text(
                    text = "Híbrido",
                    fontSize = textSize,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }
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
                        start = 16.dp,
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
                            backgroundColor = Color.Gray,
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
                            if (selectedValue != 0.0) {
                                saveToDatabase(context, selectedValue, type, hours)
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


@Composable
fun MotorcycleEmissions(onValueSelected: (Double) -> Unit) {

}

@Composable
fun BicycleEmissions(onValueSelected: (Double) -> Unit) {

}

@Composable
fun ScooterEmissions(onValueSelected: (Double) -> Unit) {

}

@Composable
fun TruckEmissions(){

}

@Composable
fun TrainEmissions(){

}

@Composable
fun BusEmissions(){

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EmissionsTransportMenu(){
    //Valores estéticos
    val beige = Color(230,230,230)
    val headerGreen = Color(17,109,29)
    val textSize = 16.sp
    val textHeight = textSize.value.dp
    val customColor = Color(30, 132, 73 )
    val buttonCornerRadius = 12.dp
    var selectedValue by remember { mutableStateOf(0) }
    var selectedScreen by remember { mutableStateOf<@Composable () -> Unit>({}) }
    val context = LocalContext.current

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
                    text = "Tipo de Transporte",
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
                Pair(R.drawable.car, 1),
                Pair(R.drawable.motorcycle, 2),
                Pair(R.drawable.bycicle, 3),
                Pair(R.drawable.scooter, 4),
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
                            .background(Color.Gray, shape = CircleShape)
                            .clickable {
                                selectedScreen =
                                    getComposableScreen(value) { selectedEmissionValue ->
                                        selectedValue = selectedEmissionValue.toInt()
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
                Pair(R.drawable.truck, 5),
                Pair(R.drawable.train, 6),
                Pair(R.drawable.bus, 7)
            )
            Spacer(modifier = Modifier.weight(0.5f))
            images.forEach { (imageRes, value) ->
                Column(
                    modifier = Modifier
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(Color.Gray, shape = CircleShape)
                            .clickable {
                                selectedValue = value
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = "Drawable",
                            modifier = Modifier
                                .size(30.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.weight(0.5f))
        }
        Box(modifier = Modifier.fillMaxWidth()) {
            selectedScreen()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun saveToDatabase(context: Context, selectedValue: Double, type: String, hours: Double) {
    val dbHandler = DBHandler(context)

    val emissionValue = when (selectedValue) {
        1.0 -> 0.25 // car
        2.0 -> 0.5  // boat
        3.0 -> 0.75 // plane
        4.0 -> 1.0  // more
        else -> 0.0
    }

    val currentDate = LocalDate.now().toString()

    dbHandler.addNewCourse(selectedValue, emissionValue, currentDate, type, hours)
    Toast.makeText(context, "Saved value $selectedValue with additional $emissionValue on $currentDate", Toast.LENGTH_SHORT).show()
}

@RequiresApi(Build.VERSION_CODES.O)
fun getComposableScreen(selectedValue: Int, onValueSelected: (Double) -> Unit): @Composable () -> Unit {
    return when (selectedValue) {
        1 -> { { CarEmissions(onValueSelected) } }
        2 -> { { MotorcycleEmissions(onValueSelected) } }
        3 -> { { BicycleEmissions(onValueSelected) } }
        4 -> { { ScooterEmissions(onValueSelected) } }
        else -> { {} }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun EmissionsTransportPreview() {
    EcoLifeTheme {
        EmissionsTransportMenu()
    }
}
package com.example.eco_life

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eco_life.data.DBHandler
import com.example.eco_life.ui.theme.EcoLifeTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CalculatorActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EcoLifeTheme {
                NavigationCalculator()
            }
        }
    }
}

@Composable
fun NavigationCalculator() {
    val navController = rememberNavController()

    Scaffold(
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "calculator_menu",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("calculator_menu") { CalculatorMenu(navController = navController) }
            composable("emissions_trash") { EmissionsTrashMenu() }
            composable("emissions_transport") { EmissionsTransportMenu() }
            composable("emissions_food") { EmissionsFoodMenu() }
            composable("emissions_energy") { EmissionsEnergyMenu() }
        }
    }
}

@Composable
fun CalculatorMenu(navController: NavController) {
    //Valores estéticos
    val beige = Color(230,230,230)
    val textSize = 20.sp
    val textHeight = textSize.value.dp
    val customColor = Color(30, 132, 73 )
    val buttonCornerRadius = 12.dp
    val headerGreen = Color(17,109,29)
    var totalEmissions by remember { mutableStateOf(0.0) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val emissions = withContext(Dispatchers.IO) {
            try {
                getTotalEmissionsForTransport(context)
            } catch (e: Exception) {
                Log.e("CalculatorMenu", "Error fetching emissions: ${e.message}")
                Log.d("CalculatorMenu", "Getting Transport Coal Print")
                0.0 // Valor en caso de error
            }
        }
        totalEmissions = emissions
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
                    text = "Tu Huella",
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
                    end = 16.dp,
                    bottom = 12.dp
                )
                .background(Color.White),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "Actualiza tu Huella",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                color = customColor,
                fontSize = textSize*1.2
            )
        }
        Column (
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .clip(RoundedCornerShape(16.dp))
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(beige)
                    .padding(
                        start = 4.dp,
                        end = 4.dp,
                        top = 8.dp
                    )
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(start = 8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Icon(
                        Icons.Default.ShoppingCart,
                        contentDescription = "Vehicle"
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(2f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Emisión por Transporte",
                        color = customColor,
                        fontWeight = FontWeight.Black
                    )
                }
                Column(
                    modifier = Modifier.weight(1.25f),
                    horizontalAlignment = Alignment.End
                ) {
                    Button(
                        onClick = {
                            navController.navigate("emissions_transport")
                        },
                        modifier = Modifier
                            .width(108.dp)
                            .height(44.dp)
                            .clip(
                                RoundedCornerShape(
                                    topStart = buttonCornerRadius,
                                    bottomStart = buttonCornerRadius,
                                    topEnd = buttonCornerRadius,
                                    bottomEnd = buttonCornerRadius
                                )
                            )
                            .padding(
                                start = 8.dp,
                                end = 8.dp,
                                bottom = 16.dp
                            ),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Green,
                            contentColor = headerGreen
                        )
                    ) {
                        Text(
                            text = "Actualizar",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(beige)
                    .padding(
                        start = 4.dp,
                        end = 4.dp,
                    ),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "${"%.2f".format(totalEmissions)} Kilos de CO2",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(beige)
                    .padding(
                        start = 4.dp,
                        end = 4.dp,
                        top = 8.dp
                    ),
                horizontalArrangement = Arrangement.Center
            ){
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .height(2.dp)
                        .background(Color.Green)
                )
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(beige)
                    .padding(
                        start = 4.dp,
                        end = 4.dp,
                        top = 8.dp,
                        bottom = 12.dp
                    )
                    .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "Emisión Actual",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Spacer(
                modifier = Modifier
                    .height(24.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(beige)
                    .padding(
                        start = 4.dp,
                        end = 4.dp,
                        top = 8.dp
                    )
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(start = 8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Icon(
                        Icons.Default.Phone,
                        contentDescription = "Electricity"
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(2f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Consumo de Energía",
                        color = customColor,
                        fontWeight = FontWeight.Black
                    )
                }
                Column(
                    modifier = Modifier.weight(1.25f),
                    horizontalAlignment = Alignment.End
                ) {
                    Button(
                        onClick = {
                            navController.navigate("emissions_energy")
                        },
                        modifier = Modifier
                            .width(108.dp)
                            .height(44.dp)
                            .clip(
                                RoundedCornerShape(
                                    topStart = buttonCornerRadius,
                                    bottomStart = buttonCornerRadius,
                                    topEnd = buttonCornerRadius,
                                    bottomEnd = buttonCornerRadius
                                )
                            )
                            .padding(
                                start = 8.dp,
                                end = 8.dp,
                                bottom = 16.dp
                            ),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Green,
                            contentColor = headerGreen
                        )
                    ) {
                        Text(
                            text = "Actualizar",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(beige)
                    .padding(
                        start = 4.dp,
                        end = 4.dp,
                    ),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "2.3 Toneladas de CO2",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(beige)
                    .padding(
                        start = 4.dp,
                        end = 4.dp,
                        top = 8.dp
                    ),
                horizontalArrangement = Arrangement.Center
            ){
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .height(2.dp)
                        .background(Color.Green)
                )
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(beige)
                    .padding(
                        start = 4.dp,
                        end = 4.dp,
                        top = 8.dp
                    ),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "Emisión Actual",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Spacer(
                modifier = Modifier
                    .height(24.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(beige)
                    .padding(
                        start = 4.dp,
                        end = 4.dp,
                        top = 8.dp
                    )
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(start = 8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Icon(
                        Icons.Default.Build,
                        contentDescription = "Utensils"
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(2f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Emisión en Alimentos",
                        color = customColor,
                        fontWeight = FontWeight.Black
                    )
                }
                Column(
                    modifier = Modifier.weight(1.25f),
                    horizontalAlignment = Alignment.End
                ) {
                    Button(
                        onClick = {
                            navController.navigate("emissions_food")
                        },
                        modifier = Modifier
                            .width(108.dp)
                            .height(44.dp)
                            .clip(
                                RoundedCornerShape(
                                    topStart = buttonCornerRadius,
                                    bottomStart = buttonCornerRadius,
                                    topEnd = buttonCornerRadius,
                                    bottomEnd = buttonCornerRadius
                                )
                            )
                            .padding(
                                start = 8.dp,
                                end = 8.dp,
                                bottom = 16.dp
                            ),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Green,
                            contentColor = headerGreen
                        )
                    ) {
                        Text(
                            text = "Actualizar",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(beige)
                    .padding(
                        start = 4.dp,
                        end = 4.dp,
                    ),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "2.3 Toneladas de CO2",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(beige)
                    .padding(
                        start = 4.dp,
                        end = 4.dp,
                        top = 8.dp
                    ),
                horizontalArrangement = Arrangement.Center
            ){
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .height(2.dp)
                        .background(Color.Green)
                )
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(beige)
                    .padding(
                        start = 4.dp,
                        end = 4.dp,
                        top = 8.dp
                    ),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "Emisión Actual",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Spacer(
                modifier = Modifier
                    .height(24.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(beige)
                    .padding(
                        start = 4.dp,
                        end = 4.dp,
                        top = 8.dp
                    )
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(start = 8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Residues"
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(2f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Emisión por Desechos",
                        color = customColor,
                        fontWeight = FontWeight.Black
                    )
                }
                Column(
                    modifier = Modifier.weight(1.25f),
                    horizontalAlignment = Alignment.End
                ) {
                    Button(
                        onClick = {
                            navController.navigate("emissions_trash")
                        },
                        modifier = Modifier
                            .width(108.dp)
                            .height(44.dp)
                            .clip(
                                RoundedCornerShape(
                                    topStart = buttonCornerRadius,
                                    bottomStart = buttonCornerRadius,
                                    topEnd = buttonCornerRadius,
                                    bottomEnd = buttonCornerRadius
                                )
                            )
                            .padding(
                                start = 8.dp,
                                end = 8.dp,
                                bottom = 16.dp
                            ),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Green,
                            contentColor = headerGreen
                        )
                    ) {
                        Text(
                            text = "Actualizar",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(beige)
                    .padding(
                        start = 4.dp,
                        end = 4.dp,
                    ),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "2.3 Toneladas de CO2",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(beige)
                    .padding(
                        start = 4.dp,
                        end = 4.dp,
                        top = 8.dp
                    ),
                horizontalArrangement = Arrangement.Center
            ){
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .height(2.dp)
                        .background(Color.Green)
                )
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(beige)
                    .padding(
                        start = 4.dp,
                        end = 4.dp,
                        top = 8.dp
                    ),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "Emisión Actual",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun getTotalEmissionsForTransport(context: Context): Double {
    val dbHandler = DBHandler(context)
    return dbHandler.getSumOfEmissionsByType("Transport")
}

@Composable
fun CalculatorPreview() {
    EcoLifeTheme {
        NavigationCalculator()
    }
}
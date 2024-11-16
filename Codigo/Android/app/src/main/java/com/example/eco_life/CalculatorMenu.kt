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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.BasicText
import androidx.compose.ui.text.TextStyle
import android.graphics.Typeface
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.components.Legends
import co.yml.charts.common.extensions.formatToSinglePrecision
import co.yml.charts.common.model.LegendsConfig
import co.yml.charts.common.model.Point
import co.yml.charts.common.utils.DataUtils
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
            composable("emissions_trash") { EmissionsTrashMenu(navController = navController) }
            composable("emissions_transport") { EmissionsTransportMenu(navController = navController) }
            composable("emissions_food") { EmissionsFoodMenu(navController = navController) }
            composable("emissions_energy") { EmissionsEnergyMenu(navController = navController) }
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
    var transportEmissions by remember { mutableStateOf(0.0) }
    var energyEmissions by remember { mutableStateOf(0.0) }
    var foodEmissions by remember { mutableStateOf(0.0) }
    var trashEmissions by remember { mutableStateOf(0.0) }
    val context = LocalContext.current
    val dbHandler = remember { DBHandler(context) }
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            transportEmissions = getTotalEmissionsForTransport(dbHandler)
            energyEmissions = getTotalEmissionsForEnergy(dbHandler)
            foodEmissions = getTotalEmissionsForFood(dbHandler)
            trashEmissions = getTotalEmissionsForTrash(dbHandler)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
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
                    text = "${"%.2f".format(transportEmissions)} Kilos de CO2",
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
                    text = "${"%.2f".format(energyEmissions)} Kilos de CO2",
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
                    text = "${"%.2f".format(foodEmissions)} Kilos de CO2",
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
                        contentDescription = "Residuos"
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
                    text = "${"%.2f".format(trashEmissions)} Kilos de CO2",
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Emisiones Diarias",
                fontSize = 24.sp,
                color = customColor,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            LineChartScreen()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun getTotalEmissionsForTransport(dbHandler: DBHandler): Double {
    return dbHandler.getEmissionsByType("Transport")
}

@RequiresApi(Build.VERSION_CODES.O)
fun getTotalEmissionsForEnergy(dbHandler: DBHandler): Double {
    return dbHandler.getEmissionsByType("Energy")
}

@RequiresApi(Build.VERSION_CODES.O)
fun getTotalEmissionsForFood(dbHandler: DBHandler): Double {
    return dbHandler.getEmissionsByType("Food")
}

@RequiresApi(Build.VERSION_CODES.O)
fun getTotalEmissionsForTrash(dbHandler: DBHandler): Double {
    return dbHandler.getEmissionsByType("Trash")
}

@Composable
fun LineChartScreen(){
    val steps = 10
    val pointsData = listOf(
        Point(0f, 40f),
        Point(1f, 90f),
        Point(2f, 0f),
        Point(3f, 60f),
        Point(4f, 10f)
    )

    val xAxisData = AxisData.Builder()
        .axisStepSize(60.dp)
        .backgroundColor(Color.Transparent)
        .steps(pointsData.size -1)
        .labelData { i -> i.toString()}
        .labelAndAxisLinePadding(15.dp)
        .axisLineColor(Color.Black)
        .axisLabelColor(Color.Black)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(steps)
        .backgroundColor(Color.Transparent)
        .labelAndAxisLinePadding(20.dp)
        .labelData { i ->
            val yScale = 100 / steps
            (i * yScale).toString()
        }
        .axisLineColor(Color.Black)
        .axisLabelColor(Color.Black)
        .build()

    val lineChartData = LineChartData(
        linePlotData =  LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    LineStyle(
                        color = Color.Black,
                        lineType = LineType.SmoothCurve(isDotted = false)
                    ),
                    IntersectionPoint(
                        color = Color.Gray
                    ),
                    SelectionHighlightPoint(color = Color.Black),
                    ShadowUnderLine(
                        alpha = 0.5f,
                        brush = Brush.verticalGradient(
                            colors =  listOf(
                                Color.Red,
                                Color.Transparent
                            )
                        )
                    ),
                    SelectionHighlightPopUp()
                )
            ),
        ),
        backgroundColor = Color.White,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(color = Color.Black)
    )

    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        lineChartData = lineChartData)
}


@Composable
fun CalculatorPreview() {
    EcoLifeTheme {
        NavigationCalculator()
    }
}
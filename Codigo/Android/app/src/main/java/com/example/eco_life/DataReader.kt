package com.example.eco_life

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.eco_life.data.DBHandler
import android.annotation.SuppressLint
import com.example.eco_life.ui.theme.EcoLifeTheme
import com.example.eco_life.ui.theme.greenColor

class DataRead : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EcoLifeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                backgroundColor = greenColor,
                                title = {
                                    Text(
                                        text = "Emissions",
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center,
                                        color = Color.White
                                    )
                                })
                        }) {
                        EmissionDataList(LocalContext.current)
                    }
                }
            }
        }
    }
}

@Composable
fun EmissionDataList(context: Context) {
    val dbHandler = DBHandler(context)
    val emissionList = dbHandler.readEmissions()

    LazyColumn {
        if (emissionList.isEmpty()) {
            item {
                Text(
                    text = "No emissions data available",
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
            }
        } else {
            itemsIndexed(emissionList) { _, item ->
                Card(
                    modifier = Modifier.padding(8.dp),
                    elevation = 6.dp
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp).fillMaxWidth(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Emission Factor: ${item.emissionFactor}",
                            modifier = Modifier.padding(4.dp),
                            color = Color.Black, textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.width(5.dp))

                        Text(
                            text = "Emission Value: ${item.emissionValue}",
                            modifier = Modifier.padding(4.dp),
                            color = Color.Black, textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.width(5.dp))

                        Text(
                            text = "Emission Date: ${item.emissionDate}",
                            modifier = Modifier.padding(4.dp),
                            color = Color.Black, textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.width(5.dp))

                        Text(
                            text = "Emission Type: ${item.type}",
                            modifier = Modifier.padding(4.dp),
                            color = Color.Black, textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.width(5.dp))

                        Text(
                            text = "Emission Hours: ${item.hours}",
                            modifier = Modifier.padding(4.dp),
                            color = Color.Black, textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
package com.example.eco_life

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.widget.Toast
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
import com.example.eco_life.ui.theme.EcoLifeTheme
import com.example.eco_life.ui.theme.greenColor

class DataDelete : ComponentActivity() {
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
                                        text = "Delete Emission Data",
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center,
                                        color = Color.White
                                    )
                                })
                        }) {
                        DeleteEmissionDataList(LocalContext.current)
                    }
                }
            }
        }
    }
}

@Composable
fun DeleteEmissionDataList(context: Context) {
    val dbHandler = DBHandler(context)
    val emissionList = dbHandler.readEmissions()

    LazyColumn {
        itemsIndexed(emissionList) { _, item ->
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                elevation = 6.dp
            ) {
                Column(
                    modifier = Modifier.padding(8.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Emission Factor: ${item.emissionFactor}",
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Emission Value: ${item.emissionValue}",
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Emission Date: ${item.emissionDate}",
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            dbHandler.deleteEmission(item.id)
                            Toast.makeText(context, "Emission deleted", Toast.LENGTH_SHORT).show()
                            (context as Activity).recreate() // Refresh the list after deletion
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Delete", color = Color.White)
                    }
                }
            }
        }
    }
}
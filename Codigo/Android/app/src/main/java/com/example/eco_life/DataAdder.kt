@file:Suppress("NAME_SHADOWING")

package com.example.eco_life

import com.example.eco_life.data.DBHandler
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eco_life.ui.theme.EcoLifeTheme
import com.example.eco_life.ui.theme.greenColor

class DataAdder : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter", "ObsoleteSdkInt")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EcoLifeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(backgroundColor = greenColor,
                                title = {
                                    Text(

                                        text = "GFG",

                                        modifier = Modifier.fillMaxWidth(),

                                        textAlign = TextAlign.Center,

                                        color = Color.White
                                    )
                                })
                        }) {
                        AddDataToDatabase(LocalContext.current)
                    }
                }
            }
        }
    }
}

@Composable
fun AddDataToDatabase(
    context: Context
) {
    context as Activity
    val emissionFactor = remember {
        mutableStateOf(TextFieldValue())
    }
    val emissionValue = remember {
        mutableStateOf(TextFieldValue())
    }
    val date = remember {
        mutableStateOf(TextFieldValue())
    }
    val type = remember {
        mutableStateOf(TextFieldValue())
    }
    val hours = remember {
        mutableStateOf(TextFieldValue())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        val dbHandler = DBHandler(context)

        Text(
            text = "SQLite Database in Android",
            color = greenColor, fontSize = 20.sp, fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = emissionFactor.value,
            onValueChange = { emissionFactor.value = it },
            placeholder = { Text(text = "Ingrese el Factor de Emisión") },
            modifier = Modifier
                .fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = emissionValue.value,
            onValueChange = { emissionValue.value = it },
            placeholder = { Text(text = "Ingrese el Valor de la Emisión") },
            modifier = Modifier
                .fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = date.value,
            onValueChange = { date.value = it },
            placeholder = { Text(text = "Ingrese fecha de la emisión") },
            modifier = Modifier
                .fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = type.value,
            onValueChange = { date.value = it },
            placeholder = { Text(text = "Ingrese el tipo de la emisión") },
            modifier = Modifier
                .fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = date.value,
            onValueChange = { date.value = it },
            placeholder = { Text(text = "Ingrese horas de la emisión") },
            modifier = Modifier
                .fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(15.dp))

        Button(onClick = {
            val emissionFactor = emissionFactor.value.text.toDoubleOrNull() ?: 0.0
            val emissionValue = emissionValue.value.text.toDoubleOrNull() ?: 0.0
            val type = type.value.text.toString() ?: " "
            val hours = hours.value.text.toDoubleOrNull() ?: 0.0
            dbHandler.addEmission(
                emissionFactor.toDouble(),
                emissionValue,
                date.value.text,
                type,
                hours
            )
            Toast.makeText(context, "Emission added to Database", Toast.LENGTH_SHORT).show()
        }) {
            Text(text = "Add Emission to Database", color = Color.White)
        }
    }
}
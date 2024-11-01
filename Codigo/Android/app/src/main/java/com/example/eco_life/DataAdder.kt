package com.example.eco_life

import DBHandler
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

class DataActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EcoLifeTheme {
                // on below line we are specifying background color for our application
                Surface(
                    // on below line we are specifying modifier and color for our app
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    // on the below line we are specifying the theme as the scaffold.
                    Scaffold(
                        // in scaffold we are specifying the top bar.
                        topBar = {
                            // inside top bar we are specifying background color.
                            TopAppBar(backgroundColor = greenColor,
                                // along with that we are specifying title for our top bar.
                                title = {
                                    // in the top bar we are specifying tile as a text
                                    Text(
                                        // on below line we are specifying
                                        // text to display in top app bar.
                                        text = "GFG",

                                        // on below line we are specifying
                                        // modifier to fill max width.
                                        modifier = Modifier.fillMaxWidth(),

                                        // on below line we are specifying
                                        // text alignment.
                                        textAlign = TextAlign.Center,

                                        // on below line we are specifying
                                        // color for our text.
                                        color = Color.White
                                    )
                                })
                        }) {
                        // on below line we are calling our method to display UI
                        AddDataToDatabase(LocalContext.current)
                    }
                }
            }
        }
    }
}

// on below line we are creating battery status function.
@Composable
fun AddDataToDatabase(
    context: Context
) {
    val activity = context as Activity
    // on below line creating a variable for battery status
    val emissionFactor = remember {
        mutableStateOf(TextFieldValue())
    }
    val emissionValue = remember {
        mutableStateOf(TextFieldValue())
    }
    val date = remember {
        mutableStateOf(TextFieldValue())
    }

    // on below line we are creating a column,
    Column(
        // on below line we are adding a modifier to it,
        modifier = Modifier
            .fillMaxSize()
            // on below line we are adding a padding.
            .padding(all = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        var dbHandler: DBHandler = DBHandler(context)

        // on below line we are adding a text for heading.
        Text(
            // on below line we are specifying text
            text = "SQlite Database in Android",
            // on below line we are specifying text color, font size and font weight
            color = greenColor, fontSize = 20.sp, fontWeight = FontWeight.Bold
        )

        // on below line adding a spacer.
        Spacer(modifier = Modifier.height(20.dp))

        // on below line we are creating a text field.
        TextField(
            // on below line we are specifying value for our email text field.
            value = emissionFactor.value,
            // on below line we are adding on value change for text field.
            onValueChange = { emissionFactor.value = it },
            // on below line we are adding place holder as text as "Enter your email"
            placeholder = { Text(text = "Ingrese el Factor de Emisión") },
            // on below line we are adding modifier to it
            // and adding padding to it and filling max width
            modifier = Modifier
                .fillMaxWidth(),
            // on below line we are adding text style
            // specifying color and font size to it.
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            // on below line we are adding single line to it.
            singleLine = true,
        )
        // on below line we are adding spacer
        Spacer(modifier = Modifier.height(20.dp))
        // on below line we are creating a text field.
        TextField(
            // on below line we are specifying value for our email text field.
            value = emissionValue.value,
            // on below line we are adding on value change for text field.
            onValueChange = { emissionValue.value = it },
            // on below line we are adding place holder as text as "Enter your email"
            placeholder = { Text(text = "Ingrese el Valor de la Emisión") },
            // on below line we are adding modifier to it
            // and adding padding to it and filling max width
            modifier = Modifier
                .fillMaxWidth(),
            // on below line we are adding text style
            // specifying color and font size to it.
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            // on below line we are adding single line to it.
            singleLine = true,
        )
        // on below line we are adding spacer
        Spacer(modifier = Modifier.height(20.dp))
        // on below line we are creating a text field.
        TextField(
            // on below line we are specifying value for our email text field.
            value = date.value,
            // on below line we are adding on value change for text field.
            onValueChange = { date.value = it },
            // on below line we are adding place holder as text as "Enter your email"
            placeholder = { Text(text = "Ingrese fecha de la emisión") },
            // on below line we are adding modifier to it
            // and adding padding to it and filling max width
            modifier = Modifier
                .fillMaxWidth(),
            // on below line we are adding text style
            // specifying color and font size to it.
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            // on below line we are adding single line to it.
            singleLine = true,
        )
        // on below line we are adding spacer
        Spacer(modifier = Modifier.height(15.dp))

        // on below line creating a button to check battery charging status
        Button(onClick = {
            val emissionFactor = emissionFactor.value.text.toIntOrNull() ?: 0
            val emissionValue = emissionValue.value.text.toIntOrNull() ?: 0
            dbHandler.addNewCourse(
                emissionFactor,
                emissionValue,
                date.value.text
            )
            Toast.makeText(context, "Emission added to Database", Toast.LENGTH_SHORT).show()
        }) {
            // on below line adding a text for our button.
            Text(text = "Add Emission to Database", color = Color.White)
        }
    }
}

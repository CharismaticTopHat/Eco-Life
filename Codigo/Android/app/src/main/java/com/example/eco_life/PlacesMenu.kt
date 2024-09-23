package com.example.eco_life

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eco_life.ui.theme.EcoLifeTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign

class PlacesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EcoLifeTheme {
                PlacesMenu()
            }
        }
    }
}

@Composable
fun PlacesMenu() {
    //Valores estéticos
    val beige = Color(230,230,230)
    val textSize = 12.sp
    val textHeight = textSize.value.dp
    val customColor = Color(30, 132, 73 )
    val buttonCornerRadius = 12.dp

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
                    text = "Recomendaciones",
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
                    start = 32.dp,
                    end = 32.dp,
                    top = 16.dp
                )
                .clip(
                    RoundedCornerShape(
                        bottomStart = buttonCornerRadius,
                        bottomEnd = buttonCornerRadius,
                        topStart = buttonCornerRadius,
                        topEnd = buttonCornerRadius
                    )
                )
                .background(beige),
            horizontalArrangement = Arrangement.Start
        ) {
            Column (
                modifier = Modifier
                    .height(140.dp)
            ){
                Image(
                    painter = painterResource(id = R.drawable.restaurant1),
                    contentDescription = "Restaurante - El Brittle Bones",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(140.dp)
                        .padding(
                            start = 16.dp,
                            top = 12.dp,
                            bottom = 12.dp
                        )
                        .clip(
                            RoundedCornerShape(
                                bottomStart = buttonCornerRadius,
                                bottomEnd = buttonCornerRadius,
                                topStart = buttonCornerRadius,
                                topEnd = buttonCornerRadius
                            )
                        )
                )
            }
            Column {
                Row {
                    Text(
                        text = "El Brittle Bones",
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.Serif,
                        color = customColor,
                        modifier = Modifier
                            .padding(start = 16.dp, top = 12.dp)
                    )
                }
                Row {
                    Column (
                        modifier = Modifier
                            .padding(
                                start = 8.dp,
                                top = 8.dp
                            )
                    ){
                        Icon(
                            Icons.Default.Place, contentDescription = "Location"
                        )
                    }
                    Column {
                        Text(
                            text = "P. Sherman, 42 Wallaby Way, Sydney",
                            fontWeight = FontWeight.Light,
                            fontFamily = FontFamily.Serif,
                            fontSize = textSize,
                            color = Color.Black,
                            modifier = Modifier
                                .padding(start = 16.dp, top = 12.dp)
                        )
                    }
                }
                Row {
                    Column (
                        modifier = Modifier
                            .padding(
                                start = 8.dp,
                                top = 12.dp
                            )
                    ){
                        Icon(
                            Icons.Default.DateRange, contentDescription = "Días laborales"
                        )
                    }
                    Column {
                        Text(
                            text = "De Lunes a Viernes",
                            fontWeight = FontWeight.Light,
                            fontFamily = FontFamily.Serif,
                            fontSize = textSize,
                            color = Color.Black,
                            modifier = Modifier
                                .padding(start = 16.dp, top = 12.dp)
                        )
                    }
                }
                Row {
                    Column (
                        modifier = Modifier
                            .padding(
                                start = 8.dp,
                                top = 12.dp
                            )
                    ){
                        Icon(
                            Icons.Default.Refresh, contentDescription = "Horio de atención"
                        )
                    }
                    Column {
                        Text(
                            text = "09:00 A.M. - 07:00 P.M.'",
                            fontWeight = FontWeight.Light,
                            fontFamily = FontFamily.Serif,
                            fontSize = textSize,
                            color = Color.Black,
                            modifier = Modifier
                                .padding(start = 16.dp, top = 16.dp)
                        )
                    }
                }
                Row (
                    modifier = Modifier
                        .padding(
                            start = 12.dp,
                            top = 16.dp,
                            bottom = 8.dp
                        ),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column {
                        Icon(
                            Icons.Default.Favorite, contentDescription = "1 Star"
                        )
                    }
                    Column {
                        Icon(
                            Icons.Default.Favorite, contentDescription = "2 Stars"
                        )
                    }
                    Column {
                        Icon(
                            Icons.Default.Favorite, contentDescription = "3 Stars"
                        )
                    }
                    Column {
                        Icon(
                            Icons.Default.MoreVert, contentDescription = "4 Stars"
                        )
                    }
                    Column {
                        Icon(
                            Icons.Default.FavoriteBorder, contentDescription = "5 Stars"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PlacesPreview() {
    EcoLifeTheme {
        CalculatorMenu()
    }
}
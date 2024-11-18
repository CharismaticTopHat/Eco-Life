package com.example.eco_life

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eco_life.ui.theme.EcoLifeTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import com.example.eco_life.model.EcoPlace
import com.example.ecoshops.data.DataSource

class PlacesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EcoLifeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    EcoPlaceList(
                        placeList = DataSource().loadEcoPlaces(),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun EcoPlaceCard(place: EcoPlace){
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .padding(8.dp)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(id = place.imageResId),
                contentDescription = stringResource(id = place.nameResId),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(180.dp)
                    .height(150.dp)
                    .padding(start = 8.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Column (
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            ){
                androidx.compose.material3.Text(
                    text = stringResource(id = place.nameResId),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0, 154, 20),
                    modifier = Modifier
                        .padding(bottom = 12.dp)
                )
                androidx.compose.material3.Text(
                    text = stringResource(id = place.addressResId),
                    fontSize = 14.sp
                )
                androidx.compose.material3.Text(
                    text = stringResource(id = place.workingDaysResId),
                    fontSize = 10.sp
                )
                androidx.compose.material3.Text(
                    text = stringResource(id = place.workingHoursResId),
                    fontSize = 10.sp
                )
            }
        }
    }
}

@Composable
internal fun EcoPlaceList(placeList: List<EcoPlace>, modifier: Modifier = Modifier){
    val titleGreen = Color(15, 77, 23)
    val generatorButtonGreen = Color(61, 198, 78)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .background(generatorButtonGreen)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Recomendaciones",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
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
                        .height(16.dp * 2)
                )
            }
        }
    }
    LazyColumn (
        modifier = Modifier
            .padding(top = 32.dp)
    ){
        items(placeList) { place ->
            EcoPlaceCard(place = place)
        }
    }
}

@Composable
fun PlacesPreview() {
    EcoLifeTheme {
        EcoPlaceList(placeList = DataSource().loadEcoPlaces())
    }
}
package com.example.eco_life

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eco_life.ui.theme.EcoLifeTheme
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EcoLifeTheme {
                StartMenu()
            }
        }
    }
}

@Composable
fun TextCarousel() {
    var currentIndex by remember { mutableIntStateOf(0) }
    val items = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        IconButton(onClick = { if (currentIndex > 0) currentIndex-- }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Previous")
        }

        LazyRow(
            modifier = Modifier
                .weight(1f)
                .background(Color.Gray),
            horizontalArrangement = Arrangement.Center
        ) {
            items(items.size) { index ->
                if (index == currentIndex) {
                    Text(
                        text = items[index],
                        modifier = Modifier
                            .padding(32.dp)
                    )
                }
            }
        }

        IconButton(onClick = { if (currentIndex < items.size - 1) currentIndex++ }) {
            Icon(Icons.Default.ArrowForward, contentDescription = "Next")
        }
    }
}

@Composable
fun StartMenu() {
    val textSize = 20.sp
    val textHeight = textSize.value.dp
    val customColor = Color(30, 132, 73 )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Green)
            .padding(start = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Inicio",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = textSize,
                    color = Color.White
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
                .padding(start = 0.dp)
                .background(Color.White),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "Recomendaciones",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                color = customColor,
                fontSize = textSize*1.25
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 0.dp)
                .background(Color.White),
            horizontalArrangement = Arrangement.Start
        ) {
            TextCarousel()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EcoLifeTheme {
        StartMenu()
    }
}
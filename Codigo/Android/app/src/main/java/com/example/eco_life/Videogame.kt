package com.example.eco_life

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.graphics.graphicsLayer
import com.example.eco_life.data.DBHandler
import androidx.compose.ui.platform.LocalContext






import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eco_life.ui.theme.EcoLifeTheme
import kotlinx.coroutines.delay
import androidx.compose.animation.core.*
import kotlin.random.Random





class VideogameActivity : ComponentActivity() {
    private lateinit var dbHandler: DBHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHandler = DBHandler(this)

        setContent {
            VideogameMenu(
                onStartGame = {},
                dbHandler = dbHandler
            )
        }
    }
}




@Composable
fun GameScreen() {

    val context = LocalContext.current
    val dbHandler = remember { DBHandler(context) }

    var isGameStarted by remember { mutableStateOf(false) }

    if (isGameStarted) {
        GridJuego(
            onGameOver = { currentScore ->
                dbHandler.updateHighScore(currentScore)
                isGameStarted = false
            }
        )
    } else {
        VideogameMenu(
            onStartGame = { isGameStarted = true },
            dbHandler = dbHandler
        )
    }
}







data class CarData(var row: Int, var column: Int)

@Composable
fun GridJuego(onGameOver: (Int) -> Unit)
{
    val gridWidth = 12 //width
    val tileSize = 40.dp //tamano de cada tile indivivual

    // posicion logica jugador en las celdas
    var playerX by remember { mutableStateOf(5) }
    var playerY by remember { mutableStateOf(10) }

    // posicion visual jugador
    var visualPlayerX by remember { mutableStateOf(playerX.toFloat()) }
    var visualPlayerY by remember { mutableStateOf(playerY.toFloat()) }

    var direction by remember { mutableStateOf<Pair<Int, Int>?>(null) }
    var currentDirection by remember { mutableStateOf("down") }
    var spriteIndex by remember { mutableStateOf(0) }

    // Carga de sprites para el jugador
    // Carga de sprites para el jugador
    val playerSprites = mapOf(
        "down" to listOf(
            painterResource(id = R.drawable.sprite_down_1),
            painterResource(id = R.drawable.sprite_down_2)
        ),
        "up" to listOf(
            painterResource(id = R.drawable.sprite_up_1),
            painterResource(id = R.drawable.sprite_up_2)
        ),
        "left" to listOf(
            painterResource(id = R.drawable.sprite_left_1),
            painterResource(id = R.drawable.sprite_left_2)
        ),
        "right" to listOf(
            painterResource(id = R.drawable.sprite_right_1),
            painterResource(id = R.drawable.sprite_right_2)
        )
    )

    //Variables de estado del juego
    var lives by remember { mutableStateOf(3) }
    var energy by remember { mutableStateOf(100f) }
    val maxEnergy = 100f
    val energyDepletionRate = 0.5f

    //Variables para monedas y depósito
    val basuralimit = 10 //limite de bolsa para la basura
    var basuracurrent by remember { mutableStateOf(0) }
    var basuradepositada by remember { mutableStateOf(0) }

    //datos de los elementos del juego
    val rows = remember { mutableStateListOf<String>() }
    val coinData = remember { mutableStateListOf<CoinData>() }
    val containerData = remember { mutableStateListOf<ContainerData>() }
    val treeData = remember { mutableStateListOf<TreeData>() }
    val carData = remember { mutableStateListOf<CarData>() } //

    //basura pngs
    val coinImages = listOf(
        painterResource(id = R.drawable.basura1),
        painterResource(id = R.drawable.basura2),
        painterResource(id = R.drawable.basura3),
        painterResource(id = R.drawable.basura4),
        painterResource(id = R.drawable.basura5),
        painterResource(id = R.drawable.basura6),
        painterResource(id = R.drawable.basura7),
        painterResource(id = R.drawable.basura8)
    )

    //basura png
    val containerImage = painterResource(id = R.drawable.can1)

    //arboles pngs
    val treeImages = listOf(
        painterResource(id = R.drawable.arbol1),
        painterResource(id = R.drawable.arbol2),
        painterResource(id = R.drawable.arbol3)
    )

    //hojas pngs
    val leafImages = listOf(
        painterResource(id = R.drawable.leaf1),
        painterResource(id = R.drawable.leafs2),
        painterResource(id = R.drawable.leafs3),
        painterResource(id = R.drawable.leafs4)
    )

    LaunchedEffect(Unit) {
        while (true) {
            spriteIndex = (spriteIndex + 1) % (playerSprites[currentDirection]?.size ?: 1)
            delay(150L) // velocidad animacion
        }
    }


    // Inicializaciones
    LaunchedEffect(Unit) {
        repeat(20) {
            rows.add(if (it % 10 in 0..3) "road" else "grass")
        }
        coinData.clear()
        coinData.addAll(generateTrashData(rows, gridWidth, coinImages, density = 0.5f))
        containerData.clear()
        containerData.addAll(generateContainerData(rows, gridWidth, containerImage, density = 0.033f))
        treeData.clear()
        treeData.addAll(generateTreeData(rows, gridWidth, treeImages, density = 0.15f))
        carData.clear()
        carData.addAll(generateCarData(rows, gridWidth)) // Generar datos iniciales de coches
    }


    //filas en X mapa
    val gridHeight = 20 // Número de filas visibles en el mapa

    val panelImage = painterResource(id = R.drawable.panel)
    val panelData = remember { mutableStateListOf<PanelData>() }

    //panel incializacion
    LaunchedEffect(Unit) {
        panelData.clear()
        panelData.addAll(generatePanelData(rows, gridWidth, panelImage, density = 0.05f))
    }


    //colisiones de panel condifiones
    LaunchedEffect(direction) {
        direction?.let {
            while (direction != null) {
                // erificar colisiones con paneles
                checkPanelCollision(playerX, playerY, panelData, energy, maxEnergy) { restoredEnergy ->
                    energy = restoredEnergy
                }
                delay(200L)
            }
        }
    }


    //movimiento del jugador y gestionar colisiones
    LaunchedEffect(direction) {
        direction?.let { (dx, dy) ->
            while (direction != null) {
                val newPlayerX = (playerX + dx).coerceIn(0, gridWidth - 1)
                val newPlayerY = playerY + dy

                // verificar colisiones con arboles antes de mover al jugador
                if (!TreeCollision(newPlayerX, newPlayerY, treeData)) {
                    playerX = newPlayerX
                    playerY = newPlayerY
                }

                //Comprobar colisiones con monedas y contenedores
                TrashCollision(playerX, playerY, coinData,basuralimit, basuracurrent) { collectedCoins ->
                    basuracurrent = collectedCoins
                }
                ContainerCollision(playerX, playerY, containerData, basuracurrent ) { depositedCoins ->
                    basuradepositada += depositedCoins
                    basuracurrent = 0 // Vaciar bolsa
                }

                //colisinones coches
                if (CarCollision(playerX, playerY, carData)) {
                    lives--
                    if (lives <= 0) {
                        onGameOver(basuradepositada)
                        //onGameOver()
                        return@LaunchedEffect
                    }
                }

                // Gestion de la energía
                energy -= energyDepletionRate
                if (energy <= 0f) {
                    onGameOver(basuradepositada)
                    return@LaunchedEffect
                }

                // Actualizar filas y objetos
                // Actualizar filas y objetos
                // Actualizar filas y objetos
                if (playerY < 10) {
                    val newRowType = if (rows.size % 10 in 0..3) "road" else "grass"
                    rows.add(0, newRowType)
                    playerY++

                    MoveCarRows(carData, 1)

                    if (newRowType == "grass") {
                        //add elementos solo si se es en pasto
                        coinData.addAll(generateCoinsForNewRow(0, gridWidth, coinImages, density = 0.5f))
                        containerData.addAll(generateContainerForNewRow(0, gridWidth, containerImage, density = 0.033f))
                        treeData.addAll(generateTreesForNewRow(0, gridWidth, treeImages, density = 0.15f))
                        panelData.addAll(generatePanelsForNewRow(0, gridWidth, panelImage, density = 0.05f))
                    } else if (newRowType == "road") {

                        //se generan coches solo para la nueva fila de carretera
                        val newCars = NewRowCars(0, gridWidth, carData)
                        carData.addAll(newCars)

                        //delete cars that arent in the view
                        removeCarsOutOfView(carData, rows.size)
                    }

                    //se actualizan elementos
                    //coches fuera del rango visible antes de actualizar posiciones
                    carData.retainAll { it.row in 0 until gridHeight }
                    updateCarPositions(carData, gridHeight, gridWidth)

                    TrashPositions(coinData)
                    updateContainerPositions(containerData)
                    updateTreePositions(treeData)
                    updatePanelPositions(panelData)


                }



                if (playerY >= rows.size - 10) {
                    rows.add(if (rows.size % 10 in 0..3) "road" else "grass")
                    rows.removeAt(0)
                    playerY--
                }

                delay(200L)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {



        //Column estatico
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            rows.forEach { rowType ->
                Row {
                    repeat(gridWidth) { column ->
                        if (rowType == "grass") {
                            GrassTile(tileSize)
                        } else {
                            RoadTile(tileSize)
                        }
                    }
                }
            }
        }
        LaunchedEffect(Unit) { //Si no esta movimiento el jugador
            //Aun quite vida
            while (true) {
                if (CarCollision(playerX, playerY, carData)) {
                    lives-- // Restar una vida
                    if (lives <= 0) {
                        onGameOver(basuradepositada) // Terminar el juego si las vidas llegan a 0
                        break
                    }
                }
                delay(300L) //verificacion de colisions
            }
        }


        //Capas de dibujo
        CarLayer(carData, tileSize)
        TrashDraw(coinData, tileSize)
        ContainerLayer(containerData, tileSize)
        TreeLayer(treeData, tileSize)
        PanelLayer(panelData, tileSize)

        //agregar hojas en movimiento
        LeafLayer(leafImages)



        // Dibujar el jugador
        val currentSprite = playerSprites[currentDirection]?.get(spriteIndex) ?: painterResource(id = R.drawable.sprite_down_1)
        Image(
            painter = currentSprite,
            contentDescription = null,
            modifier = Modifier
                .size(tileSize)
                .offset(
                    x = (playerX * tileSize.value).dp,
                    y = (playerY * tileSize.value).dp
                )
        )

        LaunchedEffect(Unit) {
            while (true) {
                updateCarPositions(carData, gridHeight, gridWidth) //update car pos
                delay(300L) //velocidad del movimiento
            }
        }








        var isMoving by remember { mutableStateOf(false) } //Bloqueo de spam

        LaunchedEffect(direction) {
            direction?.let { (dx, dy) ->
                if (!isMoving) {
                    isMoving = true

                    //Calcular la posicion destino
                    val targetX = (playerX + dx).coerceIn(0, gridWidth - 1)
                    val targetY = (playerY + dy)

                    //Determinar direccion para los sprites
                    currentDirection = when {
                        dx < 0 -> "left"
                        dx > 0 -> "right"
                        dy < 0 -> "up"
                        else -> "down"
                    }

                    // Verificar colision con arbol
                    if (!TreeCollision(targetX, targetY, treeData)) {
                        playerX = targetX
                        playerY = targetY
                    }

                    // Verificar colision con coches
                    if (CarCollision(playerX, playerY, carData)) {
                        lives-- // Restar una vida
                        if (lives <= 0) {
                            onGameOver(basuradepositada)// Terminar el juego si las vidas llegan a 0
                            return@LaunchedEffect
                        }
                    }

                    // nueva posición lógica
                    while (
                        Math.abs(visualPlayerX - playerX.toFloat()) > 0.01f ||
                        Math.abs(visualPlayerY - playerY.toFloat()) > 0.01f
                    ) {
                        visualPlayerX += (playerX - visualPlayerX) * 0.2f
                        visualPlayerY += (playerY - visualPlayerY) * 0.2f

                        //cambio de sprites cuando el usuario camina
                        spriteIndex = (spriteIndex + 1) % (playerSprites[currentDirection]?.size ?: 1)

                        delay(150L) // Controla la velocidad del cambio de sprites
                    }

                    //
                    delay(300L)

                    isMoving = false // Finaliza el movimiento
                }
            }
        }















        /*
        //jugador old
        Box(
            modifier = Modifier
                .offset(x = (playerX * tileSize.value).dp, y = (playerY * tileSize.value).dp)
                .size(tileSize / 2)
                .background(Color.Green, shape = CircleShape)
        )
        */


        //Barras UI de energía y basura en la esquina superior izquierda
        Column(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.TopStart)
        ) {
            EnergyBar(energy, maxEnergy)
            Spacer(modifier = Modifier.height(4.dp))
            CoinBagProgressBar(basuracurrent, basuralimit)
        }

        // Mostrar contadores de monedas y vidas
        Text(
            text = "Basura recogidas: $basuracurrent",
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(start = 8.dp, top = 8.dp)
                .align(Alignment.TopStart)
                .offset(y = 40.dp)
        )
        Text(
            text = "Basura depositadas: $basuradepositada",
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(start = 8.dp, top = 8.dp)
                .align(Alignment.TopStart)
                .offset(y = 60.dp)
        )
        Text(
            text = "Vidas: $lives",
            fontSize = 16.sp,
            color = Color.Red,
            modifier = Modifier
                .padding(start = 8.dp, bottom = 8.dp)
                .align(Alignment.BottomStart)
        )

        //Botones de control
        ControlButtons(
            onMoveLeft = { direction = Pair(-1, 0) },
            onMoveRight = { direction = Pair(1, 0) },
            onMoveUp = { direction = Pair(0, -1) },
            onMoveDown = { direction = Pair(0, 1) },
            onStop = { direction = null }
        )
    }

}

//data class CarData(var row: Int, var column: Int)

//TILES
@Composable
fun GrassTile(tileSize: Dp) {
    // Lista de recursos de imágenes de piso
    val floorImages = listOf(
        R.drawable.floor1,
        R.drawable.floor2,
        R.drawable.floor3,
        R.drawable.floor4,
        R.drawable.floor5,
        R.drawable.floor6
    )
    // selecciona pisos
    val randomFloor = floorImages.random()
    val grassImage: Painter = painterResource(id = randomFloor)

    Image(
        painter = grassImage,
        contentDescription = null,
        modifier = Modifier.size(tileSize)
    )
}


@Composable
fun RoadTile(tileSize: Dp) {
    val roadImage: Painter = painterResource(id = R.drawable.carretera)
    Image(
        painter = roadImage,
        contentDescription = null,
        modifier = Modifier.size(tileSize)
    )
}

data class PanelData(val row: Int, val column: Int, val image: Painter)

fun generatePanelData(
    rows: List<String>,
    gridWidth: Int,
    panelImage: Painter,
    density: Float = 0.05f //densidad
): List<PanelData> {
    val panelPositions = mutableListOf<PanelData>()
    rows.forEachIndexed { rowIndex, rowType ->
        if (rowType == "grass" && Random.nextFloat() < density) {
            val randomColumn = Random.nextInt(0, gridWidth)
            panelPositions.add(PanelData(row = rowIndex, column = randomColumn, image = panelImage))
        }
    }
    return panelPositions
}

//new panels
fun generatePanelsForNewRow(
    rowIndex: Int,
    gridWidth: Int,
    panelImage: Painter,
    density: Float = 0.05f
): List<PanelData> {
    val newPanels = mutableListOf<PanelData>()
    if (Random.nextFloat() < density) {
        val randomColumn = Random.nextInt(0, gridWidth)
        newPanels.add(PanelData(row = rowIndex, column = randomColumn, image = panelImage))
    }
    return newPanels
}

fun updatePanelPositions(panelData: MutableList<PanelData>) {
    for (i in panelData.indices) {
        panelData[i] = panelData[i].copy(row = panelData[i].row + 1)
    }
}

//ccolision con panel
fun checkPanelCollision(
    playerX: Int,
    playerY: Int,
    panelData: MutableList<PanelData>,
    currentEnergy: Float,
    maxEnergy: Float,
    onEnergyRestored: (Float) -> Unit
) {
    val panelCollision = panelData.any { it.row == playerY && it.column == playerX }
    if (panelCollision) {
        val restoredEnergy = (currentEnergy + 20f).coerceAtMost(maxEnergy)
        onEnergyRestored(restoredEnergy)
        //se elimina el panel despues de ser usado
        panelData.removeIf { it.row == playerY && it.column == playerX }
    }
}


@Composable
fun PanelLayer(panelData: List<PanelData>, tileSize: Dp) {
    panelData.forEach { (row, column, image) ->
        Panel(tileSize, row, column, image)
    }
}

@Composable
fun Panel(tileSize: Dp, row: Int, column: Int, panelImage: Painter) {
    Image(
        painter = panelImage,
        contentDescription = null,
        modifier = Modifier
            .size(tileSize)
            .offset(
                x = (column * tileSize.value).dp,
                y = (row * tileSize.value).dp
            )
    )
}

fun MoveCarRows(carData: MutableList<CarData>, shiftAmount: Int) {
    for (i in carData.indices) {
        carData[i] = carData[i].copy(row = carData[i].row + shiftAmount)
    }
}


fun removeCarsOutOfView(carData: MutableList<CarData>, maxRows: Int) {
    carData.retainAll { car -> car.row in 0 until maxRows }
}



fun NewRowCars(rowIndex: Int, gridWidth: Int, existingCars: List<CarData>): List<CarData> {
    val cars = mutableListOf<CarData>()
    val UsedColumns = existingCars.filter { it.row == rowIndex }.map { it.column }
    if (Random.nextFloat() < 0.5f) { //Probabilidades de generar un coche (por ahora no es tan dificil)
        var randomColumn: Int
        do {
            randomColumn = Random.nextInt(0, gridWidth)
        } while (UsedColumns.contains(randomColumn)) //Evitar columnas con coches
        cars.add(CarData(row = rowIndex, column = randomColumn))
    }
    return cars
}




@Composable
fun CarLayer(carData: List<CarData>, tileSize: Dp) {
    carData.forEach { car ->
        if (car.row >= 0) {
            Car(tileSize, car.row, car.column)
        }
    }
}



@Composable
fun Car(tileSize: Dp, row: Int, column: Int) {
    val carImage: Painter = painterResource(id = R.drawable.car10)

    Image(
        painter = carImage,
        contentDescription = null,
        modifier = Modifier
            .size(tileSize)
            .offset(
                x = (column * tileSize.value).dp,
                y = (row * tileSize.value).dp
            )
    )
}



fun CarCollision(playerX: Int, playerY: Int, carData: List<CarData>): Boolean {
    return carData.any { car -> car.row == playerY && car.column == playerX }
}





fun generateCarData(rows: List<String>, gridWidth: Int): List<CarData> {
    val carPositions = mutableListOf<CarData>()
    rows.forEachIndexed { rowIndex, rowType ->
        if (rowType == "road") {
            val randomColumn = Random.nextInt(0, gridWidth)
            carPositions.add(CarData(row = rowIndex, column = randomColumn))
        }
    }
    return carPositions
}

fun updateCarPositions(carData: MutableList<CarData>, gridWidth: Int, gridHeight: Int) {
    val updatedCars = mutableListOf<CarData>()
    for (car in carData) {
        val newColumn = (car.column + 1) % gridWidth // avanza hacia la derecha
        if (car.row < gridHeight) { // coches fuera del grid
            updatedCars.add(car.copy(column = newColumn))
        }
    }
    carData.clear()
    carData.addAll(updatedCars)
}














@Composable
fun EnergyBar(currentEnergy: Float, maxEnergy: Float) {
    val progress = currentEnergy / maxEnergy
    Box(
        modifier = Modifier
            .width(120.dp) // Ancho más pequeño
            .height(12.dp) // Altura más pequeña
            .background(Color.Gray, shape = CircleShape)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(progress)
                .background(Color.Blue, shape = CircleShape)
        )
    }
}

@Composable
fun CoinBagProgressBar(basuracurrent: Int, basuralimit: Int) {
    val progress = basuracurrent / basuralimit.toFloat()
    Box(
        modifier = Modifier
            .width(120.dp) //nachura
            .height(12.dp) //altura de la brra
            .background(Color.Gray, shape = CircleShape)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(progress)
                .background(Color.Green, shape = CircleShape)
        )
    }
}




fun TreeCollision(playerX: Int, playerY: Int, treeData: List<TreeData>): Boolean {
    // evisar si el jugador está en la misma celda que arbol
    return treeData.any {
        (it.row == playerY && it.column == playerX) || // Colision exacta
                (it.row == playerY - 1 && it.column == playerX) // Colision en la celda de arriba de arbol (hacerlo mas
        //grande modifico su hitbox)

    }
}



//TREES
data class TreeData(val row: Int, val column: Int, val image: Painter)

fun generateTreeData(
    rows: List<String>,
    gridWidth: Int,
    treeImages: List<Painter>,
    density: Float
): List<TreeData> {
    val treePositions = mutableListOf<TreeData>()
    rows.forEachIndexed { rowIndex, rowType ->
        if (rowType == "grass" && Random.nextFloat() < density) {
            val randomColumn = Random.nextInt(0, gridWidth)
            val treeImage = treeImages.random()
            treePositions.add(TreeData(row = rowIndex, column = randomColumn, image = treeImage))
        }
    }
    return treePositions
}

fun generateTreesForNewRow(
    rowIndex: Int,
    gridWidth: Int,
    treeImages: List<Painter>,
    density: Float
): List<TreeData> {
    val newTrees = mutableListOf<TreeData>()
    if (Random.nextFloat() < density) {
        val randomColumn = Random.nextInt(0, gridWidth)
        val treeImage = treeImages.random()
        newTrees.add(TreeData(row = rowIndex, column = randomColumn, image = treeImage))
    }
    return newTrees
}





fun updateTreePositions(treeData: MutableList<TreeData>) {
    for (i in treeData.indices) {
        treeData[i] = treeData[i].copy(row = treeData[i].row + 1)
    }
}

@Composable
fun TreeLayer(treeData: List<TreeData>, tileSize: Dp) {
    treeData.forEach { (row, column, image) ->
        Tree(tileSize, row, column, image)
    }
}

@Composable
fun Tree(tileSize: Dp, row: Int, column: Int, treeImage: Painter) {
    Image(
        painter = treeImage,
        contentDescription = null,
        modifier = Modifier
            .size(tileSize * 1.5f)//bigger
            .offset(
                x = (column * tileSize.value).dp,
                y = (row * tileSize.value).dp
            )
    )
}
//TREES
//TREES ENDING

//data class CoinData(val row: Int, val column: Int, val image: Painter)
data class ContainerData(val row: Int, val column: Int, val image: Painter)

//CONTAINERS

//
fun generateContainerData(
    rows: List<String>,
    gridWidth: Int,
    containerImage: Painter,
    density: Float = 0.1f
): List<ContainerData> {
    val containerPositions = mutableListOf<ContainerData>()
    rows.forEachIndexed { rowIndex, rowType ->
        if (rowType == "grass" && Random.nextFloat() < density) {
            val randomColumn = Random.nextInt(0, gridWidth)
            containerPositions.add(ContainerData(row = rowIndex, column = randomColumn, image = containerImage))
        }
    }
    return containerPositions
}

//
fun generateContainerForNewRow(
    rowIndex: Int,
    gridWidth: Int,
    containerImage: Painter,
    density: Float = 0.1f
): List<ContainerData> {
    val newContainers = mutableListOf<ContainerData>()
    if (Random.nextFloat() < density) {
        val randomColumn = Random.nextInt(0, gridWidth)
        newContainers.add(ContainerData(row = rowIndex, column = randomColumn, image = containerImage))
    }
    return newContainers
}

// Verifica colisiones con contenedores y deposita monedas
fun ContainerCollision(
    playerX: Int,
    playerY: Int,
    containerData: MutableList<ContainerData>,
    basuracurrent: Int,
    onCoinsDeposited: (Int) -> Unit
) {
    val containerCollision = containerData.any { it.row == playerY && it.column == playerX }
    if (containerCollision && basuracurrent > 0) {
        onCoinsDeposited(basuracurrent)
    }
}

// Actualizar posiciones de los contenedores
fun updateContainerPositions(containerData: MutableList<ContainerData>) {
    for (i in containerData.indices) {
        containerData[i] = containerData[i].copy(row = containerData[i].row + 1)
    }
}

// Capa de los contenedores
@Composable
fun ContainerLayer(containerData: List<ContainerData>, tileSize: Dp) {
    containerData.forEach { (row, column, image) ->
        Container(tileSize, row, column, image)
    }
}

@Composable
fun Container(tileSize: Dp, row: Int, column: Int, containerImage: Painter) {
    Image(
        painter = containerImage,
        contentDescription = null,
        modifier = Modifier
            .size(tileSize)
            .offset(
                x = (column * tileSize.value).dp,
                y = (row * tileSize.value).dp
            )
    )
}


//CONTAINERS
//CONTAINERS END

//colisiones con basura y actualiza el conteo en la bolsa
fun TrashCollision(
    playerX: Int,
    playerY: Int,
    coinData: MutableList<CoinData>,
    basuralimit: Int,
    basuracurrent: Int,
    onCoinsCollected: (Int) -> Unit
) {
    val coinsToRemove = coinData.filter { it.row == playerY && it.column == playerX }
    if (coinsToRemove.isNotEmpty() && basuracurrent < basuralimit) {
        coinData.removeAll(coinsToRemove)
        onCoinsCollected((basuracurrent + coinsToRemove.size).coerceAtMost(basuralimit))
    }
}




data class CoinData(val row: Int, val column: Int, val image: Painter)

// trash
fun generateTrashData(
    rows: List<String>,
    gridWidth: Int,
    coinImages: List<Painter>,
    density: Float = 0.8f
): List<CoinData> {
    val coinPositions = mutableListOf<CoinData>()

    rows.forEachIndexed { rowIndex, rowType ->
        if (rowType == "grass") {
            repeat((gridWidth * density).toInt()) {
                val randomColumn = Random.nextInt(0, gridWidth)
                val coinImage = coinImages.random()
                coinPositions.add(CoinData(row = rowIndex, column = randomColumn, image = coinImage))
            }
        }
    }
    return coinPositions
}

fun generateCoinsForNewRow(
    rowIndex: Int,
    gridWidth: Int,
    coinImages: List<Painter>,
    density: Float = 0.8f
): List<CoinData> {
    val newCoins = mutableListOf<CoinData>()
    repeat((gridWidth * density).toInt()) {
        val randomColumn = Random.nextInt(0, gridWidth)
        val coinImage = coinImages.random()
        newCoins.add(CoinData(row = rowIndex, column = randomColumn, image = coinImage))
    }
    return newCoins
}

fun TrashPositions(coinData: MutableList<CoinData>) {
    for (i in coinData.indices) {
        coinData[i] = coinData[i].copy(row = coinData[i].row + 1)
    }
}






@Composable
fun TrashDraw(coinData: List<CoinData>, tileSize: Dp) {
    coinData.forEach { (row, column, image) ->
        Coin(tileSize, row, column, image)
    }
}

@Composable
fun Coin(tileSize: Dp, row: Int, column: Int, coinImage: Painter) {
    val infiniteTransition = rememberInfiniteTransition()

    val floatOffset by infiniteTransition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Image(
        painter = coinImage,
        contentDescription = null,
        modifier = Modifier
            .size(tileSize / 2)
            .offset(
                x = (column * tileSize.value).dp,
                y = (row * tileSize.value).dp + floatOffset.dp
            )
    )
}

//LEAFS

data class Leaf(
    val image: Painter,
    val startX: Float,
    val startY: Float = 0f,
    val lDistance: Float,
    val size: Float,
    val durationMillis: Int
)

fun createLeaf(screenWidth: Dp, leafImages: List<Painter>): Leaf {
    val randomImage = leafImages.random()
    val startX = (0..screenWidth.value.toInt()).random().toFloat()
    val lDistance = (30..60).random().toFloat() * if ((0..1).random() == 0) 1 else -1
    val size = (20..50).random().toFloat()
    val durationMillis = (4000..8000).random()

    return Leaf(
        image = randomImage,
        startX = startX,
        lDistance = lDistance,
        size = size,
        durationMillis = durationMillis
    )
}

@Composable
fun LeafLayer(leafImages: List<Painter>) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val leaves = remember {
        mutableStateListOf<Leaf>().apply {

            repeat(40) { //num de hojas
                add(createLeaf(screenWidth, leafImages))
            }
        }
    }

    // Dibujar las hojas
    Box(modifier = Modifier.fillMaxSize()) {
        leaves.forEach { leaf ->
            val transition = rememberInfiniteTransition()
            val offsetX = transition.animateFloat(
                initialValue = leaf.startX,
                targetValue = leaf.startX + leaf.lDistance,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 2000, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )
            val offsetY = transition.animateFloat(
                initialValue = leaf.startY,
                targetValue = screenHeight.value,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = leaf.durationMillis, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            )

            Image(
                painter = leaf.image,
                contentDescription = null,
                modifier = Modifier
                    .offset(x = offsetX.value.dp, y = offsetY.value.dp)
                    .size(leaf.size.dp)
                    .graphicsLayer(alpha = 1f - offsetY.value / screenHeight.value) // Ajustar opacidad
            )
        }
    }
}




//LEAFS
//LEAFS Ending

@Composable
fun ControlButtons(
    onMoveLeft: () -> Unit,
    onMoveRight: () -> Unit,
    onMoveUp: () -> Unit,
    onMoveDown: () -> Unit,
    onStop: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DirectionButton(
            imageResource = R.drawable.up,
            onMove = onMoveUp,
            onStop = onStop
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DirectionButton(
                imageResource = R.drawable.left,
                onMove = onMoveLeft,
                onStop = onStop
            )
            Spacer(modifier = Modifier.width(50.dp))
            DirectionButton(
                imageResource = R.drawable.right,
                onMove = onMoveRight,
                onStop = onStop
            )
        }
        DirectionButton(
            imageResource = R.drawable.down,
            onMove = onMoveDown,
            onStop = onStop
        )
    }
}


@Composable
fun DirectionButton(imageResource: Int, onMove: () -> Unit, onStop: () -> Unit) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        onMove()
                        tryAwaitRelease()
                        onStop()
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}


@Composable
fun VideogameMenu(onStartGame: () -> Unit, dbHandler: DBHandler) {
    var highestScore by remember { mutableStateOf(0) }

    //record base de datos
    LaunchedEffect(Unit) {
        highestScore = dbHandler.getHighScore()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Videojuego",
            fontSize = 24.sp,
            color = Color.Green,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = "Récord: $highestScore",
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        Button(onClick = onStartGame) {
            Text(text = "Start Game")
        }
    }
}

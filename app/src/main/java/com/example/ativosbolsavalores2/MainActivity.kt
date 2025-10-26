package com.example.ativosbolsavalores2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HomeScreen()
        }
    }
}

data class FavoritoItem(val ticker: String, val logoUrl: String, val marketPrice: String)

@Composable
fun BoxScope.FavoritarTickerMenu(
    ticker: String,
    logoUrl: String,
    marketPrice: String,
    favoritos: Set<FavoritoItem>,
    menuAberto: Boolean,
    onToggleFavorito: (Boolean) -> Unit,
    onMenuToggle: () -> Unit,
    onTickerSelect: (String) -> Unit
) {

    val isFavorito = favoritos.any { it.ticker == ticker }

    IconButton(
        onClick = { onToggleFavorito(!isFavorito) },
        modifier = Modifier
            .padding(top = 70.dp, start = 0.dp)
            .align(Alignment.TopStart)
    ) {
        Icon(
            imageVector = if (isFavorito) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = if (isFavorito) "Remover dos favoritos" else "Adicionar aos favoritos",
            tint = if (isFavorito) Color.Red else Color.Gray,
            modifier = Modifier.size(30.dp)
        )
    }

    IconButton(
        onClick = { onMenuToggle() },
        modifier = Modifier
            .padding(top = 70.dp, end = 0.dp)
            .align(Alignment.TopEnd)
    ) {
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = "Abrir ativos favoritados.",
            tint = Color.White
        )
    }

    AnimatedVisibility(
        visible = menuAberto,
        modifier = Modifier
            .align(Alignment.TopEnd)
            .zIndex(10f),
        enter = slideInHorizontally(initialOffsetX = { it }),
        exit = slideOutHorizontally(targetOffsetX = { it })
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 300.dp),
            color = Color(0xFF1B263B),
            border = BorderStroke(1.dp, Color.White),
            shape = RoundedCornerShape(8.dp)
        ) {
            LazyColumn(modifier = Modifier.fillMaxWidth().height(380.dp)) {
                if (favoritos.isEmpty()) {
                    item {
                        Text("Clique no ícone ♡ para favoritar um ativo.", color = Color.White, modifier = Modifier.padding(16.dp))
                    }
                } else {
                    items(favoritos.toList()) { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onTickerSelect(item.ticker) }
                                .padding(horizontal = 8.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(item.logoUrl)
                                    .decoderFactory(SvgDecoder.Factory())
                                    .build(),
                                contentDescription = "Logo ${item.ticker}",
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(text = item.ticker, color = Color.White, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.width(8.dp))
                            Text(text = item.marketPrice, color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen() {
    var shortName by remember { mutableStateOf("") }
    var currency by remember { mutableStateOf("moeda") }
    var marketPrice by remember { mutableStateOf("") }
    var marketPreviousClose by remember { mutableStateOf("") }
    var marketChange by remember { mutableStateOf(" ") }
    var marketChangePercent by remember { mutableStateOf("") }
    var dayRange by remember { mutableStateOf("") }
    var fiftyTwoWeekRange by remember { mutableStateOf("") }
    var logoUrl by remember { mutableStateOf("") }

    var ticker by remember { mutableStateOf("BOVA11") }
    var token by remember { mutableStateOf("fVvtN4WFtkrPTVerbUHG9F") }

    var favoritos by remember { mutableStateOf(setOf<FavoritoItem>()) }
    var menuAberto by remember { mutableStateOf(false) }

    var scrollState = rememberScrollState()

    val context = LocalContext.current

    LaunchedEffect(ticker, token) {
        withContext(Dispatchers.IO) {
            try {
                val url = URL("https://brapi.dev/api/quote/$ticker?token=$token")
                val conn = url.openConnection() as HttpsURLConnection
                conn.requestMethod = "GET"
                conn.connect()
                val response = conn.inputStream.bufferedReader().use { it.readText() }
                val json = JSONObject(response)
                val ativo = json.getJSONArray("results").getJSONObject(0)

                shortName = ativo.optString("shortName")
                currency = ativo.optString("currency")
                marketPrice = ativo.optString("regularMarketPrice")
                marketPreviousClose = ativo.optString("regularMarketPreviousClose")
                marketChange = ativo.optString("regularMarketChange")
                marketChangePercent = ativo.optString("regularMarketChangePercent")
                dayRange = ativo.optString("regularMarketDayRange")
                fiftyTwoWeekRange = ativo.optString("fiftyTwoWeekRange")
                logoUrl = ativo.optString("logourl")

                menuAberto = false
            } catch (e: Exception) {
                // Toast.makeText(context, "Erro ao buscar ativo. Verifique sua conexão e tente novamente.", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1B263B))
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            val campos = listOf(
                "Nome curto" to shortName,
                "Moeda" to currency,
                "Preço atual" to marketPrice,
                "Fechamento anterior ($currency)" to marketPreviousClose,
                "Variação do dia ($currency)" to marketChange,
                "Variação do dia (%)" to marketChangePercent,
                "Intervalo do dia ($currency)" to dayRange,
                "Intervalo de 52 semanas ($currency)" to fiftyTwoWeekRange
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(40.dp)
                    .background(Color(0xFF1B263B)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.TopCenter
                ) {

                    FavoritarTickerMenu(
                        ticker = ticker,
                        logoUrl = logoUrl,
                        marketPrice = marketPrice,
                        favoritos = favoritos,
                        menuAberto = menuAberto,
                        onToggleFavorito = { adicionar ->
                            favoritos = if (adicionar) {
                                favoritos + FavoritoItem(ticker, logoUrl, "R$" + marketPrice)
                            } else {
                                favoritos.filterNot { it.ticker == ticker }.toSet()
                            }
                        },
                        onMenuToggle = { menuAberto = !menuAberto },
                        onTickerSelect = { tickerSelecionado ->
                            ticker = tickerSelecionado
                            menuAberto = false
                        }
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Surface (
                            shape = RoundedCornerShape(4.dp),
                            border = BorderStroke(4.dp, Color(0xFF331976D2))
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(logoUrl)
                                    .decoderFactory(SvgDecoder.Factory())
                                    .build(),
                                contentDescription = "Logo do ativo.",
                                modifier = Modifier.width(180.dp).height(180.dp).background(Color(0xFF1B263B)).padding(4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(30.dp))

                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            border = BorderStroke(4.dp, Color(0xFF331976D2))
                        ) {
                            val maxLength = 6
                            OutlinedTextField(
                                value = ticker,
                                onValueChange = {
                                    if (it.length <= 6) {
                                        ticker = it.uppercase()
                                    }
                                },
                                placeholder = {
                                    Text(
                                        text = "Digite o ticker",
                                        modifier = Modifier.fillMaxWidth(),
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Center
                                        )
                                    )
                                },
                                colors  = OutlinedTextFieldDefaults.colors (
                                    focusedPlaceholderColor = Color(0xFF331976D2),
                                ),

                                modifier = Modifier
                                    .width(180.dp)
                                    .background(Color.White),
                                shape = RoundedCornerShape(4.dp),
                                textStyle = TextStyle(
                                    color = Color.Black,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                ),
                                singleLine = true
                            )
                        }
                        Spacer(modifier = Modifier.height(30.dp))
                    }
                }

                if (!menuAberto) {

                Surface(
                    color = Color(0xFF331976D2),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        campos.forEach { (label, valor) ->
                            Surface(
                                color = Color(0xFFBDBDBD),
                                border = BorderStroke(2.dp, Color.White),
                                shape = RoundedCornerShape(4.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(6.dp)
                            ) {
                                Text(
                                    text = "$label: $valor",
                                    color = if ((label == "Variação do dia ($currency)" || label == "Variação do dia (%)") && valor > "0.00") {
                                                Color(0xFF2E7D32)
                                            } else if ((label == "Variação do dia ($currency)" || label == "Variação do dia (%)") && valor < "0.00") {
                                                Color(0xFFC62828)
                                            } else {
                                                Color.Black
                                            },
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

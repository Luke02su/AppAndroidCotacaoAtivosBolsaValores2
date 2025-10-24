package com.example.ativosbolsavalores2

import android.R
import android.R.id.bold
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableDoubleState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.decode.SvgDecoder


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HomeScreen()
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

    var ticker by remember { mutableStateOf("PETR4") }
    var token by remember { mutableStateOf("fVvtN4WFtkrPTVerbUHG9F") }

    val context = LocalContext.current

    LaunchedEffect(ticker, token) {
        if (ticker.isBlank()) {
            shortName = "0"
            currency = "moeda"
            marketPrice = " "
            marketPreviousClose = " "
            marketChange = " "
            marketChangePercent = "0"
            dayRange = "0"
            fiftyTwoWeekRange = "0"
            logoUrl = " "
            return@LaunchedEffect
        }
        withContext(Dispatchers.IO) {
            try {
                val url = URL("https://brapi.dev/api/quote/$ticker?token=$token")
                val conn = url.openConnection() as HttpsURLConnection
                conn.requestMethod = "GET"
                conn.connect()

                val response = conn.inputStream.bufferedReader().use { it.readText() }
                val json = JSONObject(response)
                val ativo = json.getJSONArray("results").getJSONObject(0)

                shortName = ativo.optString("shortName", "")
                currency = ativo.optString("currency", "moeda")
                marketPrice = ativo.optString("regularMarketPrice", "")
                marketPreviousClose = ativo.optString("regularMarketPreviousClose", "")
                marketChange = ativo.optString(
                    "regularMarketChange",
                    " "
                ) // Espaço para não quebrar a lógica de cor
                marketChangePercent = ativo.optString("regularMarketChangePercent", "")
                dayRange = ativo.optString("regularMarketDayRange", "")
                fiftyTwoWeekRange = ativo.optString("fiftyTwoWeekRange", "")
                logoUrl = ativo.optString("logourl", "")
            } catch (e: Exception) {
                //Toast.makeText(context, "Erro ao puxar ", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }

    // Column Principal (Ocupa a tela inteira e alinha tudo ao Centro)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF1B263B)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
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

        // Column que estava centralizada
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFF1B263B))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // BOX PRINCIPAL (AGORA COM TODOS OS ELEMENTOS DE SOBREPOSIÇÃO)
            Box(
                modifier = Modifier
                    .fillMaxWidth() // Preenche a largura para centralizar o conteúdo
                    .weight(1f), // Dá ao Box o espaço restante (acima do Surface)
                contentAlignment = Alignment.TopCenter // Alinha a maioria do conteúdo ao centro (padrão do Box)
            ) {

                // 1. IconButton (Posicionado no TopStart - Canto Superior Esquerdo)
                IconButton(
                    onClick = { /* ... */ },
                    // Usamos .align(Alignment.TopStart) para forçar o canto
                    modifier = Modifier
                        .padding(top = 100.dp, start = 15.dp)
                        .align(Alignment.TopStart)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favoritar ativo",
                        tint = Color.White
                    )
                }

                IconButton(
                    onClick = { /* ... */ },
                    // Usamos .align(Alignment.TopStart) para forçar o canto
                    modifier = Modifier
                        .padding(top = 100.dp, end = 15.dp)
                        .align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Lista de ativos favoritos",
                        tint = Color.White
                    )
                }

                // 2. Imagem e Campo de Texto (Centralizados no Box)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center), // Alinha este Column no centro do Box
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(logoUrl)
                            .decoderFactory(SvgDecoder.Factory())
                            .build(),
                        contentDescription = "Logo do ativo.",
                        modifier = Modifier
                            .width(180.dp)
                    )
                    OutlinedTextField(
                        value = ticker,
                        onValueChange = { ticker = it },
                        label = { Text("Digite o ticker") },
                        modifier = Modifier
                            .width(180.dp)
                            .background(color = Color.White),
                        shape = RoundedCornerShape(4.dp),
                        textStyle = TextStyle(
                            color = Color(0xFF212121),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            } // Fim do Box principal

            // Bloco Surface original (mantido abaixo do Box)
            Surface(
                color = Color(0xFF331976D2),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    campos.forEach { (label, valor) ->
                        Surface(
                            color = Color(0xFFBDBDBD),
                            border = BorderStroke(2.dp, Color.White),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Text(
                                text = "$label: $valor",
                                color = if ((label == "Variação do dia ($currency)" || label == "Variação do dia (%)") && valor > "0.00") Color(
                                    0xFF2E7D32
                                ) else Color(0xFFC62828),
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
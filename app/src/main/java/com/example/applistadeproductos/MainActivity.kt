package com.example.applistadeproductos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.applistadeproductos.ui.theme.AppListaDeProductosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        setContent {
            AppListaDeProductosTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    llamar()
                }
            }
        }
    }
}

var lista2: MutableList<String> = mutableStateListOf()
var lista: MutableList<String> = mutableStateListOf(
    "Alejandrina Opazo ----> $206.598",
    "Alicia Molina Godoy ------> $812.207",
    "ARAYA CERDA MARIO ------> $172.122",
    "BLANCA LOPEZ ORMAZABAL ------> $71.113",
    "BOTILLERIA LA PROMO LIMITADA ------> $632.700",
    "CRISTIAN REQUENES P. ------> $289.722",
    "cristian super k ---> $178.806",
    "EVER AÑASCO MONCAYO ------> $282.242",
    "HOTELERA DIEGO DE ALMAGRO LTDA. ------> $1.585.676",
    "HOTELERA PLAYA BRAVA S.A. ------> $3.866.865",
    "HOTELERA Y TURISMO OCEANO S.A. ------> $261.825",
    "INVERSIONES Y SERVICIOS INTEGRALES NORTE SPA ------> $62.288",
    "JUAN FRANCISCO ANDRES CERDA LARRAIN ------> $388.202",
    "Juan Meri Sandoval ------> $175.200",
    "laura maya ---> $133.500",
    "MADANA SPA ------> $458.457",
    "MARGARITA CAMPOS GALAZ ------> $363.624",
    "maria carreño ---> $94.254",
    "maria contreras ---> $296.000",
    "MARIA VELEZ PACHECO ------> $531.733",
    "MIGUEL ANGEL RETAMALES PEÑA ------> $333.291",
    "MILTON ALFARO CONTRERAS ------> $421.298",
    "OTILIA PEREA RIOS ------> $951.609",
    "RECREACIONES COMERCIAL BAJO MOLLE S.A. ------> $603.349",
    "Reinaldo Guerra Medina ------> $1.016.167",
    "RESTAURANT DOMINGO ANTONIO MEZA FLORES E.I.R.L. ------> $246.623",
    "Rigoberto Cortes Tapia ------> $906.529",
    "roberto fuentes ---> $105.540",
    "ROJAS CLARO HECTOR WILFREDO ------> $83.302",
    "ROSSANA DIAZ TORREALBA ------> $768.386",
    "Royal Santiago Hotel S.A. ------> $2.899.066",
    "S TRAGOS SPA ------> $120.073"
)


@Composable
fun llamar() {
    lista.forEach {
        lista2.add(it)
    }
    Greeting()
}

@Composable
fun Greeting() {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize(1f)
    ) {
        /*lista.forEach {
            lista2.add(it)
        }*/
        var producto by remember { mutableStateOf("") }
        var l: List<String>
        TextField(
            value = producto, onValueChange = { valor ->
                lista2.clear()
                println(lista2.size)
                producto = valor
                println(valor)
                println(lista.size)
                l = lista.filter { (it.uppercase()).contains(valor.uppercase()) }
                l.forEach {
                    lista2.add(it)
                    println(it)
                }
                println("el valor nuevo de lis2 es ${lista2.size}")
                println("hola comop estan")
            })
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                lista2.clear()
                /*println("hola")
                lista2.forEach {
                    println(it)
                }*/

            },
            modifier = Modifier
                .height(60.dp)
                .padding(2.dp)
        ) {
            Text(text = "darle clic")
        }
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(1f)
        ) {

            items(lista2.size) { l ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .background(Color.Cyan)
                ) {

                    Card(
                        elevation = 10.dp,
                        modifier = Modifier.padding(1.dp)

                    ) {
                        Text(
                            modifier = Modifier
                                .padding(1.dp)
                                .fillMaxWidth(.75f)
                                .height(50.dp)
                                .horizontalScroll(rememberScrollState()),
                            text = "${lista2[l]}"
                        )
                    }
                    Card(
                        elevation = 10.dp,
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Column() {
                            Text(
                                text = "und $1.240",
                                modifier = Modifier
                                    .padding(0.dp)
                                    .fillMaxWidth(1f)
                                    .height(25.dp),
                                textAlign = TextAlign.Center,
                                fontSize = 13.sp

                            )
                            Spacer(modifier = Modifier.height(1.dp))
                            Text(
                                text = "dsp $8.160",
                                modifier = Modifier
                                    .padding(0.dp)
                                    .fillMaxWidth(1f)
                                    .height(25.dp)
                                    .background(Color.Magenta),
                                textAlign = TextAlign.Center,
                                fontSize = 13.sp
                            )
                        }

                    }
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppListaDeProductosTheme {
        llamar()
    }
}
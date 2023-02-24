package com.example.applistadeproductos

import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.applistadeproductos.ui.theme.AppListaDeProductosTheme
import java.text.DecimalFormat

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var contexto = LocalContext.current
            AppListaDeProductosTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),

                    ) {
                    GetDataApi(contexto)
                    Greeting()
                }
            }
        }
    }
}

data class productos(val codigo: Int, val nombre: String, val precio: String)

var lista2: MutableList<productos> = mutableStateListOf()
var lista: MutableList<productos> = mutableStateListOf()


fun GetDataApi(contexto: Context) {
    lista.clear()
    lista2.clear()
    val queue = Volley.newRequestQueue(contexto)
    val url = "https://www.paginadeprueba009.com/prueba_kotlin.php"
    println("entro")
    val stringRequest = JsonObjectRequest(
        Request.Method.GET, url, null,
        Response.Listener { response ->

            val json = response.getJSONArray("productos")

            for (p in 0 until json.length()) {
                //println(json.getJSONObject(p).get("nombre"))
                var nombre: String = json.getJSONObject(p).get("nombre").toString()
                var precio: Double = json.getJSONObject(p).get("precio").toString().toDouble()
                var formato = DecimalFormat("#,###.###")
                var precio_format = formato.format(precio).toString()
                lista.add(

                    productos(
                        1,
                        nombre,
                        precio_format
                    )
                )
            }
            lista.forEach {
                lista2.add(it)
            }
        },
        Response.ErrorListener { })


    // Add the request to the RequestQueue.
    queue.add(stringRequest)

}


@Composable
fun Greeting() {

    var contexto = LocalContext.current
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
        var l: List<productos>
        TextField(
            value = producto, onValueChange = { valor ->
                lista2.clear()
                producto = valor
                l = lista.filter { (it.nombre.uppercase()).contains(valor.uppercase()) }
                l.forEach {
                    lista2.add(it)
                }
            })
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                var formato = DecimalFormat("#,###.###")
                var numero = 1200.345
                println(formato.format(numero).toString())
                GetDataApi(contexto)
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
                            text = "${lista2[l].nombre}"
                        )
                    }
                    Card(
                        elevation = 10.dp,
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Column() {
                            Text(
                                text = "und $${lista2[l].precio}",
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
                                    .height(25.dp),
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
        Greeting()
    }
}
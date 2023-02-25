package com.example.applistadeproductos

import android.content.Context
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings.Global
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
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.applistadeproductos.ui.theme.AppListaDeProductosTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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
                    Greeting()
                }
            }
        }
    }
}

data class productos(val codigo: String, val nombre: String, val dsp: Int, val precio: Int)


fun GetDataApi(
    contexto: Context,
    lista: MutableList<Producto> = mutableStateListOf(),
    lista2: MutableList<Producto> = mutableStateListOf()
) {


    val db = Room.databaseBuilder(
        contexto,
        AppDatabase::class.java, "database-product"
    ).build()

    val productDao = db.productDao()

    //productDao.insertAll(Producto("32", "david", 2, 2000))


    GlobalScope.launch(Dispatchers.IO) {
        lista.clear()
        lista2.clear()
        if (productDao.getAll().isEmpty()) {
            println("entro al api")

            val queue = Volley.newRequestQueue(contexto)
            val url = "https://www.paginadeprueba009.com/prueba_kotlin.php"
            val stringRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->
                    val json = response.getJSONArray("productos")
                    for (p in 0 until json.length()) {
                        var codigo: String = json.getJSONObject(p).getString("codigo")
                        var nombre: String = json.getJSONObject(p).getString("nombre")
                        var dsp: Int = json.getJSONObject(p).getInt("dsp")
                        var precio: Int = json.getJSONObject(p).getInt("precio")

                        lista.add(
                            Producto(
                                codigo,
                                nombre,
                                dsp,
                                precio
                            )
                        )
                    }

                    GlobalScope.launch(Dispatchers.IO) {

                        productDao.insertAll(lista)
                        lista.forEach {
                            lista2.add(it)
                        }

                    }

                },
                Response.ErrorListener {

                })


            // Add the request to the RequestQueue.
            queue.add(stringRequest)
        } else {
            lista2.clear()
            println("entro a la base de datos ")
            productDao.getAll().forEach {
                lista.add(it)
            }
            lista.forEach {
                lista2.add(it)
            }
        }


    }

    //val productos: List<Producto> = productDao.getAll()


}

fun llamar_datos() {
    GlobalScope.launch(Dispatchers.IO) {
        var lista3: MutableList<String> = mutableStateListOf("")

        lista3.clear()
    }

}

@Composable
fun DB(contexto: Context) {


}

fun l() {

}

@Composable
fun Greeting() {
    var lista2: MutableList<Producto> =
        mutableStateListOf()

    var lista: MutableList<Producto> by remember {
        mutableStateListOf()
    }


    var contexto = LocalContext.current
    GetDataApi(contexto, lista, lista2)

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
        var l: List<Producto>
        Spacer(modifier = Modifier.height(15.dp))
        TextField(

            value = producto, onValueChange = { valor ->
                lista2.clear()
                producto = valor
                l = lista.filter { (it.nombre.uppercase()).contains(valor.uppercase()) }
                l.forEach {
                    lista2.add(it)
                }
            },
            modifier = Modifier.height(50.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                //GetDataApi(contexto)
            },
            modifier = Modifier
                .height(60.dp)
                .padding(2.dp)
        ) {
            Text(text = "Actualizar Lista")
        }
        Spacer(modifier = Modifier.height(20.dp))
        vista(lista2)
    }
}

@Composable
fun texto(mensaje: String) {
    Text(text = mensaje)
}

@Composable
fun vista(lista2: MutableList<Producto>) {

    if (lista2.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(1f),
            contentAlignment = Alignment.Center
        ) {
            texto("cargando...")
        }
    } else {
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
                        modifier = Modifier
                            .padding(1.dp)
                            .height(80.dp)

                    ) {
                        Column() {

                            Column() {
                                Text(
                                    text = "codigo: ${lista2[l].codigo}",
                                    fontSize = 8.sp,
                                    modifier = Modifier.padding(start = 5.dp, top = 5.dp)
                                )
                                Text(
                                    modifier = Modifier
                                        .padding(1.dp)
                                        .fillMaxWidth(1f)
                                        .fillMaxHeight(.6f)
                                        .padding(5.dp)
                                        .horizontalScroll(rememberScrollState()),
                                    text = "${lista2[l].nombre}",
                                    fontSize = 18.sp
                                )

                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxHeight(1f)

                            ) {
                                var formato = DecimalFormat("#,###.###")
                                var precio_und_format = formato.format(lista2[l].precio).toString()
                                var precio_dsp_format =
                                    formato.format(lista2[l].precio * lista2[l].dsp).toString()
                                Text(
                                    text = "und $$precio_und_format",
                                    modifier = Modifier
                                        .fillMaxWidth(.5f)
                                        .fillMaxHeight(1f)
                                        .padding(top = 2.dp),
                                    textAlign = TextAlign.Center,
                                    fontSize = 15.sp

                                )
                                Spacer(modifier = Modifier.height(1.dp))
                                Text(
                                    text = "dsp $$precio_dsp_format",
                                    modifier = Modifier
                                        .fillMaxWidth(1f)
                                        .fillMaxHeight(1f)
                                        .padding(top = 2.dp),
                                    textAlign = TextAlign.Center,
                                    fontSize = 15.sp
                                )
                            }
                        }
                    }
                    Card(
                        elevation = 10.dp,
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Column() {

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
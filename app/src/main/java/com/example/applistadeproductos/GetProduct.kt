package com.example.applistadeproductos

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.platform.LocalContext
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

var listaa: MutableList<ProductData> = mutableStateListOf()

@Composable
fun get(): List<ProductData> {

    var cont = LocalContext.current
    val queue = Volley.newRequestQueue(cont)
    val url = "https://www.paginadeprueba009.com/prueba_kotlin.php"
    println("entro")
    // Request a string response from the provided URL.
    //println("entrooooooooooooooooo--------->222222222")
    val stringRequest = JsonObjectRequest(
        Request.Method.GET, url, null,
        Response.Listener { response ->

            val json = response.getJSONArray("productos")

            for (p in 0 until json.length()) {
                println(json.getJSONObject(p).get("nombre"))

                listaa.add(
                    ProductData(
                        1,
                        json.getJSONObject(p).get("nombre").toString(),
                        json.getJSONObject(p).get("precio").toString().toInt()
                    )
                )
            }

            /*listaa.forEach {
                lista2.add(it)
            }*/
            // Display the first 500 characters of the response string.
            //textView.text = "Response is: ${response.substring(0, 500)}"
        },
        Response.ErrorListener { })


    // Add the request to the RequestQueue.
    queue.add(stringRequest)

    return listaa
}
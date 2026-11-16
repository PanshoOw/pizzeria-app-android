package com.example.pizzeriaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pizzeriaapp.ui.theme.PizzeriaAppTheme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp

import com.example.pizzeriaapp.logic.procesarPedidoEjemplo

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PizzeriaAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Bienvenidos a la mejor pizzería de la ciudad",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    // Estado que almacena el resultado del pedido
    val resultado = remember {
        mutableStateOf("Presiona el botón para procesar un pedido.")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        // Mensaje de bienvenida
        Text(
            text = name
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para procesar el pedido
        Button(
            onClick = {
                resultado.value = procesarPedidoEjemplo()
            }
        ) {
            Text("Procesar pedido")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar resultado del procesamiento
        Text(
            text = resultado.value
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PizzeriaAppTheme {
        Greeting("Bienvenidos a la mejor pizzería de la ciudad")
    }
}
package com.example.pizzeriaapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun PantallaPrincipal(
    modifier: Modifier = Modifier,
    viewModel: PizzeriaViewModel = viewModel()
) {

    // =====================================
    // RESUMEN DINÁMICO
    // =====================================

    val resumen by viewModel.resumen.observeAsState()

    // =====================================
    // ESTADOS DEL FORMULARIO DE CLIENTES
    // =====================================

    var nombre by rememberSaveable {
        mutableStateOf("")
    }

    var contacto by rememberSaveable {
        mutableStateOf("")
    }

    var direccion by rememberSaveable {
        mutableStateOf("")
    }

    var correo by rememberSaveable {
        mutableStateOf("")
    }

    // Mensaje de error del registro de clientes.
    var mensaje by rememberSaveable {
        mutableStateOf("")
    }

    // =====================================
    // ESTADOS DEL FORMULARIO DE PEDIDOS
    // =====================================

    var correoPedido by rememberSaveable {
        mutableStateOf("")
    }

    var cantidadPedido by rememberSaveable {
        mutableStateOf("")
    }

    // Mensaje de error del registro de pedidos.
    var mensajePedido by rememberSaveable {
        mutableStateOf("")
    }

    // =====================================
    // ESTADO DE LAS CONFIRMACIONES
    // =====================================

    // Mensaje que aparecerá en el Snackbar.
    var confirmacionVisible by remember {
        mutableStateOf<String?>(null)
    }

    // Permite reiniciar el temporizador
    // cada vez que se realiza un registro.
    var numeroConfirmacion by remember {
        mutableIntStateOf(0)
    }

    // =====================================
    // COROUTINE: OCULTAR CONFIRMACIÓN
    // =====================================

    LaunchedEffect(numeroConfirmacion) {

        if (numeroConfirmacion > 0) {

            // Esperar 3,5 segundos sin bloquear la interfaz.
            delay(3500.milliseconds)

            // Ocultar el mensaje.
            confirmacionVisible = null
        }
    }

    // =====================================
    // CONTENEDOR PRINCIPAL
    // =====================================

    Box(
        modifier = modifier.fillMaxSize()
    ) {

        // =====================================
        // CONTENIDO CON DESPLAZAMIENTO
        // =====================================

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // =====================================
            // BIENVENIDA
            // =====================================

            Text(
                text = "Bienvenidos a la mejor pizzería de la ciudad",
                style = MaterialTheme.typography.headlineSmall
            )

            HorizontalDivider()

            // =====================================
            // RESUMEN DINÁMICO
            // =====================================

            Text(
                text = "Resumen de la pizzería",
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = "Clientes registrados: " +
                        "${resumen?.totalClientes ?: 0}"
            )

            Text(
                text = "Pedidos realizados: " +
                        "${resumen?.totalPedidos ?: 0}"
            )

            Text(
                text = "Último cliente: " +
                        (resumen?.ultimoCliente ?: "Sin clientes registrados")
            )

            HorizontalDivider()

            // =====================================
            // REGISTRO DE CLIENTES
            // =====================================

            Text(
                text = "Registrar cliente",
                style = MaterialTheme.typography.titleLarge
            )

            // Nombre completo
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Teléfono de contacto
            OutlinedTextField(
                value = contacto,
                onValueChange = { contacto = it },
                label = { Text("Contacto") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                ),
                modifier = Modifier.fillMaxWidth()
            )

            // Dirección del cliente
            OutlinedTextField(
                value = direccion,
                onValueChange = { direccion = it },
                label = { Text("Dirección") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Correo electrónico
            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo electrónico") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                modifier = Modifier.fillMaxWidth()
            )

            // =====================================
            // BOTÓN: REGISTRAR CLIENTE
            // =====================================

            Button(
                onClick = {

                    val resultado = viewModel.registrarCliente(
                        nombre = nombre,
                        contacto = contacto,
                        direccion = direccion,
                        correo = correo
                    )

                    if (resultado == null) {

                        // Eliminar errores anteriores.
                        mensaje = ""

                        // Mostrar confirmación.
                        confirmacionVisible =
                            "Cliente registrado correctamente."

                        // Reiniciar el temporizador.
                        numeroConfirmacion++

                        // Limpiar los campos.
                        nombre = ""
                        contacto = ""
                        direccion = ""
                        correo = ""

                    } else {

                        // Mostrar error de validación.
                        mensaje = resultado

                        // Ocultar cualquier confirmación anterior.
                        confirmacionVisible = null
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar cliente")
            }

            // =====================================
            // MENSAJE DE ERROR DEL CLIENTE
            // =====================================

            if (mensaje.isNotBlank()) {

                Text(
                    text = mensaje,
                    color = MaterialTheme.colorScheme.error
                )
            }

            HorizontalDivider()

            // =====================================
            // REGISTRO DE PEDIDOS
            // =====================================

            Text(
                text = "Registrar pedido",
                style = MaterialTheme.typography.titleLarge
            )

            // Correo del cliente registrado
            OutlinedTextField(
                value = correoPedido,
                onValueChange = { correoPedido = it },
                label = { Text("Correo del cliente") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                modifier = Modifier.fillMaxWidth()
            )

            // Cantidad de pizzas
            OutlinedTextField(
                value = cantidadPedido,
                onValueChange = { cantidadPedido = it },
                label = { Text("Cantidad de pizzas") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth()
            )

            // =====================================
            // BOTÓN: REGISTRAR PEDIDO
            // =====================================

            Button(
                onClick = {

                    val correoIngresado = correoPedido.trim()

                    val cantidad = cantidadPedido
                        .trim()
                        .toIntOrNull()

                    when {

                        // Comprobar que exista un correo.
                        correoIngresado.isBlank() -> {

                            mensajePedido =
                                "Debes ingresar el correo del cliente."

                            confirmacionVisible = null
                        }

                        // Comprobar que la cantidad sea numérica.
                        cantidad == null -> {

                            mensajePedido =
                                "Debes ingresar una cantidad numérica válida."

                            confirmacionVisible = null
                        }

                        else -> {

                            // Registrar el pedido mediante el ViewModel.
                            val resultado = viewModel.registrarPedido(
                                correoCliente = correoIngresado,
                                cantidad = cantidad
                            )

                            if (resultado == null) {

                                // Eliminar errores anteriores.
                                mensajePedido = ""

                                // Mostrar confirmación.
                                confirmacionVisible =
                                    "Pedido registrado correctamente."

                                // Reiniciar el temporizador.
                                numeroConfirmacion++

                                // Limpiar únicamente la cantidad.
                                // Conservamos el correo del cliente.
                                cantidadPedido = ""

                            } else {

                                // Mostrar el error correspondiente.
                                mensajePedido = resultado

                                // Ocultar cualquier confirmación anterior.
                                confirmacionVisible = null
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar pedido")
            }

            // =====================================
            // MENSAJE DE ERROR DEL PEDIDO
            // =====================================

            if (mensajePedido.isNotBlank()) {

                Text(
                    text = mensajePedido,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        // =====================================
        // SNACKBAR DE CONFIRMACIÓN
        // =====================================

        // Este componente permanece fuera de la Column.
        // Por lo tanto, no se desplaza con los formularios.

        confirmacionVisible?.let { confirmacion ->

            Snackbar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .imePadding()
                    .padding(16.dp)
            ) {

                Text(
                    text = confirmacion
                )
            }
        }
    }
}
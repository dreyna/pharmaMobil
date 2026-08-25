package pe.edu.upeu.pharmamobil.presentation.producto

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pe.edu.upeu.pharmamobil.domain.model.Producto

private const val MENSAJE_REGISTRO_EXITOSO = "Producto registrado correctamente."
private const val ERROR_NOMBRE = "El nombre es obligatorio."
private const val ERROR_PRECIO = "El precio debe ser numérico y mayor que cero."
private const val ERROR_STOCK = "El stock debe ser un entero mayor o igual a cero."

internal sealed interface ResultadoRegistroProducto {
    data class Exito(val producto: Producto) : ResultadoRegistroProducto
    data class Error(val mensaje: String) : ResultadoRegistroProducto
}

internal fun validarProductoRegistro(
    nombre: String,
    precio: String,
    stock: String,
): ResultadoRegistroProducto {
    if (nombre.isBlank()) {
        return ResultadoRegistroProducto.Error(ERROR_NOMBRE)
    }

    val precioNumerico = precio.toDoubleOrNull()
    if (precioNumerico == null || precioNumerico <= 0.0) {
        return ResultadoRegistroProducto.Error(ERROR_PRECIO)
    }

    val stockNumerico = stock.toIntOrNull()
    if (stockNumerico == null || stockNumerico < 0) {
        return ResultadoRegistroProducto.Error(ERROR_STOCK)
    }

    return ResultadoRegistroProducto.Exito(
        Producto(
            id = 1L,
            nombre = nombre.trim(),
            precio = precioNumerico,
            stock = stockNumerico,
        )
    )
}

@Composable
fun ProductoScreen() {
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    var productoRegistrado by remember { mutableStateOf<Producto?>(null) }
    var registroExitoso by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Registro de Producto",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )

        Text(
            text = "Complete los datos del producto para agregarlo al inventario.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = {
                nombre = it
                mensaje = ""
                productoRegistrado = null
            },
            label = { Text("Nombre") },
            placeholder = { Text("Ej. Paracetamol 500 mg") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )

        OutlinedTextField(
            value = precio,
            onValueChange = {
                precio = it
                mensaje = ""
                productoRegistrado = null
            },
            label = { Text("Precio") },
            placeholder = { Text("Ej. 8.50") },
            prefix = { Text("S/ ") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth(),
        )

        OutlinedTextField(
            value = stock,
            onValueChange = {
                stock = it
                mensaje = ""
                productoRegistrado = null
            },
            label = { Text("Stock") },
            placeholder = { Text("Ej. 100") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
        )

        Button(
            onClick = {
                when (val resultado = validarProductoRegistro(nombre, precio, stock)) {
                    is ResultadoRegistroProducto.Error -> {
                        mensaje = resultado.mensaje
                        productoRegistrado = null
                        registroExitoso = false
                    }

                    is ResultadoRegistroProducto.Exito -> {
                        mensaje = MENSAJE_REGISTRO_EXITOSO
                        productoRegistrado = resultado.producto
                        registroExitoso = true
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Registrar")
        }

        if (mensaje.isNotEmpty()) {
            Text(
                text = mensaje,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = if (registroExitoso) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.error
                },
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        productoRegistrado?.let { producto ->
            Text(
                text = "ID: ${producto.id} | ${producto.nombre} | " +
                    "S/ ${producto.precio} | Stock: ${producto.stock}",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pe.edu.upeu.pharmamobil.domain.model.Producto

private const val MENSAJE_REGISTRO_EXITOSO = "Producto registrado correctamente."
private const val ERROR_NOMBRE = "Nombre obligatorio"
private const val ERROR_PRECIO_NUMERICO = "Precio inválido"
private const val ERROR_PRECIO_POSITIVO = "El precio debe ser mayor a 0"
private const val ERROR_STOCK_ENTERO = "Stock debe ser un número entero"
private const val ERROR_STOCK_NEGATIVO = "Stock no puede ser negativo"

internal enum class CampoProducto {
    NOMBRE,
    PRECIO,
    STOCK,
}

internal sealed interface ResultadoRegistroProducto {
    data class Exito(val producto: Producto) : ResultadoRegistroProducto
    data class Error(
        val campo: CampoProducto,
        val mensaje: String,
    ) : ResultadoRegistroProducto
}

internal fun validarProductoRegistro(
    nombre: String,
    precio: String,
    stock: String,
): ResultadoRegistroProducto {
    val precioNumerico = precio.toDoubleOrNull()
    val stockNumerico = stock.toIntOrNull()

    when {
        nombre.isBlank() -> {
            return ResultadoRegistroProducto.Error(CampoProducto.NOMBRE, ERROR_NOMBRE)
        }

        precioNumerico == null -> {
            return ResultadoRegistroProducto.Error(
                CampoProducto.PRECIO,
                ERROR_PRECIO_NUMERICO,
            )
        }

        precioNumerico <= 0.0 -> {
            return ResultadoRegistroProducto.Error(
                CampoProducto.PRECIO,
                ERROR_PRECIO_POSITIVO,
            )
        }

        stockNumerico == null -> {
            return ResultadoRegistroProducto.Error(
                CampoProducto.STOCK,
                ERROR_STOCK_ENTERO,
            )
        }

        stockNumerico < 0 -> {
            return ResultadoRegistroProducto.Error(
                CampoProducto.STOCK,
                ERROR_STOCK_NEGATIVO,
            )
        }
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
    var nombre by rememberSaveable { mutableStateOf("") }
    var precio by rememberSaveable { mutableStateOf("") }
    var stock by rememberSaveable { mutableStateOf("") }
    var mensaje by rememberSaveable { mutableStateOf("") }
    var productoRegistrado by remember { mutableStateOf<Producto?>(null) }
    var registroExitoso by rememberSaveable { mutableStateOf(false) }
    var intentoRegistrar by rememberSaveable { mutableStateOf(false) }

    val errorActual = if (intentoRegistrar) {
        validarProductoRegistro(nombre, precio, stock) as? ResultadoRegistroProducto.Error
    } else {
        null
    }

    fun actualizarRetroalimentacion(
        nuevoNombre: String = nombre,
        nuevoPrecio: String = precio,
        nuevoStock: String = stock,
    ) {
        mensaje = if (intentoRegistrar) {
            when (
                val resultado = validarProductoRegistro(
                    nuevoNombre,
                    nuevoPrecio,
                    nuevoStock,
                )
            ) {
                is ResultadoRegistroProducto.Error -> resultado.mensaje
                is ResultadoRegistroProducto.Exito -> ""
            }
        } else {
            ""
        }
        productoRegistrado = null
        registroExitoso = false
    }

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
                actualizarRetroalimentacion(nuevoNombre = it)
            },
            label = { Text("Nombre") },
            placeholder = { Text("Ej. Paracetamol 500 mg") },
            isError = errorActual?.campo == CampoProducto.NOMBRE,
            supportingText = if (errorActual?.campo == CampoProducto.NOMBRE) {
                { Text(errorActual.mensaje) }
            } else {
                null
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )

        OutlinedTextField(
            value = precio,
            onValueChange = {
                precio = it
                actualizarRetroalimentacion(nuevoPrecio = it)
            },
            label = { Text("Precio") },
            placeholder = { Text("Ej. 8.50") },
            prefix = { Text("S/ ") },
            isError = errorActual?.campo == CampoProducto.PRECIO,
            supportingText = if (errorActual?.campo == CampoProducto.PRECIO) {
                { Text(errorActual.mensaje) }
            } else {
                null
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth(),
        )

        OutlinedTextField(
            value = stock,
            onValueChange = {
                stock = it
                actualizarRetroalimentacion(nuevoStock = it)
            },
            label = { Text("Stock") },
            placeholder = { Text("Ej. 100") },
            isError = errorActual?.campo == CampoProducto.STOCK,
            supportingText = if (errorActual?.campo == CampoProducto.STOCK) {
                { Text(errorActual.mensaje) }
            } else {
                null
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
        )

        Button(
            onClick = {
                intentoRegistrar = true
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
                        nombre = ""
                        precio = ""
                        stock = ""
                        intentoRegistrar = false
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

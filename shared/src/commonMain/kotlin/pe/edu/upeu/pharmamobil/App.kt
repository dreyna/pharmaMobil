package pe.edu.upeu.pharmamobil

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import pe.edu.upeu.pharmamobil.data.ProductoRepository
import pe.edu.upeu.pharmamobil.presentation.cliente.ClienteScreen
import pe.edu.upeu.pharmamobil.presentation.producto.ProductoScreen

private val TITULOS = listOf("Clientes", "Productos")

@Composable
fun App() {
    MaterialTheme {

        val productoRepository = remember { ProductoRepository() }

        var tabSeleccionado by rememberSaveable { mutableStateOf(0) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeContentPadding()
        ) {

            PrimaryTabRow(selectedTabIndex = tabSeleccionado) {
                TITULOS.forEachIndexed { indice, titulo ->
                    Tab(
                        selected = tabSeleccionado == indice,
                        onClick = { tabSeleccionado = indice },
                        text = { Text(titulo) }
                    )
                }
            }

            when (tabSeleccionado) {
                0 -> ClienteScreen()
                else -> ProductoScreen(
                    onRegistrar = { productoRepository.registrar(it) }
                )
            }
        }
    }
}

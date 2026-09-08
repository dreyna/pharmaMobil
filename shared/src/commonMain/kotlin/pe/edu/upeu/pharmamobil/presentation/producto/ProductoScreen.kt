package pe.edu.upeu.pharmamobil.presentation.producto

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import pe.edu.upeu.pharmamobil.domain.model.Producto
import pe.edu.upeu.pharmamobil.presentation.components.ValidatedTextField

@Composable
fun ProductoScreen(
    viewModel: ProductoViewModel,
    modifier: Modifier = Modifier
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text("PharmaMobil")
        Text("Registro de Producto")

        ValidatedTextField(
            value = uiState.formulario.nombre,
            onValueChange = viewModel::onNombreChange,
            label = "Nombre",
            error = uiState.formulario.nombreError,
            modifier = Modifier.fillMaxWidth()
        )

        ValidatedTextField(
            value = uiState.formulario.precio,
            onValueChange = viewModel::onPrecioChange,
            label = "Precio",
            error = uiState.formulario.precioError,
            modifier = Modifier.fillMaxWidth()
        )

        ValidatedTextField(
            value = uiState.formulario.stock,
            onValueChange = viewModel::onStockChange,
            label = "Stock",
            error = uiState.formulario.stockError,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = viewModel::registrar,
            enabled = !uiState.registrando,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (uiState.registrando) "Registrando..." else "Registrar")
        }

        uiState.mensajeExito?.let {
            Text(it)
        }

        HorizontalDivider()

        Text(
            text = "Inventario",
            style = MaterialTheme.typography.titleMedium
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {

            when (val fase = uiState.fase) {

                ProductoUiState.Fase.Cargando ->
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )

                ProductoUiState.Fase.SinProductos ->
                    Text(
                        text = "Aún no hay productos registrados",
                        modifier = Modifier.align(Alignment.Center)
                    )

                is ProductoUiState.Fase.ConProductos ->
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(
                            items = fase.productos,
                            key = { it.id }
                        ) { producto ->
                            ProductoItem(producto)
                        }
                    }

                is ProductoUiState.Fase.Error ->
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        Text(
                            text = fase.mensaje,
                            color = MaterialTheme.colorScheme.error
                        )

                        Button(onClick = viewModel::cargarProductos) {
                            Text("Reintentar")
                        }
                    }
            }
        }
    }
}

@Composable
private fun ProductoItem(
    producto: Producto
) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(12.dp)
        ) {

            Text(
                text = producto.nombre,
                style = MaterialTheme.typography.titleSmall
            )

            Text(
                text = "S/ ${producto.precio}  ·  stock ${producto.stock}",
                style = MaterialTheme.typography.bodyMedium
            )

            if (producto.requiereReposicion) {
                Text(
                    text = "Requiere reposición",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

package pe.edu.upeu.pharmamobil.presentation.cliente

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import pe.edu.upeu.pharmamobil.presentation.components.MensajeExito
import pe.edu.upeu.pharmamobil.presentation.components.ValidatedTextField

@Composable
fun ClienteScreen() {

    var nombre by remember {
        mutableStateOf("")
    }

    var correo by remember {
        mutableStateOf("")
    }

    var telefono by remember {
        mutableStateOf("")
    }

    var nombreError by remember {
        mutableStateOf<String?>(null)
    }

    var correoError by remember {
        mutableStateOf<String?>(null)
    }

    var telefonoError by remember {
        mutableStateOf<String?>(null)
    }

    var mensajeExito by remember {
        mutableStateOf<String?>(null)
    }

    fun validar(): Boolean {
        nombreError = ClienteValidator.validarNombre(nombre)
        correoError = ClienteValidator.validarCorreo(correo)
        telefonoError = ClienteValidator.validarTelefono(telefono)

        return nombreError == null && correoError == null && telefonoError == null
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    text = "Registrar cliente",
                    style = MaterialTheme.typography.titleMedium
                )

                ValidatedTextField(
                    value = nombre,
                    onValueChange = {
                        nombre = it
                        nombreError = null
                        mensajeExito = null
                    },
                    label = "Nombre",
                    error = nombreError,
                    leadingIcon = Icons.Default.Person,
                    modifier = Modifier.fillMaxWidth()
                )

                ValidatedTextField(
                    value = correo,
                    onValueChange = {
                        correo = it
                        correoError = null
                        mensajeExito = null
                    },
                    label = "Correo",
                    error = correoError,
                    leadingIcon = Icons.Default.Email,
                    keyboardType = KeyboardType.Email,
                    modifier = Modifier.fillMaxWidth()
                )

                ValidatedTextField(
                    value = telefono,
                    onValueChange = {
                        telefono = it
                        telefonoError = null
                        mensajeExito = null
                    },
                    label = "Teléfono",
                    error = telefonoError,
                    leadingIcon = Icons.Default.Phone,
                    ayuda = "Opcional, entre 6 y 9 dígitos",
                    keyboardType = KeyboardType.Phone,
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        mensajeExito = null
                        if (validar()) {
                            mensajeExito = "Cliente \"$nombre\" registrado correctamente"
                            nombre = ""
                            correo = ""
                            telefono = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Registrar")
                }
            }
        }

        mensajeExito?.let {
            MensajeExito(it)
        }
    }
}

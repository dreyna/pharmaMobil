# Preguntas de reflexión - Actividad autónoma sesión 3

## 1. ¿Por qué precio y stock se mantienen inicialmente como `String`?

Porque `OutlinedTextField` recibe y entrega texto mientras la persona escribe. Mantener ambos valores como `String` permite representar estados intermedios válidos de la edición, como un campo vacío o un punto decimal, sin provocar excepciones. La conversión al tipo numérico se realiza únicamente al validar.

## 2. ¿Qué ventaja ofrecen `toDoubleOrNull()` y `toIntOrNull()` frente a las conversiones directas?

Devuelven `null` cuando el texto no puede convertirse y evitan que la aplicación se cierre por una excepción. Así la pantalla puede detectar el problema y mostrar un mensaje claro. En cambio, `toDouble()` y `toInt()` lanzan una excepción si reciben datos como `abc`.

## 3. ¿Qué relación existe entre el cambio de estado y la recomposición?

Los valores creados con `mutableStateOf` son observables. Cuando cambia `nombre`, `precio`, `stock`, `mensaje` o `intentoRegistrar`, Compose vuelve a ejecutar los componentes que leen ese estado y actualiza solo la parte necesaria de la interfaz. Por eso los errores aparecen inmediatamente después del intento de registro y desaparecen o cambian al corregir los campos.

## 4. ¿Por qué se construye `Producto` únicamente después de validar?

Porque el objeto representa datos coherentes del dominio. Crearlo después de validar garantiza que el nombre no esté vacío, que el precio sea numérico y positivo, y que el stock sea un entero no negativo. De esta forma ningún producto inválido llega al inventario.

## 5. ¿Qué parte de la lógica se trasladará próximamente a un ViewModel?

El estado del formulario, las reglas de validación, el mensaje de retroalimentación y la acción de registro pasarán a un `ProductoViewModel`. `ProductoScreen` se limitará a mostrar un `ProductoUiState` y a enviar eventos, separando la lógica de presentación de los componentes visuales.

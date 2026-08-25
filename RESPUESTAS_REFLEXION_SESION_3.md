# Preguntas de reflexión - Sesión 3

## 1. ¿Por qué Compose utiliza programación declarativa?

Porque la interfaz se describe en función del estado actual. En lugar de indicar paso a paso cómo modificar cada control, se declara cómo debe verse la pantalla y Compose vuelve a dibujar únicamente lo necesario cuando cambian los datos.

## 2. ¿Qué función cumplen `remember` y `mutableStateOf`?

`mutableStateOf` crea un estado observable. `remember` conserva ese estado entre recomposiciones mientras el componente permanece en la composición. En `ProductoScreen` se usan para mantener el nombre, el precio, el stock y el mensaje que ve el usuario.

## 3. ¿Cómo se actualiza una interfaz cuando cambia el estado?

Al asignar un nuevo valor a un estado observable, Compose identifica los componentes que lo leen y los recompone. Por ejemplo, cuando cambia `mensaje`, la pantalla muestra inmediatamente la confirmación o el error correspondiente.

## 4. ¿Por qué deben validarse los datos antes de crear un objeto `Producto`?

La validación evita que el dominio reciba información incompleta o inválida. Así se impide registrar productos sin nombre, precios no numéricos o menores o iguales a cero, y cantidades de stock negativas o que no sean enteras.

## 5. ¿Cómo podría mejorarse la solución aplicando MVVM?

Se podría mover el estado y la validación a un `ProductoViewModel`. La pantalla solo observaría un `ProductoUiState` y enviaría eventos, mientras el ViewModel ejecutaría la lógica de registro. Esto facilitaría las pruebas, conservaría el estado ante cambios de configuración y separaría la interfaz de la lógica de negocio.

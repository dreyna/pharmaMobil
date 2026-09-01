# PharmaMobil - actividad práctica de dominio KMP

Proyecto Kotlin Multiplatform para Android e iOS, adaptado a la estructura desarrollada en clase.

## Sesión 4 - Navegación y estructura visual

La aplicación integra los requisitos del Reto 01 de la Sesión 4:

- cuatro destinos tipados: Inicio, Productos, Clientes y Pedidos;
- `Scaffold` y `TopAppBar` con título dinámico;
- `ModalNavigationDrawer` en teléfonos, `NavigationRail` en tablets y
  `PermanentNavigationDrawer` en pantallas amplias;
- reutilización directa de `ProductoScreen()` sin duplicar el formulario;
- conservación del estado escrito al cambiar temporalmente de destino;
- tema corporativo Material 3 con cambio dinámico entre modo claro y oscuro;
- mensajes de validación alineados con los ocho casos de la guía;
- pruebas de los destinos, identificadores y reglas del formulario.

La implementación de la Sesión 4 se encuentra en la rama `sesion-4`.

## Sesión 3 - Registro de productos con Compose

La pantalla principal implementa la actividad práctica de la guía:

- `ProductoScreen()` en el paquete `presentation.producto`;
- campos Nombre, Precio y Stock mediante `OutlinedTextField`;
- estados locales con `remember` y `mutableStateOf`;
- botón Registrar y mensaje de resultado;
- validación secuencial de nombre, conversión y rango de precio, y conversión y rango de stock;
- creación de `Producto(id = 1L, nombre, precio, stock)` cuando los datos son válidos;
- retroalimentación visual con `isError` y mensajes junto a cada campo;
- limpieza automática del formulario después de un registro correcto;
- siete pruebas automatizadas que cubren exactamente los casos de la guía.

La entrega de la actividad autónoma está documentada en
[`ACTIVIDAD_AUTONOMA_SESION_3.md`](./ACTIVIDAD_AUTONOMA_SESION_3.md). Sus cinco capturas obligatorias se encuentran en
[`evidencias/actividad-autonoma-sesion3`](./evidencias/actividad-autonoma-sesion3). Las respuestas de reflexión están en
[`RESPUESTAS_REFLEXION_SESION_3.md`](./RESPUESTAS_REFLEXION_SESION_3.md).

## Actividad implementada

- `Cliente` con null-safety y `obtenerTelefono()` usando el operador Elvis.
- `Producto` inmutable y actualización de stock mediante `copy()`.
- Consultas de colecciones con `filter`, `map` y `find`.
- `Pedido`, `DetallePedido` y estados modelados con `sealed class`.
- Repositorio asíncrono con función `suspend` y emisiones reactivas mediante `Flow`.
- Caso de uso para observar productos sin acoplar la UI al repositorio.
- Seis pruebas del dominio compartido en `commonTest`.

## Actividad autónoma

Se añadió el procesamiento reactivo de pedidos e inventario como ampliación autónoma de la sesión:

- validación de existencia y disponibilidad de productos;
- suma de cantidades repetidas antes de descontar stock;
- resultados de negocio exhaustivos mediante `sealed class`;
- actualización inmutable y segura del inventario con `Mutex`;
- observación del inventario mediante `StateFlow`;
- seis pruebas adicionales de reglas de negocio y flujo reactivo.

La explicación, las reglas y los casos comprobados están en
[ACTIVIDAD_AUTONOMA.md](./ACTIVIDAD_AUTONOMA.md).

## Estructura original del proyecto

* [/iosApp](./iosApp/iosApp) contains an iOS application. Even if you’re sharing your UI with Compose Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

* [/shared](./shared/src) is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - [commonMain](./shared/src/commonMain/kotlin) is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    the [iosMain](./shared/src/iosMain/kotlin) folder would be the right place for such calls.
    Similarly, if you want to edit the Desktop (JVM) specific part, the [jvmMain](./shared/src/jvmMain/kotlin)
    folder is the appropriate location.

### Running the apps

Use the run configurations provided by the run widget in your IDE's toolbar. You can also use these commands and options:

- Android app: `./gradlew :androidApp:assembleDebug`
- iOS app: open the [/iosApp](./iosApp) directory in Xcode and run it from there.

### Running tests

Use the run button in your IDE's editor gutter, or run tests using Gradle tasks:

- Android tests: `./gradlew :shared:testAndroidHostTest`
- iOS tests: `./gradlew :shared:iosSimulatorArm64Test`

En Windows, valida toda la actividad compartida con:

```powershell
.\gradlew.bat :shared:testAndroidHostTest
.\gradlew.bat :androidApp:assembleDebug
```

El APK de depuración se genera en:

`androidApp/build/outputs/apk/debug/androidApp-debug.apk`

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…

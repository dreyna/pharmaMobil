# Actividad autónoma sesión 3

## Reto 02: Gestión del Estado, Validación y Retroalimentación Visual

Se refinó el formulario de productos de PharmaMobil en Compose Multiplatform. La implementación mantiene los datos de entrada como `String`, conserva el estado con `remember` y `mutableStateOf`, usa conversiones seguras y crea el objeto `Producto` únicamente cuando todas las reglas se cumplen.

## Reglas implementadas

| Campo | Validación | Mensaje |
|---|---|---|
| Nombre | `isNotBlank()` | El nombre es obligatorio. |
| Precio | `toDoubleOrNull()` | Ingrese un precio numérico. |
| Precio | Mayor que `0.0` | El precio debe ser mayor que cero. |
| Stock | `toIntOrNull()` | Ingrese un stock entero. |
| Stock | Mayor o igual que `0` | El stock no puede ser negativo. |

Los mensajes no se muestran antes de pulsar **Registrar**. Después del primer intento, la validación se actualiza al editar los campos. Cada `OutlinedTextField` usa `isError` y `supportingText`. Cuando el registro es correcto se construye `Producto`, se confirma la operación y se limpian Nombre, Precio y Stock.

## Siete casos comprobados

| N.º | Nombre | Precio | Stock | Resultado esperado |
|---:|---|---:|---:|---|
| 1 | Paracetamol 500 mg | 8.50 | 100 | Registro correcto |
| 2 | Vacío | 8.50 | 100 | El nombre es obligatorio. |
| 3 | Ibuprofeno | abc | 50 | Ingrese un precio numérico. |
| 4 | Ibuprofeno | 0 | 50 | El precio debe ser mayor que cero. |
| 5 | Amoxicilina | 18.50 | abc | Ingrese un stock entero. |
| 6 | Amoxicilina | 18.50 | -5 | El stock no puede ser negativo. |
| 7 | Loratadina | 10 | 0 | Registro correcto con stock cero |

Todos estos casos están automatizados en `ProductoScreenTest.kt`.

## Archivos de la entrega

- `shared/src/commonMain/kotlin/pe/edu/upeu/pharmamobil/presentation/producto/ProductoScreen.kt`
- `shared/src/commonTest/kotlin/pe/edu/upeu/pharmamobil/presentation/producto/ProductoScreenTest.kt`
- `RESPUESTAS_REFLEXION_SESION_3.md`
- `evidencias/actividad-autonoma-sesion3/`

## Verificación en Android Studio

1. Abrir PharmaMobil.
2. Sincronizar el proyecto con Gradle.
3. Ejecutar `androidApp` en un emulador Android.
4. Probar los siete casos de la tabla.
5. Ejecutar `ProductoScreenTest` o la tarea `:shared:testAndroidHostTest`.

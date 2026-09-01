# PharmaMobil - Sesión 4

## Resultado implementado

PharmaMobil pasó de mostrar únicamente el formulario de productos a una
aplicación navegable con una identidad visual común. El destino activo se modela
mediante `Screen`, una clase sellada con cuatro opciones: Inicio, Productos,
Clientes y Pedidos.

La estructura principal usa `Scaffold` y una `TopAppBar` cuyo título cambia con
el destino. La navegación se adapta al ancho disponible:

- teléfono: `ModalNavigationDrawer`;
- tablet: `NavigationRail`;
- escritorio o pantalla amplia: `PermanentNavigationDrawer`.

`ProductoScreen()` se reutiliza directamente. Sus datos escritos se conservan al
cambiar de pantalla y volver, y las validaciones cubren los ocho casos indicados
en la guía. El tema Material 3 dispone de paletas clara y oscura y puede alternarse
desde la barra superior o desde el menú lateral.

## Lista de cotejo

- [x] El proyecto conserva los componentes de la Sesión 3.
- [x] Existe el destino Inicio.
- [x] Existe el destino Productos.
- [x] Existe el destino Clientes.
- [x] Existe el destino Pedidos.
- [x] La navegación cambia el contenido y el título correctamente.
- [x] Se implementan `Scaffold` y `TopAppBar`.
- [x] Se implementa un Navigation Drawer funcional.
- [x] Productos reutiliza estrictamente `ProductoScreen()`.
- [x] Se aplica Material 3.
- [x] Se configuran Light Theme y Dark Theme.

El inicio correcto y el comportamiento visual deben confirmarse en un emulador o
dispositivo Android antes de marcar la evidencia final de ejecución.

## Reflexión

1. Varias pantallas aisladas no comparten un flujo de uso. Una aplicación
   navegable mantiene un destino actual y permite cambiar entre módulos dentro de
   una estructura coherente.
2. `Scaffold` organiza las regiones visuales de la interfaz; la navegación decide
   qué destino se muestra. Separar ambas responsabilidades simplifica el código.
3. Reutilizar `ProductoScreen()` evita dos formularios con comportamientos
   distintos, reduce errores y mantiene una sola fuente para las validaciones.
4. `MaterialTheme` centraliza colores y tipografía para que todas las pantallas
   mantengan contraste, jerarquía e identidad visual en modo claro y oscuro.

package pe.edu.upeu.pharmamobil.presentation.producto

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class ProductoScreenTest {

    @Test
    fun registraParacetamolCuandoLosDatosSonValidos() {
        val resultado = validarProductoRegistro(
            nombre = "Paracetamol 500 mg",
            precio = "8.50",
            stock = "100",
        )

        val exito = assertIs<ResultadoRegistroProducto.Exito>(resultado)
        assertEquals(1L, exito.producto.id)
        assertEquals("Paracetamol 500 mg", exito.producto.nombre)
        assertEquals(8.50, exito.producto.precio)
        assertEquals(100, exito.producto.stock)
    }

    @Test
    fun rechazaElNombreVacio() {
        val resultado = validarProductoRegistro("", "8.50", "100")

        val error = assertIs<ResultadoRegistroProducto.Error>(resultado)
        assertEquals(CampoProducto.NOMBRE, error.campo)
        assertEquals("Nombre obligatorio", error.mensaje)
    }

    @Test
    fun rechazaElPrecioConTexto() {
        val resultado = validarProductoRegistro("Ibuprofeno", "abc", "50")

        val error = assertIs<ResultadoRegistroProducto.Error>(resultado)
        assertEquals(CampoProducto.PRECIO, error.campo)
        assertEquals("Precio inválido", error.mensaje)
    }

    @Test
    fun rechazaElPrecioIgualACero() {
        val resultado = validarProductoRegistro("Ibuprofeno", "0", "50")

        val error = assertIs<ResultadoRegistroProducto.Error>(resultado)
        assertEquals(CampoProducto.PRECIO, error.campo)
        assertEquals("El precio debe ser mayor a 0", error.mensaje)
    }

    @Test
    fun rechazaElStockConTexto() {
        val resultado = validarProductoRegistro("Amoxicilina", "18.50", "abc")

        val error = assertIs<ResultadoRegistroProducto.Error>(resultado)
        assertEquals(CampoProducto.STOCK, error.campo)
        assertEquals("Stock debe ser un número entero", error.mensaje)
    }

    @Test
    fun rechazaElStockNegativo() {
        val resultado = validarProductoRegistro("Amoxicilina", "18.50", "-5")

        val error = assertIs<ResultadoRegistroProducto.Error>(resultado)
        assertEquals(CampoProducto.STOCK, error.campo)
        assertEquals("Stock no puede ser negativo", error.mensaje)
    }

    @Test
    fun permiteStockIgualACero() {
        val resultado = validarProductoRegistro("Loratadina", "10", "0")

        val exito = assertIs<ResultadoRegistroProducto.Exito>(resultado)
        assertEquals("Loratadina", exito.producto.nombre)
        assertEquals(10.0, exito.producto.precio)
        assertEquals(0, exito.producto.stock)
    }
}

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
        assertEquals("El nombre es obligatorio.", error.mensaje)
    }

    @Test
    fun rechazaElPrecioConTexto() {
        val resultado = validarProductoRegistro("Paracetamol", "abc", "100")

        val error = assertIs<ResultadoRegistroProducto.Error>(resultado)
        assertEquals("El precio debe ser numérico y mayor que cero.", error.mensaje)
    }

    @Test
    fun rechazaElStockNegativo() {
        val resultado = validarProductoRegistro("Paracetamol", "8.50", "-5")

        val error = assertIs<ResultadoRegistroProducto.Error>(resultado)
        assertEquals("El stock debe ser un entero mayor o igual a cero.", error.mensaje)
    }
}

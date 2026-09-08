package pe.edu.upeu.pharmamobil.domain.repository

import pe.edu.upeu.pharmamobil.domain.model.Producto

/**
 * Puerto del dominio hacia el inventario de productos. El dominio solo pide
 * "registrar" y "listar"; quien guarde los datos (memoria hoy, REST manana)
 * queda del otro lado de esta interfaz.
 */
interface ProductoRepository {

    /** Incorpora el producto al inventario y devuelve el producto ya identificado. */
    suspend fun registrar(producto: Producto): Producto

    /** Entrega el inventario completo en el orden en que fue registrado. */
    suspend fun listar(): List<Producto>
}

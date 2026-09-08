package pe.edu.upeu.pharmamobil.di

import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import pe.edu.upeu.pharmamobil.data.repository.ProductoRepositorioEnMemoria
import pe.edu.upeu.pharmamobil.domain.repository.ProductoRepository
import pe.edu.upeu.pharmamobil.domain.usecase.RegistrarProductoUseCase
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertIs
import kotlin.test.assertSame

/**
 * Comprueba que el grafo se ensambla sin arrancar la aplicacion: si una
 * definicion falta o esta declarada con el tipo equivocado, falla aqui.
 */
class AppModuleTest {

    @AfterTest
    fun detenerKoin() {
        stopKoin()
    }

    @Test
    fun resuelveElRepositorioPorSuInterfazDeDominio() {

        val koin = startKoin {
            modules(dataModule, domainModule, platformModule)
        }.koin

        assertIs<ProductoRepositorioEnMemoria>(koin.get<ProductoRepository>())
    }

    @Test
    fun elRepositorioEsUnicoEnTodaLaAplicacion() {

        val koin = startKoin {
            modules(dataModule, domainModule, platformModule)
        }.koin

        assertSame(
            koin.get<ProductoRepository>(),
            koin.get<ProductoRepository>()
        )
    }

    @Test
    fun resuelveElCasoDeUsoConSuRepositorio() {

        val koin = startKoin {
            modules(dataModule, domainModule, platformModule)
        }.koin

        koin.get<RegistrarProductoUseCase>()
    }
}

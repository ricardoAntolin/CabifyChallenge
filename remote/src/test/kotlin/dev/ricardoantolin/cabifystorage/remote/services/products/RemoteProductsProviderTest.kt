package dev.ricardoantolin.cabifystorage.remote.services.products

import dev.ricardoantolin.cabifystorage.data.entities.ProductsEntity
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import retrofit2.HttpException
import retrofit2.Response

class RemoteProductsProviderTest {
    lateinit var mockWebServer: MockWebServer
    lateinit var remoteProductsProvider: RemoteProductsProvider

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        remoteProductsProvider = RemoteProductsProvider(mockWebServer.url("/").toString(), true)
    }

    @Test
    fun should_response_list_of_3_products() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(
                    "{\"products\":[{\"code\":\"VOUCHER\",\"name\":\"Cabify Voucher\",\"price\":5}," +
                            "{\"code\":\"TSHIRT\",\"name\":\"Cabify T-Shirt\",\"price\":20}," +
                            "{\"code\":\"MUG\",\"name\":\"Cabify Coffee Mug\",\"price\":7.5}]}"
                )
        )

        val expectedProductList = listOf(
            ProductsEntity("VOUCHER", "Cabify Voucher", 5f),
            ProductsEntity("TSHIRT", "Cabify T-Shirt", 20f),
            ProductsEntity("MUG", "Cabify Coffee Mug", 7.5f)
        )

        remoteProductsProvider.fetchProducts()
            .test()
            .assertNoErrors()
            .assertValue { it == expectedProductList }
            .assertComplete()
    }

    @Test
    fun should_response_empty_list_of_products() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody("{\"products\":[]}")
        )

        remoteProductsProvider.fetchProducts()
            .test()
            .assertNoErrors()
            .assertValue { it.isEmpty() }
            .assertComplete()
    }

    @Test
    fun should_response_bad_request() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(400)
                .setBody("Bad Request")
        )

        remoteProductsProvider.fetchProducts()
            .test()
            .assertError {(it as? HttpException)?.checkError(400,"Bad Request") ?: false }
    }

    @Test
    fun should_response_server_error() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(500)
                .setBody("Server Error")
        )

        remoteProductsProvider.fetchProducts()
            .test()
            .assertError { (it as? HttpException)?.checkError(500,"Server Error") ?: false }
    }

    private fun HttpException.checkError(code: Int, message: String): Boolean =
        code() == code && response().errorBody()?.string() == message


    @After
    @Throws(Exception::class)
    fun tearDown() {
        mockWebServer.shutdown()
    }
}
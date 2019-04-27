package dev.ricardoantolin.cabifystore.remote.services.products

import dev.ricardoantolin.cabifystore.data.entities.ProductEntity
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

import retrofit2.HttpException

class RemoteProductsProviderTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var remoteProductsProvider: RemoteProductsProvider

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
            ProductEntity("VOUCHER", "Cabify Voucher", 5f),
            ProductEntity("TSHIRT", "Cabify T-Shirt", 20f),
            ProductEntity("MUG", "Cabify Coffee Mug", 7.5f)
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
package diy.joins

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.core.await
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer

@DataR2dbcTest
abstract class DatabaseIntegrationTest {

    @Autowired
    lateinit var databaseClient: DatabaseClient

    @AfterEach
    fun afterTest() {
        runBlocking {
            databaseClient.execute("truncate tree, fruit").await()
        }
    }

    companion object {
        private val postgresqlContainer by lazy {
            PostgreSQLContainer<Nothing>("postgres:11").apply {
                withUsername("postgres")
                withPassword("password")
            }
        }

        @DynamicPropertySource
        @JvmStatic
        fun postgresqlProperties(registry: DynamicPropertyRegistry) {
            postgresqlContainer.start()

            registry.apply {
                with(postgresqlContainer) {
                    add("spring.r2dbc.url") { jdbcUrl.replaceFirst("jdbc", "r2dbc") }

                    add("spring.flyway.url") { jdbcUrl }
                }
            }
        }
    }
}
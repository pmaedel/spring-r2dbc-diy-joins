package diy.joins

import io.r2dbc.spi.Row
import org.springframework.data.r2dbc.convert.R2dbcConverter
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.core.ReactiveDataAccessStrategy
import org.springframework.data.r2dbc.core.awaitFirstOrNull

class FruitRepositoryCustomizationImpl(
    reactiveDataAccessStrategy: ReactiveDataAccessStrategy,
    private val databaseClient: DatabaseClient
) : FruitRepositoryCustomization {

    private val converter = reactiveDataAccessStrategy.converter

    override suspend fun findById(id: Long): Fruit? = error("This will never happen")

    override suspend fun findByIdJoined(id: Long): Fruit? {

        return databaseClient.execute("select f.*, t.id as t_id, t.kind as t_kind from fruit f left join tree t on t.id = f.tree_id where f.id = :id")
            .bind("id", id)
            .map { row, rowMetaData ->
                val fruitEntity = converter.read(Fruit::class.java, row, rowMetaData)
                val joinedTreeEntity = converter.read<Tree>(row, "t_")
                fruitEntity.copy(joinedTree = joinedTreeEntity)
            }.awaitFirstOrNull()
    }

    private inline fun <reified T> R2dbcConverter.read(row: Row, aliasPrefix: String): T = read(T::class.java,
        object : Row {
            override fun <T : Any?> get(index: Int, type: Class<T>) = row[index, type]
            override fun <T : Any?> get(name: String, type: Class<T>) = row["$aliasPrefix$name", type]
        }
    )
}
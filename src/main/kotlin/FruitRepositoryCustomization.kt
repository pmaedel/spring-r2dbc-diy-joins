package diy.joins

interface FruitRepositoryCustomization {

    suspend fun findById(id: Long): Fruit?

    suspend fun findByIdJoined(id: Long): Fruit?
}
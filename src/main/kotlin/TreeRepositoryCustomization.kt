package diy.joins

interface TreeRepositoryCustomization<ID> {

    suspend fun findById(id: ID): Tree?
}
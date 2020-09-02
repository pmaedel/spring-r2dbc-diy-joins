package diy.joins

class TreeRepositoryCustomizationImpl : TreeRepositoryCustomization<Long> {

    override suspend fun findById(id: Long): Tree? = error("as expected")
}
package diy.joins

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor

data class Fruit(
    @Id
    val id: Long? = null,
    val treeId: Long,
    val ripeness: Ripeness,
    @Transient
    val joinedTree: Tree? = null
) {

    @PersistenceConstructor
    constructor(
        id: Long?,
        treeId: Long,
        ripeness: Ripeness
    ) : this(
        id = id,
        treeId = treeId,
        ripeness = ripeness,
        joinedTree = null
    )
}

enum class Ripeness {
    UNRIPE,
    RIPE,
    OVERRIPE
}
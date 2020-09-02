package diy.joins

import org.springframework.data.annotation.Id

data class Tree(
    @Id
    val id: Long? = null,
    val kind: Kind
)

enum class Kind {
    APPLE,
    ORANGE,
    PEAR
}
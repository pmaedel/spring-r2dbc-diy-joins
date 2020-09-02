package diy.joins

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired

class RepositoryTest(
    @Autowired private val treeRepository: TreeRepository,
    @Autowired private val fruitRepository: FruitRepository
) : DatabaseIntegrationTest() {

    @Test
    fun `find by id with joined tree`() = runBlocking {
        val insertedAppleTree = treeRepository.save(Tree(kind = Kind.APPLE))

        val insertedApple = fruitRepository.save(Fruit(treeId = insertedAppleTree.id!!, ripeness = Ripeness.RIPE))

        val appleWithJoinedTree = fruitRepository.findByIdJoined(insertedApple.id!!)!!

        assertEquals(insertedApple.copy(joinedTree = insertedAppleTree), appleWithJoinedTree)
    }

    @Test
    fun `override precedence`() {

        val exception = assertThrows<IllegalStateException> { runBlocking { treeRepository.findById(123L) } }
        assertEquals("as expected", exception.message)

        assertNull(runBlocking { fruitRepository.findById(123L) }) //not as expected
    }
}
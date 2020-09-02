package diy.joins

import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface TreeRepository : CoroutineCrudRepository<Tree, Long>, TreeRepositoryCustomization<Long>
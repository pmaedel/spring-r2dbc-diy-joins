package diy.joins

import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface FruitRepository : CoroutineCrudRepository<Fruit, Long>, FruitRepositoryCustomization
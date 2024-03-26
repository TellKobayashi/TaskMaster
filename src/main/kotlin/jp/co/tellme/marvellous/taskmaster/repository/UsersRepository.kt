package jp.co.tellme.marvellous.taskmaster.repository

import jp.co.tellme.marvellous.taskmaster.entity.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
// import org.springframework.data.jpa.repository.Query

interface UsersRepository : JpaRepository<Users, Int>, JpaSpecificationExecutor<Users> {
    fun findByName(name: String): Users?

}
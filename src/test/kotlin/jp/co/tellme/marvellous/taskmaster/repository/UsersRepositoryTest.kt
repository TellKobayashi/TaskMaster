package jp.co.tellme.marvellous.taskmaster.repository

import com.github.springtestdbunit.annotation.DatabaseOperation
import com.github.springtestdbunit.annotation.DatabaseSetup
import com.github.springtestdbunit.annotation.DatabaseSetups
import jp.co.tellme.marvellous.taskmaster.entity.Users
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException

@DatabaseSetups (
    DatabaseSetup("${RepositoryTestBase.baseTestDataFilePath}truncateAllTable.xml", type = DatabaseOperation.TRUNCATE_TABLE),
    DatabaseSetup("${RepositoryTestBase.baseTestDataFilePath}UserRepositoryTest.xml")
)
class UsersRepositoryTest : RepositoryTestBase() {

    // DIコンテナから注入している。
    @Autowired
    private lateinit var usersRepository: UsersRepository

    @Test
    fun save_正常系() {
        val user = Users(
            name = "test1111",
            displayName = "test1111",
            password = "Test1111"
        )
        // Arguments: junitでのパラメータのラッパー、arrayライク
        // Arguments.arguments()

        val result = usersRepository.save(user)

        assertEquals(result, user)
    }

    @Test
    fun save_ユーザ名がすでに存在する時() {
        val user = Users(
            name = "test1",
            displayName = "test1111",
            password = "Test1111"
        )
        // Arguments: junitでのパラメータのラッパー、arrayライク
        // Arguments.arguments()

        assertThrows<DataIntegrityViolationException> { usersRepository.save(user) }
    }
}
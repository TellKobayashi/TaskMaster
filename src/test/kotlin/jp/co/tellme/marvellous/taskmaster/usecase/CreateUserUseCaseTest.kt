package jp.co.tellme.marvellous.taskmaster.usecase

import jp.co.tellme.marvellous.taskmaster.entity.Users
import jp.co.tellme.marvellous.taskmaster.model.request.user.CreateUserRequest
import jp.co.tellme.marvellous.taskmaster.repository.UsersRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.dao.DataIntegrityViolationException

@ExtendWith(MockitoExtension::class)
class CreateUserUseCaseTest {
    @Mock
    private lateinit var usersRepository: UsersRepository

    @InjectMocks
    private lateinit var createUserUseCase: CreateUserUseCase

    @Nested
    inner class CreateUser {
        @Test
        fun 正常系() {
            val request = CreateUserRequest(
                "test",
                "test_1234",
                "Test1234"
            )
            val user = Users(
                name = request.userName,
                displayName = request.displayName,
                password = request.password
            )

            Mockito.`when`(usersRepository.save( user )).thenReturn(
                user
            )

            val result = createUserUseCase.createUser(request)

            assertEquals(result.displayName, request.displayName)
            assertEquals(result.userName, request.userName)
            assertEquals(result.password, "********")
        }

        @Test
        fun 異常系_ユーザ名がすでに存在した場合() {
            val request = CreateUserRequest(
                "test",
                "test1",
                "test1234"
            )
            val user = Users(
                name = request.userName,
                displayName = request.displayName,
                password = request.password,
            )

            Mockito.`when`(usersRepository.save(user)).thenThrow(DataIntegrityViolationException("msg"))

            val exception = assertThrows<DataIntegrityViolationException> {
                createUserUseCase.createUser(request)
            }

            assertEquals(exception.message, "msg")
        }
    }
}
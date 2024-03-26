package jp.co.tellme.marvellous.taskmaster.usecase

import jp.co.tellme.marvellous.taskmaster.entity.Users
import jp.co.tellme.marvellous.taskmaster.model.request.user.CreateUserRequest
import jp.co.tellme.marvellous.taskmaster.model.response.UserResponse
import jp.co.tellme.marvellous.taskmaster.repository.UsersRepository
import org.apache.coyote.BadRequestException
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException.BadRequest

@Component
class CreateUserUseCase(
    // 副作用防止のためにprivateにしている
    private val usersRepository: UsersRepository
) {
    fun createUser(request: CreateUserRequest): UserResponse {
        val user = Users(
            name = request.userName,
            displayName = request.displayName,
            password = request.password
        )
        // userNameが固有キーなので、重複したuserNameが存在する場合エラーになる。
        // エラーはDataIntegrityViolationException --> ハンドリングで400にしている。
        // できれば重複時のエラーメッセージは固有のものにしたいが、try catchはよくないと聞くのでどう作ればいいかわからない。
        val createdUser = usersRepository.save( user )
        return UserResponse(
            displayName = createdUser.displayName!!,
            userName = createdUser.name!!
        )
    }
}
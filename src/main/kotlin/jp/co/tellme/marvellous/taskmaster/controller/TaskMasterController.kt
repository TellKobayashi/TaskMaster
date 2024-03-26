package jp.co.tellme.marvellous.taskmaster.controller

import jakarta.validation.Valid
import jp.co.tellme.marvellous.taskmaster.entity.Users
import jp.co.tellme.marvellous.taskmaster.model.request.user.CreateUserRequest
import jp.co.tellme.marvellous.taskmaster.model.response.UserResponse
import jp.co.tellme.marvellous.taskmaster.usecase.CreateUserUseCase
import jp.co.tellme.marvellous.taskmaster.util.ApiPath
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
class TaskMasterController(
    private val createUserUseCase: CreateUserUseCase
) {

    @PostMapping(ApiPath.createUser)
    fun createUser(
        @Valid
        @RequestBody
        createUserRequest: CreateUserRequest
    ):ResponseEntity<UserResponse> {
        return ResponseEntity<UserResponse>(
            createUserUseCase.createUser(createUserRequest),
            HttpStatus.CREATED
        )
    }
}
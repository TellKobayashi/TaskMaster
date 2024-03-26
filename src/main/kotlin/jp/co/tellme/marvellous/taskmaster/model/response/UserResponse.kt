package jp.co.tellme.marvellous.taskmaster.model.response

import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.annotations.JavaTypeRegistration

data class UserResponse (
    @field:JsonProperty("display_name", required = true)
    val displayName: String,
    @field:JsonProperty("user_name", required = true)
    val userName: String,
    @field:JsonProperty("password", required = true)
    val password: String = "********"
)
package jp.co.tellme.marvellous.taskmaster.model.request.user

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import org.hibernate.validator.constraints.Length



data class CreateUserRequest(
    @field:NotNull(message = "入力は必須です")
    @field:Pattern(regexp = """^[\S]+${'$'}""", message = "空白を入れることはできません")
    @field:Length(max = 16, message = "16文字以下です")
    @field:JsonProperty("display_name", required = true)
    val displayName: String? = null,

    @field:NotNull(message = "入力は必須です")
    @field:Length(min = 8, max = 16, message = "8文字以上16文字以下です")
    @field:Pattern(regexp = """^[a-z\d_]+$""", message = "半角小文字、数字、_で記述してください")
    @field:Pattern(regexp = """^(?=.*[a-z])(?=.*\d)[a-z\d_]{2,}$""", message = "半角小文字、数字を必ず1文字以上含んでください")
    @field:JsonProperty("user_name", required = true)
    val userName: String? = null,

    @field:NotNull(message = "入力は必須です")
    @field:Length(min = 8, max = 16, message = "8文字以上16文字以下です")
    @field:Pattern(regexp = """^[\w&&[^_]]+$""", message = "半角大小文字、数字で記述してください")
    @field:Pattern(regexp = """^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)[\w&&[^_]]{3,}$""", message = "半角大小文字、数字の全てを1文字以上含んでください")
    @field:JsonProperty("password", required = true)
    val password: String? = null
)

package jp.co.tellme.marvellous.taskmaster.model.request

import jp.co.tellme.marvellous.taskmaster.model.request.user.CreateUserRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.validation.BindException
import org.springframework.validation.BindingResult
import org.springframework.validation.Validator

// @SpringBootTestは高速化、引数はなんか高速化するらしい。
@SpringBootTest(classes = [ValidationAutoConfiguration::class])
class CreateUserRequestTest {

    // Validator: オブジェクトの検証ができるインターフェース。エラーはErrorsオブジェクトに渡す。
    @Autowired
    private lateinit var validator: Validator

    // NotNull,Lengthは単純なvalidationのため、単体テストでの確認は行わない。
    @Test
    fun 正常系() {
        val request = CreateUserRequest(
            displayName = "テストtest",
            userName = "abcd_1234",
            password = "Abcd1234"
        )
        val bindingResult: BindingResult = BindException(request, "testRequest")
        // validateは指定したインスタンスのバリデーション実行し、バリデーションエラーが起きた際には指定したErrorsオブジェクトに渡す。
        validator.validate(request, bindingResult)
        assertNull(bindingResult.fieldError)
    }

    @Test
    fun 表示名がnull() {
        val request = CreateUserRequest(
            displayName = null,
            userName = "abcd_1234",
            password = "Abcd1234"
        )
        val bindingResult: BindingResult = BindException(request, "testRequest")
        
        validator.validate(request, bindingResult)
        assertEquals(bindingResult.fieldErrors[0].field, "displayName")
        assertEquals(bindingResult.fieldErrors[0].defaultMessage, "入力は必須です")
    }

    @Test
    fun 表示名に空白が含まれる() {
        val request = CreateUserRequest(
            displayName = " ",
            userName = "abcd_1234",
            password = "Abcd1234"
        )
        val bindingResult: BindingResult = BindException(request, "testRequest")
        validator.validate(request, bindingResult)
        assertEquals(bindingResult.fieldErrors[0].field, "displayName")
        assertEquals(bindingResult.fieldErrors[0].defaultMessage, "空白を入れることはできません")
    }

    @Test
    fun 表示名が17文字以上() {
        val request = CreateUserRequest(
            displayName = "aaaaaaaaaaaaaaaaaa",
            userName = "abcd_1234",
            password = "Abcd1234"
        )
        val bindingResult: BindingResult = BindException(request, "testRequest")
        validator.validate(request, bindingResult)
        assertEquals(bindingResult.fieldErrors[0].field, "displayName")
        assertEquals(bindingResult.fieldErrors[0].defaultMessage, "16文字以下です")
    }

    @Test
    fun ユーザー名がnull() {
        val request = CreateUserRequest(
            displayName = "テストtest",
            userName = null,
            password = "Abcd1234"
        )
        val bindingResult: BindingResult = BindException(request, "testRequest")
        validator.validate(request, bindingResult)
        assertEquals(bindingResult.fieldErrors[0].field, "userName")
        assertEquals(bindingResult.fieldErrors[0].defaultMessage, "入力は必須です")
    }

    @Test
    fun ユーザー名が7文字以下() {
        val request = CreateUserRequest(
            displayName = "テストtest",
            userName = "abcd123",
            password = "Abcd1234"
        )
        val bindingResult: BindingResult = BindException(request, "testRequest")
        validator.validate(request, bindingResult)
        assertEquals(bindingResult.fieldErrors[0].field, "userName")
        assertEquals(bindingResult.fieldErrors[0].defaultMessage, "8文字以上16文字以下です")
    }

    @Test
    fun ユーザー名が17文字以上() {
        val request = CreateUserRequest(
            displayName = "テストtest",
            userName = "abcdefgh123456789",
            password = "Abcd1234"
        )
        val bindingResult: BindingResult = BindException(request, "testRequest")
        validator.validate(request, bindingResult)
        assertEquals(bindingResult.fieldErrors[0].field, "userName")
        assertEquals(bindingResult.fieldErrors[0].defaultMessage, "8文字以上16文字以下です")
    }

    @Test
    fun ユーザー名に半角小文字数字_以外が含まれる() {
        val request = CreateUserRequest(
            displayName = "テストtest",
            userName = "テストtest12_",
            password = "Abcd1234"
        )
        val bindingResult: BindingResult = BindException(request, "testRequest")
        validator.validate(request, bindingResult)
        assertEquals(bindingResult.fieldErrors[0].field, "userName")
        assertEquals(bindingResult.fieldErrors[0].defaultMessage, "半角小文字、数字、_で記述してください")
    }

    @Test
    fun ユーザー名に半角小文字が含まれない() {
        val request = CreateUserRequest(
            displayName = "テストtest",
            userName = "1111111_",
            password = "Abcd1234"
        )
        val bindingResult: BindingResult = BindException(request, "testRequest")
        validator.validate(request, bindingResult)
        assertEquals(bindingResult.fieldErrors[0].field, "userName")
        assertEquals(bindingResult.fieldErrors[0].defaultMessage, "半角小文字、数字を必ず1文字以上含んでください")
    }

    @Test
    fun ユーザー名に数字が含まれない() {
        val request = CreateUserRequest(
            displayName = "テストtest",
            userName = "aaaaaaa_",
            password = "Abcd1234"
        )
        val bindingResult: BindingResult = BindException(request, "testRequest")
        validator.validate(request, bindingResult)
        assertEquals(bindingResult.fieldErrors[0].field, "userName")
        assertEquals(bindingResult.fieldErrors[0].defaultMessage, "半角小文字、数字を必ず1文字以上含んでください")
    }

    @Test
    fun パスワードがnull() {
        val request = CreateUserRequest(
            displayName = "テストtest",
            userName = "abcd_1234",
            password = null
        )
        val bindingResult: BindingResult = BindException(request, "testRequest")
        validator.validate(request, bindingResult)
        assertEquals(bindingResult.fieldErrors[0].field, "password")
        assertEquals(bindingResult.fieldErrors[0].defaultMessage, "入力は必須です")
    }

    @Test
    fun パスワードが7文字以下() {
        val request = CreateUserRequest(
            displayName = "テストtest",
            userName = "abcd_1234",
            password = "Abcd123"
        )
        val bindingResult: BindingResult = BindException(request, "testRequest")
        validator.validate(request, bindingResult)
        assertEquals(bindingResult.fieldErrors[0].field, "password")
        assertEquals(bindingResult.fieldErrors[0].defaultMessage, "8文字以上16文字以下です")
    }

    @Test
    fun パスワードが17文字以上() {
        val request = CreateUserRequest(
            displayName = "テストtest",
            userName = "abcd_1234",
            password = "Abcdefgh123456789"
        )
        val bindingResult: BindingResult = BindException(request, "testRequest")
        validator.validate(request, bindingResult)
        assertEquals(bindingResult.fieldErrors[0].field, "password")
        assertEquals(bindingResult.fieldErrors[0].defaultMessage, "8文字以上16文字以下です")
    }

    @Test
    fun パスワードに半角大小文字数字以外が含まれる() {
        val request = CreateUserRequest(
            displayName = "テストtest",
            userName = "abcd_1234",
            password = "テストAbcd1234"
        )
        val bindingResult: BindingResult = BindException(request, "testRequest")
        validator.validate(request, bindingResult)
        assertEquals(bindingResult.fieldErrors[0].field, "password")
        assertEquals(bindingResult.fieldErrors[0].defaultMessage, "半角大小文字、数字で記述してください")
    }

    @Test
    fun パスワードに半角大文字が含まれない() {
        val request = CreateUserRequest(
            displayName = "テストtest",
            userName = "abcd_1234",
            password = "abcd1234"
        )
        val bindingResult: BindingResult = BindException(request, "testRequest")
        validator.validate(request, bindingResult)
        assertEquals(bindingResult.fieldErrors[0].field, "password")
        assertEquals(bindingResult.fieldErrors[0].defaultMessage, "半角大小文字、数字の全てを1文字以上含んでください")
    }

    @Test
    fun パスワードに半角小文字が含まれない() {
        val request = CreateUserRequest(
            displayName = "テストtest",
            userName = "abcd_1234",
            password = "ABCD1234"
        )
        val bindingResult: BindingResult = BindException(request, "testRequest")
        validator.validate(request, bindingResult)
        assertEquals(bindingResult.fieldErrors[0].field, "password")
        assertEquals(bindingResult.fieldErrors[0].defaultMessage, "半角大小文字、数字の全てを1文字以上含んでください")
    }

    @Test
    fun パスワードに数字が含まれない() {
        val request = CreateUserRequest(
            displayName = "テストtest",
            userName = "abcd_1234",
            password = "AbcdefgH"
        )
        val bindingResult: BindingResult = BindException(request, "testRequest")
        validator.validate(request, bindingResult)
        assertEquals(bindingResult.fieldErrors[0].field, "password")
        assertEquals(bindingResult.fieldErrors[0].defaultMessage, "半角大小文字、数字の全てを1文字以上含んでください")
    }

}
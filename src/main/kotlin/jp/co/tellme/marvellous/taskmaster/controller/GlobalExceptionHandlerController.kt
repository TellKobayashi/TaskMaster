package jp.co.tellme.marvellous.taskmaster.controller

import org.apache.coyote.BadRequestException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.resource.NoResourceFoundException

@RestControllerAdvice
class GlobalExceptionHandlerController {
    // 404: エンドポイントが不明
    @ExceptionHandler(NoResourceFoundException::class)
    fun noResourceFoundExceptionHandler(e: NoResourceFoundException): ResponseEntity<Any> {
        return ResponseEntity(
            mapOf(
                "errorCode" to HttpStatus.NOT_FOUND.name,
                "message" to e.message
            ),
            HttpStatus.NOT_FOUND
        )
    }

    // 405: 許可されないエンドポイント
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun noHttpRequestMethodNoSupportedExceptionHandler(
        e: HttpRequestMethodNotSupportedException
    ): ResponseEntity<Any> {
        return ResponseEntity(
            mapOf(
                "errorCode" to HttpStatus.METHOD_NOT_ALLOWED.name,
                "message" to e.message
            ),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    // 415は特記しない。

    // 400: クライアント側のエラー

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun httpMessageNotReadableExceptionHandler(e: HttpMessageNotReadableException): ResponseEntity<Any> {
        return ResponseEntity(
            mapOf(
                "errorCode" to HttpStatus.BAD_REQUEST.name,
                "message" to e.message
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    // 400: バリデーションエラー
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidExceptionHandler(
        e: MethodArgumentNotValidException
    ): ResponseEntity<Any> {
        val messages = e.bindingResult.allErrors.map {
            when (it) {
                is FieldError -> "${it.field}は${it.defaultMessage}"
                else -> it.defaultMessage.toString()
            }
        }
        return ResponseEntity(
            mapOf(
                "errorCode" to HttpStatus.BAD_REQUEST.name,
                "message" to messages
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    // 取り逃がしの無いように全てのエラーをハンドリングする。
    // 500: サーバー上でなんらかのエラーがあった場合のエラー
    @ExceptionHandler(Exception::class)
    fun handleAllException(
        e: Exception
    ): ResponseEntity<Any> {
        return ResponseEntity(
            mapOf(
                "errorCode" to HttpStatus.INTERNAL_SERVER_ERROR.name,
                "message" to e.message
            ),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    // DBに関するexception
    @ExceptionHandler(DataIntegrityViolationException::class)
    fun dataIntegrityViolationException(): ResponseEntity<Any> {
        return ResponseEntity(
            mapOf(
                "errorCode" to HttpStatus.BAD_REQUEST.name,
                "message" to "Parameter is non-alignment."
            ),
            HttpStatus.BAD_REQUEST
        )
    }

}
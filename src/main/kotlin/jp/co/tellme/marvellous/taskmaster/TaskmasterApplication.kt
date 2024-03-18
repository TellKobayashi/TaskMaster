package jp.co.tellme.marvellous.taskmaster

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TaskmasterApplication

fun main(args: Array<String>) {
	runApplication<TaskmasterApplication>(*args)
}

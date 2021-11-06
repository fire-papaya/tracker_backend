package uz.warcom.tracker.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.*
import kotlin.reflect.KClass

@ResponseStatus(HttpStatus.BAD_REQUEST)
open class GenericApiException(
    val code: String = "",
    override val message: String = ""
): RuntimeException(message)

@ResponseStatus(HttpStatus.NOT_FOUND)
class ResourceNotFoundException(
    override val message: String = "Resource not found"
): GenericApiException("NOT_FOUND", message) {

    constructor (id: UUID, kClass: KClass<*>) :
            this("${kClass.simpleName}($id) not found")

    constructor (id: Number, kClass: KClass<*>) :
            this("${kClass.simpleName}($id) not found")

    constructor (id: String, kClass: KClass<*>) :
            this("${kClass.simpleName}($id) not found")
}
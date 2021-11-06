package uz.warcom.tracker.api.tracker.model.response

data class DefaultResponse<T> (
    val payload: T,
    val result: ApiResult
) {
    companion object {
        fun <T> of(payload: T): DefaultResponse<T> {
            return DefaultResponse(payload, ApiResult(ErrorCode.OK))
        }
    }
}

data class ApiResult (
    val code: ErrorCode,
    val description: String
) {
    constructor(code: ErrorCode): this(code, code.description)
}
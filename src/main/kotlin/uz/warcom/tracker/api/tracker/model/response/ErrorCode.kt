package uz.warcom.tracker.api.tracker.model.response

enum class ErrorCode(val description: String) {

    OK("Successful"),
    NOT_FOUND("Requested resource was not found"),

}
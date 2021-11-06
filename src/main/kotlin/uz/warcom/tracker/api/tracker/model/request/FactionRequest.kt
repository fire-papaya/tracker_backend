package uz.warcom.tracker.api.tracker.model.request


class FactionRequest (
    var name: String = "",
    code: String = "",
    parentCode: String? = null
) {
    val code: String = code
        get() = field.uppercase()

    val parentCode: String? = parentCode
        get() = field?.uppercase()
}
package uz.warcom.tracker.api.tracker.model.request


data class FactionRequest (
    var name: String = "",
    var code: String = "",
    var parentCode: String? = null
)
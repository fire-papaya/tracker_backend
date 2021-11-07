package uz.warcom.tracker.api.tracker.model.response

data class FactionResponse (
    var name: String = "",
    var code: String = "",
    var parent: String? = null,
    var children: List<FactionResponse>? = null
)
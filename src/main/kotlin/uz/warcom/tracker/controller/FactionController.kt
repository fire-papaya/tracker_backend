package uz.warcom.tracker.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import uz.warcom.tracker.api.tracker.model.request.FactionRequest
import uz.warcom.tracker.api.tracker.model.response.FactionResponse

@Tag(name="FactionApi", description = "management of factions")
interface FactionController {
    @Operation(summary = "create new faction")
    fun createFaction (request: FactionRequest): FactionResponse
    @Operation(summary = "update existing faction")
    fun updateFaction (request: FactionRequest): FactionResponse
    @Operation(summary = "get existing faction")
    fun getFaction (code: String): FactionResponse
    @Operation(summary = "get list of all factions")
    fun getFactions (): List<FactionResponse>
}
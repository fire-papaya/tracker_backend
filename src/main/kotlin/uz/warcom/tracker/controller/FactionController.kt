package uz.warcom.tracker.controller

import uz.warcom.tracker.api.tracker.model.request.FactionRequest
import uz.warcom.tracker.api.tracker.model.response.FactionResponse

interface FactionController {

    fun createFaction (request: FactionRequest): FactionResponse
    fun updateFaction (request: FactionRequest): FactionResponse
    fun getFaction (code: String): FactionResponse
    fun getFactions (): List<FactionResponse>
}
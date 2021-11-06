package uz.warcom.tracker.controller.implementation

import org.springframework.web.bind.annotation.*
import uz.warcom.tracker.api.tracker.configuration.ApiMapStructConverter
import uz.warcom.tracker.api.tracker.model.request.FactionRequest
import uz.warcom.tracker.api.tracker.model.response.FactionResponse
import uz.warcom.tracker.service.FactionService

@RestController
@RequestMapping("/api/faction")
class DefaultFactionController
constructor(
    private val factionService: FactionService,
    private val mapStruct: ApiMapStructConverter
){

    @PostMapping
    fun createFaction (
        @RequestBody request: FactionRequest
    ) : FactionResponse {
        TODO("Not implemented yet")
    }

    @PutMapping
    fun updateFaction (
        @RequestBody request: FactionRequest
    ) : FactionResponse {
        TODO("Not implemented yet")
    }

    @GetMapping("/{code}")
    fun getFaction (
        code: String
    ) : FactionResponse  {
        return factionService.getFaction(code)
            .let { mapStruct.toFactionResponse(it) }
    }

    @GetMapping
    fun getFactions () : List<FactionResponse>  {
        return factionService.getFactionsTree()
            .map { mapStruct.toFactionResponse(it) }
    }
}
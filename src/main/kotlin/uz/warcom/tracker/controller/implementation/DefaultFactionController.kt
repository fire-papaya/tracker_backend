package uz.warcom.tracker.controller.implementation

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import uz.warcom.tracker.api.tracker.configuration.ApiMapStructConverter
import uz.warcom.tracker.api.tracker.model.request.FactionRequest
import uz.warcom.tracker.api.tracker.model.response.FactionResponse
import uz.warcom.tracker.controller.FactionController
import uz.warcom.tracker.service.FactionService

@RestController
@RequestMapping(
    value = ["/api/faction"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class DefaultFactionController
constructor(
    private val factionService: FactionService
): FactionController {

    @PostMapping
    override fun createFaction (
        @RequestBody request: FactionRequest
    ) : FactionResponse {
        return factionService.createFaction(request)
    }

    @PutMapping
    override fun updateFaction (
        @RequestBody request: FactionRequest
    ) : FactionResponse {
        return factionService.updateFaction(request)
    }

    @GetMapping("/{code}")
    override fun getFaction (
        @PathVariable code: String
    ) : FactionResponse  {
        return factionService.getFaction(code)
    }

    @GetMapping
    override fun getFactions () : List<FactionResponse>  {
        return factionService.getFactionsTree()
    }
}
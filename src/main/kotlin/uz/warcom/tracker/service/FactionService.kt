package uz.warcom.tracker.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import uz.warcom.tracker.api.tracker.configuration.ApiMapStructConverter
import uz.warcom.tracker.api.tracker.model.request.FactionRequest
import uz.warcom.tracker.api.tracker.model.response.FactionResponse
import uz.warcom.tracker.domain.entity.Faction
import uz.warcom.tracker.exception.GenericApiException
import uz.warcom.tracker.exception.ResourceNotFoundException
import java.util.*
import javax.transaction.Transactional

@Service
class FactionService
@Autowired constructor(
    private val factionsPersistence: FactionPersistenceService,
    private val mapStruct: ApiMapStructConverter
){

    fun getFaction (hashId: UUID) : FactionResponse {
        val faction = findFaction(hashId)
        return getFaction(faction)
    }

    fun getFaction (id: Int) : FactionResponse {
        val faction = findFaction(id)
        return getFaction(faction)
    }

    fun getFaction (code: String) : FactionResponse {
        val faction = findFaction(code)
        return getFaction(faction)
    }

    @Transactional
    fun getFactionsTree () : List<FactionResponse> {
        val factions = factionsPersistence.factionTree()
        return factions.map {  mapStruct.toFactionResponse(it) }
    }

    fun createFaction (factionRequest: FactionRequest): FactionResponse {
        val parent = if (factionRequest.parentCode != null)
            findFaction(factionRequest.parentCode!!)
        else null

        val faction = mapStruct.toFactionEntity(factionRequest)
            .also { it.parent = parent }

        return map(factionsPersistence.save(faction))
    }

    @Transactional
    fun updateFaction (factionRequest: FactionRequest): FactionResponse {
        val faction = findFaction(factionRequest.code)

        val parent = if (factionRequest.parentCode != null)
            findFaction(factionRequest.parentCode!!)
        else null

        faction.parent = parent
        faction.name = faction.name

        return map(factionsPersistence.save(faction))
    }

    fun deleteFaction (code: String) {
        val faction = findFaction(code)

        if (!faction.children.isNullOrEmpty())
            throw GenericApiException()

        factionsPersistence.delete(faction)
    }

    private fun map(faction: Faction): FactionResponse {
        return mapStruct.toFactionResponse(faction)
    }

    private fun getFaction(faction: Faction) : FactionResponse {
        return searchFaction(faction.code, getFactionsTree())
            ?: throw ResourceNotFoundException(faction.code, Faction::class)
    }

    private fun searchFaction (code: String, factionTree: List<FactionResponse>): FactionResponse? {
        for (faction in factionTree) {
            if (faction.code == code)
                return faction

            if (!faction.children.isNullOrEmpty())
                return searchFaction(code, faction.children!!)
        }

        return null
    }

    protected fun findFaction (hashId: UUID) : Faction {
        return factionsPersistence.find(hashId)
    }

    protected fun findFaction (id: Int) : Faction {
        return factionsPersistence.find(id)
    }

    protected fun findFaction (code: String) : Faction {
        return factionsPersistence.find(code)
    }
}
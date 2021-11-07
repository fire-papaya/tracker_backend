package uz.warcom.tracker.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import uz.warcom.tracker.api.tracker.configuration.ApiMapStructConverter
import uz.warcom.tracker.api.tracker.model.request.FactionRequest
import uz.warcom.tracker.api.tracker.model.response.FactionResponse
import uz.warcom.tracker.config.CacheConfig
import uz.warcom.tracker.domain.entity.Faction
import uz.warcom.tracker.domain.repository.FactionRepository
import uz.warcom.tracker.exception.GenericApiException
import uz.warcom.tracker.exception.ResourceNotFoundException
import java.util.*
import javax.transaction.Transactional

@Service
class FactionService
@Autowired constructor(
    private val factionRepository: FactionRepository,
    private val mapStruct: ApiMapStructConverter
){
    @Autowired
    private lateinit var self: FactionService

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

    @Cacheable(CacheConfig.FACTIONS)
    @Transactional
    fun getFactionsTree () : List<FactionResponse> {
        val factions = factionRepository.findAllByParentIsNull()
        return factions.map {  mapStruct.toFactionResponse(it) }
    }

    @CacheEvict(CacheConfig.FACTIONS)
    fun createFaction (factionRequest: FactionRequest): FactionResponse {
        val parent = if (factionRequest.parentCode != null)
            findFaction(factionRequest.parentCode!!)
        else null

        val faction = mapStruct.toFactionEntity(factionRequest)
            .also { it.parent = parent }

        return map(factionRepository.save(faction))
    }

    @CacheEvict(CacheConfig.FACTIONS)
    @Transactional
    fun updateFaction (factionRequest: FactionRequest): FactionResponse {
        val faction = findFaction(factionRequest.code)

        val parent = if (factionRequest.parentCode != null)
            findFaction(factionRequest.parentCode!!)
        else null

        faction.parent = parent
        faction.name = faction.name

        return map(factionRepository.save(faction))
    }

    private fun map(faction: Faction): FactionResponse {
        return mapStruct.toFactionResponse(faction)
    }

    @CacheEvict("factions")
    fun deleteFaction (code: String) {
        val faction = findFaction(code)

        if (!faction.children.isNullOrEmpty())
            throw GenericApiException()

        factionRepository.delete(faction)
    }

    private fun getFaction(faction: Faction) : FactionResponse {
        return searchFaction(faction.code, self.getFactionsTree())
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
        return factionRepository.findByHashId(hashId) ?: throw ResourceNotFoundException(hashId, Faction::class)
    }

    protected fun findFaction (id: Int) : Faction {
        return factionRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException(id, Faction::class)
    }

    protected fun findFaction (code: String) : Faction {
        return factionRepository.findByCode(code) ?: throw ResourceNotFoundException(code, Faction::class)
    }
}
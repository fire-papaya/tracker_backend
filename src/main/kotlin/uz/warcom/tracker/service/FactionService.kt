package uz.warcom.tracker.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import uz.warcom.tracker.api.tracker.configuration.ApiMapStructConverter
import uz.warcom.tracker.api.tracker.model.request.FactionRequest
import uz.warcom.tracker.config.CacheConfig
import uz.warcom.tracker.domain.entity.Faction
import uz.warcom.tracker.domain.repository.FactionRepository
import uz.warcom.tracker.exception.GenericApiException
import uz.warcom.tracker.exception.ResourceNotFoundException
import java.util.*

@Service
class FactionService
@Autowired constructor(
    private val factionRepository: FactionRepository,
    private val mapStruct: ApiMapStructConverter
){
    fun getFaction (hashId: UUID) : Faction {
        return factionRepository.findByHashId(hashId) ?: throw ResourceNotFoundException(hashId, Faction::class)
    }

    fun getFaction (id: Int) : Faction {
        return factionRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException(id, Faction::class)
    }

    fun getFaction (code: String) : Faction {
        return factionRepository.findByCode(code) ?: throw ResourceNotFoundException(code, Faction::class)
    }

    @Cacheable(CacheConfig.FACTIONS)
    fun getFactionsTree () : List<Faction> {
        return factionRepository.findAllByParentIsNull()
    }

    @Cacheable(CacheConfig.FACTIONS)
    fun getFactions () : List<Faction> {
        return factionRepository.findAll()
    }

    @CacheEvict(CacheConfig.FACTIONS)
    fun createFaction (factionRequest: FactionRequest): Faction {
        val parent = if (factionRequest.parentCode != null)
            getFaction(factionRequest.parentCode!!)
        else null

        val faction = mapStruct.toFactionEntity(factionRequest)
            .also { it.parent = parent }

        return factionRepository.save(faction)
    }

    @CacheEvict(CacheConfig.FACTIONS)
    fun updateFaction (factionRequest: FactionRequest): Faction {
        val faction = getFaction(factionRequest.code)

        val parent = if (factionRequest.parentCode != null)
            getFaction(factionRequest.parentCode!!)
        else null

        faction.parent = parent
        faction.name = faction.name

        return factionRepository.save(faction)
    }

    @CacheEvict("factions")
    fun deleteFaction (code: String) {
        val faction = getFaction(code)

        if (!faction.children.isNullOrEmpty())
            throw GenericApiException()

        factionRepository.delete(faction)
    }
}
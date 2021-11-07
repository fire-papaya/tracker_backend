package uz.warcom.tracker.service

import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import uz.warcom.tracker.config.CacheConfig
import uz.warcom.tracker.domain.entity.Faction
import uz.warcom.tracker.domain.repository.FactionRepository
import uz.warcom.tracker.exception.ResourceNotFoundException
import java.util.*

@Service
class FactionPersistenceService (
    private val factionRepository: FactionRepository,
) {
    @Cacheable(CacheConfig.FACTIONS)
    fun factionTree () : List<Faction> {
        return factionRepository.findAllByParentIsNull()
    }

    @CacheEvict(CacheConfig.FACTIONS)
    fun save (faction: Faction): Faction {
        return factionRepository.save(faction)
    }

    @CacheEvict("factions")
    fun delete (faction: Faction) {
        factionRepository.delete(faction)
    }

    fun find (hashId: UUID) : Faction {
        return factionRepository.findByHashId(hashId) ?: throw ResourceNotFoundException(hashId, Faction::class)
    }

    fun find (id: Int) : Faction {
        return factionRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException(id, Faction::class)
    }

    fun find (code: String) : Faction {
        return factionRepository.findByCode(code) ?: throw ResourceNotFoundException(code, Faction::class)
    }
}
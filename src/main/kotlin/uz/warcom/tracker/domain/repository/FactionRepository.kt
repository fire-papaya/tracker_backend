package uz.warcom.tracker.domain.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import uz.warcom.tracker.domain.entity.Faction
import java.util.*

@Repository
interface FactionRepository : JpaRepository<Faction, Int> {

    fun findByHashId (hashId: UUID): Faction?

    fun findByCode (code: String): Faction?

    fun findAllByParentIsNull(): List<Faction>
}
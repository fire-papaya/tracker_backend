package uz.warcom.tracker.domain.repository

import org.springframework.data.jpa.repository.JpaRepository
import uz.warcom.tracker.domain.entity.Player

interface PlayerRepository: JpaRepository<Player, Int>
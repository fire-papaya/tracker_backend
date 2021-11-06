package uz.warcom.tracker.domain.repository

import org.springframework.data.jpa.repository.JpaRepository
import uz.warcom.tracker.domain.entity.Game

interface GameRepository: JpaRepository<Game, Int>
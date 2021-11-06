package uz.warcom.tracker.domain.repository

import org.springframework.data.jpa.repository.JpaRepository
import uz.warcom.tracker.domain.entity.Objective

interface ObjectiveRepository: JpaRepository<Objective, Int>
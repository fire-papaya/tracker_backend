package uz.warcom.tracker.domain.entity

import javax.persistence.Entity
import javax.persistence.Lob

@Entity
class Objective : DefaultIntEntity() {

    var name: String = ""

    @Lob
    var description: String = ""

    var scoringType: ObjectiveScoringType = ObjectiveScoringType.MATCH

    var maxPoints: Int = 0
}
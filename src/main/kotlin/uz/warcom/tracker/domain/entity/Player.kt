package uz.warcom.tracker.domain.entity

import javax.persistence.*

@Entity
class Player : DefaultIntEntity() {

    var name: String = ""

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    var user: User? = null
}
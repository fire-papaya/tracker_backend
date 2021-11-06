package uz.warcom.tracker.domain.entity

import javax.persistence.*

@Entity
class User: DefaultIntEntity() {

    var telegramUserId: Int? = null
    var telegramAccount: String? = null
    var email: String? = null

    @OneToOne(mappedBy = "user", cascade = [CascadeType.ALL])
    @PrimaryKeyJoinColumn
    var player: Player = Player()

}
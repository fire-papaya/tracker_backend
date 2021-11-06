package uz.warcom.tracker.domain.entity

import javax.persistence.*

@Entity
class Game : AbstractEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default")
    @SequenceGenerator(name = "default", sequenceName = "_game_sequence")
    @Column(name = "id", nullable = false)
    var id: Int? = null

    var currentRound: Int = 0

    var totalRounds: Int = 0

    var status: GameStatus = GameStatus.IDLE

    @OneToMany(mappedBy = "game", cascade = [CascadeType.ALL], orphanRemoval = true)
    var playerGames: MutableList<PlayerGame> = mutableListOf()
}
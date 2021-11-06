package uz.warcom.tracker.domain.entity

import javax.persistence.*

@Entity
class PlayerGame : DefaultIntEntity() {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id", nullable = false)
    var game: Game? = null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id", nullable = false)
    var player: Player? = null

    @OneToMany(mappedBy = "playerGame", cascade = [CascadeType.ALL], orphanRemoval = true)
    var gameScores: MutableList<GameScore> = mutableListOf()

    var maxPoints: Int = 0

    var scoredPoints: Int = 0

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "faction_id", nullable = false)
    var faction: Faction? = null
}
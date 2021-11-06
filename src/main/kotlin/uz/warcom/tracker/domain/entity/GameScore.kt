package uz.warcom.tracker.domain.entity

import javax.persistence.*

@Entity
class GameScore: AbstractEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default")
    @SequenceGenerator(name = "default", sequenceName = "_game_score_sequence")
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_game_id", nullable = false)
    var playerGame: PlayerGame? = null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "objective_id", nullable = false)
    var objective: Objective? = null

}
package uz.warcom.tracker.domain.entity

import javax.persistence.*

@MappedSuperclass
abstract class DefaultIntEntity : AbstractEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default")
    @SequenceGenerator(name = "default", sequenceName = "_default_sequence")
    @Column(name = "id", nullable = false)
    var id: Int? = null

}
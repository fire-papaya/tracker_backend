package uz.warcom.tracker.domain.entity

import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import javax.persistence.*

@Entity
class Faction: DefaultIntEntity() {

    var name: String = ""

    @Column(unique = true)
    var code: String = ""

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    var parent: Faction? = null

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", targetEntity = Faction::class)
    @Fetch(FetchMode.JOIN)
    var children: List<Faction>? = null
}
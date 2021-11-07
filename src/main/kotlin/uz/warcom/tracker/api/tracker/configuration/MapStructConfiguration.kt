package uz.warcom.tracker.api.tracker.configuration

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.springframework.context.annotation.Configuration
import uz.warcom.tracker.api.tracker.model.request.FactionRequest
import uz.warcom.tracker.api.tracker.model.response.FactionResponse
import uz.warcom.tracker.domain.entity.Faction

@Configuration
@Mapper(componentModel = "spring")
interface ApiMapStructConverter {

    @Mappings(value = [
        Mapping(target = "parent", source = "parent.code")
    ])
    fun toFactionResponse (entity: Faction): FactionResponse

    fun toFactionEntity (request: FactionRequest): Faction
}
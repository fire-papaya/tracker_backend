package uz.warcom.tracker.config

import com.google.common.cache.CacheBuilder
import org.springframework.cache.Cache
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCache
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

@Configuration
@EnableCaching
class CacheConfig {

    private val FACTIONS = "factions"

    @Bean
    fun factionsCache(): Cache {
        return ConcurrentMapCache(
            FACTIONS,
            CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.DAYS)
                .build<Any, Any>().asMap(),
            false
        )
    }
}
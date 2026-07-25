package fr.meteordesign.eldritchhorrorcompanion.features.core.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

interface DiceRollNavigator {

    @Serializable
    data object Route : NavKey

    fun EntryProviderScope<NavKey>.entry()

    companion object {
        val serializerModule: SerializersModule = SerializersModule {
            polymorphic(NavKey::class) {
                subclass(Route::class, Route.serializer())
            }
        }
    }
}

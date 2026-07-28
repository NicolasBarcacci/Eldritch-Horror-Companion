package fr.meteordesign.eldritchhorrorcompanion.features.core.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule

interface TestResolverNavigator {

    @Serializable
    data object Route : NavKey

    fun EntryProviderScope<NavKey>.entry()

    fun serializerModule(): SerializersModule
}

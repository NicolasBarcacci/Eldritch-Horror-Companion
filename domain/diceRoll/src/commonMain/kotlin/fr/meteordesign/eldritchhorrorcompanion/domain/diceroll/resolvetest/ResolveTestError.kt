package fr.meteordesign.eldritchhorrorcompanion.domain.diceroll.resolvetest

sealed class ResolveTestError {
    data object InvalidDiceCount : ResolveTestError()
}

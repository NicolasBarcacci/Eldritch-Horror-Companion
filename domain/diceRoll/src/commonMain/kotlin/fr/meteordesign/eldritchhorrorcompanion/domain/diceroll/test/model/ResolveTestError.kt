package fr.meteordesign.eldritchhorrorcompanion.domain.diceroll.test.model

sealed class ResolveTestError {
    data object InvalidDiceCount : ResolveTestError()
}

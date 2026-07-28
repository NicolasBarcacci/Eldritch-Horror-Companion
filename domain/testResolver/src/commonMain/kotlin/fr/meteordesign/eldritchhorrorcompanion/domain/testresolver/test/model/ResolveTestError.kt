package fr.meteordesign.eldritchhorrorcompanion.domain.testresolver.test.model

sealed class ResolveTestError {
    data object InvalidDiceCount : ResolveTestError()
}

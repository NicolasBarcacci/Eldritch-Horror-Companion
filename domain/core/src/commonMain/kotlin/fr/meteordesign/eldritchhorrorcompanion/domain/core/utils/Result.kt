package fr.meteordesign.eldritchhorrorcompanion.domain.core.utils

sealed class Result<out Success : Any, out Failure : Any> {
    data class Success<out Success : Any>(val value: Success) : Result<Success, Nothing>()
    data class Failure<out Failure : Any>(val error: Failure) : Result<Nothing, Failure>()
}

package com.dgparkcode.note.common.domain

sealed class Outcome<out R> {

    data class Success<out T>(val data: T) : Outcome<T>()

    data class Error(val cause: Throwable) : Outcome<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success -> "Success[data=$data]"
            is Error -> "Error[cause=$cause]"
        }
    }
}
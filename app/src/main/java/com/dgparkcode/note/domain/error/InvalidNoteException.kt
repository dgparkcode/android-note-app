package com.dgparkcode.note.domain.error

class InvalidNoteException(
    message: String? = null,
    cause: Throwable? = null
) : RuntimeException(message, cause) {

    constructor(cause: Throwable) : this(null, cause)
}
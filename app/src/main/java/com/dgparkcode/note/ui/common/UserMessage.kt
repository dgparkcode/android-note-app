package com.dgparkcode.note.ui.common

import java.util.UUID

data class UserMessage(
    val id: Long = UUID.randomUUID().mostSignificantBits,
    val message: String
)

package com.dgparkcode.note.common.domain.usecase

import com.dgparkcode.note.common.domain.Outcome
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class SuspendUseCase<in P, R>(
    private val coroutineDispatcher: CoroutineDispatcher,
) {

    suspend operator fun invoke(params: P): Outcome<R> {
        return try {
            withContext(coroutineDispatcher) {
                execute(params).let { Outcome.Success(it) }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Outcome.Error(e)
        }
    }

    protected abstract suspend fun execute(params: P): R
}
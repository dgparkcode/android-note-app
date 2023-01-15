package com.dgparkcode.note.common.domain.usecase

import com.dgparkcode.note.common.domain.Outcome
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCase<in P, R>(
    private val coroutineDispatcher: CoroutineDispatcher,
) {

    operator fun invoke(params: P) = execute(params)
        .catch { emit(Outcome.Error(it)) }
        .flowOn(coroutineDispatcher)

    protected abstract fun execute(params: P): Flow<Outcome<R>>
}
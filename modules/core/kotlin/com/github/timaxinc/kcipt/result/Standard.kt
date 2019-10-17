package com.github.timaxinc.kcipt.result

/**
 * calls is [block] if [Result] is [Result.Success]
 *
 * @param block is called if [Result] is [Result.Success]
 * @return the [Result]
 */
inline fun <V, R> Result<V, R>.onSuccess(block: Result.Success<V, R>.() -> Unit): Result<V, R> {
    if (this is Result.Success) {
        this.block()
    }
    return this
}

/**
 * calls is [block] if [Result] is [Result.Failure]
 *
 * @param block is called if [Result] is [Result.Failure]
 * @return the [Result]
 */
inline fun <V, R> Result<V, R>.onFailure(block: Result.Failure<V, R>.() -> Unit): Result<V, R> {
    if (this is Result.Failure) {
        this.block()
    }
    return this
}

/**
 * crates a [Result.Success] with [this] and [reports]
 *
 * @param reports
 * @return the created [Result.Success]
 */
fun <V, R> V.asSuccess(reports: List<R> = listOf()): Result.Success<V, R> = createSuccess(this, reports)

/**
 * crates a [Result.Success] with [value] and [reports]
 *
 * @param value
 * @param reports
 * @return the created [Result.Success]
 */
fun <V, R> createSuccess(value: V, reports: List<R> = listOf()) = Result.Success(value, reports)

/**
 * crates a [Result.Failure] with [reports]
 *
 * @param reports
 * @return the created [Result.Failure]
 */
fun <V, R> createFailure(reports: List<R> = listOf()) = Result.Failure<V, R>(reports)

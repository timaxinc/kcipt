package com.github.timaxinc.kcipt.result

/**
 * represents a result of a process that can succeed or fail
 *
 * @param V type of value in case result is [Result.Success]
 * @param R type of [reports]
 */
sealed class Result<V, R> {

    /**
     * list of reports that were created by the process
     */
    abstract val reports: List<R>

    /**
     * returned by a process in case it succeeds
     *
     * @param V type of [value]
     * @param R type of [reports]
     * @property value result of the process
     * @property reports list of reports that were created by the process
     */
    data class Success<V, R>(
            val value: V, override val reports: List<R> = listOf()
    ) : Result<V, R>() {

        constructor(value: V, vararg reports: R) : this(value, reports.asList())
    }

    /**
     * returned by a process in case it fails
     *
     * @param V type of value in case result is [Result.Success]
     * @param R type of [reports]
     * @property reports list of reports that were created by the process
     */
    data class Failure<V, R>(
            override val reports: List<R>
    ) : Result<V, R>() {
    
        constructor(vararg reports: R) : this(reports.asList())
    }
}

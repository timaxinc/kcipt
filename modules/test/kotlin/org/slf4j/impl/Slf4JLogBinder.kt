package org.slf4j.impl

import org.slf4j.ILoggerFactory
import org.slf4j.Logger
import org.slf4j.Marker
import org.slf4j.helpers.MessageFormatter
import org.slf4j.spi.LoggerFactoryBinder

object Slf4JLogBinder : LoggerFactoryBinder {

    private object LoggerFactory : ILoggerFactory {
        override fun getLogger(name: String?): Logger {
            return Log4JAdapter()
        }
    }

    private class Log4JAdapter : Logger {


        override fun getName(): String {
            return "Dummy slf4j logger adapter"
        }

        private val DEBUG = org.slf4j.spi.LocationAwareLogger.DEBUG_INT
        private val ERROR = org.slf4j.spi.LocationAwareLogger.ERROR_INT
        private val INFO = org.slf4j.spi.LocationAwareLogger.INFO_INT
        private val TRACE = org.slf4j.spi.LocationAwareLogger.TRACE_INT
        private val WARN = org.slf4j.spi.LocationAwareLogger.WARN_INT


        override fun isTraceEnabled(): Boolean {
            return true
        }

        override fun trace(msg: String) {
            log(null, TRACE, msg, null, null)
        }

        override fun trace(format: String, arg: Any) {
            log(null, TRACE, format, arrayOf(arg), null)
        }

        override fun trace(format: String, arg1: Any, arg2: Any) {
            log(null, TRACE, format, arrayOf(arg1, arg2), null)
        }

        override fun trace(format: String, argArray: Array<Any>) {
            log(null, TRACE, format, argArray, null)
        }

        override fun trace(msg: String, t: Throwable) {
            log(null, TRACE, msg, null, t)
        }

        override fun isTraceEnabled(marker: Marker): Boolean {
            return false
        }

        override fun trace(marker: Marker, msg: String) {
            log(marker, TRACE, msg, null, null)
        }

        override fun trace(marker: Marker, format: String, arg: Any) {
            log(marker, TRACE, format, arrayOf(arg), null)
        }

        override fun trace(marker: Marker, format: String, arg1: Any, arg2: Any) {
            log(marker, TRACE, format, arrayOf(arg1, arg2), null)
        }

        override fun trace(marker: Marker, format: String, argArray: Array<Any>) {
            log(marker, TRACE, format, argArray, null)
        }

        override fun trace(marker: Marker, msg: String, t: Throwable) {
            log(marker, TRACE, msg, null, t)
        }

        override fun isDebugEnabled(): Boolean {
            return false
        }

        override fun debug(msg: String) {
            log(null, DEBUG, msg, null, null)
        }

        override fun debug(format: String, arg: Any) {
            log(null, DEBUG, format, arrayOf(arg), null)
        }

        override fun debug(format: String, arg1: Any, arg2: Any) {
            log(null, DEBUG, format, arrayOf(arg1, arg2), null)
        }

        override fun debug(format: String, argArray: Array<Any>) {
            log(null, DEBUG, format, argArray, null)
        }

        override fun debug(msg: String, t: Throwable) {
            log(null, DEBUG, msg, null, t)
        }

        override fun isDebugEnabled(marker: Marker): Boolean {
            return false
        }

        override fun debug(marker: Marker, msg: String) {
            log(marker, DEBUG, msg, null, null)
        }

        override fun debug(marker: Marker, format: String, arg: Any) {
            log(marker, DEBUG, format, arrayOf(arg), null)
        }

        override fun debug(marker: Marker, format: String, arg1: Any, arg2: Any) {
            log(marker, DEBUG, format, arrayOf(arg1, arg2), null)
        }

        override fun debug(marker: Marker, format: String, argArray: Array<Any>) {
            log(marker, DEBUG, format, argArray, null)
        }

        override fun debug(marker: Marker, msg: String, t: Throwable) {
            log(marker, DEBUG, msg, null, t)
        }

        override fun isInfoEnabled(): Boolean {
            return false
        }

        override fun info(msg: String) {
            log(null, INFO, msg, null, null)
        }

        override fun info(format: String, arg: Any) {
            log(null, INFO, format, arrayOf(arg), null)
        }

        override fun info(format: String, arg1: Any, arg2: Any) {
            log(null, INFO, format, arrayOf(arg1, arg2), null)
        }

        override fun info(format: String, argArray: Array<Any>) {
            log(null, INFO, format, argArray, null)
        }

        override fun info(msg: String, t: Throwable) {
            log(null, INFO, msg, null, t)
        }

        override fun isInfoEnabled(marker: Marker): Boolean {
            return false
        }

        override fun info(marker: Marker, msg: String) {
            log(marker, INFO, msg, null, null)
        }

        override fun info(marker: Marker, format: String, arg: Any) {
            log(marker, INFO, format, arrayOf(arg), null)
        }

        override fun info(marker: Marker, format: String, arg1: Any, arg2: Any) {
            log(marker, INFO, format, arrayOf(arg1, arg2), null)
        }

        override fun info(marker: Marker, format: String, argArray: Array<Any>) {
            log(marker, INFO, format, argArray, null)
        }

        override fun info(marker: Marker, msg: String, t: Throwable) {
            log(marker, INFO, msg, null, t)
        }

        override fun isWarnEnabled(): Boolean {
            return false
        }

        override fun warn(msg: String) {
            log(null, WARN, msg, null, null)
        }

        override fun warn(format: String, arg: Any) {
            log(null, WARN, format, arrayOf(arg), null)
        }

        override fun warn(format: String, argArray: Array<Any>) {
            log(null, WARN, format, argArray, null)
        }

        override fun warn(format: String, arg1: Any, arg2: Any) {
            log(null, WARN, format, arrayOf(arg1, arg2), null)
        }

        override fun warn(msg: String, t: Throwable) {
            log(null, WARN, msg, null, t)
        }

        override fun isWarnEnabled(marker: Marker): Boolean {
            return false
        }

        override fun warn(marker: Marker, msg: String) {
            log(marker, WARN, msg, null, null)
        }

        override fun warn(marker: Marker, format: String, arg: Any) {
            log(marker, WARN, format, arrayOf(arg), null)
        }

        override fun warn(marker: Marker, format: String, arg1: Any, arg2: Any) {
            log(marker, WARN, format, arrayOf(arg1, arg2), null)
        }

        override fun warn(marker: Marker, format: String, argArray: Array<Any>) {
            log(marker, WARN, format, argArray, null)
        }

        override fun warn(marker: Marker, msg: String, t: Throwable) {
            log(marker, WARN, msg, null, t)
        }

        override fun isErrorEnabled(): Boolean {
            return false
        }

        override fun error(msg: String) {
            log(null, ERROR, msg, null, null)
        }

        override fun error(format: String, arg: Any) {
            log(null, ERROR, format, arrayOf(arg), null)
        }

        override fun error(format: String, arg1: Any, arg2: Any) {
            log(null, ERROR, format, arrayOf(arg1, arg2), null)
        }

        override fun error(format: String, argArray: Array<Any>) {
            log(null, ERROR, format, argArray, null)
        }

        override fun error(msg: String, t: Throwable) {
            log(null, ERROR, msg, null, t)
        }

        override fun isErrorEnabled(marker: Marker): Boolean {
            return false
        }
        /* ------------------------------------------------------------ */

        override fun error(marker: Marker, msg: String) {
            log(marker, ERROR, msg, null, null)
        }

        override fun error(marker: Marker, format: String, arg: Any) {
            log(marker, ERROR, format, arrayOf(arg), null)
        }

        override fun error(marker: Marker, format: String, arg1: Any, arg2: Any) {
            log(marker, ERROR, format, arrayOf(arg1, arg2), null)
        }

        override fun error(marker: Marker, format: String, argArray: Array<Any>) {
            log(marker, ERROR, format, argArray, null)
        }

        override fun error(marker: Marker, msg: String, t: Throwable) {
            log(marker, ERROR, msg, null, t)
        }

        private fun log(marker: Marker?, level: Int, msg: String, argArray: Array<Any>?, t: Throwable?) {
            if (argArray == null) {
                log(level, marker, msg, t)
            } else {
                val ft = MessageFormatter.arrayFormat(msg, argArray)
                log(level, marker, ft.message, t)
            }
        }

        @Suppress("UNUSED_PARAMETER")
        private fun log(level: Int, marker: Marker?, msg: String, t: Throwable?) {

        }

    }

    override fun getLoggerFactory(): ILoggerFactory = LoggerFactory

    override fun getLoggerFactoryClassStr() = ""
}

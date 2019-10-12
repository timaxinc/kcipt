package com.github.timaxinc.kcipt.result

import com.github.timaxinc.kcipt.test.notReachable
import io.kotlintest.matchers.collections.shouldContain
import io.kotlintest.shouldBe
import io.kotlintest.specs.AnnotationSpec

class ResultTest : AnnotationSpec() {

    @Test
    fun `TEST - handel successful result`() {
        createSuccess<DummyValue, DummyReport>(DummyValue).onFailure {
            notReachable()
        }.onSuccess {
            value shouldBe DummyValue
            return
        }
        notReachable()
    }

    @Test
    fun `TEST - handel successful result with reports`() {
        val dummyReport = DummyReport("unicorn")
        createSuccess<DummyValue, DummyReport>(DummyValue, listOf(dummyReport)).onFailure {
            notReachable()
        }.onSuccess {
            value shouldBe DummyValue
            reports shouldContain dummyReport
            return
        }
        notReachable()
    }

    @Test
    fun `TEST - handel failed result with report`() {
        val dummyReport = DummyReport("unicorn")
        createFailure<DummyValue, DummyReport>(listOf(dummyReport)).onFailure {
            reports shouldContain dummyReport
            return
        }.onSuccess {
            notReachable()
        }
        notReachable()
    }
}

private object DummyValue

private class DummyReport(val message: String)

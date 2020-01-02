package com.anatawa12.jctf

import com.anatawa12.jctf.CollectionsTester
import org.junit.jupiter.api.Test

/**
 * Created by anatawa12 on 2020/01/02.
 */
class CollectionsTesterListTest : CollectionsTester<Any?>() {
    override fun createCollection(): Collection<Any?> = mutableListOf()

    override fun createElement(): Any? = Any()

    @Test
    fun addAndContains() = super.addAndContainsTest()
    @Test
    fun addAllAndContains() = super.addAllAndContainsTest()
    @Test
    fun clear() = super.clearTest()
    @Test
    fun contains() = super.containsTest()
    @Test
    fun containsAll() = super.containsAllTest()
    @Test
    fun isEmpty() = super.isEmptyTest()
    @Test
    fun remove() = super.removeTest()
    @Test
    fun removeAll() = super.removeAllTest()
    @Test
    fun retainAll() = super.retainAllTest()
    @Test
    fun toArray() = super.toArrayTest()
    @Test
    fun toArrayTyped() = super.toArrayTypedTest()
}

package com.anatawa12.jctf

import com.anatawa12.jctf.CollectionsTester
import org.junit.jupiter.api.Test

/**
 * Created by anatawa12 on 2020/01/02.
 */
class ListTesterListTest : ListTester<Any?>() {
    override fun createCollection(): List<Any?> = mutableListOf()

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

    @Test
    fun addIndexed() = super.addIndexedTest()
    @Test
    fun addAllIndexed() = super.addAllIndexedTest()
    @Test
    fun indexOf() = super.indexOfTest()
    @Test
    fun lastIndexOf() = super.lastIndexOfTest()
    @Test
    fun set() = super.setTest()

    @Test
    fun equals() = super.equalsTest()
    @Test
    fun hashcode() = super.hashcodeTest()
}

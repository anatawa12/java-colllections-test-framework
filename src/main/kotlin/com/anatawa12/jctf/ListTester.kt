package com.anatawa12.jctf

import org.junit.jupiter.api.Assertions.*

/**
 * Created by anatawa12 on 2020/01/02.
 */
abstract class ListTester<E> : CollectionsTester<E>() {
    abstract override fun createCollection(): List<E>
    override fun createSizedCollection(size: Int): List<E>
            = super.createSizedCollection(size) as List<E>
    override fun createCollectionWithElements(elements: Iterable<E>): List<E>
            = super.createCollectionWithElements(elements) as List<E>

    /**
     * requires:
     * - [Collection<E>] is [MutableCollection]
     * - [MutableCollection.add] not throws UnsupportedOperationException
     * - `createCollection().contains(createElement())` returns false
     * - `(createCollection() as MutableCollection<E>).add(createElement())` not throws any exception
     * - `(createCollection() as MutableCollection<E>).add(createElement())` returns true
     */
    override fun addAndContainsTest() {
        val element = createElement()
        val list = createCollection() as MutableList<E>

        assertTrue(list.add(element)) { "add(E): collection is not modified" }
        assertEquals(list.last(), element) { "get(last): not a added element" }
    }

    /**
     * requires:
     * - [Collection<E>] is [MutableCollection]
     * - [MutableCollection.add] not throws UnsupportedOperationException
     * - `createCollection().contains(createElement())` returns false
     * - `(createCollection() as MutableCollection<E>).add(createElement())` not throws any exception
     * - `(createCollection() as MutableCollection<E>).add(createElement())` returns true
     */
    open fun addIndexedTest() {
        val element = createElement()
        val list = createSizedCollection(5) as MutableList<E>

        val oldSize = list.size
        list.add(1, element)
        assertEquals(list[1], element) { "get(add index): not a added element" }
        assertEquals(oldSize + 1, list.size) { "size: added 5 elements but size isn't increased by 5 from before" }
    }

    /**
     * requires:
     * - [Collection<E>] is [MutableCollection]
     * - [MutableCollection.add] not throws UnsupportedOperationException
     * - `createCollection().contains(createElement())` returns false
     * - `(createCollection() as MutableCollection<E>).addAll(createElement())` not throws any exception
     * - `(createCollection() as MutableCollection<E>).addAll(createElement())` returns true
     */
    override fun addAllAndContainsTest() {
        val elements = createElements(5)
        val list = createSizedCollection(5) as MutableList<E>

        val oldSize = list.size
        assertTrue(list.addAll(elements)) { "addAll(int, Collection<E>): collection is not modified" }
        assertEquals(oldSize + 5, list.size) { "size: added 5 elements but size isn't increased by 5 from before" }

        for (i in 0 until 5) {
            assertEquals(elements[i], list[oldSize + i]) { "get($i): collection does not have added element" }
        }
    }

    /**
     * requires:
     * - [Collection<E>] is [MutableCollection]
     * - [MutableCollection.add] not throws UnsupportedOperationException
     * - `createCollection().contains(createElement())` returns false
     * - `(createCollection() as MutableCollection<E>).addAll(createElement())` not throws any exception
     * - `(createCollection() as MutableCollection<E>).addAll(createElement())` returns true
     */
    open fun addAllIndexedTest() {
        val elements = createElements(5)
        val list = createSizedCollection(5) as MutableList<E>

        val oldSize = list.size
        assertTrue(list.addAll(1, elements)) { "addAll(int, Collection<E>): collection is not modified" }
        assertEquals(oldSize + 5, list.size) { "size: added 5 elements but size isn't increased by 5 from before" }

        for (i in 0 until 5) {
            assertEquals(elements[i], list[1 + i]) { "get($i): collection does not have added element" }
        }
    }

    open fun indexOfTest() {
        val e0 = createElement()
        val e1 = createElement()
        val e2 = createElement()
        val list = createCollectionWithElements(listOf(e0, e1, e2, e1, e0))
        assertEquals(0, list.indexOf(e0)) { "indexOf()" }
        assertEquals(1, list.indexOf(e1)) { "indexOf()" }
        assertEquals(2, list.indexOf(e2)) { "indexOf()" }
    }

    open fun lastIndexOfTest() {
        val e0 = createElement()
        val e1 = createElement()
        val e2 = createElement()
        val list = createCollectionWithElements(listOf(e0, e1, e2, e1, e0))
        assertEquals(4, list.lastIndexOf(e0)) { "lastIndexOf()" }
        assertEquals(3, list.lastIndexOf(e1)) { "lastIndexOf()" }
        assertEquals(2, list.lastIndexOf(e2)) { "lastIndexOf()" }
    }

    // no test for listIterator now

    open fun setTest() {
        val list = createSizedCollection(5) as MutableList<E>
        val element = createElement()
        list[2] = element
        assertEquals(element, list[2]) { "get(2)" }
    }
}

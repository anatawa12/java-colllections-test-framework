@file:Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")

package com.anatawa12.jctf

import org.junit.jupiter.api.Assertions.*
import java.util.Collection as JCollection

/**
 * Created by anatawa12 on 2020/01/02.
 */
/**
 * @param E type of element
 */
@Suppress("UNCHECKED_CAST")
abstract class CollectionsTester<E> {
    abstract fun createCollection(): Collection<E>
    abstract fun createElement(): E
    open fun createSizedCollection(size: Int): Collection<E> = createCollectionWithElements((0 until size).map { createElement() })
    open fun createCollectionWithElements(elements: Iterable<E>): Collection<E> {
        val collection = createCollection()
        (collection as MutableCollection<E>).apply {
            for (element in elements) check(add(element)) { "add(E): returns false" }
        }
        return collection
    }

    open fun createElements(count: Int): List<E> {
        val elements = mutableListOf<E>()
        repeat(count) {
            elements += createElement()
        }

        assertEquals(elements.toSet().size, count) { "createElement(): must returns unique element" }

        return elements
    }

    /**
     * requires:
     * - [Collection<E>] is [MutableCollection]
     * - [MutableCollection.add] not throws UnsupportedOperationException
     * - `createCollection().contains(createElement())` returns false
     * - `(createCollection() as MutableCollection<E>).add(createElement())` not throws any exception
     * - `(createCollection() as MutableCollection<E>).add(createElement())` returns true
     */
    open fun addAndContainsTest() {
        val element = createElement()
        val collection = createCollection() as MutableCollection<E>

        assertFalse(collection.contains(element)) { "createElement(): collection has result of `createElement()`" }
        assertTrue(collection.add(element)) { "add(E): collection is not modified" }
        assertTrue(collection.contains(element)) { "contains(E): collection does not have added element" }
    }

    /**
     * requires:
     * - [Collection<E>] is [MutableCollection]
     * - [MutableCollection.add] not throws UnsupportedOperationException
     * - `createCollection().contains(createElement())` returns false
     * - `(createCollection() as MutableCollection<E>).addAll(createElement())` not throws any exception
     * - `(createCollection() as MutableCollection<E>).addAll(createElement())` returns true
     */
    open fun addAllAndContainsTest() {
        val elements = createElements(5)
        val collection = createCollection() as MutableCollection<E>

        elements.forEach { element ->
            assertFalse(collection.contains(element)) { "createElement(): collection has result of `createElement()`" }
        }

        val oldSize = collection.size
        assertTrue(collection.addAll(elements)) { "addAll(Collection<E>): collection is not modified" }
        assertEquals(collection.size, oldSize + 5) { "size: added 5 elements but size isn't increased by 5 from before" }

        elements.forEach { element ->
            assertTrue(collection.contains(element)) { "contains(E): collection does not have added element" }
        }
    }

    /**
     * requires:
     * - [Collection<E>] is [MutableCollection]
     * - [MutableCollection.clear] not throws UnsupportedOperationException
     */
    open fun clearTest() {
        val collection = createSizedCollection(5) as MutableCollection<E>

        collection.clear()

        @Suppress("ReplaceSizeZeroCheckWithIsEmpty")
        assertEquals(collection.size, 0) { "size(): cleared collection but size is not 0" }
    }

    /**
     * requires:
     * - [Collection.iterator] must work well
     */
    open fun containsTest() {
        val unAddedElement = createElement()
        val collection = createSizedCollection(5)

        for (e in collection) {
            assertTrue(collection.contains(e)) { "contains(E): contains with a element of the collection" }
        }

        assertFalse(collection.contains(unAddedElement)) { "contains(E): contains with a non-element" }
    }

    open fun containsAllTest() {
        val unAddedElements = createElements(5)
        val collection = createSizedCollection(5)

        assertTrue(collection.containsAll(collection.toList())) {
            "containsAll(Collection<E>): collection which all element is contain in target collection"
        }

        assertFalse(collection.containsAll(unAddedElements.toList())) {
            "containsAll(Collection<E>): collection which all element is not contain in target collection"
        }

        assertFalse(collection.containsAll(unAddedElements.toList() + collection)) {
            "containsAll(Collection<E>): collection which some element is not contain in target collection"
        }
    }


    /**
     * requires:
     * - [Collection<E>] is [MutableCollection]
     * - [MutableCollection.clear] not throws UnsupportedOperationException
     */
    open fun isEmptyTest() {
        val collection = createSizedCollection(5) as MutableCollection<E>

        assertFalse(collection.isEmpty()) { "isEmpty()" }

        collection.clear()

        assertTrue(collection.isEmpty()) { "isEmpty()" }
    }

    // no test for iterator now

    open fun removeTest() {
        val collection = createSizedCollection(5) as MutableCollection<E>

        val firstElement = collection.first()
        assertTrue(collection.remove(firstElement)) { "remove(E): remove element" }

        assertFalse(collection.contains(firstElement)) { "contains(E): contains removed element" }

        assertTrue(collection.size <= 4) { "size: ${collection.size}: size is not decrease" }
    }

    open fun removeAllTest() {
        val collection = createSizedCollection(5) as MutableCollection<E>

        val removeElements = mutableListOf<E>()
        for (e in collection) {
            removeElements += e
            if (removeElements.size == 2) break
        }

        assertTrue(collection.removeAll(removeElements)) { "removeAll(Collection<E>): remove element" }

        assertFalse(collection.containsAll(removeElements)) { "containsAll(Collection<E>): contains removed element" }

        assertTrue(collection.size <= 3) { "size: ${collection.size}: size is not decrease" }
    }

    open fun retainAllTest() {
        val collection = createSizedCollection(5) as MutableCollection<E>

        val retainElements = mutableListOf<E>()
        val removeElements = mutableListOf<E>()
        for (e in collection) {
            if (retainElements.size == 2) {
                removeElements += e
            } else {
                retainElements += e
            }
        }

        assertTrue(collection.retainAll(retainElements)) { "removeAll(Collection<E>): retain element" }

        assertTrue(collection.containsAll(retainElements)) { "containsAll(Collection<E>): contains removed element" }
        assertFalse(collection.containsAll(removeElements)) { "containsAll(Collection<E>): contains removed element" }

        assertTrue(collection.size <= 3) { "size: ${collection.size}: size is not decrease" }
    }
    
    // no test for size

    open fun toArrayTest() {
        val collection = createSizedCollection(5)
        val array =  (collection as JCollection<E>).toArray()
        assertEquals(collection.size, array.size) { "toArray().size" }
        collection.zip(array).mapIndexed { index, (a, b) ->
            assertEquals(a, b) { "toArray()[$index]" }
        }
    }

    open fun toArrayTypedTest() {
        val collection = createSizedCollection(5)
        val array =  (collection as JCollection<E>).toArray(emptyArray<Any?>())
        assertEquals(collection.size, array.size) { "toArray().size" }
        collection.zip(array).mapIndexed { index, (a, b) ->
            assertEquals(a, b) { "toArray(T[])[$index]" }
        }
    }
}

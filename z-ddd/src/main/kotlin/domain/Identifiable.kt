package domain

/**
 *
 */
interface Identifiable<ID : Identifier> {
    fun getId(): ID
}
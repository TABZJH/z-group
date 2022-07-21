package domain

/**
 * 可获取ID
 */
interface Identifiable<ID : Identifier?> {
    fun getId(): ID
}
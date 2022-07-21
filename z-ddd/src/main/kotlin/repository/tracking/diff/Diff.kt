package repository.tracking.diff

interface Diff {

    fun getFieldName(): String

    fun getFieldType(): Class<*>

    fun getType(): DiffType

    fun getOldVal(): Any?

    fun getNewVal(): Any?

    fun isEmpty(): Boolean
}
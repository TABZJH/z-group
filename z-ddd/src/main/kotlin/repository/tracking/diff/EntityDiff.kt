package repository.tracking.diff

class EntityDiff : Diff, Iterable<Map.Entry<String, Diff>> {

    private var fieldName: String = ""
    private var fieldType: Class<*> = Any::class.java
    private var type: DiffType = DiffType.Unchanged
    private var oldVal: Any? = null
    private var newVal: Any? = null

    companion object {
        var EMPTY: EntityDiff = EntityDiff()
    }

    override fun getFieldName(): String {
        return fieldName
    }

    fun setFieldName(fieldName: String) {
        this.fieldName = fieldName
    }

    override fun getFieldType(): Class<*> {
        return fieldType
    }

    fun setFieldType(fieldType: Class<*>) {
        this.fieldType = fieldType
    }

    override fun getType(): DiffType {
        return type
    }

    fun setType(diffType: DiffType) {
        this.type = diffType
    }

    override fun getOldVal(): Any? {
        return oldVal
    }

    override fun getNewVal(): Any? {
        return newVal
    }

    private var diffMap: HashMap<String, Diff> = HashMap()

    override fun isEmpty(): Boolean {
        return diffMap.isEmpty()
    }

    override fun iterator(): Iterator<Map.Entry<String, Diff>> {
        return diffMap.entries.iterator()
    }

    fun getDiff(fieldName: String): Diff? {
        return diffMap[fieldName]
    }

    fun addDiff(diff: Diff) {
        diffMap[diff.getFieldName()] = diff
    }

    fun setOldVal(oldVal: Any?) {
        this.oldVal = oldVal
    }

    fun setNewVal(newVal: Any?) {
        this.newVal = newVal
    }
}
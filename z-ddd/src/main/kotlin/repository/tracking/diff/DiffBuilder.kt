package repository.tracking.diff

import java.lang.reflect.Field

class DiffBuilder() {

    private var field: Field? = null
    private var oldVal: Any? = null
    private var newVal: Any? = null

    private val list: List<Diff> = ArrayList()

    constructor(field: Field, oldVal: Any?, newVal: Any?) : this() {
        this.field = field
        this.oldVal = oldVal
        this.newVal = newVal
    }

    fun build(): EntityDiff {
        val entityDiff = EntityDiff()
        field?.let {
            entityDiff.setFieldType(it.type)
            entityDiff.setFieldName(it.name)
        }
        list.forEach { entityDiff.addDiff(it) }
        if (list.isNotEmpty()) {
            entityDiff.setType(DiffType.Modified)
        }
        entityDiff.setOldVal(oldVal)
        entityDiff.setNewVal(oldVal)
        return entityDiff
    }


}
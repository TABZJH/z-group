package repository.tracking

import domain.Entity
import domain.Identifier
import repository.tracking.diff.EntityDiff
import repository.tracking.diff.EntityDiff.Companion.EMPTY
import java.lang.reflect.Field

class DiffUtils {

    companion object {

        fun <T : Entity<ID>, ID : Identifier> buildMap(values: List<T>): Map<ID, T> {
            val map = HashMap<ID, T>()
            values.forEach {
                map[it.getId()] = it
            }
            return map
        }

        fun <T> diff(oldVal: T?, newVal: T): EntityDiff {
            return entityDiff(null, oldVal, newVal)
        }

        private fun <T> entityDiff(field: Field?, oldVal: T?, newVal: T): EntityDiff {
            return EMPTY
        }
    }
}
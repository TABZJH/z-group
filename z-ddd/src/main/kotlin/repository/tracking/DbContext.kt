package repository.tracking

import domain.Aggregate
import domain.Identifier
import lombok.Getter
import repository.tracking.diff.EntityDiff


internal class DbContext<T : Aggregate<ID?>, ID : Identifier?>(@field:Getter private val aggregateClass: Class<out T>) {
    private val aggregateMap: HashMap<ID, T> = HashMap()
    fun attach(aggregate: T) {
        if (!aggregateMap.containsKey(aggregate.getId())) {
            merge(aggregate)
        }
    }

    fun detach(aggregate: T) {
        aggregateMap.remove(aggregate.getId())
    }

    fun detectChanges(aggregate: T): EntityDiff {
        if (aggregate.getId() == null) {
            return EntityDiff.EMPTY
        }
        val snapshot = aggregateMap[aggregate.getId()]
        if (snapshot == null) {
            attach(aggregate)
        }
        return DiffUtils.diff(snapshot, aggregate)
    }

    fun find(id: ID): T? {
        return aggregateMap[id]
    }

    fun merge(aggregate: T) {
        aggregate.getId()?.let {
            val snapshot: T = TODO()
            aggregateMap[it] = snapshot
        }
    }

    fun setId(aggregate: T, id: ID) {
        TODO()
    }
}
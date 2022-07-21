package repository.tracking

import domain.Aggregate
import domain.Identifier
import repository.tracking.diff.EntityDiff


interface AggregateManager<A : Aggregate<ID?>, ID : Identifier?> {

    fun attach(aggregate: A)

    fun attach(aggregate: A, id: ID)

    fun detach(aggregate: A)

    fun find(id: ID): A?

    fun detectChanges(aggregate: A): EntityDiff

    fun merge(aggregate: A)

    companion object {
        fun <A : Aggregate<ID?>, ID : Identifier?> newInstance(targetClass: Class<A>): AggregateManager<A, ID> {
            return ThreadLocalAggregateManager(targetClass)
        }
    }
}
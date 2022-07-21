package repository.tracking

import domain.Aggregate
import domain.Identifier
import repository.tracking.diff.EntityDiff

class ThreadLocalAggregateManager<A : Aggregate<ID?>, ID : Identifier?> constructor(aTargetClass: Class<out A>) :
    AggregateManager<A, ID> {

    private var context: ThreadLocal<DbContext<A, ID>> = ThreadLocal.withInitial { DbContext(targetClass) }
    private var targetClass: Class<out A> = aTargetClass

    override fun attach(aggregate: A) {
        context.get().attach(aggregate)
    }

    override fun attach(aggregate: A, id: ID) {
        context.get().setId(aggregate, id)
        context.get().attach(aggregate)
    }

    override fun detach(aggregate: A) {
        context.get().detach(aggregate)
    }

    override fun find(id: ID): A? {
        return context.get().find(id)
    }

    override fun detectChanges(aggregate: A): EntityDiff {
        return context.get().detectChanges(aggregate)
    }

    override fun merge(aggregate: A) {
        context.get().merge(aggregate)
    }
}
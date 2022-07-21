package repository

import domain.Aggregate
import domain.Identifier
import lombok.AccessLevel
import lombok.Getter
import repository.tracking.AggregateManager
import repository.tracking.diff.EntityDiff


/**
 * 这个类是一个通用的支撑类，为了减少开发者的重复劳动。在用的时候需要继承这个类
 */
abstract class DbRepositorySupport<T : Aggregate<ID?>, ID : Identifier?> protected constructor(@field:Getter private val targetClass: Class<T>) :
    Repository<T, ID> {
    /**
     * 让AggregateManager去维护Snapshot
     */
    @Getter(AccessLevel.PROTECTED)
    private val aggregateManager: AggregateManager<T, ID> = AggregateManager.newInstance(targetClass)

    /**
     * 这几个方法是继承的子类应该去实现的
     */
    protected abstract fun onInsert(aggregate: T)
    protected abstract fun onSelect(id: ID): T?
    protected abstract fun onUpdate(aggregate: T, diff: EntityDiff)
    protected abstract fun onDelete(aggregate: T)

    /**
     * Attach的操作就是让Aggregate可以被追踪
     */
    override fun attach(aggregate: T) {
        aggregateManager.attach(aggregate)
    }

    /**
     * Detach的操作就是让Aggregate停止追踪
     */
    override fun detach(aggregate: T) {
        aggregateManager.detach(aggregate)
    }

    override fun find(id: ID): T? {
        val aggregate: T? = onSelect(id)
        // 这里的就是让查询出来的对象能够被追踪。
        // 如果自己实现了一个定制查询接口，要记得单独调用attach。
        aggregate?.let { attach(it) }
        return aggregate
    }

    override fun remove(aggregate: T) {
        onDelete(aggregate)
        // 删除停止追踪
        detach(aggregate)
    }

    override fun save(aggregate: T) {
        // 如果没有ID，直接插入
        if (aggregate.getId() == null) {
            onInsert(aggregate)
            attach(aggregate)
            return
        }

        // 做Diff
        val diff: EntityDiff = aggregateManager.detectChanges(aggregate)
        if (diff.isEmpty()) {
            return
        }

        // 调用UPDATE
        onUpdate(aggregate, diff)

        // 最终将DB带来的变化更新回AggregateManager
        aggregateManager.merge(aggregate)
    }
}
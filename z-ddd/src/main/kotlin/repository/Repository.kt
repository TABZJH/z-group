package repository

import domain.Aggregate
import domain.Identifier


interface Repository<A : Aggregate<ID>, ID : Identifier> {

    /**
     * 将一个Aggregate附属到一个Repository，让它变为可追踪。
     * Change-Tracking在下文会讲，非必须
     */
    fun attach(aggregate: A)

    /**
     * 解除一个Aggregate的追踪
     * Change-Tracking在下文会讲，非必须
     */
    fun detach(aggregate: A)

    /**
     * 通过ID寻找Aggregate。
     * 找到的Aggregate自动是可追踪的
     */
    fun find(id: ID): A?

    /**
     * 将一个Aggregate从Repository移除
     * 操作后的aggregate对象自动取消追踪
     */
    fun remove(aggregate: A)

    /**
     * 保存一个Aggregate
     * 保存后自动重置追踪条件
     */
    fun save(aggregate: A)
}
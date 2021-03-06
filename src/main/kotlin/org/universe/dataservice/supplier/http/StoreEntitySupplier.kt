package org.universe.dataservice.supplier.http

import org.koin.core.component.KoinComponent
import org.universe.dataservice.data.ProfileId
import org.universe.dataservice.data.ProfileSkin

/**
 * [EntitySupplier] that delegates to another [EntitySupplier] to resolve entities.
 *
 * Resolved entities will always be stored in [cache] if it wasn't null or empty for flows.
 */
public class StoreEntitySupplier(private val supplier: EntitySupplier) : EntitySupplier, KoinComponent {

    private val cache: CacheEntitySupplier get() = EntitySupplier.cache

    override suspend fun getId(name: String): ProfileId? {
        return supplier.getId(name)?.also { cache.save(it) }
    }

    override suspend fun getSkin(uuid: String): ProfileSkin? {
        return supplier.getSkin(uuid)?.also { cache.save(it) }
    }
}

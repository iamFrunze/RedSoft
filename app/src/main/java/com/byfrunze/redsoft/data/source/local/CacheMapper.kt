package com.byfrunze.redsoft.data.source.local

import com.byfrunze.redsoft.data.model.Data
import com.byfrunze.redsoft.data.model.Product
import com.byfrunze.redsoft.util.EntityMapper
import javax.inject.Inject

class CacheMapper
@Inject
constructor() :
    EntityMapper<ProductCacheEntity, Product> {

    override fun mapFromEntity(entity: ProductCacheEntity): Product {
        return Product(
            id = entity.id,
            title = entity.title,
            short_description = entity.short_description,
            image_url = entity.image_url,
            amount = entity.amount,
            price = entity.price,
            producer = entity.producer,
            catergories = entity.catergories,
        )
    }

    override fun mapToEntity(domainModel: Product): ProductCacheEntity {
        return ProductCacheEntity(
            id = domainModel.id,
            title = domainModel.title,
            short_description = domainModel.short_description,
            image_url = domainModel.image_url,
            amount = domainModel.amount,
            price = domainModel.price,
            producer = domainModel.producer,
            catergories = domainModel.catergories,
        )
    }

    fun mapFromEntityList(entities: List<ProductCacheEntity>): List<Product> {
        return entities.map { mapFromEntity(it) }
    }
}

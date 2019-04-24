package dev.ricardoantolin.cabifystorage.storage

import dev.ricardoantolin.cabifystorage.storage.extensions.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.realm.Realm
import io.realm.RealmObject
import io.realm.RealmQuery

abstract class RealmService<T : RealmObject, ID> {

    inline fun <reified T : RealmObject> findByPrimaryKey(id: ID): Flowable<T> =
        getPrimaryKeyFieldName(T::class.java)
            ?.let {
                Realm.getDefaultInstance()
                    .where(T::class.java)
                    .equalTo(it, "$id")
                    .findFirstAsync()
                    .asFlowable<T>()
                    .addRealmSchedulers()
            } ?: throw IllegalArgumentException("object.not.have.primary.key")

    inline fun <reified T : RealmObject> findAll(): Flowable<List<T>> = with(Realm.getDefaultInstance()) {
        where(T::class.java)
            .findAllAsync()
            .toFlowableList(this)
    }

    fun findByQuery(query: RealmQuery<T>): Flowable<List<T>> =
        query.findAllAsync()
            .toFlowableList(Realm.getDefaultInstance())

    fun save(entity: T): Completable =
        entity.saveManaged()

    fun save(entities: List<T>): Completable =
        entities.saveAllManaged()

    fun delete(entity: T): Completable =
        entity.deleteManaged()

    fun delete(query: RealmQuery<T>): Completable =
        query.findAllAsync().deleteAllManaged()
}
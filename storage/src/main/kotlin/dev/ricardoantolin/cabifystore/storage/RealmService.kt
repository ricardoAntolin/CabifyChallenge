package dev.ricardoantolin.cabifystore.storage

import dev.ricardoantolin.cabifystore.storage.extensions.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.realm.Realm
import io.realm.RealmObject
import io.realm.RealmQuery

abstract class RealmService<T : RealmObject, ID> {

    inline fun <reified T : RealmObject> findByPrimaryKey(id: ID): Flowable<T> =
        Flowable.defer {
            getPrimaryKeyFieldName(T::class.java)
                ?.let {
                    Realm.getDefaultInstance()
                        .where(T::class.java)
                        .equalTo(it, "$id")
                        .findFirstAsync()
                        .asFlowable<T>()
                } ?: throw IllegalArgumentException("object.not.have.primary.key")
        }.addRealmSchedulers()

    inline fun <reified T : RealmObject> findAll(): Flowable<List<T>> =
        Flowable.defer {
            with(Realm.getDefaultInstance()) {
                where(T::class.java)
                    .findAllAsync()
                    .toFlowableList(this)
            }
        }.addRealmSchedulers()

    fun findByQuery(query: RealmQuery<T>): Flowable<List<T>> =
        Flowable.defer {
            query.findAllAsync()
                .toFlowableList(Realm.getDefaultInstance())
        }.addRealmSchedulers()

    fun save(entity: T): Completable =
        Completable.defer {
            entity.saveManaged()
        }.addRealmSchedulers()

    fun save(entities: List<T>): Completable =
        Completable.defer {
            entities.saveAllManaged()
        }.addRealmSchedulers()

    fun delete(entity: T): Completable =
        Completable.defer {
            entity.deleteManaged()
        }.addRealmSchedulers()

    fun delete(query: RealmQuery<T>): Completable =
        Completable.defer {
            query.findAllAsync().deleteAllManaged()
        }.addRealmSchedulers()
}
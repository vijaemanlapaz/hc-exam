package vj.com.myapplication.databases

import android.util.Log
import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmQuery
import vj.com.myapplication.models.City

/**
 *
 * Created by VJ on 8/20/2017.
 */

class RealmController private constructor() {
    private val TAG = RealmController::class.java.simpleName
    val realm: Realm

    val city: City get() = realm.where(City::class.java).findFirst()
    val cities: RealmQuery<City>? get() = realm.where(City::class.java)

    init {
        /*RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(2) // Must be bumped when the schema changes
                .migration(new MigrationConfiguration()) // Migration to run instead of throwing an exception
                .build();
        Realm.setDefaultConfiguration(config);*/

        realm = Realm.getDefaultInstance()
    }

    fun refresh() {
        realm.refresh()
    }

    fun deleteAll(`object`: Class<out RealmModel>) {
        Log.e(TAG, "=== Delete All ===")
        realm.beginTransaction()
        realm.delete(`object`)
        realm.commitTransaction()
    }

    fun delete() {
        Log.e(TAG, "=== Delete ===")
        realm.beginTransaction()
        realm.deleteAll()
        realm.commitTransaction()
    }

    fun insertOrUpdate(obj: RealmModel) {
        Log.e(TAG, "=== insertOrUpdate ===")
        realm.beginTransaction()
        realm.insertOrUpdate(obj)
        realm.commitTransaction()
    }

    fun insertOrUpdate(collection: Collection<RealmModel>) {
        Log.e(TAG, "=== insertOrUpdate ===")
        realm.beginTransaction()
        realm.insertOrUpdate(collection)
        realm.commitTransaction()
    }

    fun <T : RealmModel> copyToRealmOrUpdate(obj: T): T {
        realm.executeTransaction { realm -> realm.copyToRealmOrUpdate(obj) }
        return obj
    }

    fun <E : RealmModel> copyFromRealm(`object`: E): E {
        return realm.copyFromRealm(`object`)
    }

    fun <E : RealmModel> copyFromRealm(`object`: Iterable<E>): List<E> {
        return realm.copyFromRealm(`object`)
    }


    fun <E : RealmModel> getAll(clazz: Class<E>): RealmQuery<E> {
        var result: RealmQuery<E>
        realm.beginTransaction()
        result = realm.where(clazz)
        realm.commitTransaction()
        return result
    }

    companion object {

        private var instance: RealmController? = null

        fun with(): RealmController {
            if (instance == null) {
                instance = RealmController()
            }
            return instance!!
        }
    }
}
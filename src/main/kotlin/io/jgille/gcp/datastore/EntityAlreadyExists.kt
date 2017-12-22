package io.jgille.gcp.datastore

import com.google.cloud.datastore.DatastoreException
import com.google.cloud.datastore.Entity
import java.util.*

class EntityAlreadyExists {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val datastore = DatastoreService.setupDatastore()

            val key = datastore.newKeyFactory()
                    .setKind("test")
                    .newKey(UUID.randomUUID().toString())
            val entity = Entity.newBuilder(key)
                    .set("message", "Hello world")
                    .build()
            
            datastore.add(entity)
            try {
                datastore.add(entity)
            } catch (e: DatastoreException) {
                e.printStackTrace()
            }
        }

    }
}
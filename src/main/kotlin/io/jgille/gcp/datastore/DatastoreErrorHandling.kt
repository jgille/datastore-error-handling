package io.jgille.gcp.datastore

import com.google.cloud.NoCredentials
import com.google.cloud.ServiceOptions
import com.google.cloud.datastore.Datastore
import com.google.cloud.datastore.DatastoreException
import com.google.cloud.datastore.DatastoreOptions
import com.google.cloud.datastore.Entity
import com.google.cloud.http.HttpTransportOptions
import java.util.*

object DatastoreService {

    fun setupDatastore(): Datastore {
        val transportOptions = HttpTransportOptions.newBuilder()
                .setConnectTimeout(5000)
                .setReadTimeout(5000).build()

        val builder = DatastoreOptions.newBuilder()
                .setTransportOptions(transportOptions)
                .setRetrySettings(ServiceOptions.getNoRetrySettings())
                .setNamespace("error_handling")
                .setHost("127.0.0.1:8438")
                .setProjectId("test")
                .setCredentials(NoCredentials.getInstance())
        @Suppress("UsePropertyAccessSyntax")
        return builder.build().getService()
    }
}

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
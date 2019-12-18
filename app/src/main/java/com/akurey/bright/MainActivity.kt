package com.akurey.bright

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.amazonaws.mobile.client.AWSMobileClient;
import android.util.Log
import android.widget.Button
import com.akurey.bright.AWSModel.TimeLogDO
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread
import io.realm.Realm
import io.realm.RealmConfiguration

class MainActivity : AppCompatActivity() {

    private var dynamoDBMapper: DynamoDBMapper? = null

    companion object {
        private val TAG: String = this::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AWSSetup()
        initRealm()
        // get reference to button
        val btn_click_me = findViewById(R.id.add_button) as Button
        btn_click_me.setOnClickListener {
            createLog()
        }
    }

    private fun initRealm() {
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .schemaVersion(1)
            .build()
        Realm.setDefaultConfiguration(config)
        val realm = Realm.getInstance(config)
        realm.close()
    }

    private fun AWSSetup() {
        AWSMobileClient.getInstance().initialize(this) {
            Log.d(TAG, "AWSMobileClient is initialized")
        }.execute()

        val client = AmazonDynamoDBClient(AWSMobileClient.getInstance().credentialsProvider)
        dynamoDBMapper = DynamoDBMapper.builder()
            .dynamoDBClient(client)
            .awsConfiguration(AWSMobileClient.getInstance().configuration)
            .build()
    }


    fun createLog() {
        val logItem = TimeLogDO()
        logItem.timeLogId = UUID.randomUUID().toString()
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val endDate = sdf.format(Date())
        val sartDate = sdf.format(Date().time - 10 * 60 * 60 * 1000)
        logItem.startDate = sartDate
        logItem.endDate = endDate
        logItem.firstName = "Esteban"
        logItem.lastName = "Brenes"
        logItem.facility = "Akurey"
        thread(start = true) {
            dynamoDBMapper?.save(logItem)
        }
    }
}

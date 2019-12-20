package com.akurey.bright

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.akurey.bright.AWSModel.EmployeeDO
import com.akurey.bright.AWSModel.TimeLogDO
import com.akurey.bright.repository.EmployeeRepository
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import io.realm.Realm
import io.realm.RealmConfiguration
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread


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
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = PinFragment.newInstance()
        fragment.parent = this
        transaction.add(R.id.container, fragment)
        transaction.addToBackStack(fragment::class.java.name)
        transaction.commit()
        // get reference to button
//        val btn_click_me = findViewById(R.id.add_button) as Button
//        btn_click_me.setOnClickListener {
//            getEmployee("1234") {
//                Toast.makeText(this, it?.firstName, Toast.LENGTH_LONG).show()
//            }
//        }
    }

    private fun initRealm() {
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .schemaVersion(1)
            .build()
        Realm.setDefaultConfiguration(config)
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

    private fun createLog() {
        EmployeeRepository.getInstance().getEmployee()?.let { employee ->
            val logItem = TimeLogDO()
            logItem.timeLogId = UUID.randomUUID().toString()
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss aa", Locale.getDefault())
            val endDate = sdf.format(Date())
            val sartDate = sdf.format(Date().time - 10 * 60 * 60 * 1000)
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            val dateTime = sdf.format(Date())

            logItem.dateTime = dateTime
            logItem.startDate = sartDate
            logItem.endDate = endDate
            logItem.firstName = employee.firstName
            logItem.lastName = employee.lastName
            logItem.facility = employee.facilityName
            logItem.employeeId = employee.employeeId
//            private String _dateTime;
//            private Double _hours;

            thread(start = true) {
                dynamoDBMapper?.save(logItem)
            }
        }
    }

    private fun createEmployee() {
        val employee = EmployeeDO()
        employee.employeeId = UUID.randomUUID().toString()
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        employee.dateCreated = sdf.format(Date())
        employee.firstName = "Esteban"
        employee.lastName = "Brenes"
        employee.facilityName = "Akurey"
        employee.enabled = true
        employee.pinActive = true
        employee.pin = "1234"

        thread(start = true) {
            dynamoDBMapper?.save(employee)
        }
        EmployeeRepository.getInstance().saveEmployee(employee)
    }


    fun getEmployee(pin: String, callback: (EmployeeDO?) -> Unit) {
        thread(start = true) {
            val expressionAttributeValues: MutableMap<String, AttributeValue> = HashMap()
            expressionAttributeValues[":pin"] = AttributeValue().withS(pin)
            expressionAttributeValues[":pinActive"] = AttributeValue().withN("1")
            expressionAttributeValues[":enabled"] = AttributeValue().withN("1")
            val queryExpression = DynamoDBScanExpression()
                .withFilterExpression("pin = :pin and pinActive = :pinActive and enabled = :enabled")
                .withExpressionAttributeValues(expressionAttributeValues)
            val result = dynamoDBMapper?.scan(EmployeeDO::class.java, queryExpression)
            runOnUiThread { callback(result?.first()) }
        }
    }
}

package com.akurey.bright

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.akurey.bright.AWSModel.EmployeeDO
import com.akurey.bright.AWSModel.TimeLogDO
import com.akurey.bright.data.Employee
import com.akurey.bright.repository.EmployeeRepository
import com.amazonaws.AmazonClientException
import com.amazonaws.AmazonServiceException
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
        EmployeeRepository.getInstance().getEmployee()?.let {
            goToTimeLog(it)
        } ?: run {
            goToPin()
        }
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

    fun createLog(
        employee: Employee,
        startDate: Date,
        endDate: Date,
        hours: Double
    ) {
        val logItem = TimeLogDO()
        logItem.timeLogId = UUID.randomUUID().toString()
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm aa", Locale.getDefault())
        logItem.startDate = sdf.format(startDate)
        logItem.endDate = sdf.format(endDate)
        sdf.applyPattern("yyyy/MM/dd HH:mm:ssZ")
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        logItem.dateTime = sdf.format(Date())
        logItem.firstName = employee.firstName
        logItem.lastName = employee.lastName
        logItem.facility = employee.facilityName
        logItem.employeeId = employee.employeeId
        logItem.hours = hours
        thread(start = true) {
            val spinner = this.findViewById<ProgressBar>(R.id.progressBar)
            runOnUiThread { spinner.visibility = View.VISIBLE }

            try {
                dynamoDBMapper?.save(logItem)
                runOnUiThread {
                    spinner.visibility = View.INVISIBLE
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Time Reported")
                    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm aa")
                    builder.setMessage(sdf.format(startDate) + " - " + sdf.format(endDate))
                    builder.setPositiveButton(android.R.string.ok) { dialog, id ->
                        dialog.dismiss()
                    }
                    builder.show()
                }

            } catch (t: Throwable) {
                runOnUiThread {
                    spinner.visibility = View.INVISIBLE
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle(R.string.error)
                    builder.setMessage("something went wrong")
                    builder.setPositiveButton(android.R.string.ok) { dialog, id ->
                        dialog.dismiss()
                    }
                    builder.show()
                }

            }
        }
    }
//
//    fun createEmployee() {
//        val employee = EmployeeDO()
//        employee.employeeId = UUID.randomUUID().toString()
//        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm aa")
//        employee.dateCreated = sdf.format(Date())
//        employee.firstName = "James"
//        employee.lastName = "Smith"
//        employee.facilityName = "Akurey"
//        employee.enabled = true
//        employee.pinActive = true
//        employee.pin = "2345"
//
//        thread(start = true) {
//            dynamoDBMapper?.save(employee)
//        }
//       // EmployeeRepository.getInstance().saveEmployee(employee)
//    }

    fun goToTimeLog(employee: Employee) {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = TimeLogFragment.newInstance()
        fragment.parent = this
        fragment.employee = employee
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    fun goToPin() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = PinFragment.newInstance()
        fragment.parent = this
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    fun setPinInactive(employee: EmployeeDO) {
        employee.pinActive = false
        thread(start = true) {
            dynamoDBMapper?.save(employee)
        }
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
            runOnUiThread { callback(result?.firstOrNull()) }
        }
    }
}


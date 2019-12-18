package com.akurey.bright.repository

import com.akurey.bright.AWSModel.EmployeeDO
import com.akurey.bright.data.Employee
import io.realm.Realm

class EmployeeRepository: EmployeeDataSource {

    // region Static
    companion object {
        private object SingletonHelper {
            internal var INSTANCE: EmployeeRepository? = null
        }

        fun getInstance(): EmployeeRepository {
            if (SingletonHelper.INSTANCE === null)
                SingletonHelper.INSTANCE = EmployeeRepository()
            return SingletonHelper.INSTANCE!!
        }
    }

    override fun saveEmployee(awsEmployee: EmployeeDO) {
        val realm = Realm.getDefaultInstance()
        val employee = Employee(awsEmployee)
        realm.beginTransaction()

        try {
            realm.copyToRealmOrUpdate(employee)
            realm.commitTransaction()
        } catch (e: Exception) {
            realm.cancelTransaction()
        } finally {
            realm.close()
        }
    }

    override fun getEmployee(): Employee? {
        val realm = Realm.getDefaultInstance()
        return realm.where(Employee::class.java).findFirst()
    }

}
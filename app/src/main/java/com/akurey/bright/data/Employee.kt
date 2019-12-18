package com.akurey.bright.data

import com.akurey.bright.AWSModel.EmployeeDO
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Employee (
    @PrimaryKey open var employeeId: String = "",
    open var dateCreated: Date = Date(),
    open var firstName: String = "",
    open var lastName: String = "",
    open var facilityName: String = "",
    open var enabled: Boolean = true,
    open var pin: String = "",
    open var pinActive: Boolean = true
) : RealmObject() {

    constructor(employee: EmployeeDO) : this() {
        this.employeeId = employee.employeeId
        this.dateCreated = Date()//employee.dateCreated
        this.firstName = employee.firstName
        this.lastName = employee.lastName
        this.facilityName = employee.facilityName
        this.enabled = employee.enabled
        this.pin = employee.pin
        this.pinActive = employee.pinActive
    }

}
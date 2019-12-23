package com.akurey.bright.repository

import com.akurey.bright.AWSModel.EmployeeDO
import com.akurey.bright.data.Employee

interface EmployeeDataSource {
    fun saveEmployee(awsEmployee: EmployeeDO): Employee?
    fun getEmployee(): Employee?
}
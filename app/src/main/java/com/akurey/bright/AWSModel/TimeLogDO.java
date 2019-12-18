package com.akurey.bright.AWSModel;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

@DynamoDBTable(tableName = "bright-mobilehub-1834366341-TimeLog")

public class TimeLogDO {
    private String _timeLogId;
    private String _dateTime;
    private String _employeeId;
    private String _endDate;
    private String _facility;
    private String _firstName;
    private Double _hours;
    private String _lastName;
    private String _startDate;

    @DynamoDBHashKey(attributeName = "timeLogId")
    @DynamoDBAttribute(attributeName = "timeLogId")
    public String getTimeLogId() {
        return _timeLogId;
    }

    public void setTimeLogId(final String _timeLogId) {
        this._timeLogId = _timeLogId;
    }
    @DynamoDBAttribute(attributeName = "dateTime")
    public String getDateTime() {
        return _dateTime;
    }

    public void setDateTime(final String _dateTime) {
        this._dateTime = _dateTime;
    }
    @DynamoDBAttribute(attributeName = "employeeId")
    public String getEmployeeId() {
        return _employeeId;
    }

    public void setEmployeeId(final String _employeeId) {
        this._employeeId = _employeeId;
    }
    @DynamoDBAttribute(attributeName = "endDate")
    public String getEndDate() {
        return _endDate;
    }

    public void setEndDate(final String _endDate) {
        this._endDate = _endDate;
    }
    @DynamoDBAttribute(attributeName = "facility")
    public String getFacility() {
        return _facility;
    }

    public void setFacility(final String _facility) {
        this._facility = _facility;
    }
    @DynamoDBAttribute(attributeName = "firstName")
    public String getFirstName() {
        return _firstName;
    }

    public void setFirstName(final String _firstName) {
        this._firstName = _firstName;
    }
    @DynamoDBAttribute(attributeName = "hours")
    public Double getHours() {
        return _hours;
    }

    public void setHours(final Double _hours) {
        this._hours = _hours;
    }
    @DynamoDBAttribute(attributeName = "lastName")
    public String getLastName() {
        return _lastName;
    }

    public void setLastName(final String _lastName) {
        this._lastName = _lastName;
    }
    @DynamoDBAttribute(attributeName = "startDate")
    public String getStartDate() {
        return _startDate;
    }

    public void setStartDate(final String _startDate) {
        this._startDate = _startDate;
    }

}

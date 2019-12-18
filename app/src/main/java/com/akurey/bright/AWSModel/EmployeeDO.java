package com.akurey.bright.AWSModel;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

@DynamoDBTable(tableName = "bright-mobilehub-1834366341-Employee")

public class EmployeeDO {
    private String _employeeId;
    private String _dateCreated;
    private Boolean _enabled;
    private String _facilityName;
    private String _firstName;
    private String _lastName;
    private String _pin;
    private Boolean _pinActive;

    @DynamoDBHashKey(attributeName = "employeeId")
    @DynamoDBAttribute(attributeName = "employeeId")
    public String getEmployeeId() {
        return _employeeId;
    }

    public void setEmployeeId(final String _employeeId) {
        this._employeeId = _employeeId;
    }
    @DynamoDBAttribute(attributeName = "dateCreated")
    public String getDateCreated() {
        return _dateCreated;
    }

    public void setDateCreated(final String _dateCreated) {
        this._dateCreated = _dateCreated;
    }
    @DynamoDBAttribute(attributeName = "enabled")
    public Boolean getEnabled() {
        return _enabled;
    }

    public void setEnabled(final Boolean _enabled) {
        this._enabled = _enabled;
    }
    @DynamoDBAttribute(attributeName = "facilityName")
    public String getFacilityName() {
        return _facilityName;
    }

    public void setFacilityName(final String _facilityName) {
        this._facilityName = _facilityName;
    }
    @DynamoDBAttribute(attributeName = "firstName")
    public String getFirstName() {
        return _firstName;
    }

    public void setFirstName(final String _firstName) {
        this._firstName = _firstName;
    }
    @DynamoDBAttribute(attributeName = "lastName")
    public String getLastName() {
        return _lastName;
    }

    public void setLastName(final String _lastName) {
        this._lastName = _lastName;
    }
    @DynamoDBAttribute(attributeName = "pin")
    public String getPin() {
        return _pin;
    }

    public void setPin(final String _pin) {
        this._pin = _pin;
    }
    @DynamoDBAttribute(attributeName = "pinActive")
    public Boolean getPinActive() {
        return _pinActive;
    }

    public void setPinActive(final Boolean _pinActive) {
        this._pinActive = _pinActive;
    }

}

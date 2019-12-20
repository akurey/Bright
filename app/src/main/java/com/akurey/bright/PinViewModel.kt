package com.akurey.bright

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PinViewModel : ViewModel() {
    private val _isDataReady = MutableLiveData<Boolean>()
    private val _firstDigit = MutableLiveData<String>()
    private val _secondDigit = MutableLiveData<String>()
    private val _thirdDigit = MutableLiveData<String>()
    private val _fourthDigit = MutableLiveData<String>()
    private val _isLoading = MutableLiveData<Boolean>()
    val isDataReady: LiveData<Boolean>
        get() = _isDataReady
    val firstDigit: LiveData<String>
        get() = _firstDigit
    val secondDigit: LiveData<String>
        get() = _secondDigit
    val thirdDigit: LiveData<String>
        get() = _thirdDigit
    val fourthDigit: LiveData<String>
        get() = _fourthDigit
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    init {
        _isDataReady.value = false
        _firstDigit.value = ""
        _secondDigit.value = ""
        _thirdDigit.value = ""
        _fourthDigit.value = ""
        _isLoading.value = false
    }

    fun initData() {
        _isDataReady.value = false
        _firstDigit.value = ""
        _secondDigit.value = ""
        _thirdDigit.value = ""
        _fourthDigit.value = ""
        _isLoading.value = false
    }

    fun setFirstDigit(value: String) {
        _firstDigit.value = value
        validateDigits()
    }

    fun setSecondDigit(value: String) {
        _secondDigit.value = value
        validateDigits()
    }

    fun setThirdDigit(value: String) {
        _thirdDigit.value = value
        validateDigits()
    }

    fun setFourthDigit(value: String) {
        _fourthDigit.value = value
        validateDigits()
    }

    fun setIsLoading(value: Boolean) {
        _isLoading.value = value
    }

    private fun validateDigits() {
        _isDataReady.value = !(_firstDigit.value!!.isEmpty() || _secondDigit.value!!.isEmpty()
                || _thirdDigit.value!!.isEmpty() || _fourthDigit.value!!.isEmpty())
    }
}

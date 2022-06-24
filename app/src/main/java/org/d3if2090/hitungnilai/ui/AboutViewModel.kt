package org.d3if2090.hitungnilai.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if2090.hitungnilai.network.About

enum class ApiStatus { LOADING, SUCCESS, FAILED }
class AboutViewModel : ViewModel() {

    private val data = MutableLiveData<List<AboutModel>>()
    private val status = MutableLiveData<ApiStatus>()
    init {
        getAbout()
    }

    private fun getAbout() {
        viewModelScope.launch(Dispatchers.IO) {
            status.postValue(ApiStatus.LOADING)
            try {
                data.postValue (About.service.getAbout())
                status.postValue(ApiStatus.SUCCESS)
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                status.postValue(ApiStatus.FAILED)
            }
        }
    }
    fun getStatus(): LiveData<ApiStatus> = status
}
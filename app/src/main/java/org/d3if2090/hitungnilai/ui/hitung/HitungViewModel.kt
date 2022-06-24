package org.d3if2090.hitungnilai.ui.hitung

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if2090.hitungnilai.MainActivity
import org.d3if2090.hitungnilai.db.NilaiDao
import org.d3if2090.hitungnilai.db.NilaiEntity
import org.d3if2090.hitungnilai.model.HasilNilai
import org.d3if2090.hitungnilai.model.KategoriNilai
import org.d3if2090.hitungnilai.model.hitungnilai
import org.d3if2090.hitungnilai.network.UpdateWorker
import java.util.concurrent.TimeUnit

class HitungViewModel(private val db: NilaiDao) : ViewModel() {

    private val hasilNilai = MutableLiveData<HasilNilai?>()
    private val navigasi = MutableLiveData<KategoriNilai?>()

//    val data = db.getLastNilai()

    fun hitungNilai(
        praktikum: Float,
        assessment1: Float,
        assessment2: Float,
        assessment3: Float
    ) {
        val dataNilai = NilaiEntity(
            praktikum = praktikum,
            assessment1 = assessment1,
            assessment2 = assessment2,
            assessment3 = assessment3
        )
        hasilNilai.value = dataNilai.hitungnilai()

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.insert (dataNilai)
            }
        }
    }

    fun getHasilNilai(): LiveData<HasilNilai?> = hasilNilai

    fun mulaiNavigasi() {
        navigasi.value = hasilNilai.value?.kategori
    }

    fun selesaiNavigasi() {
        navigasi.value = null
    }

    fun getNavigasi(): LiveData<KategoriNilai?> = navigasi

    fun scheduleUpdater(app: Application) {
        val request = OneTimeWorkRequestBuilder<UpdateWorker>()
            .setInitialDelay(2, TimeUnit.SECONDS)
            .build()

        WorkManager.getInstance(app).enqueueUniqueWork(
            MainActivity.CHANNEL_ID,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }
}
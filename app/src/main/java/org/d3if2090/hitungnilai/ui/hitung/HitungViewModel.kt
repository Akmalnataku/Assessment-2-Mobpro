package org.d3if2090.hitungnilai.ui.hitung

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if2090.hitungnilai.db.NilaiDao
import org.d3if2090.hitungnilai.db.NilaiEntity
import org.d3if2090.hitungnilai.model.HasilNilai
import org.d3if2090.hitungnilai.model.KategoriNilai

class HitungViewModel(private val db: NilaiDao) : ViewModel() {

    private val hasilNilai = MutableLiveData<HasilNilai?>()
    private val navigasi = MutableLiveData<KategoriNilai?>()

    val data = db.getLastNilai()

    fun hitungNilai(
        praktikum: Float,
        assessment1: Float,
        assessment2: Float,
        assessment3: Float
    ) {
        val nilai =
            ((praktikum * 0.25) + (assessment1 * 0.2) + (assessment2 * 0.25) + (assessment3 * 0.3))
        val kategori = getKategori(nilai)
        hasilNilai.value = HasilNilai(nilai, kategori)

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val dataNilai = NilaiEntity(
                    praktikum = praktikum,
                    assessment1 = assessment1,
                    assessment2 = assessment2,
                    assessment3 = assessment3
                )
                db.insert (dataNilai)
            }
        }
    }

    private fun getKategori(nilai: Double): KategoriNilai {
        val kategori =
            when {
                nilai >= 80 -> KategoriNilai.A
                nilai <= 59.99 -> KategoriNilai.C
                else -> KategoriNilai.B
            }
        return kategori
    }

    fun getHasilNilai(): LiveData<HasilNilai?> = hasilNilai

    fun mulaiNavigasi() {
        navigasi.value = hasilNilai.value?.kategori
    }

    fun selesaiNavigasi() {
        navigasi.value = null
    }

    fun getNavigasi(): LiveData<KategoriNilai?> = navigasi
}
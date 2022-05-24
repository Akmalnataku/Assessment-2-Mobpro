package org.d3if2090.hitungnilai

import androidx.lifecycle.ViewModel
import org.d3if2090.hitungnilai.model.HasilNilai
import org.d3if2090.hitungnilai.model.KategoriNilai

class MainViewModel: ViewModel() {
    fun hitungNilai(
        praktikum: Float,
        assessment1: Float,
        assessment2: Float,
        assessment3: Float
    ): HasilNilai {
        val nilai =
            ((praktikum * 0.25) + (assessment1 * 0.2) + (assessment2 * 0.25) + (assessment3 * 0.3))
        val kategori = getKategori(nilai)
        return HasilNilai(nilai, kategori)
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
}
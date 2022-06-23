package org.d3if2090.hitungnilai.ui.histori

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if2090.hitungnilai.db.NilaiDao

class HistoriViewModelFactory(private val db: NilaiDao) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun < T : ViewModel> create(modelclass: Class<T>): T {
        if (modelclass.isAssignableFrom(HistoriViewModel::class.java)) {
            return HistoriViewModel(db) as T
        }
        throw IllegalStateException("Unknown ViewModel class")
    }
}
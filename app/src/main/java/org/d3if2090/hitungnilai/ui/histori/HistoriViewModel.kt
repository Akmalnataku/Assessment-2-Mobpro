package org.d3if2090.hitungnilai.ui.histori

import androidx.lifecycle.ViewModel
import org.d3if2090.hitungnilai.db.NilaiDao

class HistoriViewModel(db: NilaiDao) : ViewModel() {
    val data = db.getLastNilai()
}
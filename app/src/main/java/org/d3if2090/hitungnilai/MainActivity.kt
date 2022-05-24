package org.d3if2090.hitungnilai

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import org.d3if2090.hitungnilai.databinding.ActivityMainBinding
import org.d3if2090.hitungnilai.model.HasilNilai
import org.d3if2090.hitungnilai.model.KategoriNilai

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener { hitungNilai() }
        binding.btnReset.setOnClickListener { reset() }
        viewModel.getHasilNilai().observe(this, {showResult(it)})
    }

    private fun hitungNilai() {
        val praktikum = binding.praktikumInp.text.toString()
        if (TextUtils.isEmpty(praktikum)) {
            Toast.makeText(this, R.string.nilai_invalid, Toast.LENGTH_LONG).show()
            return
        }

        val assessment1 = binding.assessment1Inp.text.toString()
        if (TextUtils.isEmpty(assessment1)) {
            Toast.makeText(this, R.string.nilai_invalid, Toast.LENGTH_LONG).show()
            return
        }

        val assessment2 = binding.assessment2Inp.text.toString()
        if (TextUtils.isEmpty(assessment2)) {
            Toast.makeText(this, R.string.nilai_invalid, Toast.LENGTH_LONG).show()
            return
        }

        val assessment3 = binding.assessment3Inp.text.toString()
        if (TextUtils.isEmpty(assessment3)) {
            Toast.makeText(this, R.string.nilai_invalid, Toast.LENGTH_LONG).show()
            return
        }

        viewModel.hitungNilai(
            praktikum.toFloat(),
            assessment1.toFloat(),
            assessment2.toFloat(),
            assessment3.toFloat()
        )
    }

    private fun getKategoriLabel(kategori: KategoriNilai): String {
        val stringRes = when (kategori) {
            KategoriNilai.A -> R.string.bagus
            KategoriNilai.B -> R.string.lumayan
            KategoriNilai.C -> R.string.buruk
        }
        return getString(stringRes)
    }

    private fun reset() {
        val text1 = binding.praktikumInp.text.toString()
        val text2 = binding.assessment1Inp.text.toString()
        val text3 = binding.assessment2Inp.text.toString()
        val text4 = binding.assessment3Inp.text.toString()

        if (text1.isEmpty() && text2.isEmpty() && text3.isEmpty() && text4.isEmpty()) {
            Toast.makeText(this, "Already Empty!", Toast.LENGTH_LONG).show()
        } else {
            binding.praktikumInp.setText("")
            binding.assessment1Inp.setText("")
            binding.assessment2Inp.setText("")
            binding.assessment3Inp.setText("")
            binding.nilaiTextView.text = ""
            binding.indexTextView.text = ""
            binding.praktikumInp.requestFocus()
            //supaya cursornya hilang
            binding.praktikumInp.clearFocus()
            return
        }
    }

    private fun showResult(result: HasilNilai?) {
        if (result == null) return
        binding.nilaiTextView.text = getString(R.string.nilai_x, result.nilai)
        binding.indexTextView.text = getString(
            R.string.index_x,
            getKategoriLabel(result.kategori)
        )
    }

}
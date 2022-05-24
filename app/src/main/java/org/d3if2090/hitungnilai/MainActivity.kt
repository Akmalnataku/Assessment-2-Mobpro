package org.d3if2090.hitungnilai

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.d3if2090.hitungnilai.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener { hitungNilai() }
        binding.btnReset.setOnClickListener { reset() }
    }

    private fun hitungNilai() {
        val praktikum = binding.praktikumEditText.text.toString()
        if (TextUtils.isEmpty(praktikum)) {
            Toast.makeText(this, R.string.nilai_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val assessment1 = binding.ass1EditText.text.toString()
        if (TextUtils.isEmpty(assessment1)) {
            Toast.makeText(this, R.string.nilai_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val assessment2 = binding.ass2EditText.text.toString()
        if (TextUtils.isEmpty(assessment2)) {
            Toast.makeText(this, R.string.nilai_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val assessment3 = binding.ass3EditText.text.toString()
        if (TextUtils.isEmpty(assessment3)) {
            Toast.makeText(this, R.string.nilai_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val nilai =
            ((praktikum.toFloat() * 0.25) + (assessment1.toFloat() * 0.2) +
                    (assessment2.toFloat() * 0.25) + (assessment3.toFloat() * 0.3))

        val kategori = getKategori(nilai)

        binding.nilaiTextView.text = getString(R.string.nilai_x, nilai)
        binding.indexTextView.text = getString(R.string.index_x, kategori)
    }

    private fun getKategori(nilai: Double): String {
        val stringRes =
            when {
                nilai >= 80 -> R.string.bagus
                nilai <= 79.9 -> R.string.lumayan
                else -> R.string.buruk
            }
        return getString(stringRes)
    }

    private fun reset() {
        val text1 = binding.praktikumEditText.text.toString()
        val text2 = binding.ass1EditText.text.toString()
        val text3 = binding.ass2EditText.text.toString()
        val text4 = binding.ass3EditText.text.toString()

        if (text1.isEmpty() && text2.isEmpty() && text3.isEmpty() && text4.isEmpty()) {
            Toast.makeText(this, "Already Empty!", Toast.LENGTH_LONG).show()
        } else {
            binding.praktikumEditText.setText("")
            binding.ass1EditText.setText("")
            binding.ass2EditText.setText("")
            binding.ass3EditText.setText("")
            binding.nilaiTextView.text = ""
            binding.indexTextView.text = ""
            binding.praktikumEditText.requestFocus()
            //supaya cursornya hilang
            binding.praktikumEditText.clearFocus()
            return
        }
    }
}
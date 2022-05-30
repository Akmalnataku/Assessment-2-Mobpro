package org.d3if2090.hitungnilai.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import org.d3if2090.hitungnilai.R
import org.d3if2090.hitungnilai.databinding.FragmentHitungBinding
import org.d3if2090.hitungnilai.model.HasilNilai
import org.d3if2090.hitungnilai.model.KategoriNilai

class HitungFragment : Fragment() {

    private lateinit var binding: FragmentHitungBinding

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?
                          , savedInstanceState: Bundle?): View {
        binding = FragmentHitungBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.button.setOnClickListener { hitungNilai() }
        binding.btnReset.setOnClickListener { reset() }
        binding.saranButton.setOnClickListener { viewModel.mulaiNavigasi() }

        viewModel.getHasilNilai().observe(requireActivity(), {showResult(it)})
        viewModel.getNavigasi().observe(viewLifecycleOwner, {
            if (it == null) return@observe
            findNavController().navigate(
                HitungFragmentDirections
                    .actionHitungFragmentToSaranFragment(it)
            )
            viewModel.selesaiNavigasi()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_about) {
            findNavController().navigate(
                R.id.action_hitungFragment_to_aboutFragment
            )
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun hitungNilai() {
        val praktikum = binding.praktikumInp.text.toString()
        if (TextUtils.isEmpty(praktikum)) {
            Toast.makeText(context, R.string.nilai_invalid, Toast.LENGTH_LONG).show()
            return
        }

        val assessment1 = binding.assessment1Inp.text.toString()
        if (TextUtils.isEmpty(assessment1)) {
            Toast.makeText(context, R.string.nilai_invalid, Toast.LENGTH_LONG).show()
            return
        }

        val assessment2 = binding.assessment2Inp.text.toString()
        if (TextUtils.isEmpty(assessment2)) {
            Toast.makeText(context, R.string.nilai_invalid, Toast.LENGTH_LONG).show()
            return
        }

        val assessment3 = binding.assessment3Inp.text.toString()
        if (TextUtils.isEmpty(assessment3)) {
            Toast.makeText(context, R.string.nilai_invalid, Toast.LENGTH_LONG).show()
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
            Toast.makeText(context, "Already Empty!", Toast.LENGTH_LONG).show()
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
        binding.saranButton.visibility = View.VISIBLE
    }

}
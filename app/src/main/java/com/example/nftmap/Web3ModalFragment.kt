package com.example.nftmap

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nftmap.databinding.FragmentWeb3modalBinding
import com.example.nftmap.nftwallet.NftsOwned
import com.example.nftmap.nftwallet.UserNftsService
import com.example.nftmap.ui.AdapterClass
import com.walletconnect.web3.modal.client.Web3Modal
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class Web3ModalFragment : Fragment() {

    private var _binding: FragmentWeb3modalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeb3modalBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val nftsOwned = arrayListOf<NftsOwned>()

        val recyclerV = binding.nftRecyclerView
        try {
            Web3Modal.getAccount()
            val llm = LinearLayoutManager(requireContext())
            llm.orientation = LinearLayoutManager.VERTICAL
            recyclerV.layoutManager = llm
            val mAdapeter = AdapterClass(nftsOwned)
            recyclerV.adapter = mAdapeter
            runBlocking {
                val nftService = UserNftsService.create()
                val newNfts = nftService.fillNfts().forEach {
                    /*  println(it.name.toString())*/
                    nftsOwned.add(it)
                }
                mAdapeter.notifyDataSetChanged()
            }
        } catch (_:Exception){
            Toast.makeText(requireContext(),"Please connect wallet",Toast.LENGTH_LONG).show()

        }



/*
            Toast.makeText(requireContext(),  acc  , Toast.LENGTH_LONG).show()*/



        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


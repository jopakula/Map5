package com.example.map5.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.map5.MainViewModel
import com.example.map5.R
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class ListFragment : Fragment() {
    private val mainViewModel: MainViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val v = inflater.inflate(R.layout.fragment_list, container, false)
        val recyclerView = v.findViewById<RecyclerView>(R.id.listRecyclerView)
        val recyclerViewAdapter = ListRecyclerViewAdapter(mainViewModel)

        recyclerView.adapter = recyclerViewAdapter
        mainViewModel.points.observe(viewLifecycleOwner) { points -> recyclerViewAdapter.updatePoints(points) }

        return v
    }
}
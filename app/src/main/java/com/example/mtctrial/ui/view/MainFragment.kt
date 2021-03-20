package com.example.mtctrial.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtctrial.R
import com.example.mtctrial.ui.adapter.ListElement
import com.example.mtctrial.ui.adapter.PlayerListElement
import com.example.mtctrial.ui.adapter.RecyclerAdapter
import com.example.mtctrial.ui.adapter.TeamListElement
import com.example.mtctrial.ui.viewmodel.MainViewModel
import com.example.mtctrial.ui.viewmodelfactory.MainViewModelFactory
import kotlinx.android.synthetic.main.main_fragment.*


class MainFragment : Fragment() {

    private val playerList: MutableList<PlayerListElement> = mutableListOf()
    private val teamList: MutableList<TeamListElement> = mutableListOf()

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModelFactory: MainViewModelFactory by lazy {
        MainViewModelFactory(activity!!.application)
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        linearLayoutManager = LinearLayoutManager(activity)
        rvList.layoutManager = linearLayoutManager

        val dividerItemDecoration = DividerItemDecoration(
            rvList.context,
            linearLayoutManager.orientation
        )

        rvList.addItemDecoration(dividerItemDecoration)

        initObservers()
        initListeners()
    }

    private fun initListeners() {
        btnSearchButton.setOnClickListener {
            val searchString: String = etSearchField.text.toString()
            if (searchString.isNotEmpty()) viewModel.searchData(searchString)
        }
    }

    private fun initObservers() {
        viewModel.playerLiveData.observe(viewLifecycleOwner, Observer { playerListElements ->
            playerList.clear()
            playerList.addAll(playerListElements)
            updateList()
        })

        viewModel.teamLiveData.observe(viewLifecycleOwner, Observer { teamListElements ->
            teamList.clear()
            teamList.addAll(teamListElements)
            updateList()
        })
    }

    private fun updateList() {
        val newList = mutableListOf<ListElement>()
        newList.addAll(playerList)
        newList.addAll(teamList)

        if (rvList.adapter == null){
            rvList.adapter = context?.let { RecyclerAdapter(it, newList.toMutableList()) }
        } else {
            (rvList.adapter as RecyclerAdapter).setData(newList)
        }
    }
}
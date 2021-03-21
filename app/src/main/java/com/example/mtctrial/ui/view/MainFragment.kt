package com.example.mtctrial.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtctrial.R
import com.example.mtctrial.ui.adapter.*
import com.example.mtctrial.ui.viewmodel.MainViewModel
import com.example.mtctrial.ui.viewmodelfactory.MainViewModelFactory
import kotlinx.android.synthetic.main.main_fragment.*


class MainFragment : Fragment() {

    private var showMorePlayersButton = false
    private var showMoreTeamsButton = false

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModelFactory: MainViewModelFactory by lazy {
        MainViewModelFactory(activity!!.application)
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    private val listener: RecyclerAdapter.Listener = object: RecyclerAdapter.Listener {
        override fun onClick(element: ListElement) {

            if (viewModel.currentSearchString.isEmpty()) return
            
            if (element is ButtonListElement){
                when (element.type){
                    ButtonListElement.ListButtonType.BUTTON_MORE_PLAYERS -> {
                        showMorePlayersButton = false
                        viewModel.searchData(
                            viewModel.currentSearchString,
                            MainViewModel.SearchType.PLAYERS,
                            viewModel.currentPlayersList)
                    }

                    ButtonListElement.ListButtonType.BUTTON_MORE_TEAMS -> {
                        showMoreTeamsButton = false
                        viewModel.searchData(
                            viewModel.currentSearchString,
                            MainViewModel.SearchType.TEAMS,
                            viewModel.currentTeamsList)
                    }
                }
            }
        }
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
            val searchString: String = etSearchField.text.toString().toLowerCase()
            viewModel.currentSearchString = searchString
            showMorePlayersButton = false
            showMoreTeamsButton = false
            updateList()

            if (searchString.isEmpty()) return@setOnClickListener

            viewModel.searchData(searchString, null, null)
        }
    }

    private fun initObservers() {
        viewModel.playerLiveData.observe(viewLifecycleOwner, Observer { playerListElements ->
            viewModel.originalPlayerList.clear()
            viewModel.originalPlayerList.addAll(playerListElements)
            updateList()
        })

        viewModel.teamLiveData.observe(viewLifecycleOwner, Observer { teamListElements ->
            viewModel.originalTeamList.clear()
            viewModel.originalTeamList.addAll(teamListElements)
            updateList()
        })

        viewModel.networkErrorLiveData.observe(viewLifecycleOwner, Observer { error ->
            Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
        })

        viewModel.requestSpinnerLiveData.observe(viewLifecycleOwner, Observer { requestInProcess ->
            if (requestInProcess) {
                pbSpinner.visibility = View.VISIBLE
                ivSearchIcon.visibility = View.GONE
            } else {
                pbSpinner.visibility = View.GONE
                ivSearchIcon.visibility = View.VISIBLE
            }
        })

        viewModel.morePlayersFetchedCountLiveData.observe(viewLifecycleOwner, Observer { playersFetchedCount ->
            showMorePlayersButton = playersFetchedCount == 10 && viewModel.currentSearchString.isNotEmpty()
        })

        viewModel.moreTeamsFetchedCountLiveData.observe(viewLifecycleOwner, Observer { teamsFetchedCount ->
            showMoreTeamsButton = teamsFetchedCount == 10 && viewModel.currentSearchString.isNotEmpty()
        })
    }

    private fun updateList() {
        viewModel.currentPlayersList = 0
        viewModel.currentTeamsList = 0

        val filteredPlayerList = viewModel.originalPlayerList.filter {
            val add = viewModel.filterListElement(it)
            if (add) viewModel.currentPlayersList ++
            add
        }

        val filteredTeamsList = viewModel.originalTeamList.filter {
            val add = viewModel.filterListElement(it)
            if (add) viewModel.currentTeamsList ++
            add
        }

        viewModel.filteredList.clear()
        if (filteredPlayerList.isNotEmpty()) {
            viewModel.filteredList.add(SeparatorListElement("Players (${filteredPlayerList.size})"))
            viewModel.filteredList.addAll(filteredPlayerList)
            if (showMorePlayersButton)
                viewModel.filteredList.add(ButtonListElement(ButtonListElement.ListButtonType.BUTTON_MORE_PLAYERS))
        }

        if (filteredTeamsList.isNotEmpty()) {
            viewModel.filteredList.add(SeparatorListElement("Teams (${filteredTeamsList.size})"))
            viewModel.filteredList.addAll(filteredTeamsList)
            if (showMoreTeamsButton)
                viewModel.filteredList.add(ButtonListElement(ButtonListElement.ListButtonType.BUTTON_MORE_TEAMS))
        }

        if (viewModel.filteredList.isEmpty()){
            viewModel.filteredList.add(SeparatorListElement("No results found"))
        }

        if (rvList.adapter == null){
            rvList.adapter = context?.let {
                RecyclerAdapter(it, listener, viewModel.filteredList.toMutableList())
            }
        } else {
            (rvList.adapter as RecyclerAdapter).setData(viewModel.filteredList)
        }
    }
}
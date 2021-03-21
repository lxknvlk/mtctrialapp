package com.example.mtctrial.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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

    private val listener: RecyclerAdapter.Listener = object: RecyclerAdapter.Listener {
        override fun onClick(element: ListElement) {
            Toast.makeText(activity, "More button clicked", Toast.LENGTH_SHORT).show()
            if (element is ButtonListElement){
                when (element.type){
                    ButtonListElement.ListButtonType.BUTTON_MORE_PLAYERS -> {
                        viewModel.searchData(
                            viewModel.currentSearchString,
                            MainViewModel.SearchType.PLAYERS,
                            viewModel.currentPlayersList)
                    }

                    ButtonListElement.ListButtonType.BUTTON_MORE_TEAMS -> {
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
            updateList()

            if (searchString.isEmpty()) return@setOnClickListener

            viewModel.searchData(searchString, null, null)
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
    }

    private fun updateList() {
        viewModel.originalList.clear()
        viewModel.originalList.add(SeparatorListElement("Players"))
        viewModel.originalList.addAll(playerList)
        viewModel.originalList.add(ButtonListElement(ButtonListElement.ListButtonType.BUTTON_MORE_PLAYERS))
        viewModel.originalList.add(SeparatorListElement("Teams"))
        viewModel.originalList.addAll(teamList)
        viewModel.originalList.add(ButtonListElement(ButtonListElement.ListButtonType.BUTTON_MORE_TEAMS))

        viewModel.currentPlayersList = 0
        viewModel.currentTeamsList = 0

        val filtered = viewModel.originalList.filter {
            val playerElementMatchesQuery = it is PlayerListElement && (
                    it.playerSecondName.toLowerCase().contains(viewModel.currentSearchString)
                            || it.playerFirstName.toLowerCase().contains(viewModel.currentSearchString)
                            || it.playerClub.toLowerCase().contains(viewModel.currentSearchString)
                            || it.playerNationality.toLowerCase().contains(viewModel.currentSearchString))

            val teamElementMatchesQuery = it is TeamListElement && (
                    it.teamStadium.toLowerCase().contains(viewModel.currentSearchString)
                            || it.teamNationality.toLowerCase().contains(viewModel.currentSearchString)
                            || it.teamCity.toLowerCase().contains(viewModel.currentSearchString)
                            || it.teamName.toLowerCase().contains(viewModel.currentSearchString))

            val isSeparatorElement = it is SeparatorListElement
            val isSearchStringEmpty = viewModel.currentSearchString.isEmpty()

            when (it){
                is PlayerListElement -> viewModel.currentPlayersList ++
                is TeamListElement -> viewModel.currentTeamsList ++
            }

            isSeparatorElement || isSearchStringEmpty || playerElementMatchesQuery || teamElementMatchesQuery
        }

        viewModel.filteredList.clear()
        viewModel.filteredList.addAll(filtered)

        if (rvList.adapter == null){
            rvList.adapter = context?.let { RecyclerAdapter(it, listener, viewModel.filteredList.toMutableList()) }
        } else {
            (rvList.adapter as RecyclerAdapter).setData(viewModel.filteredList)
        }
    }
}
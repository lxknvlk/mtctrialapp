package com.example.mtctrial.ui.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
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
import java.util.*


class MainFragment : Fragment() {

    private var showMorePlayersButton = false
    private var showMoreTeamsButton = false

    private var timer: Timer = Timer()
    private val DELAY: Long = 400

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
            if (element is ButtonListElement){
                if (viewModel.currentSearchString.isEmpty()) return

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
            } else if (element is PlayerListElement) {
                viewModel.togglePlayerFavorite(element)
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

        rvList.adapter = context?.let {
            RecyclerAdapter(it, listener, mutableListOf())
        }

        initObservers()
        initListeners()
    }

    private fun initListeners() {
        btnSearchButton.setOnClickListener {
            doSearch()
        }

        etSearchField.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                timer = Timer()
                timer.schedule(object : TimerTask() {
                    override fun run() {
                        doSearch()
                    }
                }, DELAY)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                timer.cancel()
            }
        })

        ivFavButton.setOnClickListener {
            val newFragment: FavoritesFragment = FavoritesFragment.newInstance()
            fragmentManager?.let { fragment -> newFragment.show(fragment, "dialog") }
        }
    }

    private fun doSearch() {
        val searchString: String = etSearchField.text.toString().toLowerCase(Locale.ROOT)
        viewModel.currentSearchString = searchString
        showMorePlayersButton = false
        showMoreTeamsButton = false
        updateList()

        if (searchString.isEmpty()) return

        viewModel.searchData(searchString, null, null)
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

        val filteredList = mutableListOf<ListElement>()
        if (filteredPlayerList.isNotEmpty()) {
            filteredList.add(SeparatorListElement("Players (${filteredPlayerList.size})"))
            filteredList.addAll(filteredPlayerList)
            if (showMorePlayersButton)
                filteredList.add(ButtonListElement(ButtonListElement.ListButtonType.BUTTON_MORE_PLAYERS))
        }

        if (filteredTeamsList.isNotEmpty()) {
            filteredList.add(SeparatorListElement("Teams (${filteredTeamsList.size})"))
            filteredList.addAll(filteredTeamsList)
            if (showMoreTeamsButton)
                filteredList.add(ButtonListElement(ButtonListElement.ListButtonType.BUTTON_MORE_TEAMS))
        }

        if (filteredList.isEmpty()){
            filteredList.add(SeparatorListElement("No results found"))
        }

        activity?.runOnUiThread {
            (rvList.adapter as RecyclerAdapter).setData(filteredList.toMutableList())
        }
    }
}
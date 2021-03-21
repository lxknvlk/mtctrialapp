package com.example.mtctrial.ui.view

import android.content.DialogInterface
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtctrial.R
import com.example.mtctrial.ui.adapter.ButtonListElement
import com.example.mtctrial.ui.adapter.ListElement
import com.example.mtctrial.ui.adapter.PlayerListElement
import com.example.mtctrial.ui.adapter.RecyclerAdapter
import com.example.mtctrial.ui.viewmodel.FavoritesViewModel
import com.example.mtctrial.ui.viewmodel.MainViewModel
import com.example.mtctrial.ui.viewmodelfactory.FavoritesViewModelFactory
import com.example.mtctrial.ui.viewmodelfactory.MainViewModelFactory
import kotlinx.android.synthetic.main.main_fragment.*

class FavoritesFragment : DialogFragment() {

    companion object {
        fun newInstance() = FavoritesFragment()
    }

    interface OnDismissListener {
        fun dismissed()
    }

    private var onDismissListener: OnDismissListener? = null

    fun setOnDismissListener(onDismissListener: OnDismissListener){
        this.onDismissListener = onDismissListener
    }

    private lateinit var viewModel: FavoritesViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager

    private val listener: RecyclerAdapter.Listener = object: RecyclerAdapter.Listener {
        override fun onClick(element: ListElement) {
            if (element is PlayerListElement) {
                viewModel.togglePlayerFavorite(element)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let {
            val viewModelFactory = FavoritesViewModelFactory(it.application)
            viewModel = ViewModelProvider(this, viewModelFactory).get(FavoritesViewModel::class.java)
        }

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
    }

    private fun initObservers() {
        viewModel.favoritePlayersLiveData.observe(this, Observer { playerListElements ->
            (rvList.adapter as RecyclerAdapter).setData(playerListElements.toMutableList())
        })
    }

    override fun onDismiss(dialog: DialogInterface) {
        onDismissListener?.dismissed()
        super.onDismiss(dialog)
    }
}
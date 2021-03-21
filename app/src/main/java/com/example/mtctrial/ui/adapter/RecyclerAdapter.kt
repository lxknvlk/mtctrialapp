package com.example.mtctrial.ui.adapter

import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mtctrial.R

class RecyclerAdapter(
    private val context: Context,
    private val listener: Listener,
    private var elementsList: MutableList<ListElement>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_SEPARATOR = 0
        private const val TYPE_PLAYER = 1
        private const val TYPE_TEAM = 2
        private const val TYPE_LIST_BUTTON = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ListElement> {
        return when (viewType) {
            TYPE_SEPARATOR -> {
                val view = LayoutInflater.from(context).inflate(R.layout.list_separator, parent, false)
                SeparatorViewHolder(view)
            }
            TYPE_PLAYER -> {
                val view = LayoutInflater.from(context).inflate(R.layout.list_player, parent, false)
                PlayerViewHolder(view)
            }
            TYPE_TEAM -> {
                val view = LayoutInflater.from(context).inflate(R.layout.list_team, parent, false)
                TeamViewHolder(view)
            }
            TYPE_LIST_BUTTON -> {
                val view = LayoutInflater.from(context).inflate(R.layout.list_button, parent, false)
                ListButtonViewholder(view)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (elementsList[position]) {
            is PlayerListElement -> TYPE_PLAYER
            is SeparatorListElement -> TYPE_SEPARATOR
            is TeamListElement -> TYPE_TEAM
            is ButtonListElement -> TYPE_LIST_BUTTON
            else -> throw IllegalArgumentException("Invalid type of data $position")
        }
    }

    override fun getItemCount(): Int {
        return elementsList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val element = elementsList[position]
        when (holder) {
            is PlayerViewHolder ->      holder.bind(element as PlayerListElement)
            is SeparatorViewHolder ->   holder.bind(element as SeparatorListElement)
            is TeamViewHolder ->        holder.bind(element as TeamListElement)
            is ListButtonViewholder ->  holder.bind(element as ButtonListElement)
            else -> throw IllegalArgumentException()
        }
    }

    fun setData(newElements: List<ListElement>) {
        val diffCallback = DataDiffCallback(elementsList, newElements)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        elementsList.clear()
        elementsList.addAll(newElements)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class SeparatorViewHolder(view: View) : BaseViewHolder<ListElement>(view) {
        private val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        override fun bind(item: ListElement) {
            item as SeparatorListElement
            tvTitle.text = item.title.trim()
        }
    }

    inner class ListButtonViewholder(view: View) : BaseViewHolder<ListElement>(view) {
        private val btnButton: Button = view.findViewById(R.id.btnButton)
        override fun bind(item: ListElement) {
            item as ButtonListElement
            when (item.type) {
                ButtonListElement.ListButtonType.BUTTON_MORE_TEAMS -> btnButton.text = "More Teams"
                ButtonListElement.ListButtonType.BUTTON_MORE_PLAYERS -> btnButton.text = "More Players"
            }

            btnButton.setOnClickListener {
                listener.onClick(item)
            }
        }
    }

    inner class TeamViewHolder(view: View) : BaseViewHolder<ListElement>(view) {
        private val tvTeamName: TextView = view.findViewById(R.id.tvTeamName)
        private val tvCity: TextView = view.findViewById(R.id.tvCity)
        private val tvStadium: TextView = view.findViewById(R.id.tvStadium)
        override fun bind(item: ListElement) {
            item as TeamListElement
            tvTeamName.text = item.teamName.trim()
            tvCity.text = item.teamCity.trim()
            tvStadium.text = item.teamStadium.trim()
        }
    }

    inner class PlayerViewHolder(view: View) : BaseViewHolder<ListElement>(view) {
        private val tvFirstName: TextView = view.findViewById(R.id.tvFirstName)
        private val tvSecondName: TextView = view.findViewById(R.id.tvSecondName)
        private val tvAge: TextView = view.findViewById(R.id.tvAge)
        private val tvClub: TextView = view.findViewById(R.id.tvClub)
        override fun bind(item: ListElement) {
            item as PlayerListElement
            tvFirstName.text = item.playerFirstName.trim()
            tvSecondName.text = item.playerSecondName.trim()
            tvAge.text = item.playerAge.toString().trim()
            tvClub.text = item.playerClub.trim()

            if (item.playerFirstName.trim().isEmpty()){
                tvFirstName.visibility = View.GONE
            } else {
                tvFirstName.visibility = View.VISIBLE
            }
        }
    }

    interface Listener {
        fun onClick(element: ListElement)
    }
}
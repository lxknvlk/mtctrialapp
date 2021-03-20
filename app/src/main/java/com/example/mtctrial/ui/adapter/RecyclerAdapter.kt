package com.example.mtctrial.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mtctrial.R

class RecyclerAdapter(
    private val context: Context,
    private var elementsList: List<ListElement>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_SEPARATOR = 0
        private const val TYPE_PLAYER = 1
        private const val TYPE_TEAM = 2
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
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (elementsList[position]) {
            is PlayerListElement -> TYPE_PLAYER
            is SeparatorListElement -> TYPE_SEPARATOR
            is TeamListElement -> TYPE_TEAM
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
            else -> throw IllegalArgumentException()
        }
    }


    inner class SeparatorViewHolder(view: View) : BaseViewHolder<ListElement>(view) {
        private val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        override fun bind(item: ListElement) {
            item as SeparatorListElement
            tvTitle.text = item.title
        }
    }

    inner class TeamViewHolder(view: View) : BaseViewHolder<ListElement>(view) {
        private val tvTeamName: TextView = view.findViewById(R.id.tvTeamName)
        private val tvCity: TextView = view.findViewById(R.id.tvCity)
        private val tvStadium: TextView = view.findViewById(R.id.tvStadium)
        override fun bind(item: ListElement) {
            item as TeamListElement
            tvTeamName.text = item.teamName
            tvCity.text = item.teamCity
            tvStadium.text = item.teamStadium
        }
    }

    inner class PlayerViewHolder(view: View) : BaseViewHolder<ListElement>(view) {
        private val tvFirstName: TextView = view.findViewById(R.id.tvFirstName)
        private val tvSecondName: TextView = view.findViewById(R.id.tvSecondName)
        private val tvAge: TextView = view.findViewById(R.id.tvAge)
        private val tvClub: TextView = view.findViewById(R.id.tvClub)
        override fun bind(item: ListElement) {
            item as PlayerListElement
            tvFirstName.text = item.playerFirstName
            tvSecondName.text = item.playerSecondName
            tvAge.text = item.playerAge.toString()
            tvClub.text = item.playerClub
        }
    }
}
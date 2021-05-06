package io.github.metalcupcake5.dndapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.metalcupcake5.dndapp.data.Character

class CharacterListAdapter() : ListAdapter<Character, CharacterListAdapter.CharacterViewHolder>(CharactersComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.name, current.id)
    }

    class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val characterItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(text: String, id: Int) {
            characterItemView.text = text
            itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("id", id)
                itemView?.findNavController()?.navigate(R.id.action_characterListFragment_to_characterInfoFragment, bundle)
            }
        }

        companion object {
            fun create(parent: ViewGroup): CharacterViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return CharacterViewHolder(view)
            }
        }
    }

    class CharactersComparator : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.name == newItem.name
        }
    }
}

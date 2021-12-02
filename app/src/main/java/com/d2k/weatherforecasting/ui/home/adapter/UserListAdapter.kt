package com.d2k.weatherforecasting.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.d2k.weatherforecasting.databinding.AdapterUserListBinding
import com.d2k.weatherforecasting.db.entity.UserTable
import javax.inject.Inject

class UserListAdapter @Inject constructor() : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: AdapterUserListBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<UserTable>() {
        override fun areItemsTheSame(oldItem: UserTable, newItem: UserTable): Boolean {

            return oldItem.email == newItem.email
        }

        override fun areContentsTheSame(oldItem: UserTable, newItem: UserTable): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            AdapterUserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val UserTable = differ.currentList[position]
        holder.binding.apply {
            tvFirstName.text = UserTable.firstName
            tvLastName.text = UserTable.lastName
            tvUserEmail.text = UserTable.email

        }

        holder.itemView.setOnClickListener {
            setUserTableClickListener?.let {
                it(UserTable)
            }
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var setUserTableClickListener: ((UserTable: UserTable) -> Unit)? = null

    fun onUserTableClicked(listener: (UserTable) -> Unit) {
        setUserTableClickListener = listener
    }
    fun removeAt(position: Int) {
        differ.currentList.removeAt(position)
        notifyItemRemoved(position)
    }


}
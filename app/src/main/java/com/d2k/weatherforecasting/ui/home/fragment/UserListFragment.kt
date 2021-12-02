package com.d2k.weatherforecasting.ui.home.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.d2k.weatherforecasting.R
import com.d2k.weatherforecasting.databinding.FragmentUserListBinding
import com.d2k.weatherforecasting.db.entity.UserTable
import com.d2k.weatherforecasting.ui.home.adapter.UserListAdapter
import com.d2k.weatherforecasting.ui.vm.UserVm
import com.d2k.weatherforecasting.utils.DataHandler
import com.d2k.weatherforecasting.utils.SwipeToDeleteCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class UserListFragment : Fragment(R.layout.fragment_user_list) {

    lateinit var fragmentUserListBinding: FragmentUserListBinding
    val viewModel: UserVm by viewModels()
    var userId : Long = 0
    lateinit var fav: MenuItem

    @Inject
    lateinit var userAdapter : UserListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        fragmentUserListBinding = FragmentUserListBinding.bind(view)
        init()
        viewModel.userList.observe(viewLifecycleOwner, Observer { it ->
            when(it){
                is DataHandler.SUCCESS->{
                    userAdapter.differ.submitList(it.data)
                }
                is DataHandler.ERROR -> {
                    Toast.makeText(requireActivity(),""+it.message,Toast.LENGTH_LONG).show()
                    userAdapter.differ.submitList(it.data)
                    //LogData("onViewCreated: ERROR ${dataHandler.message}")
                }
                is DataHandler.LOADING -> {
                    //LogData("onViewCreated: LOADING")
                }
            }
        })
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.adduser,menu)
        fav = menu.findItem(R.id.add)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add ->{
                findNavController().navigate(R.id.action_userListFragment_to_addUserFragment)
                return true
            }
            else -> {}
        }
        return false
    }

    private fun init(){
        fragmentUserListBinding.rcUserList.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = fragmentUserListBinding.rcUserList.adapter as UserListAdapter
                viewModel.deleteUser(adapter.differ.currentList.get(viewHolder.adapterPosition))
                viewModel.getAllUser()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(fragmentUserListBinding.rcUserList)

        userAdapter.onUserTableClicked {
            findNavController().navigate(R.id.action_userListFragment_to_weatherForecastFragment)
        }
    }
}
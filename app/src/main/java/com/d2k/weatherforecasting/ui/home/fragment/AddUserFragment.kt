package com.d2k.weatherforecasting.ui.home.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.d2k.weatherforecasting.R
import com.d2k.weatherforecasting.databinding.FragmentAddUserBinding
import com.d2k.weatherforecasting.db.entity.UserTable
import com.d2k.weatherforecasting.extension.showToastForValid
import com.d2k.weatherforecasting.ui.vm.UserVm
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddUserFragment : Fragment(R.layout.fragment_add_user) {

    lateinit var fragmentAddUserBinding: FragmentAddUserBinding
    val viewModel: UserVm by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentAddUserBinding = FragmentAddUserBinding.bind(view)
        
        
        fragmentAddUserBinding.tvSave.setOnClickListener { 
            saveUser()
            return@setOnClickListener
        }
        fragmentAddUserBinding.tvCancel.setOnClickListener {
            findNavController().navigate(R.id.action_addUserFragment_to_userListFragment)
        }
    }

    private fun saveUser() {
        if (TextUtils.isEmpty(fragmentAddUserBinding.etUsername.text) ||
            TextUtils.isEmpty(fragmentAddUserBinding.etLastName.text) ||
            TextUtils.isEmpty(fragmentAddUserBinding.etEmail.text)){

            showToastForValid(requireActivity().resources.getString(R.string.enter_all_details))
            return
        }
        if (Patterns.EMAIL_ADDRESS.matcher(fragmentAddUserBinding.etEmail.text.toString()).matches()){
            val userTable = UserTable(null,fragmentAddUserBinding.etUsername.text.toString(),
                fragmentAddUserBinding.etLastName.text.toString(),fragmentAddUserBinding.etEmail.text.toString())

            viewModel.insertIntoUser(userTable)
            findNavController().navigate(R.id.action_addUserFragment_to_userListFragment)
        }
    }
}
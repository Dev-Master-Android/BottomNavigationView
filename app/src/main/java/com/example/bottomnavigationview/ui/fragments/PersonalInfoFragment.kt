package com.example.bottomnavigationview.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bottomnavigationview.R
import com.example.bottomnavigationview.data.model.PersonalInfo
import com.example.bottomnavigationview.databinding.FragmentPersonalInfoBinding

import com.example.bottomnavigationview.viewmodel.PersonalInfoViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class PersonalInfoFragment : Fragment() {

    private lateinit var binding: FragmentPersonalInfoBinding
    private lateinit var personalInfoViewModel: PersonalInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPersonalInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        personalInfoViewModel = ViewModelProvider(this)[PersonalInfoViewModel::class.java]

        personalInfoViewModel.personalInfo.observe(viewLifecycleOwner) { info ->
            info?.let {
                binding.firstName.text = it.firstName
                binding.lastName.text = it.lastName
                binding.phoneNumber.text = it.phoneNumber
                binding.email.text = it.email
                binding.address.text = it.address
            }
        }

        val name = binding.firstName.setOnClickListener { showEditDialog("Имя", "firstName") }
        val lastName =  binding.lastName.setOnClickListener { showEditDialog("Фамилия", "lastName") }
        val phoneNumber =  binding.phoneNumber.setOnClickListener { showEditDialog("Номер телефона", "phoneNumber") }
        val email = binding.email.setOnClickListener { showEditDialog("Электронная почта", "email") }
        val address = binding.address.setOnClickListener { showEditDialog("Адрес", "address") }
        val currentInfo = PersonalInfo(0,name.toString(), lastName.toString(), phoneNumber.toString(), email.toString(), address.toString())
        binding.clearButton.setOnClickListener {
            personalInfoViewModel.clearPersonalInfo( )
            clearFields()
        }
    }

    private fun showEditDialog(title: String, field: String) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_field, null)
        val editText: EditText = dialogView.findViewById(R.id.editTextField)
        val currentInfo = personalInfoViewModel.personalInfo.value

        when (field) {
            "firstName" -> editText.setText(currentInfo?.firstName)
            "lastName" -> editText.setText(currentInfo?.lastName)
            "phoneNumber" -> editText.setText(currentInfo?.phoneNumber)
            "email" -> editText.setText(currentInfo?.email)
            "address" -> editText.setText(currentInfo?.address)
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Редактировать $title")
            .setView(dialogView)
            .setPositiveButton("Сохранить") { _, _ ->
                val newValue = editText.text.toString()
                if (newValue.isNotEmpty()) {
                    val updatedInfo = currentInfo?.copy(
                        firstName = if (field == "firstName") newValue else currentInfo.firstName,
                        lastName = if (field == "lastName") newValue else currentInfo.lastName,
                        phoneNumber = if (field == "phoneNumber") newValue else currentInfo.phoneNumber,
                        email = if (field == "email") newValue else currentInfo.email,
                        address = if (field == "address") newValue else currentInfo.address
                    )
                        ?: PersonalInfo(
                            firstName = if (field == "firstName") newValue else "",
                            lastName = if (field == "lastName") newValue else "",
                            phoneNumber = if (field == "phoneNumber") newValue else "",
                            email = if (field == "email") newValue else "",
                            address = if (field == "address") newValue else ""
                        )

                    if (currentInfo == null) {
                        personalInfoViewModel.insert(updatedInfo)
                    } else {
                        personalInfoViewModel.update(updatedInfo)
                    }
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }
    private fun clearFields() {
        binding.firstName.text = "Имя"
        binding.lastName.text = "Фамилия"
        binding.phoneNumber.text = "Номер телефона"
        binding.email.text = "Электронная почта"
        binding.address.text = "Адрес"
    }
}


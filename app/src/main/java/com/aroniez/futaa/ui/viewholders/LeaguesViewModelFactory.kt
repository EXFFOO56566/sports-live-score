package com.aroniez.futaa.ui.viewholders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aroniez.futaa.repository.LeaguesRepository
import com.aroniez.futaa.ui.viewmodels.LeaguesViewModel

class LeaguesViewModelFactory(private val leaguesRepository: LeaguesRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = LeaguesViewModel(leaguesRepository) as T
}
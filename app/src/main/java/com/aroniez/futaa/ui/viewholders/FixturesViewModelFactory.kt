package com.aroniez.futaa.ui.viewholders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aroniez.futaa.repository.FixturesRepository
import com.aroniez.futaa.ui.viewmodels.FixturesViewModel

class FixturesViewModelFactory(private val sourceRepository: FixturesRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = FixturesViewModel(sourceRepository) as T
}
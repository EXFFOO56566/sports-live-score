package com.aroniez.futaa.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.aroniez.futaa.repository.FixturesRepository

class FixturesViewModel(private val sourceRepository: FixturesRepository) : ViewModel() {
    fun getFavoritesMatches() = sourceRepository.favorites()
}
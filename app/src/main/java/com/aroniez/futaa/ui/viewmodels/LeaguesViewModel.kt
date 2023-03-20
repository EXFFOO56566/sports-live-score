package com.aroniez.futaa.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.aroniez.futaa.repository.LeaguesRepository

class LeaguesViewModel(private val competitionsRepository: LeaguesRepository) : ViewModel() {
    fun getCompetitions() = competitionsRepository.competitions()
}
package com.uni.eventbridge.di

import com.uni.eventbridge.presentation.auth.AuthViewModel
import com.uni.eventbridge.presentation.createEvent.CreateEventViewModel
import com.uni.eventbridge.presentation.eventDetails.EventDetailsViewModel
import com.uni.eventbridge.presentation.events.EventsViewModel
import com.uni.eventbridge.presentation.home.HomeViewModel
import com.uni.eventbridge.presentation.map.NavigationViewModel
import com.uni.eventbridge.presentation.profile.ProfileViewModel
import com.uni.eventbridge.presentation.search.SearchViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val ViewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::EventDetailsViewModel)
    viewModelOf(::EventsViewModel)
    viewModelOf(::CreateEventViewModel)
    viewModelOf(::AuthViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::NavigationViewModel)
}
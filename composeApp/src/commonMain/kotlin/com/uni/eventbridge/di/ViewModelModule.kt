package com.uni.eventbridge.di

import com.uni.eventbridge.presentation.createEvent.CreateEventViewModel
import com.uni.eventbridge.presentation.eventDetails.EventDetailsViewModel
import com.uni.eventbridge.presentation.events.EventsViewModel
import com.uni.eventbridge.presentation.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val ViewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModel { params ->
        EventDetailsViewModel(
            eventRepository = get(),
            eventId = params.get<Long>(),
        )
    }
    viewModelOf(::EventsViewModel)
    viewModelOf(::CreateEventViewModel)
}
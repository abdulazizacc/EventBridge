package com.uni.eventbridge.di

import EventRemoteDataSource
import com.uni.eventbridge.data.repository.EventRepositoryImpl
import com.uni.eventbridge.domain.repository.EventRepository
import org.koin.dsl.module

val dataModule = module {
    single { EventRemoteDataSource() }
    single<EventRepository> { EventRepositoryImpl(get()) }
}
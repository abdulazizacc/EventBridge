package com.uni.eventbridge.di

import com.uni.eventbridge.data.local.EventBridgeDatabase
import com.uni.eventbridge.data.local.SearchHistoryLocalDataSource
import com.uni.eventbridge.data.local.buildDatabase
import com.uni.eventbridge.data.local.getDatabaseBuilder
import com.uni.eventbridge.data.repository.SearchRepositoryImpl
import com.uni.eventbridge.data.repository.SupabaseAuthRepository
import com.uni.eventbridge.data.repository.SupabaseEventRepository
import com.uni.eventbridge.data.repository.SupabaseProfileRepository
import com.uni.eventbridge.domain.repository.AuthRepository
import com.uni.eventbridge.domain.repository.EventRepository
import com.uni.eventbridge.domain.repository.ProfileRepository
import com.uni.eventbridge.domain.repository.SearchRepository
import org.koin.dsl.module

val dataModule = module {
    single<EventRepository> { SupabaseEventRepository() }
    single<AuthRepository> { SupabaseAuthRepository(get()) }
    single<ProfileRepository> { SupabaseProfileRepository() }

    single<EventBridgeDatabase> {
        buildDatabase(getDatabaseBuilder())
    }
    single { get<EventBridgeDatabase>().searchHistoryDao() }
    single { SearchHistoryLocalDataSource(get()) }
    single<SearchRepository> {
        SearchRepositoryImpl(
            localDataSource = get(),
            remoteDataSource = get(),
        )
    }}
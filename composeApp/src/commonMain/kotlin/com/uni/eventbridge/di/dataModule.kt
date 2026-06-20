package com.uni.eventbridge.di

import com.uni.eventbridge.data.local.EventBridgeDatabase
import com.uni.eventbridge.data.local.SearchHistoryLocalDataSource
import com.uni.eventbridge.data.local.buildDatabase
import com.uni.eventbridge.data.local.getDatabaseBuilder
import com.uni.eventbridge.data.repository.LocationRepositoryImpl
import com.uni.eventbridge.data.repository.SearchRepositoryImpl
import com.uni.eventbridge.data.repository.SupabaseAccountRepository
import com.uni.eventbridge.data.repository.SupabaseEventRepository
import com.uni.eventbridge.domain.repository.AccountRepository
import com.uni.eventbridge.domain.repository.EventRepository
import com.uni.eventbridge.domain.repository.LocationRepository
import com.uni.eventbridge.domain.repository.SearchRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val dataModule = module {
    single<EventRepository> { SupabaseEventRepository() }
    single<AccountRepository> { SupabaseAccountRepository(get()) }

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
    }

    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
        }
    }


    single<LocationRepository> { LocationRepositoryImpl(get(), get()) }

}
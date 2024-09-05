package com.studystream.di

import com.studystream.data.repository.*
import com.studystream.domain.repository.*
import org.koin.core.module.dsl.new
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    single { new(::PasswordManager) } bind PasswordManager::class
    single { new(::AuthRepositoryImpl) } bind AuthRepository::class
    single { new(::AccountRepositoryImpl) } bind AccountRepository::class
    single { new(::TokenRepositoryImpl) } bind TokenRepository::class
    single { new(::DocumentRepositoryImpl) } bind DocumentRepository::class
    single { new(::FileStorageRepositoryImpl) } bind FileStorageRepository::class
    single { new(::ProfileRepositoryImpl) } bind ProfileRepository::class
    single { new(::DeviceRepositoryImpl) } bind DeviceRepository::class
    single { new(::TicketRepositoryImpl) } bind TicketRepository::class
    single { new(::SecurityRepositoryImpl) } bind SecurityRepository::class
}
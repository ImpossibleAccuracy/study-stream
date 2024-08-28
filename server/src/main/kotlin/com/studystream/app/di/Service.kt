package com.studystream.app.di

import com.studystream.app.data.service.*
import com.studystream.app.domain.service.*
import org.koin.core.module.dsl.new
import org.koin.dsl.bind
import org.koin.dsl.module

val serviceModule = module {
    single { new(::PasswordManager) } bind PasswordManager::class
    single { new(::AuthServiceImpl) } bind AuthService::class
    single { new(::AccountServiceImpl) } bind AccountService::class
    single { new(::TokenServiceImpl) } bind TokenService::class
    single { new(::DocumentServiceImpl) } bind DocumentService::class
    single { new(::FileStorageServiceImpl) } bind FileStorageService::class
    single { new(::ProfileServiceImpl) } bind ProfileService::class
    single { new(::DeviceServiceImpl) } bind DeviceService::class
    single { new(::TicketServiceImpl) } bind TicketService::class
    single { new(::SecurityServiceImpl) } bind SecurityService::class
}
package com.studystream.app.di

import com.studystream.app.data.service.*
import com.studystream.app.domain.service.*
import org.koin.core.module.dsl.new
import org.koin.dsl.module

val serviceModule = module {
    single<PasswordManager> { new(::PasswordManager) }
    single<AuthService> { new(::AuthServiceImpl) }
    single<AccountService> { new(::AccountServiceImpl) }
    single<TokenService> { new(::TokenServiceImpl) }
    single<DocumentService> { new(::DocumentServiceImpl) }
    single<FileStorageService> { new(::FileStorageServiceImpl) }
}
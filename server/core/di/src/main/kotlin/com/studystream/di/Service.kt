package com.studystream.di

import com.studystream.data.service.*
import com.studystream.domain.service.AccountService
import com.studystream.domain.service.AuthService
import com.studystream.domain.service.DocumentService
import com.studystream.domain.service.TokenService
import org.koin.core.module.dsl.new
import org.koin.dsl.module

val serviceModule = module {
    single<PasswordManager> { new(::PasswordManager) }
    single<AuthService> { new(::AuthServiceImpl) }
    single<AccountService> { new(::AccountServiceImpl) }
    single<TokenService> { new(::TokenServiceImpl) }
    single<DocumentService> { new(::DocumentServiceImpl) }
}
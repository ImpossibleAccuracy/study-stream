package com.studystream.di

import com.studystream.data.service.AccountServiceImpl
import com.studystream.data.service.AuthServiceImpl
import com.studystream.data.service.PasswordManager
import com.studystream.data.service.TokenServiceImpl
import com.studystream.domain.properties.AppProperties
import com.studystream.domain.service.AccountService
import com.studystream.domain.service.AuthService
import com.studystream.domain.service.TokenService
import org.koin.core.module.dsl.new
import org.koin.dsl.module

fun serviceModule(properties: AppProperties) = module {
    single<PasswordManager> { new(::PasswordManager) }
    single<AuthService> { new(::AuthServiceImpl) }
    single<AccountService> { new(::AccountServiceImpl) }
    single<TokenService> { TokenServiceImpl(properties.token) }
}
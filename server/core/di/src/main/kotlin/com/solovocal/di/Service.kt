package com.solovocal.di

import com.solovocal.data.service.AccountServiceImpl
import com.solovocal.data.service.AuthServiceImpl
import com.solovocal.data.service.TokenServiceImpl
import com.solovocal.domain.properties.AppProperties
import com.solovocal.domain.service.AccountService
import com.solovocal.domain.service.AuthService
import com.solovocal.domain.service.TokenService
import org.koin.core.module.dsl.new
import org.koin.dsl.module

fun serviceModule(properties: AppProperties) = module {
    single<AuthService> { new(::AuthServiceImpl) }
    single<AccountService> { new(::AccountServiceImpl) }
    single<TokenService> { TokenServiceImpl(properties.token) }
}
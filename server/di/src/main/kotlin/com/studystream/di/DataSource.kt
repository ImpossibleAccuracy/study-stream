package com.studystream.di

import com.studystream.data.datasource.base.*
import com.studystream.data.datasource.impl.*
import org.koin.core.module.dsl.new
import org.koin.dsl.bind
import org.koin.dsl.module

val dataSourceModule = module {
    factory { new(::AccountDataSourceImpl) } bind AccountDataSource::class
    factory { new(::DeviceDataSourceImpl) } bind DeviceDataSource::class
    factory { new(::DeviceDataSourceImpl) } bind DeviceDataSource::class
    factory { new(::DocumentDataSourceImpl) } bind DocumentDataSource::class
    factory { new(::DocumentTypeDataSourceImpl) } bind DocumentTypeDataSource::class
    factory { new(::PrivilegeDataSourceImpl) } bind PrivilegeDataSource::class
    factory { new(::ProfileDataSourceImpl) } bind ProfileDataSource::class
    factory { new(::RoleDataSourceImpl) } bind RoleDataSource::class
    factory { new(::TicketDataSourceImpl) } bind TicketDataSource::class
    factory { new(::TicketTypeDataSourceImpl) } bind TicketTypeDataSource::class
}
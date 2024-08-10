package com.studystream.feature.filestorage.di

import com.studystream.feature.filestorage.data.FileStorageServiceImpl
import com.studystream.feature.filestorage.domain.FileStorageService
import org.koin.core.module.dsl.new
import org.koin.dsl.module

val fileStorageModule = module {
    single<FileStorageService> { new(::FileStorageServiceImpl) }
}
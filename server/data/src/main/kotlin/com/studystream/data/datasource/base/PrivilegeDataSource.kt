package com.studystream.data.datasource.base

import com.studystream.data.model.Privilege

interface PrivilegeDataSource {
    suspend fun save(block: Privilege.() -> Unit): Privilege

    suspend fun findByTitleIn(list: List<String>): List<Privilege>

    suspend fun getAll(): List<Privilege>
}
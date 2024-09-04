package com.studystream.data.datasource.impl

import com.studystream.data.database.dao.PrivilegeDao
import com.studystream.data.database.tables.PrivilegeTable
import com.studystream.data.datasource.base.PrivilegeDataSource
import com.studystream.data.model.Privilege
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList

class PrivilegeDataSourceImpl : PrivilegeDataSource {
    override suspend fun save(block: Privilege.() -> Unit): Privilege =
        PrivilegeDao.new(block)

    override suspend fun findByTitleIn(list: List<String>): List<Privilege> =
        PrivilegeDao
            .find(PrivilegeTable.title inList list)
            .toList()

    override suspend fun getAll(): List<Privilege> =
        PrivilegeDao.all().toList()
}
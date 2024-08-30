package com.studystream.data.service

import com.studystream.data.database.dao.PrivilegeDao
import com.studystream.data.database.dao.RoleDao
import com.studystream.data.database.tables.PrivilegeTable
import com.studystream.data.database.utils.toSizedCollection
import com.studystream.data.utils.ioCatchingCall
import com.studystream.domain.security.Permission
import com.studystream.domain.security.Role
import com.studystream.domain.service.SecurityService
import kotlinx.coroutines.async

class SecurityServiceImpl(
    private val roleDao: RoleDao,
    private val privilegeDao: PrivilegeDao,
) : SecurityService {
    override suspend fun createAllRoles(): Result<Int> = ioCatchingCall {
        val dbRoles = roleDao.all().toList()

        Role.entries
            .asSequence()
            .map { it.roleName }
            .distinct()
            .filterNot { role ->
                dbRoles.any {
                    role == it.title
                }
            }
            .onEach { role ->
                roleDao.new {
                    this.title = role
                }
            }
            .count()
    }

    override suspend fun createAllPermissions(): Result<Int> = ioCatchingCall {
        val dbPermissions = privilegeDao.all().toList()

        Permission.entries
            .asSequence()
            .map { it.privilegeName }
            .distinct()
            .filterNot { permission ->
                dbPermissions.any { it.title == permission }
            }
            .onEach {
                privilegeDao.new {
                    this.title = it
                }
            }
            .count()
    }

    override suspend fun createRolesPermissions(): Result<Int> = ioCatchingCall {
        val dbRoles = roleDao.all().toList()

        Role.entries
            .map { role ->
                async {
                    val dbRole = dbRoles.first { it.title == role.roleName }

                    role.permissions
                        .filter { permission ->
                            dbRole.privileges.none { it.title == permission.privilegeName }
                        }
                        .map { it.privilegeName }
                        .also {
                            val list = privilegeDao.find {
                                PrivilegeTable.title inList it
                            }

                            dbRole.privileges = dbRole.privileges.plus(list).toSizedCollection()
                        }
                        .isNotEmpty()
                }
            }
            .count { it.await() }
    }
}
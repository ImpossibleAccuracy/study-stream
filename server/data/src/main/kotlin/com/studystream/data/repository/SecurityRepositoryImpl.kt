package com.studystream.data.repository

import com.studystream.data.database.utils.toSizedCollection
import com.studystream.data.datasource.base.PrivilegeDataSource
import com.studystream.data.datasource.base.RoleDataSource
import com.studystream.data.utils.ioCatchingCall
import com.studystream.domain.repository.SecurityRepository
import com.studystream.domain.security.Permission
import com.studystream.domain.security.Role
import kotlinx.coroutines.async

class SecurityRepositoryImpl(
    private val roleDataSource: RoleDataSource,
    private val privilegeDataSource: PrivilegeDataSource,
) : SecurityRepository {
    override suspend fun createAllRoles(): Result<Int> = ioCatchingCall {
        val dbRoles = roleDataSource.getAll()

        Role.entries
            .asSequence()
            .map { it.roleName }
            .distinct()
            .filterNot { role ->
                dbRoles.any {
                    role == it.title
                }
            }
            .toList()
            .onEach { role ->
                roleDataSource.save {
                    this.title = role
                }
            }
            .count()
    }

    override suspend fun createAllPermissions(): Result<Int> = ioCatchingCall {
        val dbPermissions = privilegeDataSource.getAll()

        Permission.entries
            .asSequence()
            .map { it.privilegeName }
            .distinct()
            .filterNot { permission ->
                dbPermissions.any { it.title == permission }
            }
            .toList()
            .onEach {
                privilegeDataSource.save {
                    this.title = it
                }
            }
            .count()
    }

    override suspend fun createRolesPermissions(): Result<Int> = ioCatchingCall {
        val dbRoles = roleDataSource.getAll()

        Role.entries
            .map { role ->
                async {
                    val dbRole = dbRoles.first { it.title == role.roleName }

                    role.permissions
                        .toList()
                        .filter { permission ->
                            dbRole.privileges
                                .toList()
                                .none { it.title == permission.privilegeName }
                        }
                        .takeIf { it.isNotEmpty() }
                        ?.map { it.privilegeName }
                        ?.also { missingPrivileges ->
                            val list = privilegeDataSource.findByTitleIn(missingPrivileges)

                            dbRole.privileges = dbRole.privileges.plus(list).toSizedCollection()
                        }
                        ?.isNotEmpty() ?: false
                }
            }
            .count { it.await() }
    }
}
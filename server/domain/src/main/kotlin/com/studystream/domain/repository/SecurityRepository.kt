package com.studystream.domain.repository

interface SecurityRepository {
    suspend fun createAllRoles(): Result<Int>

    suspend fun createAllPermissions(): Result<Int>

    suspend fun createRolesPermissions(): Result<Int>
}
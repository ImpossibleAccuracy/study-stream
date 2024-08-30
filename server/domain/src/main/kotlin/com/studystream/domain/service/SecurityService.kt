package com.studystream.domain.service

interface SecurityService {
    suspend fun createAllRoles(): Result<Int>

    suspend fun createAllPermissions(): Result<Int>

    suspend fun createRolesPermissions(): Result<Int>
}
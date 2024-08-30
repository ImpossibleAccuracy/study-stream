package com.studystream.app.server.plugins.security

import com.studystream.app.domain.coroutine.backgroundScope
import com.studystream.app.domain.service.SecurityService
import io.ktor.server.application.*
import io.ktor.util.logging.*
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.koin.ktor.ext.get

fun Application.createAllRolesAndPrivileges() {
    val securityRepository = get<SecurityService>()
    val logger = get<Logger>()

    backgroundScope.launch {
        newSuspendedTransaction {
            securityRepository.createAllPermissions().getOrThrow().let {
                if (it == 0) {
                    logger.info("No missing permissions")
                } else {
                    logger.warn("Added $it missing permissions")
                }
            }

            securityRepository.createAllRoles().getOrThrow().let {
                if (it == 0) {
                    logger.info("No missing roles")
                } else {
                    logger.warn("Added $it missing roles")
                }
            }

            securityRepository.createRolesPermissions().getOrThrow().let {
                if (it == 0) {
                    logger.info("All roles have all permissions")
                } else {
                    logger.warn("Added missing permissions to $it roles")
                }
            }
        }
    }
}
package com.studystream.domain.security

enum class Role(
    val roleName: String,
    val permissions: List<Permission>,
) {
    USER("User", listOf()),

    ADMIN("Admin", Permission.entries),

    MODERATOR(
        "Moderator",
        listOf(
            Permission.ACCOUNTS_READ,
            Permission.PROFILES_READ,
            Permission.TICKETS_READ,
            Permission.TICKET_TYPES_CREATE,
            Permission.TICKET_TYPES_DELETE,
        )
    ),
}
package com.studystream.app.server.feature.account

import com.studystream.app.server.feature.account.routes.*
import com.studystream.app.server.feature.account.routes.device.installGetDevicesListRoute
import com.studystream.app.server.feature.account.routes.device.installSaveDeviceRoute
import io.ktor.server.routing.*

fun Routing.installAccountRoutes() {
    installCreateAccountRoute()
    installGetAccountsListRoute()
    installGetAccountsDetailsRoute()
    installUpdateAccountsRoute()
    installDeleteAccountsRoute()

    installSaveDeviceRoute()
    installGetDevicesListRoute()
}

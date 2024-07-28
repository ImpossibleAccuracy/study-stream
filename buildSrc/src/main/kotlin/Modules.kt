@Suppress("ConstPropertyName", "unused")
object Modules {
    object Shared {
        private const val Base = ":shared"
    }

    object Server {
        private const val Base = ":server"

        const val App = "$Base:app"
        const val Domain = "$Base:core:domain"
        const val Data = "$Base:core:data"
        const val Di = "$Base:core:di"

        object Plugin {
            private const val Base = "${Server.Base}:plugin"

            const val Monitoring = "$Base:monitoring"
            const val Route = "$Base:route"
            const val Security = "$Base:security"
            const val Serialization = "$Base:serialization"
            const val Sockets = "$Base:sockets"
        }

        object Shared {
            private const val Base = "${Server.Base}:shared"

            const val Feature = "$Base:feature"
            const val Security = "$Base:security"
            const val Socket = "$Base:socket"
        }

        object Feature {
            private const val Base = "${Server.Base}:feature"

            const val Auth = "$Base:auth"
        }
    }
}
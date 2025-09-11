package discord.embeddedappsdk

inline val Platform.Companion.DESKTOP: Platform
    get() = "desktop".unsafeCast<Platform>()

inline val Platform.Companion.MOBILE: Platform
    get() = "mobile".unsafeCast<Platform>()
package discord.embeddedappsdk

import web.time.EpochTimeStamp

fun <T : EventPayloadData> DiscordSDK.subscribe(event: EventType<T>, callback: (T) -> Unit) {
    subscribe(event.name) {
        callback(it as T)
    }
}

fun Long.toEpochTimeStamp() = this.toDouble().unsafeCast<EpochTimeStamp>()
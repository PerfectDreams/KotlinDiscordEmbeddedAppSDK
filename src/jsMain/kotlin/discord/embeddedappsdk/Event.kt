package discord.embeddedappsdk

class EventType<T : EventPayloadData>(val name: String) {
    companion object
}

inline val EventType.Companion.ACTIVITY_LAYOUT_MODE_UPDATE: EventType<ActivityLayoutModeUpdateEvent>
    get() = EventType("ACTIVITY_LAYOUT_MODE_UPDATE")
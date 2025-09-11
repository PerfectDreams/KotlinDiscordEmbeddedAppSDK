package discord.embeddedappsdk

external interface EventPayloadData

external class ActivityLayoutModeUpdateEvent : EventPayloadData {
    @JsName("layout_mode")
    val layoutMode: Int
}
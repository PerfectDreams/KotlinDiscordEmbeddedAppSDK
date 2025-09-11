@file:JsModule("@discord/embedded-app-sdk")
@file:JsNonModule
package discord.embeddedappsdk

import js.promise.Promise
import kotlinx.js.JsPlainObject
import web.time.EpochTimeStamp

external class DiscordSDK(val clientId: String) {
    val instanceId: String
    val customId: String?
    val referrerId: String?
    val platform: Platform
    val mobileAppVersion: String?
    val sdkVersion: String
    val commands: Commands
    val av: String?
    val channelId: String?
    val guildId: String?
    // TODO: source
    val sourceOrigin: String

    fun ready(): Promise<Unit>
    fun subscribe(event: String, callback: (EventPayloadData) -> Unit)
    fun close(closeCode: Int, reason: String)
}

external interface Commands {
    fun authorize(params: AuthorizeParams): Promise<AuthorizeResponse>
    fun authenticate(params: AuthenticateParams): Promise<AuthenticateResponse>
    fun openShareMomentDialog(params: ShareMomentDialog): Promise<dynamic>
    fun openExternalLink(params: OpenExternalLinkRequest): Promise<dynamic>
    fun setActivity(params: SetActivityRequest): Promise<dynamic>
    fun userSettingsGetLocale(): Promise<UserSettingsGetLocaleResponse>
}

@JsPlainObject
external interface AuthorizeParams {
    var client_id: String
    var response_type: String
    var state: String
    var prompt: String
    var scope: Array<String>
}

@JsPlainObject
external interface AuthorizeResponse {
    val code: String
}

@JsPlainObject
external interface AuthenticateParams {
    var access_token: String
}

@JsPlainObject
external interface AuthenticateResponse {
    val access_token: String
    val user: User
    val scopes: Array<String>
    val expires: String
    val application: Application
}

@JsPlainObject
external interface User {
    val id: String
    val username: String
    val discriminator: String
    val global_name: String?
    val avatar: String?
    val avatar_decoration_data: AvatarDecorationData?
    val bot: Boolean
    val flags: Number?
    val premium_type: Number?
}

@JsPlainObject
external interface AvatarDecorationData {
    val asset: String
    val sku_id: String?
}

@JsPlainObject
external interface Application {
    val description: String
    val icon: String?
    val id: String
    val rpc_origins: Array<String>?
    val name: String
}

@JsPlainObject
external interface ShareMomentDialog {
    var mediaUrl: String
}

@JsPlainObject
external interface SetActivityRequest {
    var activity: Activity
}

@JsPlainObject
external interface OpenExternalLinkRequest {
    var url: String
}

@JsPlainObject
external interface UserSettingsGetLocaleResponse {
    var locale: String
}

@JsPlainObject
external interface Activity {
    var type: Int
    var state: String?
    var details: String?
    var timestamps: Timestamps?
    var assets: ActivityAssets?
    var party: ActivityParty?
}

@JsPlainObject
external interface ActivityAssets {
    var large_image: String?
    var large_text: String?
    var small_image: String?
    var small_text: String?
}

@JsPlainObject
external interface ActivityParty {
    var id: String?
    var size: Array<Int>
}

@JsPlainObject
external interface Timestamps {
    var start: EpochTimeStamp?
    var end: EpochTimeStamp?
}

@JsPlainObject
external interface Button {
    var label: String
    var url: String
}

sealed external interface Platform {
    companion object
}

sealed external interface Events {
    companion object
}
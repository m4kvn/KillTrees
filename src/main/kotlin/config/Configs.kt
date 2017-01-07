package config

import com.squareup.moshi.Json

/**
 * Created by masahiro on 2017/01/07.
 */

data class Configs(
        @Json(name = "クリエイティブモード時に道具の耐久値を減らす") val onCreativeDurabilityReduce: Boolean = false,
        @Json(name = "道具が壊れた時にメッセージを表示する") val onToolBrokenMsg:Boolean = true,
        @Json(name = "道具の耐久値をメッセージで表示する") val onToolDurabilityMsg:Boolean = true
)

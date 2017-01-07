package config

import com.google.gson.annotations.SerializedName

/**
 * Created by masahiro on 2017/01/07.
 */

data class Configs(

        @SerializedName("クリエイティブモード時に道具の耐久値を減らす")
        val onCreativeDurabilityReduce: Boolean = false,

        @SerializedName("道具が壊れたことを表示する")
        val onToolBrokenMsg: Boolean = false,

        @SerializedName("道具の耐久値と壊したブロックの数を表示する")
        val onToolDurabilityMsg: Boolean = true

)
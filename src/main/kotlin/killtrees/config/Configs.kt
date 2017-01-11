package killtrees.config

import org.bukkit.Material
import com.google.gson.annotations.SerializedName
import org.bukkit.TreeSpecies

data class Configs(

        @SerializedName("クリエイティブモード時に道具の耐久値を減らす")
        val onCreativeDurabilityReduce: Boolean = false,

        @SerializedName("道具の耐久値と壊したブロックの数を表示する")
        val onToolDurabilityMsg: Boolean = true,

        @SerializedName("一度に壊せるブロック数の上限")
        val maxBlockAmount: Int = 200,

        @SerializedName("破壊する隣接ブロックの距離")
        val rangeBreakBlock: Int = 1,

        @SerializedName("破壊できる原木ブロックの種類")
        val breakWoodTypes: List<String> = TreeSpecies.values().map { it.name },

        @SerializedName("破壊できるブロックの種類")
        val breakBlockTypes: List<String> = emptyList(),

        @SerializedName("使える道具の種類")
        val validToolTypes: List<String> = listOf(
                Material.DIAMOND_AXE.name,
                Material.STONE_AXE.name,
                Material.WOOD_AXE.name,
                Material.IRON_AXE.name,
                Material.GOLD_AXE.name
        )
)
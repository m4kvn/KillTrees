package killtrees.utils

import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

fun Player.isCreativeMode() : Boolean = gameMode == GameMode.CREATIVE

fun Player.itemInMainHand() : ItemStack = inventory.itemInMainHand
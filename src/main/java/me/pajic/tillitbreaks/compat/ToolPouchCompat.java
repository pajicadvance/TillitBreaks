package me.pajic.tillitbreaks.compat;

import me.pajic.tillitbreaks.util.TIBUtil;
import me.pajic.toolpouch.util.ToolPouchUtil;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.lang3.tuple.MutableTriple;

public class ToolPouchCompat {

	public static void addArrowsFromToolPouch(Player player, MutableTriple<Integer, Integer, Boolean> arrows) {
		TIBUtil.addArrowsFromItemList(ToolPouchUtil.getItemsFromToolPouch(player, stack -> stack.is(ItemTags.ARROWS)), arrows);
	}
}

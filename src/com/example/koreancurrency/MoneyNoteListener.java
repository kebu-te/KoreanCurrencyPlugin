package com.example.koreancurrency;

import com.example.koreancurrency.utils.BankManager;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.NumberFormat;
import java.util.Locale;

public class MoneyNoteListener implements Listener {
    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!player.isSneaking()) return; // shift 누른 상태만 허용

        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null || item.getType() != Material.PAPER || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasDisplayName()) return;

        String displayName = meta.getDisplayName().replace(",", "").replace("원", "").trim();
        int value;
        try {
            value = Integer.parseInt(displayName);
        } catch (NumberFormatException e) {
            return;
        }

        // 입금 처리
        BankManager.deposit(player.getUniqueId(), value);
        player.getInventory().removeItem(item);
        String formatted = NumberFormat.getNumberInstance(Locale.KOREA).format(value);
        player.sendMessage("📥 수표 " + formatted + "원을 입금했습니다.");
        event.setCancelled(true);
    }
}

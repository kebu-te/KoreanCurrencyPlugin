package com.example.koreancurrency.commands;

import com.example.koreancurrency.utils.BankManager;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.NumberFormat;
import java.util.Locale;

public class WithdrawCommand implements CommandExecutor {
    private static final int MIN_UNIT = 10;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return false;
        if (args.length != 1) {
            player.sendMessage("❗ 사용법: /출금 <금액>");
            return true;
        }

        try {
            int amount = Integer.parseInt(args[0]);
            if (amount < MIN_UNIT || amount % MIN_UNIT != 0) {
                player.sendMessage("❗ 최소 단위는 " + MIN_UNIT + "원이며, 10원 단위로만 출금할 수 있습니다.");
                return true;
            }

            if (!BankManager.withdraw(player.getUniqueId(), amount)) {
                player.sendMessage("❗ 잔액이 부족합니다.");
                return true;
            }

            ItemStack moneyItem = new ItemStack(Material.PAPER, 1);
            ItemMeta meta = moneyItem.getItemMeta();
            if (meta != null) {
                String formatted = NumberFormat.getNumberInstance(Locale.KOREA).format(amount);
                meta.setDisplayName(formatted + "원");
                meta.setCustomModelData(amount); // 위조 방지용
                moneyItem.setItemMeta(meta);
            }

            player.getInventory().addItem(moneyItem);
            player.sendMessage("💳 " + amount + "원을 출금했습니다.");
        } catch (NumberFormatException e) {
            player.sendMessage("❗ 금액은 숫자로 입력해야 합니다.");
        }

        return true;
    }
}

package com.example.koreancurrency.commands;

import com.example.koreancurrency.utils.BankManager;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.text.NumberFormat;
import java.util.Locale;

public class BalanceCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            int balance = BankManager.getBalance(player.getUniqueId());
            String formatted = NumberFormat.getNumberInstance(Locale.KOREA).format(balance);
            player.sendMessage("📒 현재 잔액: " + formatted + "원");
            return true;
        }
        return false;
    }
}

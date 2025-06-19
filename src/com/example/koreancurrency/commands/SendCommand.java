package com.example.koreancurrency.commands;

import com.example.koreancurrency.utils.BankManager;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.UUID;

public class SendCommand implements CommandExecutor {
    private static final int MIN_UNIT = 10;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player senderPlayer)) return false;
        if (args.length != 2) {
            senderPlayer.sendMessage("❗ 사용법: /송금 <플레이어> <금액>");
            return true;
        }

        Player receiver = Bukkit.getPlayer(args[0]);
        if (receiver == null || !receiver.isOnline()) {
            senderPlayer.sendMessage("❗ 해당 플레이어가 온라인이 아닙니다.");
            return true;
        }

        try {
            int amount = Integer.parseInt(args[1]);
            if (amount < MIN_UNIT || amount % MIN_UNIT != 0) {
                senderPlayer.sendMessage("❗ 최소 단위는 " + MIN_UNIT + "원이며, 10원 단위로만 송금할 수 있습니다.");
                return true;
            }

            UUID senderUUID = senderPlayer.getUniqueId();
            UUID receiverUUID = receiver.getUniqueId();

            if (!BankManager.withdraw(senderUUID, amount)) {
                senderPlayer.sendMessage("❗ 잔액이 부족합니다.");
                return true;
            }

            BankManager.deposit(receiverUUID, amount);

            String formatted = NumberFormat.getNumberInstance(Locale.KOREA).format(amount);
            senderPlayer.sendMessage("✅ " + receiver.getName() + "님에게 " + formatted + "원을 송금했습니다.");
            receiver.sendMessage("📨 " + senderPlayer.getName() + "님에게서 " + formatted + "원을 송금받았습니다.");
        } catch (NumberFormatException e) {
            senderPlayer.sendMessage("❗ 금액은 숫자로 입력해야 합니다.");
        }

        return true;
    }
}

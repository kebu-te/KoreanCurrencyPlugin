package com.example.koreancurrency.commands;

import com.example.koreancurrency.utils.BankManager;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.text.NumberFormat;
import java.util.Locale;

public class DepositCommand implements CommandExecutor {
    private static final int MIN_UNIT = 10;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage("❗ 이 명령어는 관리자만 사용할 수 있습니다.");
            return true;
        }

        if (args.length != 2) {
            sender.sendMessage("❗ 사용법: /입금 <플레이어> <금액>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null || !target.isOnline()) {
            sender.sendMessage("❗ 해당 플레이어가 온라인이 아닙니다.");
            return true;
        }

        try {
            int amount = Integer.parseInt(args[1]);
            if (amount < MIN_UNIT || amount % MIN_UNIT != 0) {
                sender.sendMessage("❗ 최소 단위는 " + MIN_UNIT + "원이며, 10원 단위로만 입금할 수 있습니다.");
                return true;
            }

            BankManager.deposit(target.getUniqueId(), amount);

            String formatted = NumberFormat.getNumberInstance(Locale.KOREA).format(amount);
            sender.sendMessage("✅ " + target.getName() + "님에게 " + formatted + "원을 지급했습니다.");
            target.sendMessage("📥 관리자에게서 " + formatted + "원을 입금 받았습니다.");
        } catch (NumberFormatException e) {
            sender.sendMessage("❗ 금액은 숫자로 입력해야 합니다.");
        }

        return true;
    }
}

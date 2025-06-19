package com.example.koreancurrency;

import com.example.koreancurrency.commands.*;
import org.bukkit.plugin.java.JavaPlugin;

public class KoreanCurrency extends JavaPlugin {
    @Override
    public void onEnable() {
        getCommand("잔액확인").setExecutor(new BalanceCommand());
        getCommand("출금").setExecutor(new WithdrawCommand());
        getCommand("송금").setExecutor(new SendCommand());
        getCommand("입금").setExecutor(new DepositCommand());

        getServer().getPluginManager().registerEvents(new MoneyNoteListener(), this);
        getLogger().info("KoreanCurrency 플러그인이 활성화되었습니다.");
    }

    @Override
    public void onDisable() {
        getLogger().info("KoreanCurrency 플러그인이 비활성화되었습니다.");
    }
}

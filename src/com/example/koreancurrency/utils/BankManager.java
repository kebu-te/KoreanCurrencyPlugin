package com.example.koreancurrency.utils;

import java.util.HashMap;
import java.util.UUID;

public class BankManager {
    private static final HashMap<UUID, Integer> balances = new HashMap<>();
    private static final int INITIAL_BALANCE = 1000;

    public static int getBalance(UUID uuid) {
        return balances.getOrDefault(uuid, INITIAL_BALANCE);
    }

    public static void deposit(UUID uuid, int amount) {
        balances.put(uuid, getBalance(uuid) + amount);
    }

    public static boolean withdraw(UUID uuid, int amount) {
        int current = getBalance(uuid);
        if (current >= amount) {
            balances.put(uuid, current - amount);
            return true;
        }
        return false;
    }

    public static void setBalance(UUID uuid, int amount) {
        balances.put(uuid, amount);
    }
}

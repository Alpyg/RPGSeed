package io.alpyg.rpg.economy;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.service.context.Context;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.service.economy.account.Account;
import org.spongepowered.api.service.economy.account.UniqueAccount;
import org.spongepowered.api.service.economy.transaction.ResultType;
import org.spongepowered.api.service.economy.transaction.TransactionResult;
import org.spongepowered.api.service.economy.transaction.TransactionType;
import org.spongepowered.api.service.economy.transaction.TransactionTypes;
import org.spongepowered.api.service.economy.transaction.TransferResult;
import org.spongepowered.api.text.Text;

import io.alpyg.rpg.Seed;
import io.alpyg.rpg.adventurer.data.AdventurerKeys;

public class PlayerAccount implements UniqueAccount {
	
	private UUID uuid;
	private Player player;
	
	public PlayerAccount(UUID uuid) {
		this.uuid = uuid;
		this.player = Sponge.getServer().getPlayer(uuid).get();
	}

	@Override
	public Text getDisplayName() {
		return Text.of(Seed.getUserStorageService().get(uuid).get().getName());
	}

	@Override
	public BigDecimal getDefaultBalance(Currency currency) {
		return BigDecimal.ZERO;
	}

	@Override
	public boolean hasBalance(Currency currency, Set<Context> contexts) {
		return player.get(AdventurerKeys.BALANCE).isPresent();
	}

	@Override
	public BigDecimal getBalance(Currency currency, Set<Context> contexts) {
		int balance = 0;
		if (hasBalance(currency, contexts)) {
			
			balance = player.get(AdventurerKeys.BALANCE).get();
		}
		
		return new BigDecimal(balance);
	}

	@Override
	public Map<Currency, BigDecimal> getBalances(Set<Context> contexts) {
        Map<Currency, BigDecimal> balances = new HashMap<>();

        for (Currency currency : Seed.getEconomy().getCurrencies()) {
            balances.put(currency, getBalance(currency, contexts));
        }

        return balances;
	}

	@Override
	public TransactionResult setBalance(Currency currency, BigDecimal amount, Cause cause, Set<Context> contexts) {
		TransactionResult transactionResult;

        amount = amount.min(BigDecimal.valueOf(1000000000));

        if (hasBalance(currency, contexts)) {
        	BigDecimal delta = amount.subtract(getBalance(currency));
            TransactionType transactionType = delta.compareTo(BigDecimal.ZERO) >= 0 ? TransactionTypes.DEPOSIT : TransactionTypes.WITHDRAW;

            player.offer(AdventurerKeys.BALANCE, amount.intValue());

            transactionResult = new SeedTransactionResult(this, currency, delta.abs(), contexts, ResultType.SUCCESS, transactionType);
        } else {
        	transactionResult = new SeedTransactionResult(this, currency, BigDecimal.ZERO, contexts, ResultType.FAILED, TransactionTypes.DEPOSIT);
        }

        return transactionResult;
	}

	@Override
	public Map<Currency, TransactionResult> resetBalances(Cause cause, Set<Context> contexts) {
        Map<Currency, TransactionResult> result = new HashMap<>();

        for (Currency currency : Seed.getEconomy().getCurrencies()) {
            result.put(currency, resetBalance(currency, cause, contexts));
        }

        return result;
	}

	@Override
	public TransactionResult resetBalance(Currency currency, Cause cause, Set<Context> contexts) {
		return setBalance(currency, BigDecimal.valueOf(0), cause);
	}

	@Override
	public TransactionResult deposit(Currency currency, BigDecimal amount, Cause cause, Set<Context> contexts) {
        BigDecimal curBalance = getBalance(currency, contexts);
        BigDecimal newBalance = curBalance.add(amount);

        return setBalance(currency, newBalance, cause);
	}

	@Override
	public TransactionResult withdraw(Currency currency, BigDecimal amount, Cause cause, Set<Context> contexts) {
        BigDecimal curBalance =  getBalance(currency, contexts);
        BigDecimal newBalance = curBalance.subtract(amount);

        if (newBalance.compareTo(BigDecimal.ZERO) >= 0) {
            return setBalance(currency, newBalance, cause);
        }

        return new SeedTransactionResult(this, currency, amount, contexts, ResultType.ACCOUNT_NO_FUNDS, TransactionTypes.WITHDRAW);
	}

	@Override
	public TransferResult transfer(Account to, Currency currency, BigDecimal amount, Cause cause, Set<Context> contexts) {
		TransferResult transferResult;

		if (hasBalance(currency, contexts)) {
        	BigDecimal curBalance = getBalance(currency, contexts);
            BigDecimal newBalance = curBalance.subtract(amount);

            if (newBalance.compareTo(BigDecimal.ZERO) >= 0) {
            	withdraw(currency, amount, cause, contexts);

            	if (to.hasBalance(currency)) {
                	to.deposit(currency, amount, cause, contexts);

                    transferResult = new SeedTransferResult(this, to, currency, amount, contexts, ResultType.SUCCESS, TransactionTypes.TRANSFER);

                    return transferResult;
            	} else {
                	transferResult = new SeedTransferResult(this, to, currency, amount, contexts, ResultType.FAILED, TransactionTypes.TRANSFER);

                    return transferResult;
                }
            } else {
            	transferResult = new SeedTransferResult(this, to, currency, amount, contexts, ResultType.ACCOUNT_NO_FUNDS, TransactionTypes.TRANSFER);

                return transferResult;
            }
        }

        transferResult = new SeedTransferResult(this, to, currency, amount, contexts, ResultType.FAILED, TransactionTypes.TRANSFER);

        return transferResult;
	}

	@Override
	public String getIdentifier() {
		return uuid.toString();
	}

	@Override
	public Set<Context> getActiveContexts() {
		return new HashSet<>();
	}

	@Override
	public UUID getUniqueId() {
		return uuid;
	}

}

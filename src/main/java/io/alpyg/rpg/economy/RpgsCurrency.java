package io.alpyg.rpg.economy;

import java.math.BigDecimal;

import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class RpgsCurrency implements Currency {

	@Override
	public String getId() {
		return "currency_silver";
	}

	@Override
	public String getName() {
		return "silver";
	}

	@Override
	public Text getDisplayName() {
		return Text.of(TextColors.WHITE, "Silver");
	}

	@Override
	public Text getPluralDisplayName() {
		return Text.of(TextColors.WHITE, "Silver");
	}

	@Override
	public Text getSymbol() {
		return Text.of(TextColors.WHITE, "s");
	}

	@Override
	public Text format(BigDecimal amount, int numFractionDigits) {
		return Text.of(TextColors.GRAY, amount.intValue());
	}

	@Override
	public int getDefaultFractionDigits() {
		return 0;
	}

	@Override
	public boolean isDefault() {
		return true;
	}

}

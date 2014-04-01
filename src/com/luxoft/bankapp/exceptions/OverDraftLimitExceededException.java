package com.luxoft.bankapp.exceptions;

/**
 * Created by Sergey Popov on 26.03.2014.
 */
public class OverDraftLimitExceededException extends NoEnoughFundsException {
    private float availableMoney;

	public OverDraftLimitExceededException(float availableMoney) {
		super("You haven't enough money. You can take only " + availableMoney);
        this.availableMoney = availableMoney;
    }

	public float getAvailableMoney() {
		return availableMoney;
	}

}

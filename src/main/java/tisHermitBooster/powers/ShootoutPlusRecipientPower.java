package tisHermitBooster.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static tisHermitBooster.tisHermitBoosterMod.makeID;

public class ShootoutPlusRecipientPower extends BasePower {
    public static final String POWER_ID = makeID(ShootoutPlusRecipientPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    public ShootoutPlusRecipientPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);
    }

    public void onEnergyRecharge() {
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}

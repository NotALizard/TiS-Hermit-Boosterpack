package tisHermitBooster.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static tisHermitBooster.tisHermitBoosterMod.makeID;

public class ShootoutRecipientPower extends BasePower {
    public static final String POWER_ID = makeID(ShootoutRecipientPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;
    private boolean upgraded;

    public ShootoutRecipientPower(AbstractCreature owner, boolean upgraded) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);
        this.upgraded = upgraded;
    }

    public void upgrade(){
        upgraded = true;
    }

    public boolean isUpgraded(){
        return upgraded;
    }

    public void onEnergyRecharge() {
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    public void updateDescription() {
        if(this.upgraded){
            this.description = DESCRIPTIONS[1];
        }
        else{
            this.description = DESCRIPTIONS[0];
        }
    }
}

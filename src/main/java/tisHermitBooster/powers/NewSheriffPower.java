package tisHermitBooster.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tisCardPack.powers.TauntPower;

import java.util.ArrayList;
import java.util.Objects;

import static tisHermitBooster.tisHermitBoosterMod.makeID;

public class NewSheriffPower extends BasePower {
    public static final String POWER_ID = makeID(NewSheriffPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private ArrayList<AbstractMonster> targets;

    public NewSheriffPower(AbstractCreature owner, AbstractMonster target) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);
        this.targets = new ArrayList<>();
        targets.add(target);
    }

    public void addTarget(AbstractMonster target){
        if(!this.targets.contains(target)){
            this.targets.add(target);
        }
    }

    public void onEnergyRecharge() {
        //For each monster, remove taunt only if we are the one taunting them.
        for(AbstractMonster m : targets){
            if(!m.isDead){
                TauntPower tp = (TauntPower)m.getPower(TauntPower.POWER_ID);
                if(tp != null){
                    if (Objects.equals(tp.source, this.owner)) {
                        this.addToBot(new RemoveSpecificPowerAction(m, this.source, TauntPower.POWER_ID));
                    }
                }
            }
        }
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}

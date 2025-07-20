package tisHermitBooster.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import spireTogether.network.P2P.P2PPlayer;
import spireTogether.util.SpireHelp.Multiplayer.Players;

import static tisHermitBooster.tisHermitBoosterMod.makeID;

public class FrontierJusticePower extends BasePower implements OnReceivePowerPower {
    public static final String POWER_ID = makeID(FrontierJusticePower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public FrontierJusticePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public boolean onReceivePower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if(this.owner instanceof AbstractPlayer){
            //Only want to eval this power on the character owner's client so that the list of "other players" is accurate
            if (power.type == PowerType.DEBUFF) {
                for(int i = 0; i < this.amount; i++){
                    P2PPlayer np = Players.GetRandomPlayer(true, true);
                    if(np == null) return true;
                    np.addPower(new ArtifactPower(owner, 1));
                }
            }
        }
        return true;
    }

    public void updateDescription() {
        if(this.amount > 1){
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
        else{
            this.description = DESCRIPTIONS[0];
        }
    }
}

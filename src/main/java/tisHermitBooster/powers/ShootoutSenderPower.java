package tisHermitBooster.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import spireTogether.network.P2P.P2PPlayer;
import spireTogether.network.objects.entities.NetworkPower;
import spireTogether.network.objects.items.NetworkCard;
import spireTogether.util.SpireHelp.Multiplayer.Players;

import java.util.Iterator;

import static tisHermitBooster.tisHermitBoosterMod.makeID;

public class ShootoutSenderPower extends BasePower {
    public static final String POWER_ID = makeID(ShootoutSenderPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    public ShootoutSenderPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.hasTag(AbstractCard.CardTags.STARTER_STRIKE) || card.hasTag(AbstractCard.CardTags.STARTER_DEFEND)) {
            this.flash();
            if(this.sendCard(card)){
                //Purge if the card was sent successfully
                action.exhaustCard = true;
            }
        }
    }

    private boolean sendCard(AbstractCard card){
        boolean recipientFound = false;
        for(P2PPlayer np : Players.GetPlayers(true, true)) {
            if((np.hasPower(ShootoutRecipientPower.POWER_ID) || np.hasPower(ShootoutPlusRecipientPower.POWER_ID)) && !np.hasPower(ShootoutSenderPower.POWER_ID)){
                recipientFound = true;
                AbstractCard sendCopy = card.makeStatEquivalentCopy();
                if(np.hasPower(ShootoutPlusRecipientPower.POWER_ID)){
                    sendCopy.upgrade();
                }
                sendCopy.setCostForTurn(-9);
                np.addCard(NetworkCard.Generate(sendCopy), CardGroup.CardGroupType.HAND);
            }
        }
        return recipientFound;
    }

    public void onEnergyRecharge() {
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}

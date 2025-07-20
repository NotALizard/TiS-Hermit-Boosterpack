package tisHermitBooster.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hermit.cards.Defend_Hermit;
import hermit.cards.Strike_Hermit;
import spireTogether.network.P2P.P2PPlayer;
import spireTogether.network.objects.items.NetworkCard;
import spireTogether.util.SpireHelp.Multiplayer.Players;
import spireTogether.util.SpireLogger;

import static tisHermitBooster.tisHermitBoosterMod.makeID;

public class ShootoutSenderPower extends BasePower {
    public static final String POWER_ID = makeID(ShootoutSenderPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = true;
    private static final CardStrings defendStrings;
    private static final CardStrings strikeStrings;

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
                    applyAdvancedUpgrade(sendCopy); //Allows upgrading strikes and defends to +2 / +3 / ...
                }
                sendCopy.setCostForTurn(-9);
                np.addCard(NetworkCard.Generate(sendCopy), CardGroup.CardGroupType.HAND);
            }
        }
        return recipientFound;
    }

    private void applyAdvancedUpgrade(AbstractCard card){
        if(!card.upgraded){
            card.upgrade();
            return;
        }

        try{
            int timesUpgraded = 1;
            if(!(card.name.charAt(card.name.length()-1) == '+')){
                String[] parts = card.name.split("\\+");
                if(parts.length > 1){
                    String numStr = parts[parts.length-1];
                    timesUpgraded = Integer.parseInt(numStr);
                }
            }

            timesUpgraded++;

            if(card.hasTag(AbstractCard.CardTags.STARTER_STRIKE)){
                card.baseDamage += 3;
                card.upgraded = true;
                card.name = strikeStrings.NAME + "+" + timesUpgraded;
            }
            if(card.hasTag(AbstractCard.CardTags.STARTER_DEFEND)){
                card.baseBlock += 3;
                card.upgraded = true;
                card.name = defendStrings.NAME + "+" + timesUpgraded;
            }
        }
        catch (Exception e){
            SpireLogger.LogError("Shootout - Error while trying to apply repated upgrades to a Strike/Defend card, giving up and moving on", SpireLogger.ErrorType.NON_FATAL);
        }
    }

    public void onEnergyRecharge() {
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    static {
        defendStrings = CardCrawlGame.languagePack.getCardStrings(Defend_Hermit.ID);
        strikeStrings = CardCrawlGame.languagePack.getCardStrings(Strike_Hermit.ID);
    }
}

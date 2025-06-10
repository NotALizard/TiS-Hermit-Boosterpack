package tisHermitBooster.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.characters.hermit;
import spireTogether.network.P2P.P2PPlayer;
import spireTogether.network.objects.items.NetworkCard;
import tisHermitBooster.powers.ShootoutRecipientPower;
import tisHermitBooster.powers.ShootoutSenderPower;

import static tisHermitBooster.tisHermitBoosterMod.cardPath;
import static tisHermitBooster.tisHermitBoosterMod.makeID;

public class Shootout extends AbstractHermitMultiplayerCard {
    public static final String ID = makeID(Shootout.class.getSimpleName());
    public static final String IMG = cardPath("skill/Shootout.png");
    private static final CardRarity RARITY;
    private static final CardTarget TARGET;
    private static final CardType TYPE;
    public static final CardColor COLOR;
    private static final int COST = 2;

    static {
        RARITY = CardRarity.UNCOMMON;
        TARGET = CardTarget.SELF;
        TYPE = CardType.SKILL;
        COLOR = hermit.Enums.COLOR_YELLOW;
    }

    public Shootout() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //Set own power to correct recipient state
        if(p.hasPower(ShootoutSenderPower.POWER_ID)){
            addToBot(new RemoveSpecificPowerAction(p, p, ShootoutSenderPower.POWER_ID));
        }
        if(p.hasPower(ShootoutRecipientPower.POWER_ID)){
            ShootoutRecipientPower power = (ShootoutRecipientPower)p.getPower(ShootoutRecipientPower.POWER_ID);
            if(upgraded){
                power.upgrade();
                power.updateDescription();
            }
        }
        else{
            this.addToBot(new ApplyPowerAction(p, p, new ShootoutRecipientPower(p, this.upgraded), -1));
        }

        //Set listener power on other players who don't have it already
        for(P2PPlayer player : this.getPlayers(true, true)) {
            if(!player.hasPower(ShootoutSenderPower.POWER_ID) && !player.hasPower(ShootoutRecipientPower.POWER_ID) ){
                player.addPower(new ShootoutSenderPower(p));
            }
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }

    }
}
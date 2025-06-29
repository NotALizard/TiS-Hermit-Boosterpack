package tisHermitBooster.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.cards.AbstractDynamicCard;
import hermit.cards.AbstractHermitCard;
import hermit.characters.hermit;
import spireTogether.network.P2P.P2PPlayer;
import tisHermitBooster.actions.DealersChoiceAction;

import static tisHermitBooster.tisHermitBoosterMod.cardPath;
import static tisHermitBooster.tisHermitBoosterMod.makeID;

public class DealersChoice extends AbstractHermitMultiplayerCard {
    public static final String ID = makeID(DealersChoice.class.getSimpleName());
    public static final String IMG = cardPath("skill/DealersChoice.png");
    private static final CardRarity RARITY;
    private static final CardTarget TARGET;
    private static final CardType TYPE;
    public static final CardColor COLOR;
    private static final int COST = -1;

    static {
        RARITY = CardRarity.RARE;
        TARGET = CardTarget.SELF;
        TYPE = CardType.SKILL;
        COLOR = hermit.Enums.COLOR_YELLOW;
    }

    public DealersChoice() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.tags.add(AbstractHermitCard.Enums.DEADON);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DealersChoiceAction(p,this.upgraded,this.freeToPlayOnce,this.energyOnUse,this.getPlayers(true, true)));
        if (this.isDeadOn()) {
            this.TriggerDeadOnEffect(p, m);
        }
    }

    public void DeadOnEffect(AbstractPlayer p, AbstractMonster m) {
        for(P2PPlayer player : this.getPlayers(true, true)) {
            player.gainEnergy(1);
        }
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractDynamicCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (this.isDeadOnPos()) {
            this.glowColor = AbstractDynamicCard.GOLD_BORDER_GLOW_COLOR.cpy();
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
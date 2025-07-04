package tisHermitBooster.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.cards.AbstractDynamicCard;
import hermit.cards.AbstractHermitCard;
import hermit.characters.hermit;
import hermit.powers.Rugged;
import tisCardPack.actions.ApplyTauntAction;
import tisHermitBooster.powers.NewSheriffPower;

import static tisHermitBooster.tisHermitBoosterMod.cardPath;
import static tisHermitBooster.tisHermitBoosterMod.makeID;

public class NewSheriff extends AbstractHermitMultiplayerCard {
    public static final String ID = makeID(NewSheriff.class.getSimpleName());
    public static final String IMG = cardPath("skill/NewSheriff.png");
    private static final CardRarity RARITY;
    private static final CardTarget TARGET;
    private static final CardType TYPE;
    public static final CardColor COLOR;
    private static final int COST = 2;
    private static final int MAGIC = 4;
    private static final int UPGRADE_MAGIC = 2;

    static {
        RARITY = CardRarity.UNCOMMON;
        TARGET = CardTarget.ENEMY;
        TYPE = CardType.SKILL;
        COLOR = hermit.Enums.COLOR_YELLOW;
    }

    public NewSheriff() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.baseMagicNumber = this.magicNumber = MAGIC;
        this.tags.add(AbstractHermitCard.Enums.DEADON);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyTauntAction(m, p, magicNumber));
        if(p.hasPower(NewSheriffPower.POWER_ID)){
            NewSheriffPower lr = (NewSheriffPower)p.getPower(NewSheriffPower.POWER_ID);
            lr.addTarget(m);
        }
        else{
            addToBot(new ApplyPowerAction(p, p, new NewSheriffPower(p, m)));
        }
        if (this.isDeadOn()) {
            this.TriggerDeadOnEffect(p, m);
        }
    }

    public void DeadOnEffect(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new Rugged(p, 1)));
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
            this.upgradeMagicNumber(UPGRADE_MAGIC);
            this.initializeDescription();
        }
    }
}
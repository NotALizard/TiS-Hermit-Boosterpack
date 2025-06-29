package tisHermitBooster.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.characters.hermit;
import tisHermitBooster.powers.FrontierJusticePower;

import static tisHermitBooster.tisHermitBoosterMod.cardPath;
import static tisHermitBooster.tisHermitBoosterMod.makeID;

public class FrontierJustice extends AbstractHermitMultiplayerCard {
    public static final String ID = makeID(FrontierJustice.class.getSimpleName());
    public static final String IMG = cardPath("power/FrontierJustice.png");
    private static final CardRarity RARITY;
    private static final CardTarget TARGET;
    private static final CardType TYPE;
    public static final CardColor COLOR;
    private static final int COST = 1;

    static {
        RARITY = CardRarity.RARE;
        TARGET = CardTarget.SELF;
        TYPE = CardType.POWER;
        COLOR = hermit.Enums.COLOR_YELLOW;
    }

    public FrontierJustice() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new FrontierJusticePower(p, 1), 1));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.isInnate = true;
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
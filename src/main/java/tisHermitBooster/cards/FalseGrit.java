package tisHermitBooster.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import hermit.characters.hermit;
import hermit.powers.Rugged;
import spireTogether.network.P2P.P2PPlayer;

import static tisHermitBooster.tisHermitBoosterMod.cardPath;
import static tisHermitBooster.tisHermitBoosterMod.makeID;

public class FalseGrit extends AbstractHermitMultiplayerCard {
    public static final String ID = makeID(FalseGrit.class.getSimpleName());
    public static final String IMG = cardPath("skill/FalseGrit.png");
    private static final CardRarity RARITY;
    private static final CardTarget TARGET;
    private static final CardType TYPE;
    public static final CardColor COLOR;
    private static final int COST = 2;
    private static final int MAGIC = 2;
    private static final int UPGRADE_MAGIC = 1;

    static {
        RARITY = CardRarity.UNCOMMON;
        TARGET = CardTarget.SELF;
        TYPE = CardType.SKILL;
        COLOR = hermit.Enums.COLOR_YELLOW;
    }

    public FalseGrit() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        for(P2PPlayer np : this.getPlayers(true, true)) {
            if(np.hasPower(WeakPower.POWER_ID)){
                np.addPower(new StrengthPower(p, magicNumber));
            }
            if(np.hasPower(FrailPower.POWER_ID)){
                np.addPower(new DexterityPower(p, magicNumber));
            }
            if(np.hasPower(VulnerablePower.POWER_ID)){
                np.addPower(new Rugged(p, magicNumber));
            }
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
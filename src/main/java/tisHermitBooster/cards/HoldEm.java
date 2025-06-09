package tisHermitBooster.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import hermit.characters.hermit;
import spireTogether.network.P2P.P2PPlayer;
import spireTogether.network.objects.items.NetworkCard;

import static tisHermitBooster.tisHermitBoosterMod.cardPath;
import static tisHermitBooster.tisHermitBoosterMod.makeID;

public class HoldEm extends AbstractHermitMultiplayerCard {
    public static final String ID = makeID(HoldEm.class.getSimpleName());
    public static final String IMG = cardPath("skill/default.png");
    private static final CardRarity RARITY;
    private static final CardTarget TARGET;
    private static final CardType TYPE;
    public static final CardColor COLOR;
    private static final int COST = 1;
    private static final int MAGIC = 5;
    private static final int UPGRADE_MAGIC = 2;

    static {
        RARITY = CardRarity.UNCOMMON;
        TARGET = CardTarget.SELF;
        TYPE = CardType.SKILL;
        COLOR = hermit.Enums.COLOR_YELLOW;
    }

    public HoldEm() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.baseMagicNumber = this.magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, -1)));
        this.addToBot(new ApplyPowerAction(p, p, new PlatedArmorPower(p, magicNumber)));

        for(P2PPlayer np : this.getPlayers(true, true)) {
            np.addPower(new DexterityPower(p, -1));
            np.addPower(new PlatedArmorPower(p, magicNumber));
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
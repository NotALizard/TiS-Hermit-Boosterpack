package tisHermitBooster.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.powers.Concentration;
import spireTogether.network.P2P.P2PPlayer;
import tisCardPack.actions.ApplyTauntAction;

import static tisHermitBooster.tisHermitBoosterMod.cardPath;
import static tisHermitBooster.tisHermitBoosterMod.makeID;

public class Diversion extends AbstractHermitMultiplayerCard {
    public static final String ID = makeID(Diversion.class.getSimpleName());
    public static final String IMG = cardPath("skill/Diversion.png");
    private static final CardRarity RARITY;
    private static final CardTarget TARGET;
    private static final CardType TYPE;
    public static final CardColor COLOR;
    private static final int COST = 1;
    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 1;

    static {
        RARITY = CardRarity.SPECIAL;
        TARGET = CardTarget.ENEMY;
        TYPE = CardType.SKILL;
        COLOR = CardColor.COLORLESS;
    }

    public Diversion() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = MAGIC;
        this.exhaust = true;
        this.retain = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyTauntAction(m, p, 2));

        for(P2PPlayer pl : this.getPlayers(true, true)) {
            pl.draw(magicNumber);
            pl.addPower(new Concentration(p,1));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.exhaust = false;
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_MAGIC);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }

    }
}
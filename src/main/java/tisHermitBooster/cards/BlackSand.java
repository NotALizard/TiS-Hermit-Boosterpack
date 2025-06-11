package tisHermitBooster.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import hermit.characters.hermit;
import spireTogether.network.P2P.P2PPlayer;

import static tisHermitBooster.tisHermitBoosterMod.cardPath;
import static tisHermitBooster.tisHermitBoosterMod.makeID;

public class BlackSand extends AbstractHermitMultiplayerCard {
    public static final String ID = makeID(BlackSand.class.getSimpleName());
    public static final String IMG = cardPath("skill/BlackSand.png");
    private static final CardRarity RARITY;
    private static final CardTarget TARGET;
    private static final CardType TYPE;
    public static final CardColor COLOR;
    private static final int COST = 2;

    static {
        RARITY = CardRarity.RARE;
        TARGET = CardTarget.SELF;
        TYPE = CardType.SKILL;
        COLOR = hermit.Enums.COLOR_YELLOW;
    }

    public BlackSand() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.isEthereal = true;
        this.baseBlock = 0;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.upgraded){
            this.addToTop(new GainBlockAction(p, p, this.block));
        }
        for(P2PPlayer np : this.getPlayers(true, true)) {
            np.addBlock(this.block);
        }
    }

    public void applyPowers() {
        AbstractPlayer p =  AbstractDungeon.player;
        this.baseBlock = p.maxHealth - p.currentHealth;
        super.applyPowers();

        this.rawDescription = this.upgraded ? cardStrings.UPGRADE_DESCRIPTION : cardStrings.DESCRIPTION;
        this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    public void onMoveToDiscard() {
        this.rawDescription = this.upgraded ? cardStrings.UPGRADE_DESCRIPTION : cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
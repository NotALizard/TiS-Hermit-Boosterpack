package tisHermitBooster.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.EmpowerCircleEffect;
import com.megacrit.cardcrawl.vfx.combat.MiracleEffect;
import com.megacrit.cardcrawl.vfx.combat.WaterDropEffect;
import com.megacrit.cardcrawl.vfx.combat.WaterSplashParticleEffect;
import hermit.characters.hermit;
import spireTogether.network.P2P.P2PPlayer;
import spireTogether.network.objects.items.NetworkCard;

import java.util.Iterator;

import static tisHermitBooster.tisHermitBoosterMod.cardPath;
import static tisHermitBooster.tisHermitBoosterMod.makeID;

public class DesertRain extends AbstractHermitMultiplayerCard {
    public static final String ID = makeID(DesertRain.class.getSimpleName());
    public static final String IMG = cardPath("skill/DesertRain.png");
    private static final CardRarity RARITY;
    private static final CardTarget TARGET;
    private static final CardType TYPE;
    public static final CardColor COLOR;
    private static final int COST = 2;
    private static final int MAGIC = 2;
    private static final int UPGRADE_MAGIC = 1;

    static {
        RARITY = CardRarity.RARE;
        TARGET = CardTarget.SELF;
        TYPE = CardType.SKILL;
        COLOR = hermit.Enums.COLOR_YELLOW;
    }

    public DesertRain() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.baseMagicNumber = this.magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int curseTotal = countExhaustedCurses(p);

        if(curseTotal > 0){
            for(P2PPlayer player : this.getPlayers(true, true)) {
                player.heal(curseTotal*magicNumber);
            }
        }
        for(int i = 0; i < 20; i++) {
            addToTop(new VFXAction(new EmpowerCircleEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)));
        }
        this.addToBot(new VFXAction(new MiracleEffect()));

        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    private int countExhaustedCurses(AbstractPlayer p){
        int curseTotal = 0;
        Iterator iter = p.exhaustPile.group.iterator();

        while(iter.hasNext()) {
            AbstractCard c = (AbstractCard)iter.next();
            if (c.type == CardType.CURSE) {
                curseTotal++;
            }
        }
        return curseTotal;
    }

    public void applyPowers() {
        super.applyPowers();
        int curseTotal = countExhaustedCurses(AbstractDungeon.player);
        int healing = curseTotal * magicNumber;

        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0] + healing + cardStrings.EXTENDED_DESCRIPTION[1];
        this.initializeDescription();
    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_MAGIC);
            this.initializeDescription();
        }
    }
}
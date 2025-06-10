package tisHermitBooster.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import hermit.characters.hermit;
import hermit.powers.Bruise;
import hermit.powers.HorrorPower;
import hermit.powers.Rugged;
import spireTogether.network.P2P.P2PPlayer;
import spireTogether.network.objects.items.NetworkCard;

import java.util.ArrayList;
import java.util.Random;

import static tisHermitBooster.tisHermitBoosterMod.cardPath;
import static tisHermitBooster.tisHermitBoosterMod.makeID;

public class Barfight extends AbstractHermitMultiplayerCard {
    public static final String ID = makeID(Barfight.class.getSimpleName());
    public static final String IMG = cardPath("skill/Barfight.png");
    private static final CardRarity RARITY;
    private static final CardTarget TARGET;
    private static final CardType TYPE;
    public static final CardColor COLOR;
    private static final int COST = 1;
    private static final int MAGIC = 5;
    private static final int UPGRADE_MAGIC = 1;
    private static final int ALLY_BRUISE = 5;
    private static final int UPGRADE_ALLY_BRUISE = -2;
    private int baseAllyBruise, allyBruise;

    static {
        RARITY = CardRarity.UNCOMMON;
        TARGET = CardTarget.SELF;
        TYPE = CardType.SKILL;
        COLOR = hermit.Enums.COLOR_YELLOW;
    }

    public Barfight() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = MAGIC;
        this.baseAllyBruise = this.allyBruise = ALLY_BRUISE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for(AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            this.addToBot(new ApplyPowerAction(mo, p, new Bruise(mo, magicNumber), magicNumber, true, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }
        addToBot(new ApplyPowerAction(p, p, new Bruise(p, allyBruise), allyBruise, true, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        for(P2PPlayer np : this.getPlayers(true, false)) {
            np.addPower(new Bruise(p, allyBruise));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.allyBruise += UPGRADE_ALLY_BRUISE;
            this.upgradeMagicNumber(UPGRADE_MAGIC);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
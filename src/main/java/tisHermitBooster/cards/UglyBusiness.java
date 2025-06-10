package tisHermitBooster.cards;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import hermit.characters.hermit;
import hermit.patches.EnumPatch;
import spireTogether.network.P2P.P2PPlayer;

import static tisHermitBooster.tisHermitBoosterMod.cardPath;
import static tisHermitBooster.tisHermitBoosterMod.makeID;

public class UglyBusiness extends AbstractHermitMultiplayerCard {
    public static final String ID = makeID(UglyBusiness.class.getSimpleName());
    public static final String IMG = cardPath("attack/UglyBusiness.png");
    private static final CardRarity RARITY;
    private static final CardTarget TARGET;
    private static final CardType TYPE;
    public static final CardColor COLOR;
    private static final int COST = 1;
    private static final int DAMAGE = 15;
    private static final int UPGRADE_DAMAGE = 5;
    private static final int MAGIC = 2;
    private static final int UPGRADE_MAGIC = 0;

    static {
        RARITY = CardRarity.UNCOMMON;
        TARGET = CardTarget.ENEMY;
        TYPE = CardType.ATTACK;
        COLOR = hermit.Enums.COLOR_YELLOW;
    }

    public UglyBusiness() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = DAMAGE;
        this.baseMagicNumber = this.magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), EnumPatch.HERMIT_GUN2));
        P2PPlayer rp = getRandomPlayer(true, true);
        if(rp != null){
            rp.addPower(new WeakPower(p, magicNumber, false));
            rp.addPower(new FrailPower(p, magicNumber, false));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_DAMAGE);
            this.upgradeMagicNumber(UPGRADE_MAGIC);
            this.initializeDescription();
        }
    }
}
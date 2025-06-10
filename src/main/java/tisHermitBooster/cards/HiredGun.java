package tisHermitBooster.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.cards.AbstractDynamicCard;
import hermit.cards.AbstractHermitCard;
import hermit.characters.hermit;
import hermit.patches.EnumPatch;
import hermit.powers.Rugged;


import static tisHermitBooster.tisHermitBoosterMod.*;

public class HiredGun extends AbstractHermitMultiplayerCard {
    public static final String ID = makeID(HiredGun.class.getSimpleName());
    public static final String IMG = cardPath("attack/HiredGun.png");
    private static final AbstractCard.CardRarity RARITY;
    private static final AbstractCard.CardTarget TARGET;
    private static final AbstractCard.CardType TYPE;
    public static final AbstractCard.CardColor COLOR;
    private static final int COST = 1;
    private static final int DAMAGE = 6;
    private static final int UPGRADE_DAMAGE = 2;
    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 0;

    static {
        RARITY = CardRarity.COMMON;
        TARGET = CardTarget.ENEMY;
        TYPE = CardType.ATTACK;
        COLOR = hermit.Enums.COLOR_YELLOW;
    }

    public HiredGun() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = DAMAGE;
        this.baseMagicNumber = this.magicNumber = MAGIC;
        this.exhaust = true;
        this.tags.add(AbstractHermitCard.Enums.DEADON);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), EnumPatch.HERMIT_GUN3));
        if (this.isDeadOn() && p.chosenClass != hermit.Enums.HERMIT) {
            this.TriggerDeadOnEffect(p, m);
        }
    }

    public void DeadOnEffect(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new Rugged(p, this.magicNumber), this.magicNumber));
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractDynamicCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (this.isDeadOnPos() && AbstractDungeon.player.chosenClass != hermit.Enums.HERMIT) {
            this.glowColor = AbstractDynamicCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.exhaust = false;
            this.upgradeName();
            this.upgradeDamage(UPGRADE_DAMAGE);
            this.upgradeMagicNumber(UPGRADE_MAGIC);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
package tisHermitBooster.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import hermit.actions.ComboAction;
import hermit.patches.EndOfTurnPatch;
import hermit.patches.VigorPatch;
import hermit.powers.BigShotPower;
import hermit.powers.ComboPower;
import hermit.powers.Concentration;
import hermit.powers.SnipePower;
import hermit.relics.BlackPowder;
import spireTogether.cards.CustomMultiplayerCard;

import static hermit.cards.AbstractHermitCard.deadOnThisTurn;

public abstract class AbstractHermitMultiplayerCard extends CustomMultiplayerCard {
    static boolean doneInit = false;
    public boolean trig_deadon = false;
    public int trig_times = 1;
    protected CardStrings cardStrings;

    public static void init() {
        doneInit = true;
    }

    public AbstractHermitMultiplayerCard(String id, String name, String img, int cost, String rawDescription, AbstractCard.CardType type, AbstractCard.CardColor color, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
        this.cardStrings = CardCrawlGame.languagePack.getCardStrings(cardID);
    }

    public AbstractHermitMultiplayerCard(String id, String img, int cost, AbstractCard.CardType type, AbstractCard.CardColor color, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target) {
        this(id, CardCrawlGame.languagePack.getCardStrings(id).NAME, img, cost, CardCrawlGame.languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity, target);
    }

    //Add Dead-On helpers to Multiplayer card base class.
    public boolean isDeadOn() {
        boolean conc = AbstractDungeon.player.hasPower(Concentration.POWER_ID);
        double hand_pos = (double)AbstractDungeon.player.hand.group.indexOf(this) + (double)0.5F;
        double hand_size = (double)AbstractDungeon.player.hand.size();
        double relative = Math.abs(hand_pos - hand_size / (double)2.0F);
        if (relative < (double)1.0F || conc) {
            this.trig_deadon = true;
        }

        return this.trig_deadon;
    }

    public boolean isDeadOnPos() {
        boolean conc = AbstractDungeon.player.hasPower(Concentration.POWER_ID);
        double hand_pos = (double)AbstractDungeon.player.hand.group.indexOf(this) + (double)0.5F;
        double hand_size = (double)AbstractDungeon.player.hand.size();
        double relative = Math.abs(hand_pos - hand_size / (double)2.0F);
        return relative < (double)1.0F || conc;
    }

    protected boolean onDeadOn() {
        int DeadOnTimes = this.DeadOnAmount();

        for(int a = 0; a < DeadOnTimes; ++a) {
            if (AbstractDungeon.player.hasPower(BigShotPower.POWER_ID)) {
                ++VigorPatch.isActive;
            }

            if (!this.dontTriggerOnUseCard) {
                if (deadOnThisTurn.size() > 0) {
                    deadOnThisTurn.set(deadOnThisTurn.size() - 1, true);
                }

                ++EndOfTurnPatch.deadon_counter;
            }
        }

        if (AbstractDungeon.player.hasPower(ComboPower.POWER_ID)) {
            ComboPower comb = (ComboPower)AbstractDungeon.player.getPower(ComboPower.POWER_ID);
            if (comb.uses < comb.amount) {
                ++comb.uses;
                if (!doneInit) {
                    init();
                }

                comb.flash();
                this.addToBot(new ComboAction(false, this));
            }
        }

        if (AbstractDungeon.player.hasPower(SnipePower.POWER_ID)) {
            AbstractDungeon.player.getPower(SnipePower.POWER_ID).flash();
        }

        return true;
    }

    public void TriggerDeadOnEffect(AbstractPlayer p, AbstractMonster m) {
        this.onDeadOn();
        int DeadOnTimes = this.DeadOnAmount();
        this.DeadOnEffectStacking(p, m, DeadOnTimes);

        for(int a = 0; a < DeadOnTimes; ++a) {
            this.DeadOnEffect(p, m);

            for(AbstractRelic c : AbstractDungeon.player.relics) {
                if (c.relicId.equals(BlackPowder.ID)) {
                    this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, c));
                    this.addToBot(new DamageAllEnemiesAction((AbstractCreature)null, DamageInfo.createDamageMatrix(2, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE, true));
                }
            }
        }

        this.addToTop(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, SnipePower.POWER_ID, 1));
    }

    public void DeadOnEffect(AbstractPlayer p, AbstractMonster m) {
    }

    public void DeadOnEffectStacking(AbstractPlayer p, AbstractMonster m, int val) {
    }

    public int DeadOnAmount() {
        int do_times = this.trig_times;
        if (AbstractDungeon.player.hasPower(SnipePower.POWER_ID)) {
            ++do_times;
        }

        return do_times;
    }
}

package tisHermitBooster.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import hermit.characters.hermit;

import static tisHermitBooster.tisHermitBoosterMod.makeID;

public class WoozyPotion extends BasePotion{
    public static final String ID = makeID("WoozyPotion");

    private static final Color LIQUID_COLOR = CardHelper.getColor(49, 202, 49);
    private static final Color HYBRID_COLOR = CardHelper.getColor(176, 48, 96);
    private static final Color SPOTS_COLOR = null;

    public WoozyPotion(){
        super(ID, 1, PotionRarity.COMMON, PotionSize.FAIRY, LIQUID_COLOR, HYBRID_COLOR, SPOTS_COLOR);
        playerClass = hermit.Enums.HERMIT;
        isThrown = true;
        targetRequired = true;
    }

    @Override
    public String getDescription() {
        return String.format(DESCRIPTIONS[0], potency);
    }

    @Override
    public void use(AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new WeakPower(target, potency, false), potency));
            addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new VulnerablePower(target, potency, false), potency));
            addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new FrailPower(target, potency, false), potency));
        }
    }

    @Override
    public void addAdditionalTips() {
        //Adding a tooltip for Strength
        this.tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.WEAK.NAMES[0]), GameDictionary.keywords.get(GameDictionary.WEAK.NAMES[0])));
        this.tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.VULNERABLE.NAMES[0]), GameDictionary.keywords.get(GameDictionary.VULNERABLE.NAMES[0])));
        this.tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.FRAIL.NAMES[0]), GameDictionary.keywords.get(GameDictionary.FRAIL.NAMES[0])));
    }
}
